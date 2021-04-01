package Cleaner;

    public class CleaningFreeSlot {
        private final String roomID;
        private final String startTime;
        private final String endTime;

        public CleaningFreeSlot(String roomID, String startTime, String endTime) {
            this.roomID = roomID;
            this.startTime = startTime;
            this.endTime = endTime;
        }

        @Override
        public String toString(){
            return this.roomID + " "+ this.startTime + " " + this.endTime;
        }

        public String getRoomID() {
            return roomID;
        }

        public String getStartTime() {
            return startTime;
        }

        public String getEndTime() {
            return endTime;
        }
    }

