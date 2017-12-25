package project.hms.data.dto;

import project.hms.model.enums.RoomType;

import javax.validation.constraints.Future;
import javax.validation.constraints.NotNull;
import java.sql.Date;

/**
 * 订单信息表单
 */
public class OrderDto {

    /**
     * 房间类型
     */
    @NotNull
    private RoomType roomType;

    /**
     * 入住时间
     */
    @NotNull
    private Date checkInTime;

    /**
     * 退房时间
     */
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
