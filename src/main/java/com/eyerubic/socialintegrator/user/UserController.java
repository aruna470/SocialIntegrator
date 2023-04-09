package com.eyerubic.socialintegrator.user;

import javax.annotation.Resource;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.eyerubic.socialintegrator.Constants;
import com.eyerubic.socialintegrator.helpers.AppLogger;
import com.eyerubic.socialintegrator.helpers.ContextData;

@RestController
public class UserController {
    
    @Autowired
    private UserService userService;

    @Resource(name = "requestScopedBeanAppLogger")
    AppLogger appLogger;

    @Resource(name = "requestScopedBeanContextData")
    ContextData contextData;

    @PostMapping(value="/user/signup")
    public ResponseEntity<SignupDTO> signup(@Valid @RequestBody SignupDTO signupDTO) {
        SignupDTO resSignupDto = userService.signup(signupDTO);
        appLogger.writeActivityLog("User signup successfully. Email:" + signupDTO.getEmail(), Constants.ACT_SIGNUP);
        return new ResponseEntity<>(resSignupDto, HttpStatus.OK);
    }

    @PostMapping(value="/user/verifyCode")
    public ResponseEntity<VerifyCodeDTO> verifyCode(@Valid @RequestBody VerifyCodeDTO verifyCodeDTO) {
        userService.verifyCode(verifyCodeDTO);
        appLogger.writeActivityLog("User successfully verified the code. Email:" + verifyCodeDTO.getEmail(), Constants.ACT_SIGNUP);
        return new ResponseEntity<>(verifyCodeDTO, HttpStatus.OK);
    }

    @PostMapping(value="/user/signin")
    public ResponseEntity<SigninDTO> signin(@Valid @RequestBody SigninDTO signinDTO) {
        SigninDTO resSigninDTO = userService.signin(signinDTO);
        appLogger.writeActivityLog("User successfully signin. Email:" + resSigninDTO.getEmail(), Constants.ACT_SIGNIN);
        return new ResponseEntity<>(resSigninDTO, HttpStatus.OK);
    }

    @PostMapping(value="/user/forgotPassword")
    public ResponseEntity<ForgotPasswordDTO> forgotPassword(@Valid @RequestBody ForgotPasswordDTO forgotPwDTO) {
        ForgotPasswordDTO resForgotPwDTO = userService.forgotPassword(forgotPwDTO);
        appLogger.writeActivityLog("Verification code sent for forgot password. Email:" + forgotPwDTO.getEmail(), Constants.ACT_FPW);
        return new ResponseEntity<>(resForgotPwDTO, HttpStatus.OK);
    }

    @PostMapping(value="/user/resetPassword")
    public ResponseEntity<ResetPasswordDTO> resetPassword(@Valid @RequestBody ResetPasswordDTO resetPwDTO) {
        userService.resetPassword(resetPwDTO);
        appLogger.writeActivityLog("Password reset success. Email:" + resetPwDTO.getEmail(), Constants.ACT_FPW);
        return new ResponseEntity<>(resetPwDTO, HttpStatus.OK);
    }

    @PostMapping(value="/user/changePassword")
    public ResponseEntity changePassword(@Valid @RequestBody ChangePasswordDTO changePwDTO) { //NOSONAR
        userService.changePassword(changePwDTO, Integer.parseInt(contextData.getUserId()));
        appLogger.writeActivityLog("Password change success. User id:" + contextData.getUserId(), Constants.ACT_CPW);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
