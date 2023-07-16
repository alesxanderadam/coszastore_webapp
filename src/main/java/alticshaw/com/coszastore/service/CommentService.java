package alticshaw.com.coszastore.service;

import alticshaw.com.coszastore.entity.CommentEntity;
import alticshaw.com.coszastore.payload.response.CommentResponse;
import alticshaw.com.coszastore.repository.CommentRepository;
import alticshaw.com.coszastore.service.imp.CommentServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import java.util.List;

@Service
public class CommentService implements CommentServiceImp {
    private final CommentRepository commentRepository;

    @Override
    public List<CommentResponse> getAllComments() {
        return null;
    }

    @Override
    public void post(CommentEntity comment, BindingResult commentBindingResult) {
        if (!commentBindingResult.hasErrors()) {

        } else {

        }
    }

    @Override
    public void edit(CommentEntity comment, BindingResult commentBindingResult) {

    }

    @Override
    public void delete(Integer id) {

    }



    @Autowired
    public CommentService(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }
}
