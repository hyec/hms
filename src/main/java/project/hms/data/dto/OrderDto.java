package project.hms.data.dto;

import project.hms.model.enums.RoomType;

import javax.validation.constraints.Future;
import javax.validation.constraints.NotNull;
import java.sql.Date;

public class OrderDto {

    @NotNull
    private RoomType roomType;

    @NotNull
    private Date checkInTime;

    @NotNull
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
