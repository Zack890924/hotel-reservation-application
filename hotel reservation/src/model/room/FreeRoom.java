package model.room;
import model.room.roomType;
public class FreeRoom extends Room{

    public FreeRoom(String RoomNumber, Double RoomPrice, roomType Type) {
        super(RoomNumber, 0.0, Type);
    }
    @Override
    public String toString(){
        return "Free Room: " + super.toString();
    }
}
