package alticshaw.com.coszastore.service;

import alticshaw.com.coszastore.dto.BlogDto;
import alticshaw.com.coszastore.dto.UserResponseWithBlogDto;
import alticshaw.com.coszastore.entity.BlogEntity;
import alticshaw.com.coszastore.entity.BlogTagEntity;
import alticshaw.com.coszastore.entity.TagEntity;
import alticshaw.com.coszastore.entity.UserEntity;
import alticshaw.com.coszastore.entity.ids.BlogTagIds;
import alticshaw.com.coszastore.exception.*;
import alticshaw.com.coszastore.payload.request.BlogRequest;
import alticshaw.com.coszastore.payload.response.BlogResponse;
import alticshaw.com.coszastore.payload.response.TagResponse;
import alticshaw.com.coszastore.repository.*;
import alticshaw.com.coszastore.service.imp.BlogServiceImp;
import alticshaw.com.coszastore.service.imp.FileStorageServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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
            // Blog request: userId
            UserEntity user = userRepository
                    .findById(blogRequest.getUser_id())
                    .orElseThrow(() ->
                            new UserNotFoundException("User not found with id: " + blogRequest.getUser_id()));
            //Blog request: tags
            List<TagEntity> tags = tagRepository.findAllById(blogRequest.getTagIdSet());
            if (!tags.isEmpty()) {
                String imageName = saveNullOrValidImage(blogRequest.getImage());
                BlogEntity blog = new BlogEntity();
                blog.setContent(blogRequest.getContent());
                blog.setImage(imageName);
                blog.setUser(user);
                blog.setTitle(blogRequest.getTitle());
                blog.setCreatedTime(new Timestamp(System.currentTimeMillis()));
                blogRepository.save(blog);

                Set<BlogTagEntity> blogTags = getBlogTagEntitySet(tags, blog);
                blogTagRepository.saveAll(blogTags);
                blog.setBlogTags(blogTags);
                blogRepository.save(blog);
            } else {
                throw new TagNotFoundException("Can not find any tag!");
            }
            return true;
        } else {
            throw new CustomValidationException(bindingResult);
        }
    }

    @Override
    public BlogResponse getAllResponseBlogs(Integer pageNo, Integer pageSize) {
        Pageable paging = PageRequest.of(pageNo, pageSize);
        Page<BlogEntity> pagedResult = blogRepository.findAll(paging);
        List<BlogDto> blogs = new ArrayList<>();
        int currentPage = 0;
        int totalItems = 0;
        int totalPages = 0;
        if (pagedResult.hasContent()) {
            blogs = pagedResult.getContent().stream()
                    .map(data -> new BlogDto(
                            data.getId(),
                            (data.getImage() != null) ?
                                    fileStorageServiceImp.getImageDirectoryPath() + "\\" + data.getImage() : null,
                            data.getContent(),
                            new UserResponseWithBlogDto(data.getUser().getId(), data.getUser().getUsername()),
                            getListTagResponseByBlogId(data.getId()),
                            data.getCreatedTime(),
                            data.getUpdatedTime(),
                            commentRepository.countByBlogId(data.getId())
                    ))
                    .collect(Collectors.toList());
            currentPage = pagedResult.getNumber();
            totalItems = pagedResult.getSize();
            totalPages = pagedResult.getTotalPages();
        }
        return new BlogResponse(blogs, currentPage, totalItems, totalPages);
    }

    @Override
    public List<TagResponse> getListTagResponseByBlogId(int id) {
        return blogTagRepository.findTagsByBlogId(id).stream()
                .map(data -> new TagResponse().mapTagEntityToTagResponse(data))
                .collect(Collectors.toList());
    }

    @Override
    public boolean deleteById(String id) {
        try {
            int blogId = Integer.parseInt(id);
            BlogEntity blogEntity = blogRepository.findById(blogId)
                    .orElseThrow(() -> new BlogNotFoundException("Blog not found with id: " + id));
            blogRepository.delete(blogEntity);
            if (blogEntity.getImage() != null) {
                fileStorageServiceImp.deleteByName(blogEntity.getImage());
            }
            return true;
        } catch (NumberFormatException e) {
            throw new CustomIllegalArgumentException("Invalid blog id: " + id);

        }
    }

    @Override
    public boolean edit(String id, BlogRequest blogRequest, BindingResult bindingResult) {
        try {
            int blogId = Integer.parseInt(id);
            if (!bindingResult.hasErrors()) {
                BlogEntity blog = blogRepository.findById(blogId).orElseThrow(() ->
                        new BlogNotFoundException("Blog not found with id: " + blogId));

                UserEntity user = userRepository
                        .findById(blogRequest.getUser_id())
                        .orElseThrow(() ->
                                new UserNotFoundException("User not found with id: " + blogRequest.getUser_id()));
                List<TagEntity> tags = tagRepository.findAllById(blogRequest.getTagIdSet());
                if (!tags.isEmpty()) {
                    if (blog.getImage() != null) {
                        fileStorageServiceImp.deleteByName(blog.getImage());
                    }
                    String imageName = saveNullOrValidImage(blogRequest.getImage());
                    blog.setContent(blogRequest.getContent());
                    blog.setImage(imageName);
                    blog.setUser(user);
                    blog.setTitle(blogRequest.getTitle());
                    blog.setUpdatedTime(new Timestamp(System.currentTimeMillis()));
                    Set<BlogTagEntity> blogTags = getBlogTagEntitySet(tags, blog);

                    blogTagRepository.deleteAllByBlog(blog);

                    blogTagRepository.saveAll(blogTags);
                    blog.setBlogTags(blogTags);
                    blogRepository.save(blog);
                } else {
                    throw new TagNotFoundException("Can not found any tag!");
                }
                return true;
            } else {
                throw new CustomValidationException(bindingResult);
            }
        } catch (NumberFormatException e) {
            throw new CustomIllegalArgumentException("Invalid blog id: " + id);
        }
    }

    private String saveNullOrValidImage(MultipartFile image) {
        String imageName = null;
        if (!(image == null || image.isEmpty())) {
            if (fileStorageServiceImp.isImage(image)) {
                imageName = fileStorageServiceImp.save(image);
            } else {
                throw new NotImageException(image.getOriginalFilename() + " is not an image!");
            }
        }
        return imageName;
    }

    private Set<BlogTagEntity> getBlogTagEntitySet(List<TagEntity> tags, BlogEntity blog) {
        return tags.stream()
                .map(tag -> {
                    BlogTagEntity blogTagEntity = new BlogTagEntity();
                    BlogTagIds blogTagIds = new BlogTagIds();

                    blogTagIds.setBlogId(blog.getId());
                    blogTagIds.setTagId(tag.getId());

                    blogTagEntity.setIds(blogTagIds);
                    blogTagEntity.setBlog(blog);
                    blogTagEntity.setTag(tag);

                    return blogTagEntity;
                })
                .collect(Collectors.toSet());
    }
}
