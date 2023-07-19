package alticshaw.com.coszastore.controller;

import alticshaw.com.coszastore.payload.request.BlogRequest;
import alticshaw.com.coszastore.payload.response.BaseResponse;
import alticshaw.com.coszastore.service.imp.BlogServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
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

    @PostMapping(value = "/post", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<?> postBlog(@ModelAttribute @Valid BlogRequest blogRequest, BindingResult bindingResult) {
        boolean isSuccess = blogServiceImp.post(blogRequest, bindingResult);
        BaseResponse response = new BaseResponse();
        response.setStatusCode(200);
        response.setMessage("Post blog successfully!");
        response.setData(isSuccess);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/list/user/{userId}")
    public ResponseEntity<?> getBlogsByUser(@PathVariable String userId) {
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
