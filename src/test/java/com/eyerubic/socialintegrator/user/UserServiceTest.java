package com.eyerubic.socialintegrator.user;

import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.eyerubic.socialintegrator.Constants;
import com.eyerubic.socialintegrator.helpers.Jwt;
import com.eyerubic.socialintegrator.helpers.Mail;
import com.eyerubic.socialintegrator.helpers.Util;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;


@ExtendWith(SpringExtension.class)
class UserServiceTest {
    
    @Mock
    private UserRepository userRepository;

    @Mock
    private Util util;

    @Mock
    private Mail mail;

    @Mock
    private Jwt jwt;

    @InjectMocks
    private UserService userService;

    @Test
    void userSignup() {
        User user = getMockUser();
        when(userRepository.save(ArgumentMatchers.any(User.class))).thenReturn(user);

        SignupDTO supDTO = getMockSignupDTO();
        SignupDTO signupDTO = userService.signup(supDTO);
        assertEquals(signupDTO.getFirstName(), user.getFirstName());
    }

    @Test
    void userSignup_InvalidPassword() {
        SignupDTO supDTO = getMockSignupDTO();
        supDTO.setPassword("hello");

        try {
            userService.signup(supDTO);
        } catch (Exception e) {
            assertEquals(Constants.MSG_INVALID_PASSWORD, e.getMessage());
        }
    }

    @Test
    void userSingup_ExistingUser() {
        User user = getMockUser();
        when(userRepository.findByEmail("aruna470@gmail.com"))
            .thenReturn(user);

        try {
            SignupDTO supDTO = getMockSignupDTO();
            userService.signup(supDTO);
        } catch (Exception e) {
            assertEquals(Constants.MSG_DUPLICATE_RECORD, e.getMessage());
        }
    }

    @Test
    void userSignup_VerifyWithValidCode() {
        User user = getMockUser();
        when(userRepository.findByCodeEmail("aruna470@gmail.com", 1234))
            .thenReturn(user);

        VerifyCodeDTO verifyCodeDTO = new VerifyCodeDTO();
        verifyCodeDTO.setEmail("aruna470@gmail.com");
        verifyCodeDTO.setVerificationCode("1234");

        try {
            userService.verifyCode(verifyCodeDTO);
            assertTrue(true);
        } catch (Exception e) {
            assertTrue(false);
        }
    }

    @Test
    void userSignup_VerifyWithInValidCode() {
        User user = null;
        when(userRepository.findByCodeEmail("aruna470@gmail.com", 1235))
            .thenReturn(user);

        VerifyCodeDTO verifyCodeDTO = new VerifyCodeDTO();
        verifyCodeDTO.setEmail("aruna470@gmail.com");
        verifyCodeDTO.setVerificationCode("1234");

        try {
            userService.verifyCode(verifyCodeDTO);
        } catch (Exception e) {
            assertEquals(Constants.MSG_INVALID_CODE_OR_EMAIL, e.getMessage());
        }
    }

    @Test
    void userSignin_InvalidUsername() {
        User user = null;
        when(userRepository.findByEmail("aruna470@gmail.com"))
            .thenReturn(user);

        SigninDTO signinDTO = new SigninDTO();
        signinDTO.setEmail("aruna470@gmail.com");
        signinDTO.setPassword("test123");

        try {
            userService.signin(signinDTO);
        } catch (Exception e) {
            assertEquals(Constants.MSG_INVALID_UNAME_PASS, e.getMessage());
        }
    }

    @Test
    void userSignin_InvalidPassword() {
        User user = getMockUser();
        user.setPassword(userService.encodePassword("test123456"));
        when(userRepository.findByEmail("aruna470@gmail.com"))
            .thenReturn(user);

        SigninDTO signinDTO = new SigninDTO();
        signinDTO.setEmail("aruna470@gmail.com");
        signinDTO.setPassword("test123");

        try {
            userService.signin(signinDTO);
        } catch (Exception e) {
            assertEquals(Constants.MSG_INVALID_UNAME_PASS, e.getMessage());
        }
    }

    @Test
    void userSignin_EmailNotVerified() {
        User user = getMockUser();
        user.setEmailVerified(0);
        user.setPassword(userService.encodePassword("test123456"));
        when(userRepository.findByEmail("aruna470@gmail.com"))
            .thenReturn(user);

        SigninDTO signinDTO = new SigninDTO();
        signinDTO.setEmail("aruna470@gmail.com");
        signinDTO.setPassword("test123456");

        try {
            userService.signin(signinDTO);
        } catch (Exception e) {
            assertEquals(Constants.MSG_EMAIL_NOT_VERIFIED, e.getMessage());
        }
    }

    @Test
    void userSignin_InactiveUser() {
        User user = getMockUser();
        user.setStatus(0);
        user.setEmailVerified(1);
        user.setPassword(userService.encodePassword("test123456"));
        when(userRepository.findByEmail("aruna470@gmail.com"))
            .thenReturn(user);

        SigninDTO signinDTO = new SigninDTO();
        signinDTO.setEmail("aruna470@gmail.com");
        signinDTO.setPassword("test123456");

        try {
            userService.signin(signinDTO);
        } catch (Exception e) {
            assertEquals(Constants.MSG_ACC_INACTIVE, e.getMessage());
        }
    }

    @Test
    void userSignin_Success() {
        User user = getMockUser();
        user.setStatus(1);
        user.setEmailVerified(1);
        user.setPassword(userService.encodePassword("test123456"));
        when(userRepository.findByEmail("aruna470@gmail.com"))
            .thenReturn(user);

        SigninDTO signinDTO = new SigninDTO();
        signinDTO.setEmail("aruna470@gmail.com");
        signinDTO.setPassword("test123456");

        try {
            SigninDTO sInDTO = userService.signin(signinDTO);
            assertEquals("Aruna", sInDTO.getFirstName());
        } catch (Exception e) {
            assertTrue(false);
        }
    }

    @Test
    void forgotPwd_UserNotExists() {
        User user = null;
        when(userRepository.findByEmail("aruna470@gmail.com"))
            .thenReturn(user);

        ForgotPasswordDTO forgotPasswordDTO = new ForgotPasswordDTO();
        forgotPasswordDTO.setEmail("aruna470@gmail.com");

        try {
            userService.forgotPassword(forgotPasswordDTO);
        } catch (Exception e) {
            assertEquals(Constants.MSG_USER_NOT_EXISTS, e.getMessage());
        }
    }

    @Test
    void forgotPwd_Success() {
        User user = getMockUser();
        when(userRepository.findByEmail("aruna470@gmail.com"))
            .thenReturn(user);

        ForgotPasswordDTO forgotPasswordDTO = new ForgotPasswordDTO();
        forgotPasswordDTO.setEmail("aruna470@gmail.com");

        try {
            userService.forgotPassword(forgotPasswordDTO);
            assertTrue(true);
        } catch (Exception e) {
            System.out.println("=======================");
            System.out.println(e);
            assertTrue(false);
        }
    }

    private User getMockUser() {
        User user = new User();
        user.setFirstName("Aruna");
        user.setLastName("Attanayake");
        user.setEmail("aruna470@gmail.com");
        user.setPassword("test123456");

        return user;
    }

    private SignupDTO getMockSignupDTO() {
        SignupDTO signupDTO = new SignupDTO();
        signupDTO.setFirstName("Aruna");
        signupDTO.setLastName("Attanayake");
        signupDTO.setEmail("aruna470@gmail.com");
        signupDTO.setPassword("test123456");

        return signupDTO;
    }
}
