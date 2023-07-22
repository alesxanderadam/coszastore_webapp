package alticshaw.com.coszastore.service.imp;

import alticshaw.com.coszastore.payload.response.TagResponse;
import org.springframework.validation.BindingResult;

import java.util.List;

public interface TagServiceImp {
    List<TagResponse> getAllTags();
    boolean add(String tagName);
}
