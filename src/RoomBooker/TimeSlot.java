package RoomBooker;

import java.sql.Time;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;

public class TimeSlot {

    private LocalTime startingTime;
    private LocalTime endingTime;

    public TimeSlot(String startingTime, String endingTime){
        this.startingTime = LocalTime.parse(startingTime);
        this.endingTime = LocalTime.parse(startingTime);
    }

    public static ArrayList<TimeSlot> returnTimeSlots(LocalTime startingTime, int slots){
        ArrayList<TimeSlot> temporaryTimeSlotAL = new ArrayList<>();
        LocalTime endingTime;
        for (int i = 0; i < slots; i++) {
            endingTime = startingTime.plus(30,ChronoUnit.MINUTES);
            temporaryTimeSlotAL.add(new TimeSlot(startingTime.toString(), endingTime.toString()));
            startingTime = startingTime.plus(30, ChronoUnit.MINUTES);
        }
        return temporaryTimeSlotAL;
    }

    public static int getSlotNumber(LocalTime startingTime, LocalTime endingTime){

        long timeDifference = startingTime.until(endingTime, ChronoUnit.MINUTES);
        return (int)timeDifference/30;
    }

    public boolean exists(TimeSlot ts){
        return (startingTime.equals(ts.getStartingTime())) && (endingTime.equals(ts.getEndingTime()));
    }

    public LocalTime getStartingTime() {
        return startingTime;
    }

    public LocalTime getEndingTime() {
        return endingTime;
    }
    @Override
    public String toString() {
        return startingTime+" to "+endingTime;
    }

}
