package Admin;


import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;

public class userBookings {
    private int roomID;
    private int userID;
    private String username;
    private LocalTime startingTime;
    private LocalTime endingTime;
    private LocalDate Date;
    private String resources;
    private String refreshments;
    private String refreshmentsTime;

    public userBookings(int roomID, int userID, String username, LocalTime startingTime, LocalTime endingTime, LocalDate Date, String resources) {
        this.roomID = roomID;
        this.userID = userID;
        this.username = username;
        this.startingTime = startingTime;
        this.endingTime = endingTime;
        this.Date = Date;
        this.resources = resources;
    }

    public userBookings(int roomID, int userID, LocalTime startingTime, LocalDate Date) {
        this.roomID = roomID;
        this.userID = userID;
        this.startingTime = startingTime;
        this.Date = Date;
    }

    public userBookings(int roomID, int userID, String username, LocalTime startingTime, LocalTime endingTime, LocalDate Date, String resources, String refreshments, String refreshmentsTime) {
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

    public int getDuration() {
        return (int) Duration.between(this.getStartTime(), this.getEndTime()).toMinutes();
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

    public LocalTime getStartTime() {
        return startingTime;
    }

    public void setStartTime(LocalTime startTime) {
        this.startingTime = startTime;
    }

    public LocalTime getEndTime() {
        return endingTime;
    }

    public void setEndTime(LocalTime endTime) {
        this.endingTime = endTime;
    }

    public LocalDate getDate() {
        return Date;
    }

    public void setDate(LocalDate startDate) {
        this.Date = startDate;
    }

    public String getResources() {
        return resources;
    }

    public void setResources(String resources) {
        this.resources = resources;
    }

}
