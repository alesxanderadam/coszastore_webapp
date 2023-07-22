package alticshaw.com.coszastore.service;

import alticshaw.com.coszastore.dto.UserDto;
import alticshaw.com.coszastore.entity.BlogEntity;
import alticshaw.com.coszastore.entity.BlogTagEntity;
import alticshaw.com.coszastore.entity.UserEntity;
import alticshaw.com.coszastore.exception.CustomValidationException;
import alticshaw.com.coszastore.exception.TagNotFoundException;
import alticshaw.com.coszastore.exception.UserNotFoundException;
import alticshaw.com.coszastore.payload.request.BlogRequest;
import alticshaw.com.coszastore.payload.response.BlogResponse;
import alticshaw.com.coszastore.payload.response.TagResponse;
import alticshaw.com.coszastore.repository.*;
import alticshaw.com.coszastore.service.imp.BlogServiceImp;
import alticshaw.com.coszastore.service.imp.FileStorageServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.web.multipart.MultipartFile;

import java.sql.Timestamp;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class BlogService implements BlogServiceImp {
    private final BlogRepository blogRepository;
    private final UserRepository userRepository;
    private final FileStorageServiceImp fileStorageServiceImp;
    private final BlogTagRepository blogTagRepository;
    private final CommentRepository commentRepository;
    private final TagRepository tagRepository;
    @Autowired
    public BlogService(BlogRepository blogRepository,
                       UserRepository userRepository,
                       FileStorageServiceImp fileStorageServiceImp,
                       BlogTagRepository blogTagRepository,
                       CommentRepository commentRepository,
                       TagRepository tagRepository
    ) {
        this.blogRepository = blogRepository;
        this.userRepository = userRepository;
        this.fileStorageServiceImp = fileStorageServiceImp;
        this.blogTagRepository = blogTagRepository;
        this.commentRepository = commentRepository;
        this.tagRepository = tagRepository;
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
                imageName = fileStorageServiceImp.save(image);
            }
            BlogEntity blog = new BlogEntity();

            Set<BlogTagEntity> blogTagEntitySet = new HashSet<>();
            Arrays.stream(blogRequest.getTagIdArray())
                    .filter(tagId -> {
                        boolean isExisted = tagRepository.existsById(tagId);
                        if (!isExisted) {
                            throw new TagNotFoundException("Tag not found with id: " + tagId);
                        }
                        return true;
                    })
                    .forEach(tagId -> {
                        BlogTagEntity blogTagEntity = new BlogTagEntity();
                        blogTagEntity.setTag(tagRepository.findById(tagId));
                        blogTagEntity.setBlog(blog);
                        blogTagEntitySet.add(blogTagEntity);
                    });
            blog.setContent(blogRequest.getContent());
            blog.setImage(imageName);
            blog.setUser(user);
            blog.setTitle(blogRequest.getTitle());
            blog.setCreatedTime(new Timestamp(System.currentTimeMillis()));
            blog.setBlogTags(blogTagEntitySet);

            blogRepository.save(blog);
            return true;
        } else {
            throw new CustomValidationException(bindingResult);
        }
    }

    @Override
    public List<BlogResponse> getAllBlogs() {
        List<BlogEntity> blogEntity = blogRepository.findAll();
        return blogEntity.stream()
                .map(data -> new BlogResponse(
                        data.getImage(),
                        data.getContent(),
                        new UserDto(data.getUser().getId(), data.getUser().getUsername()),
                        getTagsByBlogId(data.getId()),
                        data.getCreatedTime(),
                        data.getUpdatedTime(),
                        commentRepository.countByBlogId(data.getId())
                ))
                .collect(Collectors.toList());
    }

    @Override
    public List<TagResponse> getTagsByBlogId(int id) {
        return blogTagRepository.findTagsByBlogId(id).stream()
                .map(data -> new TagResponse().mapTagEntityToTagResponse(data))
                .collect(Collectors.toList());
    }
}
