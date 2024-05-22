package model.room;
import model.room.roomType;
public interface IRoom {
    public String getRoomNumber();
    public Double getRoomPrice();
    public boolean isFree();
    public roomType getRoomType();
}
