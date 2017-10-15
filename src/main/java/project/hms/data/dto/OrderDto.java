package project.hms.data.dto;

import org.hibernate.validator.constraints.NotEmpty;
import project.hms.model.enums.RoomType;

import javax.validation.constraints.Future;
import java.util.Date;

public class OrderDto {

    @NotEmpty
    private RoomType roomType;

    @NotEmpty
    private Date checkInTime;

    @NotEmpty
    @Future
    private Date checkOutTime;

    public RoomType getRoomType() {
        return roomType;
    }

    public void setRoomType(RoomType roomType) {
        this.roomType = roomType;
    }

    public Date getCheckInTime() {
        return checkInTime;
    }

    public void setCheckInTime(Date checkInTime) {
        this.checkInTime = checkInTime;
    }

    public Date getCheckOutTime() {
        return checkOutTime;
    }

    public void setCheckOutTime(Date checkOutTime) {
        this.checkOutTime = checkOutTime;
    }
}
