package alticshaw.com.coszastore.service;

import alticshaw.com.coszastore.entity.CommentEntity;
import alticshaw.com.coszastore.exception.ValidationCustomException;
import alticshaw.com.coszastore.payload.response.CommentResponse;
import alticshaw.com.coszastore.repository.CommentRepository;
import alticshaw.com.coszastore.service.imp.CommentServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import java.sql.Timestamp;
import java.util.List;

@Service
public class CommentService implements CommentServiceImp {
    private final CommentRepository commentRepository;

    @Override
    public List<CommentResponse> getAllComments(int blogId) {

        return commentRepository.findAllByBlogId(blogId) ;
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
        return true;
    }



    @Autowired
    public CommentService(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }
}
