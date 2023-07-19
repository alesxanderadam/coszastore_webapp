package alticshaw.com.coszastore.controller;

import alticshaw.com.coszastore.payload.request.CommentRequest;
import alticshaw.com.coszastore.payload.response.BaseResponse;
import alticshaw.com.coszastore.service.imp.CommentServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/comment")
public class CommentController {
    private final CommentServiceImp commentServiceImp;

    @Autowired
    public CommentController(CommentServiceImp commentServiceImp) {
        this.commentServiceImp = commentServiceImp;
    }

    @PostMapping("/post")
    public ResponseEntity<?> postComment(
            @RequestBody @Valid CommentRequest comment,
            BindingResult commentBidingResult
    ) {
        boolean isSuccess = commentServiceImp.post(comment, commentBidingResult);
        BaseResponse response = new BaseResponse();
        response.setStatusCode(200);
        response.setMessage("Post comment successfully!");
        response.setData(isSuccess);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/list/{blogId}")
    public ResponseEntity<?> getAllComments(@PathVariable String blogId) {
        BaseResponse response = new BaseResponse();
        response.setStatusCode(200);
        response.setMessage("List all comment!");
        response.setData(commentServiceImp.getAllComments(blogId));
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteComment(@PathVariable String id) {
        boolean isSuccess = commentServiceImp.delete(id);
        BaseResponse response = new BaseResponse();
        response.setStatusCode(200);
        response.setMessage("Delete comment with id: " + id + " successfully");
        response.setData(isSuccess);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping("/edit/{id}")
    public ResponseEntity<?> editComment(
            @PathVariable String id,
            @RequestBody @Valid CommentRequest newComment,
            BindingResult commentBindingResult
    ) {
        boolean isSuccess = commentServiceImp.edit(id, newComment, commentBindingResult);
        BaseResponse response = new BaseResponse();
        response.setStatusCode(200);
        response.setMessage("Update comment successfully!");
        response.setData(isSuccess);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}
