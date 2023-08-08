package alticshaw.com.coszastore.service.imp;

import alticshaw.com.coszastore.payload.request.CommentRequest;
import alticshaw.com.coszastore.payload.response.CommentResponse;
import org.springframework.validation.BindingResult;

import java.util.List;

public interface CommentServiceImp {
    List<CommentResponse> getAllComments(String blogId);
    boolean post(CommentRequest comment, BindingResult commentBindingResult);
    boolean edit(String id,CommentRequest comment, BindingResult commentBindingResult);
    boolean delete(String id);
}
