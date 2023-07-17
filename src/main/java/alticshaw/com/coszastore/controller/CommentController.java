package alticshaw.com.coszastore.controller;

import alticshaw.com.coszastore.entity.CommentEntity;
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
            @RequestBody @Valid CommentEntity comment,
            BindingResult commentBidingResult
    ) {
        boolean isSuccess = commentServiceImp.post(comment, commentBidingResult);
        BaseResponse response = new BaseResponse();
        response.setStatusCode(200);
        response.setMessage("Post comment successfully!");
        response.setData(isSuccess);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/{blogId}")
    public ResponseEntity<?> getAllComments(@PathVariable int blogId) {
        BaseResponse response = new BaseResponse();
        response.setStatusCode(200);
        response.setMessage("List all comment!");
        response.setData(commentServiceImp.getAllComments());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/delete/{id}")
    public ResponseEntity<?> deleteComment(@PathVariable Integer id) {
        return new ResponseEntity<>("", HttpStatus.OK);
    }

    @PostMapping("/edit")
    public ResponseEntity<?> editComment(
            @RequestBody @Valid CommentEntity comment,
            BindingResult commentBindingResult
    ) {
        boolean isSuccess = commentServiceImp.edit(comment, commentBindingResult);
        BaseResponse response = new BaseResponse();
        response.setStatusCode(200);
        response.setMessage("Update comment successfully!");
        response.setData(isSuccess);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}
