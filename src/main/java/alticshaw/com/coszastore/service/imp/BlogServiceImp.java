package alticshaw.com.coszastore.service.imp;

import alticshaw.com.coszastore.payload.request.BlogRequest;
import alticshaw.com.coszastore.payload.response.BlogResponse;
import alticshaw.com.coszastore.payload.response.TagResponse;
import org.springframework.validation.BindingResult;

import java.util.List;

public interface BlogServiceImp {
    boolean post(BlogRequest blogRequest, BindingResult bindingResult);
    List<BlogResponse> getAllBlogs();
    List<TagResponse> getTagsByBlogId(int blogId);
}
