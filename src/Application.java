import authentication.ValidationForUserDetails;
import crytography.InitialPasswordEncy;
import crytography.PhasePasswordEncy;
import database.LogIn_DB;
import database.SignUp_DB;
import pojo.LogInUserDetails;
import pojo.SignUpUserDetails;
import utils.CommonUtils;
import utils.FileMerge;
import utils.FileSplit;
import utils.SendMail;

import java.io.File;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Scanner;

public class Application {

    public static void main(String[] args) {


        home();
    }


    private static void home() {
        while (true) {
            System.out.println("1.Signup \n2.Login\n");
            switch (new Scanner(System.in).nextInt()) {
                case 1:
                    signUp();
                    break;
                case 2:
                    logIn();
                    fileOperation();
                    break;
                default:
                    System.out.println("select accorndingly");
            }
        }
    }

    private static void fileOperation() {

        while (true) {
            System.out.println("1.Upload \n2.Download\n");
            switch (new Scanner(System.in).nextInt()) {
                case 1:
                    uploadFile();
                    break;
                case 2:
                    downloadFile();
                    break;
                default:
                    System.out.println("select accorndingly");
            }
        }
    }

    private static void downloadFile() {
        FileSplit file = new FileSplit();
        FileMerge fileMerge = new FileMerge();
        System.out.println("Enter correct file path ");
        String fileName = new Scanner(System.in).next();
        if (file.fileExitis(fileName)) {
            File originalFile = fileMerge.fileMerge(fileName);
        } else {
            System.err.println("file does not exits");
        }
    }

    private static void uploadFile() {
        FileSplit file = new FileSplit();
        System.out.println("Enter correct file path ");
        String fileName = null;
        //String fileName =showFileList();
        if (file.fileExitis(fileName)) {
            if (file.fileUpload(fileName)) {
                System.out.println("File upload successfully..!");
            } else {
                System.err.println("File upload failure..!");
            }
        } else {
            System.err.println("file does not exits");
        }

    }

    private static void logIn() {
        LogInUserDetails logIn = new LogInUserDetails();
        LogIn_DB logIn_db = new LogIn_DB();
        int loginAttempts = 1;
        boolean loopstatus = true;
        while (loginAttempts != 4) {
            loginAttempts++;
            PhasePasswordEncy phasePasscryto = new PhasePasswordEncy();
            InitialPasswordEncy initPassCrytp = new InitialPasswordEncy();
            String login_password;
            String phase_password;
            Scanner scan = new Scanner(System.in);
            System.out.println("Enter user name =");
            logIn.setUserName(scan.next());

            try {
                logIn.setPass_ip(InetAddress.getLocalHost().getHostAddress());
                //login process here
                if (logIn_db.logInDbUserNameCheck(logIn)) {
                    if (logIn_db.ResetFlagCheck(logIn)) {
                        System.out.println("Enter password =");
                        login_password = scan.next();
                        //logIn.(initPassCrytp.encrypt(login_password));
                        logIn.setPassword(initPassCrytp.encrypt(login_password));
                        if (logIn_db.initialPasswordLogin(logIn, initPassCrytp)) {
                            System.out.println("Enter phase password =");
                            phase_password = scan.next();
                            logIn.setPhasePassword(phasePasscryto.encrypt(phase_password));
                            logIn.setPhase_ip(InetAddress.getLocalHost().getHostAddress());
                            if (logIn_db.phasePasswordLogin(logIn)) {
                                System.out.println("Log in successfully..!");
                                loopstatus = false;
                                break;
                            } else {
                                System.out.println("Phase password wrong..!");
                            }
                        } else {
                            System.out.println("username or password wrong...!");
                        }
                    } else {
                        System.out.println("reset your password first..!");
                        loopstatus = true;
                        break;

                    }
                } else {
                    System.out.println("user name wrong..!");
                }
            } catch (Exception e) {
                e.printStackTrace();
                break;
            }
        }
        if (loopstatus) {
            forgetPassword(logIn.getUserName(), logIn_db);
        }


    }

