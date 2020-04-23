package database;


import crytography.InitialPasswordEncy;
import crytography.PhasePasswordEncy;
import pojo.LogInUserDetails;
import utils.CommonUtils;

import java.sql.*;

public class LogIn_DB {


    private Connection con;
    private Connection con1;
    private Connection con2;

    private void init() {
        try {
            Class.forName(CommonUtils.CLASS_NAME);
            con = DriverManager.getConnection(CommonUtils.SERVER_1, CommonUtils.DB_USER_NAME, CommonUtils.DB_PASSWORD);
            con1 = DriverManager.getConnection(CommonUtils.SERVER_3, CommonUtils.DB_USER_NAME, CommonUtils.DB_PASSWORD);
            con2 = DriverManager.getConnection(CommonUtils.SERVER_2, CommonUtils.DB_USER_NAME, CommonUtils.DB_PASSWORD);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public boolean initialPasswordLogin(LogInUserDetails logInUser) {
        //we use ip for RSA
        init();
        InitialPasswordEncy initPassCrypt = new InitialPasswordEncy();
        String userName = logInUser.getUserName();
        String ency_password = logInUser.getPassword();
        String decy_password = initPassCrypt.decrypt(ency_password);
        System.out.println("initial pasword decy =" + decy_password);
        String pass_ip = logInUser.getPass_ip();
        try {
            Statement stmt = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_READ_ONLY);

            PreparedStatement prs = con.prepareStatement("select * from user_detail_s1 where User_detail_S1_User_Name= ? && User_detail_S1_FPassword = ?;");
            prs.setString(1, userName);
            prs.setString(2, decy_password);
            ResultSet rs = prs.executeQuery();
            if (rs.last()) {
                return rs.getRow() != 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean phasePasswordLogin(LogInUserDetails logIn) {
        //we use pp for AES
        PhasePasswordEncy phasePassCrypto = new PhasePasswordEncy();
        String ency_phase_password = logIn.getPhasePassword();
        String decy_phase_password = phasePassCrypto.decrypt(ency_phase_password);
        System.out.println("phase password decy " + decy_phase_password);
        if (logIn.getPass_ip().trim().equals(logIn.getPhase_ip().trim())) {
            try {
                Statement stmt = con2.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
                        ResultSet.CONCUR_READ_ONLY);

                String uusername = logIn.getUserName();
                String PPassword = logIn.getPhasePassword();
                PreparedStatement prs = con2.prepareStatement("select * from user_detail_s2   where User_detail_S2_User_Name=? && User_detail_S2_sPassword = ?;");
                prs.setString(1, uusername);
                prs.setString(2, PPassword);
                ResultSet rs = prs.executeQuery();

                if (rs.last()) {
                    return rs.getRow() != 0;
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return false;
    }
}
