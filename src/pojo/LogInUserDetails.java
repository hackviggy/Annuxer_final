package pojo;

public class LogInUserDetails {
    private String userName, password, phasePassword, pass_ip, phase_ip;

    public LogInUserDetails(String userName, String password, String phasePassword, String pass_ip, String phase_ip) {
        this.userName = userName;
        this.password = password;
        this.phasePassword = phasePassword;
        this.pass_ip = pass_ip;
        this.phase_ip = phase_ip;
    }

    public LogInUserDetails() {
    }

    @Override
    public String toString() {
        return "LogInUserDetails{" +
                "userName='" + userName + '\'' +
                ", password='" + password + '\'' +
                ", phasePassword='" + phasePassword + '\'' +
                ", pass_ip='" + pass_ip + '\'' +
                ", phase_ip='" + phase_ip + '\'' +
                '}';
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhasePassword() {
        return phasePassword;
    }

    public void setPhasePassword(String phasePassword) {
        this.phasePassword = phasePassword;
    }

    public String getPass_ip() {
        return pass_ip;
    }

    public void setPass_ip(String pass_ip) {
        this.pass_ip = pass_ip;
    }

    public String getPhase_ip() {
        return phase_ip;
    }

    public void setPhase_ip(String phase_ip) {
        this.phase_ip = phase_ip;
    }
}
