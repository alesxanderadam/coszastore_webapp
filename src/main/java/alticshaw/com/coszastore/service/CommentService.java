package alticshaw.com.coszastore.service;

import alticshaw.com.coszastore.entity.BlogEntity;
import alticshaw.com.coszastore.entity.CommentEntity;
import alticshaw.com.coszastore.exception.*;
import alticshaw.com.coszastore.payload.request.CommentRequest;
import alticshaw.com.coszastore.payload.response.CommentResponse;
import alticshaw.com.coszastore.repository.BlogRepository;
import alticshaw.com.coszastore.repository.CommentRepository;
import alticshaw.com.coszastore.service.imp.CommentServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CommentService implements CommentServiceImp {
    private final CommentRepository commentRepository;
    private final BlogRepository blogRepository;

    @Autowired
    public CommentService(CommentRepository commentRepository, BlogRepository blogRepository) {
        this.commentRepository = commentRepository;
        this.blogRepository = blogRepository;
    }

    @Override
    public List<CommentResponse> getAllComments(String blogId) {
        try {
            int validBlogId = Integer.parseInt(blogId);
            isExistedBlog(validBlogId);
            List<CommentEntity> commentEntityList = commentRepository.findAllByBlogId(validBlogId);
            return commentEntityList.stream()
                    .map(data -> new CommentResponse().mapCommentEntityToCommentResponse(data))
                    .collect(Collectors.toList());
        } catch (NumberFormatException e) {
            throw new CustomIllegalArgumentException("Illegal blog id: + " + blogId);
        }
    }

    @Override
    public boolean post(CommentRequest comment, BindingResult commentBindingResult) {
        if (!commentBindingResult.hasErrors()) {
            Optional<BlogEntity> blogOptional = isExistedBlog(comment.getBlogId());
            System.out.println(comment);
            CommentEntity commentEntity = new CommentEntity(
                    comment.getContent(),
                    comment.getEmail(),
                    comment.getName(),
                    comment.getWebsite(),
                    blogOptional.get(),
                    new Timestamp(System.currentTimeMillis())
            );
            commentRepository.save(commentEntity);
            return true;
        } else {
            throw new CustomValidationException(commentBindingResult);
        }
    }

    @Override
    public boolean edit(String id, CommentRequest comment, BindingResult commentBindingResult) {
        try {
            int commentId = Integer.parseInt(id);
            if (!commentBindingResult.hasErrors()) {
                isExistedComment(commentId);
                Optional<BlogEntity> optionalBlog = isExistedBlog(comment.getBlogId());

                commentRepository.updateComment(
                        comment.getContent(),
                        comment.getEmail(),
                        comment.getWebsite(),
                        comment.getName(),
                        optionalBlog.get(),
                        commentId
                );
                return true;
            } else {
                throw new CustomValidationException(commentBindingResult);
            }
        } catch (NumberFormatException e) {
            throw new CustomIllegalArgumentException("Comment not found with id: " + id);
        }
    }

    @Override
    public boolean delete(String id) {
        try {
            int commentId = Integer.parseInt(id);
            isExistedComment(commentId);
            commentRepository.deleteById(commentId);
            return true;
        } catch (NumberFormatException e) {
            throw new CustomIllegalArgumentException("Illegal comment id: " + id);
        }
    }

    private void isExistedComment(int commentId) {
        Optional<CommentEntity> commentOptional = commentRepository.findById(commentId);
        commentOptional.orElseThrow(() ->
                new CommentNotFoundException("Comment not found with id: " + commentId));
    }

    private Optional<BlogEntity> isExistedBlog(int blogId) {
        Optional<BlogEntity> optionalBlog = blogRepository.findById(blogId);
        optionalBlog.orElseThrow(() -> new BlogNotFoundException("Blog not found with id: " + blogId));
        return optionalBlog;
    }
}
