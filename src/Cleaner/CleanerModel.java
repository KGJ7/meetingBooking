package Cleaner;

import Admin.userBookings;
import DBUtil.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
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
                occupiedBookings.add(new userBookings(rs.getInt(1),rs.getInt(2),rs.getString(3),LocalTime.parse(rs.getString(4)),LocalTime.parse(rs.getString(5)),LocalDate.parse(rs.getString(6)),rs.getString(7),rs.getString(8),rs.getString(9)));
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

    public boolean addCleanerSlot(int roomID, LocalTime startingTime, LocalDate Date){
        LocalTime endingTime = startingTime.plus(30, ChronoUnit.MINUTES);
        String sql = "INSERT INTO Bookings(RoomID, UserID, Username, StartingTime, EndingTime, Date, Resources, Refreshment, RefreshmentTime) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try{
            PreparedStatement ps = null;
            Connection con = DBConnection.getConnection();
            assert con != null;
            ps = con.prepareStatement(sql);
            ps.setInt(1, roomID);
            ps.setInt(2, 2);
            ps.setString(3, "CLEANER");
            ps.setString(4, startingTime.toString());
            ps.setString(5, endingTime.toString());
            ps.setString(6, date.toString());
            ps.setString(8, "");
            ps.setString(9, "");
            ps.execute();
            return true;
        } catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }
    public LocalTime getNextBooking(String tempDate){
        String sql = "SELECT * FROM Bookings WHERE Date = ? ORDER BY StartingTime ASC";
        try{
            Connection con = DBConnection.getConnection();
            PreparedStatement ps = null;
            ResultSet rs = null;
            ArrayList<userBookings> userBookingsArrayList = new ArrayList<>();
            assert con !=null;
            if(userBookingsArrayList.size()>1){
                return getNextFreeSpot(date.toString());
            }else {
                for (userBookings i : userBookingsArrayList) {
                    if (i.getStartTime().isAfter(startTime)) {
                        return i.getStartTime();
                    }
                }
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }
    private LocalTime getNextFreeSpot(String date){
        String sql = "SELECT * FROM Bookings WHERE UserID = ? AND Date = ? ORDER BY EndingTime DESC";
        try {
            Connection con = DBConnection.getConnection();
            PreparedStatement ps = null;
            ResultSet rs = null;
            ArrayList<userBookings> ub = new ArrayList<>();
            assert con != null;
            ps = con.prepareStatement(sql);
            ps.setInt(1, 2);
            ps.setString(2, date);
            rs = ps.executeQuery();
            while (rs.next()) {
                ub.add(new userBookings(rs.getInt(1),rs.getInt(2),rs.getString(3),LocalTime.parse(rs.getString(4)),LocalTime.parse(rs.getString(5)),LocalDate.parse(rs.getString(6)),rs.getString(7),rs.getString(8),rs.getString(9)));
            }
            for(userBookings x: ub){
                return x.getStartTime().plus(30, ChronoUnit.MINUTES);
            }
        }catch(Exception e){
            System.out.println("Error: " + e);
        }
        return null;
    }

}


