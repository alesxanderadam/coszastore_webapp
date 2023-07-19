package alticshaw.com.coszastore.service;

import alticshaw.com.coszastore.exception.CustomValidationException;
import alticshaw.com.coszastore.payload.request.BlogRequest;
import alticshaw.com.coszastore.repository.BlogRepository;
import alticshaw.com.coszastore.service.imp.BlogServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

@Service
public class BlogService implements BlogServiceImp {
    private final BlogRepository blogRepository;

    @Autowired
    public BlogService(BlogRepository blogRepository) {
        this.blogRepository = blogRepository;
    }

    @Override
    public boolean post(BlogRequest blogRequest, BindingResult bindingResult) {
        if (!bindingResult.hasErrors()) {

        } else {
            throw new CustomValidationException(bindingResult);
        }
        return false;
    }
}
