package com.eyerubic.socialintegrator;

/**
 * This class keeps all the application constants
 */
public class Constants {

    private Constants() {
        throw new IllegalStateException("Constants class");
    }

    // Custom status codes
    public static final int CODE_INVALID_INPUT = 1200;
    public static final int CODE_MISSING_MANDATORY_FIELD = 1201;
    public static final int CODE_EXCEED_MAX_CHAR_LENGTH = 1202;
    public static final int CODE_DUPLICATE_RECORD = 1203;
    public static final int CODE_UNKNONW = 1204;

    public static final int CODE_INTERNAL_ERROR = 1303;

    public static final int CODE_INVALID_UNAME_PASS = 1401;
    public static final int CODE_INVALID_CODE_OR_EMAIL = 1402;
    public static final int CODE_EMAIL_NOT_VERIFIED = 1403;
    public static final int CODE_ACC_INACTIVE = 1404;
    public static final int CODE_ENTITY_NOT_BELONGS_TO_USER = 1405;
    public static final int CODE_RECORD_NOT_FOUND = 1406;
    public static final int CODE_USER_NOT_EXISTS = 1407;
    public static final int CODE_EMAIL_SEND_FAILED = 1408;
    public static final int CODE_INVALID_PASSWORD = 1409;
    public static final int CODE_INVALID_OLD_PASSWORD = 1410;

    // Status messages associates with custom status codes
    public static final String MSG_INVALID_INPUT = "Invalid input";
    public static final String MSG_MISSING_MANDATORY_FIELD = "Missing mandatory field";
    public static final String MSG_EXCEED_MAX_CHAR_LENGTH = "Invalid character length";
    public static final String MSG_INVALID_UNAME_PASS = "Invalid username or password";
    public static final String MSG_INTERNAL_ERROR = "Internal error occured";
    public static final String MSG_DUPLICATE_RECORD = "Duplicate record";
    public static final String MSG_INVALID_CODE_OR_EMAIL = "Invalid verification code or email";
    public static final String MSG_EMAIL_NOT_VERIFIED = "Email not verified";
    public static final String MSG_ACC_INACTIVE = "Account inactive";
    public static final String MSG_ENTITY_NOT_BELONGS_TO_USER = "Record not belongs to user";
    public static final String MSG_RECORD_NOT_FOUND = "Record not found";
    public static final String MSG_USER_NOT_EXISTS = "User does not exist";
    public static final String MSG_EMAIL_SEND_FAILED = "Email send failed";
    public static final String MSG_INVALID_PASSWORD = "Password should be minimum eight characters, at least one letter and one number. Alphanumeric only.";
    public static final String MSG_INVALID_OLD_PASSWORD = "Invalid old password";

    // Other
    public static final int EMAIL_VERIFIED = 1;
    public static final int EMAIL_NOT_VERIFIED = 0;
    public static final int ACC_ACTIVE = 1;
    public static final int ACC_INACTIVE = 0;

    // Activity types
    public static final String ACT_SIGNUP = "SIGNUP";
    public static final String ACT_SIGNIN = "SIGNIN";
    public static final String ACT_FPW = "FPW";
    public static final String ACT_CPW = "CPW";
    public static final String ACT_CRT_INT = "CRT_INT";
}
