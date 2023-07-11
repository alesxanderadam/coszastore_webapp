package alticshaw.com.coszastore.controllers;

import alticshaw.com.coszastore.entity.UserEntity;
import alticshaw.com.coszastore.payload.request.AuthRequest;
import alticshaw.com.coszastore.payload.response.BaseResponse;
import alticshaw.com.coszastore.payload.response.MessageResponse;
import alticshaw.com.coszastore.services.imp.AuthServiceImp;
import alticshaw.com.coszastore.utils.JwtHelper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    @Resource
    private AuthenticationManager authenticationManager;

    @Resource
    private JwtHelper jwtHelper;

    @Resource
    private BaseResponse baseResponse = new BaseResponse();

    @Resource
    private MessageResponse messageResponse = new MessageResponse();
    @Resource
    private AuthServiceImp authServiceImp;
    @PostMapping("/signIn")
    public ResponseEntity<?> signIn(@RequestParam String email, @RequestParam String password){
            UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(email, password);
            authenticationManager.authenticate(token);
            String jwt = jwtHelper.generateToken("Tui là subject token khi login thành công nè");
            baseResponse.setStatusCode(200);
            baseResponse.setData(jwt);
            baseResponse.setMessage(messageResponse.success());
            return new ResponseEntity<>(baseResponse, HttpStatus.OK);
    }

    @PostMapping("/signUp")
    public ResponseEntity<?> signUp(@Valid AuthRequest request, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            baseResponse.setStatusCode(400);
            baseResponse.setMessage("Validation error");

            for (FieldError error : bindingResult.getFieldErrors()) {
                baseResponse.setMessage(baseResponse.getMessage() + " - " + error.getDefaultMessage());
            }

            return new ResponseEntity<>(baseResponse, HttpStatus.BAD_REQUEST);
        } else {
            UserEntity user = authServiceImp.addUser(request);
            baseResponse.setStatusCode(200);
            baseResponse.setMessage(messageResponse.success());
            baseResponse.setData(user);
            return new ResponseEntity<>(baseResponse, HttpStatus.OK);
        }
    }
}
