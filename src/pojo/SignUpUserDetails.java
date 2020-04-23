package pojo;

public class SignUpUserDetails {
    private String userName, userEmail, userPassword, confirmPassword, phasePassword, confirmPhasePassword, userIP;

    public SignUpUserDetails(String userName, String userEmail, String userIP, String userPassword, String confirmPassword, String phasePassword, String confirmPhasePassword) {
        this.userName = userName;
        this.userEmail = userEmail;
        this.userPassword = userPassword;
        this.confirmPassword = confirmPassword;
        this.phasePassword = phasePassword;
        this.confirmPhasePassword = confirmPhasePassword;
        this.userIP = userIP;
    }

    public SignUpUserDetails() {
    }

    @Override
    public String toString() {
        return "SignUpUserDetails{" +
                "userName='" + userName + '\'' +
                ", userEmail='" + userEmail + '\'' +
                ", userPassword='" + userPassword + '\'' +
                ", confirmPassword='" + confirmPassword + '\'' +
                ", phasePassword='" + phasePassword + '\'' +
                ", confirmPhasePassword='" + confirmPhasePassword + '\'' +
                '}';
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserIP() {
        return userIP;
    }

    public void setUserIP(String userIP) {
        this.userIP = userIP;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    public String getPhasePassword() {
        return phasePassword;
    }

    public void setPhasePassword(String phasePassword) {
        this.phasePassword = phasePassword;
    }

    public String getConfirmPhasePassword() {
        return confirmPhasePassword;
    }

    public void setConfirmPhasePassword(String confirmPhasePassword) {
        this.confirmPhasePassword = confirmPhasePassword;
    }
}
