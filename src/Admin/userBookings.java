package Admin;


import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;

public class userBookings {
    private int roomID;
    private int userID;
    private String username;
    private String startingTime;
    private String endingTime;
    private LocalDate Date;
    private String resources;
    private String refreshments;
    private String refreshmentsTime;

    public userBookings(int roomID, int userID, String username, String startingTime, String endingTime, LocalDate Date, String resources, String refreshments, String refreshmentsTime) {
        this.roomID = roomID;
        this.userID = userID;
        this.username = username;
        this.startingTime = startingTime;
        this.endingTime = endingTime;
        this.Date = Date;
        this.resources = resources;
        this.refreshments = refreshments;
        this.refreshmentsTime = refreshmentsTime;
    }

    public int getRoomID() {
        return roomID;
    }

    public void setRoomID(int roomID) {
        this.roomID = roomID;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getStartingTime() {
        return startingTime;
    }

    public void setStartingTime(String startingTime) {
        this.startingTime = startingTime;
    }

    public String getEndingTime() {
        return endingTime;
    }

    public void setEndingTime(String endingTime) {
        this.endingTime = endingTime;
    }

    public LocalDate getDate() {
        return Date;
    }

    public void setDate(LocalDate date) {
        Date = date;
    }

    public String getResources() {
        return resources;
    }

    public void setResources(String resources) {
        this.resources = resources;
    }

    public String getRefreshments() {
        return refreshments;
    }

    public void setRefreshments(String refreshments) {
        this.refreshments = refreshments;
    }

    public String getRefreshmentsTime() {
        return refreshmentsTime;
    }

    public void setRefreshmentsTime(String refreshmentsTime) {
        this.refreshmentsTime = refreshmentsTime;
    }
}
