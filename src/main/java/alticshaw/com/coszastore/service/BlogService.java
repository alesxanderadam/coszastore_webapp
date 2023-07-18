package alticshaw.com.coszastore.service;

import alticshaw.com.coszastore.repository.BlogRepository;
import alticshaw.com.coszastore.service.imp.BlogServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BlogService implements BlogServiceImp {
    private final BlogRepository blogRepository;

    @Autowired
    public BlogService(BlogRepository blogRepository) {
        this.blogRepository = blogRepository;
    }
}
