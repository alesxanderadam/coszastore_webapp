package alticshaw.com.coszastore.service.imp;

import alticshaw.com.coszastore.entity.CommentEntity;
import alticshaw.com.coszastore.payload.response.CommentResponse;
import org.springframework.validation.BindingResult;

import java.util.List;

public interface CommentServiceImp {
    List<CommentResponse> getAllComments();
    boolean post(CommentEntity comment, BindingResult commentBindingResult);
    boolean edit(CommentEntity comment, BindingResult commentBindingResult);
    boolean delete(Integer id);
}
