package Login;
import DBUtil.DBConnection;

import java.sql.ResultSet;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class LoginModel {
    Connection connection;
    public LoginModel(){
        try{
            this.connection = DBConnection.getConnection();
        }catch (SQLException e){
            e.printStackTrace();
        }
        if(this.connection == null){
            System.exit(1);
        }
    }

    public boolean isConnected(){
        return this.connection != null;
    }

    public void registerUser(String Username, String FirstName, String LastName, String Password, String Email)throws SQLException{
        PreparedStatement ps = null;
        ResultSet rs = null;
        String sql = "";
    }

    public boolean isLogin(String Username, String Password, String Account) throws SQLException {
        PreparedStatement ps = null;
        ResultSet rs = null;
        String sql = "SELECT * FROM Users where Username = ? and Password = ? and Account = ?";
        try{
            ps = this.connection.prepareStatement(sql);
            ps.setString(1,Username);
            ps.setString(2,Password);
            ps.setString(3,Account);

            rs = ps.executeQuery();
            if(rs.next()){
                return true;
            } return false;
        } catch(SQLException e){
            return false;
        }finally {
            ps.close();
            rs.close();
        }
    }
}
