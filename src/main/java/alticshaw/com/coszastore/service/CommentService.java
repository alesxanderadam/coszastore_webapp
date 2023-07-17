package alticshaw.com.coszastore.service;

import alticshaw.com.coszastore.entity.CommentEntity;
import alticshaw.com.coszastore.exception.CommentNotFoundException;
import alticshaw.com.coszastore.exception.ValidationCustomException;
import alticshaw.com.coszastore.payload.response.CommentResponse;
import alticshaw.com.coszastore.repository.CommentRepository;
import alticshaw.com.coszastore.service.imp.CommentServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CommentService implements CommentServiceImp {
    private final CommentRepository commentRepository;

    @Override
    public List<CommentResponse> getAllComments(int blogId) {
        List<CommentEntity> commentEntityList = commentRepository.findAllByBlogId(blogId);
        List<CommentResponse> commentResponseList = new ArrayList<>();
        for (CommentEntity data : commentEntityList) {
            CommentResponse comment = new CommentResponse(
                    data.getContent(),
                    data.getEmail(),
                    data.getWebsite(),
                    data.getName(),
                    data.getCreatedTime(),
                    data.getUpdatedTime(),
                    data.getBlog()
            );
            commentResponseList.add(comment);
        }
        return commentResponseList;
    }

    @Override
    public boolean post(CommentEntity comment, BindingResult commentBindingResult) {
        if (!commentBindingResult.hasErrors()) {
            comment.setCreatedTime(new Timestamp(System.currentTimeMillis()));
            commentRepository.save(comment);
            return true;
        } else {
            throw new ValidationCustomException(commentBindingResult);
        }
    }

    @Override
    public boolean edit(CommentEntity comment, BindingResult commentBindingResult) {
        if (!commentBindingResult.hasErrors()) {
            Optional<CommentEntity> commentOptional = commentRepository.findById(comment.getId());
            commentOptional.orElseThrow(() ->
                    new CommentNotFoundException("Comment not found - Can not edit!"));
            commentRepository.updateComment(
                    comment.getContent(),
                    comment.getEmail(), 
                    comment.getWebsite(),
                    comment.getName(),
                    comment.getBlog(),
                    comment.getId()
            );
            return true;
        } else {
            throw new ValidationCustomException(commentBindingResult);
        }
    }

    @Override
    public boolean delete(Integer id) {
        try {
            commentRepository.findById(id);
        } catch (Exception e) {
            throw new CommentNotFoundException("Helo");
        }
        return true;
    }



    @Autowired
    public CommentService(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }
}
