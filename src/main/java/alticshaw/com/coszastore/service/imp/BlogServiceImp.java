package alticshaw.com.coszastore.service.imp;

import alticshaw.com.coszastore.payload.request.BlogRequest;
import org.springframework.validation.BindingResult;

public interface BlogServiceImp {
    boolean post(BlogRequest blogRequest, BindingResult bindingResult);
}
