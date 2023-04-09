package com.eyerubic.socialintegrator.user;
 
import java.security.SecureRandom;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.transaction.Transactional;

import org.springframework.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.eyerubic.socialintegrator.Constants;
import com.eyerubic.socialintegrator.exception.CustomException;
import com.eyerubic.socialintegrator.helpers.Jwt;
import com.eyerubic.socialintegrator.helpers.Mail;
import com.eyerubic.socialintegrator.helpers.Util;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Service
@Transactional
public class UserService {
    
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private Jwt jwt;

    @Autowired
    private Util util;

    @Autowired
    private Mail mail;

    private static final SecureRandom RANDOM = new SecureRandom();

    // Minimum eight characters, at least one letter and one number. Alpha numeric only
    private static final String PWDPATTERN = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8,}$"; // NOSONAR

    private static final Pattern pattern = Pattern.compile(PWDPATTERN);

    public SignupDTO signup(SignupDTO signupDTO) {        
        if (isUserExists(signupDTO.getEmail())) {
            throw new CustomException("email", Constants.CODE_DUPLICATE_RECORD, 
                Constants.MSG_DUPLICATE_RECORD, "Signup failed. Email " + signupDTO.getEmail() + " exists");
        }

        if (!isValidPassword(signupDTO.getPassword())) {
            throw new CustomException("password", Constants.CODE_INVALID_PASSWORD, 
                Constants.MSG_INVALID_PASSWORD, "Signup failed. Invalid password. Email " + signupDTO.getEmail());
        }

        int verificationCode = RANDOM.nextInt(8999) + 1000;

        User user = new User();

        user.setFirstName(signupDTO.getFirstName());
        user.setLastName(signupDTO.getLastName());
        user.setEmail(signupDTO.getEmail());
        user.setEmailVerified(Constants.EMAIL_NOT_VERIFIED);
        user.setStatus(Constants.ACC_INACTIVE);
        user.setCreatedAt(util.getCurrentDateTimeUtc());
        user.setVerificationCode(verificationCode);
        user.setPassword(encodePassword(signupDTO.getPassword()));

        userRepository.save(user);
        
        mail.sendSignupEmail(String.valueOf(verificationCode), user.getEmail(), user.getFirstName());

        return signupDTO;
    }

    public void verifyCode(VerifyCodeDTO verifyCode) {
        User user = userRepository.findByCodeEmail(verifyCode.getEmail(), 
            Integer.parseInt(verifyCode.getVerificationCode()));

        if (user != null) {
            user.setEmailVerified(Constants.EMAIL_VERIFIED);
            user.setStatus(Constants.ACC_ACTIVE);
            userRepository.save(user);
        } else {
            throw new CustomException("", Constants.CODE_INVALID_CODE_OR_EMAIL, 
                Constants.MSG_INVALID_CODE_OR_EMAIL, "");
        }
    }

    public SigninDTO signin(SigninDTO signinDTO) {
        User user = userRepository.findByEmail(signinDTO.getEmail());
        if (user == null) {
            throw new CustomException("", Constants.CODE_INVALID_UNAME_PASS, 
                Constants.MSG_INVALID_UNAME_PASS, "Auth fail. Email:" + signinDTO.getEmail(), 
                HttpStatus.UNAUTHORIZED);
        }

        if (!isPasswordMatch(signinDTO.getPassword(), user.getPassword())) {
            throw new CustomException("", Constants.CODE_INVALID_UNAME_PASS, 
                Constants.MSG_INVALID_UNAME_PASS, "Auth fail. Email:" + signinDTO.getEmail(), 
                HttpStatus.UNAUTHORIZED);
        }

        if (user.getEmailVerified() == Constants.EMAIL_NOT_VERIFIED) {
            throw new CustomException("", Constants.CODE_EMAIL_NOT_VERIFIED, 
                Constants.MSG_EMAIL_NOT_VERIFIED, "Auth fail. Email not verified. Email:" + signinDTO.getEmail(), 
                HttpStatus.UNAUTHORIZED);
        }

        if (user.getStatus() == Constants.ACC_INACTIVE) {
            throw new CustomException("", Constants.CODE_ACC_INACTIVE, 
                Constants.MSG_ACC_INACTIVE, "Auth fail. Account inactive. Email:" + signinDTO.getEmail(), 
                HttpStatus.UNAUTHORIZED);
        }

        String token = jwt.getToken(user.getEmail(), user.getId());
        signinDTO.setToken(token);
        signinDTO.setFirstName(user.getFirstName());
        signinDTO.setLastName(user.getLastName());

        return signinDTO;
    }

    public ForgotPasswordDTO forgotPassword(ForgotPasswordDTO forgotPwDTO) {
        User user = userRepository.findByEmail(forgotPwDTO.getEmail());

        if (user == null) {
            throw new CustomException("", Constants.CODE_USER_NOT_EXISTS, 
                Constants.MSG_USER_NOT_EXISTS, "Forgot password fail. User not found. Email:" + forgotPwDTO.getEmail(), 
                HttpStatus.OK);
        }

        int verificationCode = RANDOM.nextInt(8999) + 1000;
        user.setVerificationCode(verificationCode);

        if (!mail.sendForgotPwEmail(String.valueOf(verificationCode), forgotPwDTO.getEmail(), user.getFirstName())) {
            throw new CustomException("", Constants.CODE_EMAIL_SEND_FAILED, 
                Constants.MSG_EMAIL_SEND_FAILED, "Email send failed. Email:" + forgotPwDTO.getEmail(), 
                HttpStatus.OK);
        }

        return forgotPwDTO;
    }

    public void resetPassword(ResetPasswordDTO resetPwDTO) {
        User user = userRepository.findByCodeEmail(resetPwDTO.getEmail(), Integer.parseInt(resetPwDTO.getVerificationCode()));

        if (user == null) {
            throw new CustomException("", Constants.CODE_INVALID_CODE_OR_EMAIL, 
                Constants.MSG_INVALID_CODE_OR_EMAIL, "Forgot password fail. Invalid email of verification code. Email:" + resetPwDTO.getEmail(), 
                HttpStatus.OK);
        }

        user.setPassword(encodePassword(resetPwDTO.getPassword()));
        userRepository.save(user);
    }

    public void changePassword(ChangePasswordDTO changePwDTO, Integer userId) {
        User user = userRepository.findByUserId(userId);

        if (user == null) {
            throw new CustomException("", Constants.CODE_USER_NOT_EXISTS, 
                Constants.MSG_USER_NOT_EXISTS, "Change password failed. User does not exists. UserId:" + userId, 
                HttpStatus.OK);
        }

        if (!isValidOldPassword(changePwDTO.getOldPassword(), user.getPassword())) {
            throw new CustomException("", Constants.CODE_INVALID_OLD_PASSWORD, 
                Constants.MSG_INVALID_OLD_PASSWORD, "Change password failed. Invalid old password. UserId:" + userId, 
                HttpStatus.OK);
        }

        if (!isValidPassword(changePwDTO.getPassword())) {
            throw new CustomException("password", Constants.CODE_INVALID_PASSWORD, 
                Constants.MSG_INVALID_PASSWORD, "Change password failed. Invalid password. UserId:" + userId);
        }

        user.setPassword(encodePassword(changePwDTO.getPassword()));
        userRepository.save(user);
    }

    public String encodePassword(String password) {
        BCryptPasswordEncoder passEncoder = new BCryptPasswordEncoder();
        return passEncoder.encode(password);
    }

    private boolean isUserExists(String email) {
        return userRepository.findByEmail(email) != null;
    }

    private boolean isPasswordMatch(String rawPwd, String encodedPwd) {
        return new BCryptPasswordEncoder().matches(rawPwd, encodedPwd);
    }

    private boolean isValidPassword(String password) {
        Matcher matcher = pattern.matcher(password);
        return matcher.matches();
    }

    private boolean isValidOldPassword(String oldPwd, String oldPwdDb) {
        return isPasswordMatch(oldPwd, oldPwdDb);
    }
}