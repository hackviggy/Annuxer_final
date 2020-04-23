package authentication;

import pojo.SignUpUserDetails;

public class ValidationForUserDetails {
    private SignUpUserDetails sign;

    public ValidationForUserDetails(SignUpUserDetails sign) {
        this.sign = sign;
    }

    public boolean checkUserPassword(String userPassword) {
        //password length greater then 8
        if (userPassword.trim().length() >= 8) {
            //password must contain a digit
            if (userPassword.matches("(?=.*[0-9]).*")) {
                //password must contain a UpperCase leter
                if (userPassword.matches("(?=.*[A-Z]).*")) {
                    //password must contain a LowerCase leter
                    if (userPassword.matches("(?=.*[a-z]).*")) {
                        //password must contain a Special character
                        return userPassword.matches("(?=.*[~!@#$%^&*()_+-?/`=,.;:']).*");
                    }
                }
            }
        }
        return false;
    }

    public boolean checkEmailId() {
        return sign.getUserEmail().matches("^[\\w-_\\.+]*[\\w-_\\.]\\@([\\w]+\\.)+[\\w]+[\\w]$");
    }

}
