package alticshaw.com.coszastore.controller;

import alticshaw.com.coszastore.payload.request.BlogRequest;
import alticshaw.com.coszastore.service.imp.BlogServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/blog")
public class BlogController {
    private final BlogServiceImp blogServiceImp;

    @Autowired
    public BlogController(BlogServiceImp blogServiceImp) {
        this.blogServiceImp = blogServiceImp;
    }

    @PostMapping("/post")
    public ResponseEntity<?> postBlog(@RequestPart @Valid BlogRequest blogRequest, BindingResult bindingResult) {

        return new ResponseEntity<>("", HttpStatus.OK);
    }

    @GetMapping("/list/{userid}")
    public ResponseEntity<?> getBlogsByUser(@PathVariable String userid) {
        return new ResponseEntity<>("", HttpStatus.OK);
    }

    @GetMapping("/list/all")
    public ResponseEntity<?> getAllBlogs() {
        return new ResponseEntity<>("", HttpStatus.OK);
    }

    @PutMapping("/edit/{id}")
    public ResponseEntity<?> editBlog(@PathVariable String id) {
        return new ResponseEntity<>("", HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteBlog(@PathVariable String id) {
        return new ResponseEntity<>("", HttpStatus.OK);
    }
}
