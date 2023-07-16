package alticshaw.com.coszastore.service.imp;

import alticshaw.com.coszastore.entity.CommentEntity;
import alticshaw.com.coszastore.payload.request.CommentRequest;
import alticshaw.com.coszastore.payload.response.CommentResponse;
import org.springframework.validation.BindingResult;

import java.util.List;

public interface CommentServiceImp {
    List<CommentResponse> getAllComments();
    void post(CommentEntity comment, BindingResult commentBindingResult);
    void edit(CommentEntity comment, BindingResult commentBindingResult);
    void delete(Integer id);
}
