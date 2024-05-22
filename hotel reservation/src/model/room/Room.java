package model.room;
import model.room.roomType;
import java.util.Objects;
public class Room implements IRoom{

    private final String RoomNumber;
    private final Double RoomPrice;
    private final roomType Type;

    public Room(final String RoomNumber, final Double RoomPrice, final roomType Type){
        this.RoomNumber = RoomNumber;
        this.RoomPrice = RoomPrice;
        this.Type = Type;
    }
    @Override
    public String getRoomNumber() {
        return this.RoomNumber;
    }

    @Override
    public Double getRoomPrice(){
        return this.RoomPrice;
    }

    @Override
    public roomType getRoomType(){
        return this.Type;
    }
    @Override
    public boolean isFree(){
        return this.RoomPrice != null && this.RoomPrice.equals(0.0);
    }

    @Override
    public String toString() {
        return "Room Number: " + this.RoomNumber + " Price: " + this.RoomPrice + " roomType: " + this.Type;
    }

    @Override
    public boolean equals(Object o){
        if (this == o ) return true;
        if (o == null || getClass()!= o.getClass()) return false;
        Room room = (Room) o;
        return RoomNumber.equals(room.RoomNumber);
    }

    @Override
    public int hashCode(){
        return Objects.hash(RoomNumber);
    }
}
