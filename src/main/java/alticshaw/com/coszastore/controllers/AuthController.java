package alticshaw.com.coszastore.controllers;

import alticshaw.com.coszastore.entity.UserEntity;
import alticshaw.com.coszastore.payload.request.SignInRequest;
import alticshaw.com.coszastore.payload.request.SignUpRequest;
import alticshaw.com.coszastore.payload.response.BaseResponse;
import alticshaw.com.coszastore.payload.response.MessageResponse;
import alticshaw.com.coszastore.repository.UserRepository;
import alticshaw.com.coszastore.services.imp.AuthServiceImp;
import alticshaw.com.coszastore.utils.JwtHelper;
import io.jsonwebtoken.Claims;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
//@CrossOrigin("*")
@RestController
@RequestMapping("/api/auth")
public class AuthController {
    @Resource
    private AuthenticationManager authenticationManager;

    @Resource
    private JwtHelper jwtHelper;

    @Resource
    private BaseResponse baseResponse;

    @Resource
    private MessageResponse messageResponse = new MessageResponse();

    @Resource
    private AuthServiceImp authServiceImp;

    @Resource
    private UserRepository userRepository;

    @PostMapping("/signIn")
    public ResponseEntity<?> signIn(@RequestBody @Valid SignInRequest request){
        try{
            UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword());
            authenticationManager.authenticate(token);
            String jwt = jwtHelper.generateToken(request.getEmail());
            baseResponse.setStatusCode(200);
            baseResponse.setData(jwt);
            baseResponse.setMessage(messageResponse.success());
            return new ResponseEntity<>(baseResponse, HttpStatus.OK);
        }catch (AuthenticationException e){
            baseResponse.setStatusCode(401);
            baseResponse.setMessage("Invalid email or password");
            return new ResponseEntity<>(baseResponse, HttpStatus.UNAUTHORIZED);
        }

    }

    @PostMapping("/signUp")
    public ResponseEntity<?> signUp(@Valid SignUpRequest request, BindingResult bindingResult) {
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

    @GetMapping("/infor")
    public ResponseEntity<?> getInforUser(@RequestParam String token){
        Claims claims = jwtHelper.decodeToken(token);
        if (claims != null) {
            String email = claims.get("email").toString();
            UserEntity user = userRepository.findByEmail(email);
            baseResponse.setStatusCode(200);
            baseResponse.setMessage(messageResponse.success());
            baseResponse.setData(user);
            return new ResponseEntity<>(baseResponse,HttpStatus.OK);
        }
        return new ResponseEntity<>("Not authorization to appcet ",HttpStatus.UNAUTHORIZED);
    }
}
