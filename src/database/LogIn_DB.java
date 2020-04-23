package database;


import pojo.LogInUserDetails;

import java.sql.*;

public class LogIn_DB {


    private Connection con;
    private Connection con1;
    private Connection con2;

    private void init() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/server1", "root", "12345678");
            con1 = DriverManager.getConnection("jdbc:mysql://localhost:3308/server3", "root", "12345678");
            con2 = DriverManager.getConnection("jdbc:mysql://localhost:3307/server2", "root", "12345678");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public boolean initialPasswordLogin(LogInUserDetails logInUser) {
        init();
        String userName = logInUser.getUserName();
        String password = logInUser.getPassword();
        String pass_ip = logInUser.getPass_ip();
        try {
            Statement stmt = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_READ_ONLY);

            PreparedStatement prs = con.prepareStatement("select * from user_detail_s1 where User_detail_S1_User_Name= ? && User_detail_S1_FPassword = ?;");
            prs.setString(1, userName);
            prs.setString(2, password);
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