    private static SignUpUserDetails setNewPassword() {
        SignUpUserDetails signup = new SignUpUserDetails();
        Scanner scan = new Scanner(System.in);
        System.out.println("Enter Password = ");
        signup.setUserPassword(scan.next());
        System.out.println("Enter Confirm Password = ");
        signup.setConfirmPassword(scan.next());
        System.out.println("Enter Phase Password = ");
        signup.setPhasePassword(scan.next());
        System.out.println("Enter Confirm Phase Password = ");
        signup.setConfirmPhasePassword(scan.next());

        return signup;
    }

    private static void forgetPassword(String U_name, LogIn_DB logInDb) {
        logInDb.updateReset(true, U_name);
        System.out.println("You are use exitising amount of attempts please reset your password..!");
        SignUpUserDetails forgetPasswordUserDetails = new SignUpUserDetails();
        SignUp_DB signUp_db = new SignUp_DB();
        SendMail sendMail = new SendMail();
        Scanner scan = new Scanner(System.in);
        System.out.println("Enter your valid mail id :");
        forgetPasswordUserDetails.setUserEmail(scan.next());
        int OTP = sendMail.sendMailToUser(forgetPasswordUserDetails.getUserEmail());
        if (sendMail.verifyOTP()) {
            SignUpUserDetails userDetails = setNewPassword();
            userDetails.setUserName(forgetPasswordUserDetails.getUserName());
            userDetails.setUserEmail(forgetPasswordUserDetails.getUserEmail());
            if (checkFieldFromUserDetails(userDetails)) {
                if (signUp_db.passwordUpdate(userDetails)) {
                    logInDb.updateReset(false, U_name);
                    signUp_db.updateOTP(OTP, userDetails.getUserName());
                } else {
                    System.err.println("password is not updated");
                }
            }
        } else {
            System.err.println("You typed wrong OTP please make sure type correct otp..!");
        }
    }

    private static void signUp() {
        SignUp_DB signUp_db = new SignUp_DB();
        SendMail sendMail = new SendMail();
        int OTP;
        SignUpUserDetails userDetails = getUserDetails();
        String username = userDetails.getUserName();
        if (checkFieldFromUserDetails(userDetails)) {
            if (signUp_db.insertUserDetails(userDetails)) {
                OTP = sendMail.sendMailToUser(userDetails.getUserEmail());
                if (sendMail.verifyOTP()) {
                    signUp_db.updateOTP(OTP, username);
                    System.out.println("Account Create successfully..!");
                    home();
                } else {
                    System.err.println("You typed wrong  please make sure type correct otp..!");
                }
            } else {
                System.err.println("User already exits..!");
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
        if (!validation.checkUserp_Password(userDetails.getPhasePassword())) {
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
        System.out.print("Enter user name = ");
        signup.setUserName(scan.next());
        System.out.println("Email ID Notification");
        System.out.println("----------------------------");
        System.out.println(CommonUtils.EMAIL_PASSAGE);
        System.out.println("----------------------------");
        System.out.print("Enter Email ID = ");
        signup.setUserEmail(scan.next());
        System.out.println("Password Policy ");
        System.out.println("----------------------------");
        System.out.println("*" + " it's less that 8 characters long. \n" + "*" + " " +
                "it doesn't contain an digits or upper case characters \n" + "*" + " characters ~ in not allowed \n"
                + "*" + " there should be a digit  \n" + "*" + " there should be a lower case character");
        System.out.println("----------------------------");
        System.out.print("Enter Password = ");
        signup.setUserPassword(scan.next());
        System.out.print("Enter Confirm Password = ");
        signup.setConfirmPassword(scan.next());
        System.out.println("PhrasePassword Policy ");
        System.out.println("----------------------------");
        System.out.println("*" + " it's less that 15 characters long. \n" + "*" + " " +
                "it doesn't contain an digits or upper case characters \n" + "*" + " characters ~ in not allowed \n"
                + "*" + " there should be a digit  \n" + "*" + " there should be a lower case character");
        System.out.println("----------------------------");
        System.out.print("Enter Phrase Password = ");
        signup.setPhasePassword(scan.next());
        System.out.print("Enter Confirm Phrase Password = ");
        signup.setConfirmPhasePassword(scan.next());
        try {
            signup.setUserIP(InetAddress.getLocalHost().getHostAddress());
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        return signup;
    }
}

