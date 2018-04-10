package model;

/**
 * Created by 11059 on 2017/3/13.
 */
public class LoginModel {


    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getResult_code() {
        return result_code;
    }

    public void setResult_code(String result_code) {
        this.result_code = result_code;
    }

    private  String message;
    private String result_code;

    public LoginReturn getLoginReturn() {
        return loginReturn;
    }

    public void setLoginReturn(LoginReturn loginReturn) {
        this.loginReturn = loginReturn;
    }

    private LoginReturn loginReturn;
}
