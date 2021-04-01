package Caterer;

public class CatererModel {

    private final String roomID;
    private final String time;
    private final String refreshment;

    public CatererModel(String roomID, String time, String refreshment) {
        this.roomID = roomID;
        this.time = time;
        this.refreshment = refreshment;
    }

    public String getRoomID() {
        return roomID;
    }

    public String getTime() {
        return time;
    }

    public String getRefreshment() {
        return refreshment;
    }
}

