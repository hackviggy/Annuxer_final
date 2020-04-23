import authentication.ValidationForUserDetails;
import database.LogIn_DB;
import database.SignUp_DB;
import pojo.LogInUserDetails;
import pojo.SignUpUserDetails;
import utils.SendMail;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Scanner;

public class Application {

    public static void main(String[] args) {
        //commite for testing suepr da 
        home();
    }

    private static void home() {
        System.out.println("1.Signup \n2.Login");

        switch (new Scanner(System.in).nextInt()) {
            case 1:
                signUp();
                break;
            case 2:
                logIn();
                break;
            default:
                System.out.println("select accorndingly");
        }
    }

    private static void logIn() {
        LogInUserDetails logIn = new LogInUserDetails();
        LogIn_DB logIn_db = new LogIn_DB();
        Scanner scan = new Scanner(System.in);
        System.out.println("Enter user name =");
        logIn.setUserName(scan.next());
        System.out.println("Enter password =");
        logIn.setPassword(scan.next());
        try {
            logIn.setPass_ip(InetAddress.getLocalHost().getHostAddress());

            //login process here

            if (logIn_db.initialPasswordLogin(logIn)) {
                System.out.println("Enter phase password =");
                logIn.setPhasePassword(scan.next());
                logIn.setPhase_ip(InetAddress.getLocalHost().getHostAddress());
                if (logIn_db.phasePasswordLogin(logIn)) {
                    System.out.println("Log in successfully..!");
                } else {
                    System.out.println("Phase password wrong..!");
                }
            } else {
                System.out.println("username or password wrong...!");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    private static void signUp() {
        SignUp_DB signUp_db = new SignUp_DB();
        SendMail sendMail = new SendMail();
        int OTP;
        SignUpUserDetails userDetails = getUserDetails();
        String username = userDetails.getUserName();
        if (checkFieldFromUserDetails(userDetails)) {
            signUp_db.insertUserDetails(userDetails);
            OTP = sendMail.sendMailToUser(userDetails.getUserEmail());
            if (sendMail.verifyOTP()) {
                signUp_db.updateOTP(OTP, username);
                System.out.println("Account Create successfully..!");
                home();
            } else {
                System.err.println("You typed wrong  please makesure type correct otp..!");
            }
        }
    }

    private static boolean checkFieldFromUserDetails(SignUpUserDetails userDetails) {
        ValidationForUserDetails validation = new ValidationForUserDetails(userDetails);
        if (!validation.checkEmailId()) {
            System.err.println("Mail ID WRONG..!");
            return false;
        }
        if (!validation.checkUserPassword(userDetails.getUserPassword())) {
            System.err.println("NOT FULFILL PASSWORD POLICY");
            return false;
        }
        if (!validation.checkUserPassword(userDetails.getPhasePassword())) {
            System.err.println("NOT FULFILL PHASE PASSWORD POLICY");
            return false;
        }
        if (!userDetails.getUserPassword().trim().equals(userDetails.getConfirmPassword().trim())) {
            System.err.println("PASSWORD MISMATCH..!");
            return false;
        }
        if (!userDetails.getPhasePassword().trim().equals(userDetails.getConfirmPhasePassword().trim())) {
            System.err.println("PHASE PASSWORD MISMATCH..!");
            return false;
        }
        return true;
    }

    private static SignUpUserDetails getUserDetails() {
        SignUpUserDetails signup = new SignUpUserDetails();
        Scanner scan = new Scanner(System.in);
        System.out.println("Enter user name = ");
        signup.setUserName(scan.next());
        System.out.println("Enter Email ID = ");
        signup.setUserEmail(scan.next());
        System.out.println("Enter Password = ");
        signup.setUserPassword(scan.next());
        System.out.println("Enter Confirm Password = ");
        signup.setConfirmPassword(scan.next());
        System.out.println("Enter Phase Password = ");
        signup.setPhasePassword(scan.next());
        System.out.println("Enter Confirm Phase Password = ");
        signup.setConfirmPhasePassword(scan.next());
        try {
            signup.setUserIP(InetAddress.getLocalHost().getHostAddress());
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        return signup;
    }
}

