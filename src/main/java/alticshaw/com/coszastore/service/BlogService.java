package alticshaw.com.coszastore.service;

import alticshaw.com.coszastore.entity.BlogEntity;
import alticshaw.com.coszastore.entity.UserEntity;
import alticshaw.com.coszastore.exception.CustomValidationException;
import alticshaw.com.coszastore.exception.UserNotFoundException;
import alticshaw.com.coszastore.payload.request.BlogRequest;
import alticshaw.com.coszastore.repository.BlogRepository;
import alticshaw.com.coszastore.repository.UserRepository;
import alticshaw.com.coszastore.service.imp.BlogServiceImp;
import alticshaw.com.coszastore.service.imp.FileStorageServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.web.multipart.MultipartFile;

import java.sql.Timestamp;
import java.util.Optional;

@Service
public class BlogService implements BlogServiceImp {
    private final BlogRepository blogRepository;
    private final UserRepository userRepository;
    private final FileStorageServiceImp fileStorageServiceImp;

    @Autowired
    public BlogService(BlogRepository blogRepository,
                       UserRepository userRepository,
                       FileStorageServiceImp fileStorageServiceImp) {
        this.blogRepository = blogRepository;
        this.userRepository = userRepository;
        this.fileStorageServiceImp = fileStorageServiceImp;
    }

    @Override
    public boolean post(BlogRequest blogRequest, BindingResult bindingResult) {
        if (!bindingResult.hasErrors()) {
            Optional<UserEntity> userOptional = userRepository.findById(blogRequest.getUserId());
            UserEntity user = userOptional.orElseThrow(() ->
                    new UserNotFoundException("User not found with id: " + blogRequest.getUserId()));

            MultipartFile image = blogRequest.getImage();
            String imageName = null;
            if (image != null && fileStorageServiceImp.isImage(image)) {
                fileStorageServiceImp.save(image);
                imageName = image.getOriginalFilename();
            }
            BlogEntity blog = new BlogEntity();
            blog.setContent(blogRequest.getContent());
            blog.setImage(imageName);
            blog.setUser(user);
            blog.setTitle(blogRequest.getTitle());
            blog.setCreatedTime(new Timestamp(System.currentTimeMillis()));
            blogRepository.save(blog);
            return true;
        } else {
            throw new CustomValidationException(bindingResult);
        }
    }
}
