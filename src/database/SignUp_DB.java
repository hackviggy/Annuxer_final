package database;

import pojo.SignUpUserDetails;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.text.SimpleDateFormat;
import java.util.Date;

public class SignUp_DB {
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

    public void insertUserDetails(SignUpUserDetails userDetails) {
        String currentDate = new SimpleDateFormat("dd-MM-yyyy").format(new Date());
        //replace original query into the this statement

        init();

        try {


            PreparedStatement stmt = con.prepareStatement("insert into user_detail_s1(User_detail_S1_User_Name,User_detail_S1_Mail_ID,User_detail_S1_FPassword,User_detail_S1_OTP) values(?,?,?,?);");
            //set details according to table fields type

            stmt.setString(1, userDetails.getUserName());
            stmt.setString(2, userDetails.getUserEmail());
            stmt.setString(3, userDetails.getUserPassword());
            stmt.setInt(4, 0);
            //stmt.setString(5, currentDate);
            stmt.execute();
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {

            //replace original query into the this statement
            PreparedStatement stmt = con2.prepareStatement("insert into user_detail_s2(User_detail_S2_User_Name,User_detail_S2_Mail_ID,User_detail_S2_sPassword) values(?,?,?);");
            //set details according to table fields type

            stmt.setString(1, userDetails.getUserName());
            stmt.setString(2, userDetails.getUserEmail());
            stmt.setString(3, userDetails.getPhasePassword());
            //stmt.setString(4,currentDate);
            stmt.execute();
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {

            //replace original query into the this statement
            PreparedStatement stmt = con1.prepareStatement("insert into user_detail_s3(User_detail_S3_User_Name,User_detail_S3_Mail_ID,User_detail_S3_IP_LoggedIn) values(?,?,?);");
            //set details according to table fields type

            stmt.setString(1, userDetails.getUserName());
            stmt.setString(2, userDetails.getUserEmail());
            stmt.setString(3, userDetails.getUserIP());
            //stmt.setString(4,currentDate);
            stmt.execute();
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    public void updateOTP(int OTP, String name) {
        try {
            init();
            PreparedStatement stmt = con.prepareStatement("UPDATE `User_detail_S1` SET `User_detail_S1_OTP` = ? WHERE `User_detail_S1_User_Name` = ? ;");
            //set details according to table fields type
            stmt.setInt(1, OTP);
            stmt.setString(2, name);
            stmt.execute();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
