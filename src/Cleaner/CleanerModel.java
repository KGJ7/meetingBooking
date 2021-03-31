package Cleaner;

import Admin.userBookings;
import DBUtil.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;

public class CleanerModel {

    private LocalTime startTime;
    private LocalDate date;

    private ArrayList<userBookings> occupiedBookings = new ArrayList<userBookings>();

    public CleanerModel(String startTime, LocalDate date) {
        this.startTime = LocalTime.parse(startTime);
        this.date = date;

    }

    public void getOccupiedCleanerTimes() throws SQLException {
        PreparedStatement ps = null;
        ResultSet rs = null;
        try{
            Connection con = DBConnection.getConnection();
            String sql = "SELECT * FROM Bookings WHERE UserID IS ? AND Date is ?";
            assert con != null;

            ps = con.prepareStatement(sql);
            ps.setInt(1,2);
            ps.setString(2, String.valueOf(date));

            while(rs.next()){
                occupiedBookings.add(new userBookings(rs.getInt(1),rs.getInt(2),LocalTime.parse(rs.getString(4)),LocalDate.parse(rs.getString(6))));
            }
        } catch(Exception e){
            System.out.println("Error: " + e);

        }finally{
            assert ps != null;
            ps.close();
            assert rs != null;
            rs.close();
        }
    }

    public boolean isBookingOccupied(){
        for(userBookings i:occupiedBookings){
            boolean temp = startTime.equals(i.getStartTime());
            if(temp){
                return true;
            }
        }return false;
    }

    public boolean addCleaner(int roomID, LocalTime startingTime, LocalDate Date){

    }
}
