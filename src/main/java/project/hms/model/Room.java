package project.hms.model;

import project.hms.model.enums.RoomStatus;
import project.hms.model.enums.RoomType;

import javax.persistence.*;

/**
 * 数据库房间模型
 */
@Entity
@Table(name = "rooms")
public class Room extends Base {

    /**
     * 房间单价
     */
    @Column(nullable = false)
    private float price;

    /**
     * 房间号
     */
    @Column(unique = true, nullable = false)
    private int number;

    /**
     * 房间类型
     */
    @Enumerated(EnumType.STRING)
    @Column(length = 10, nullable = false)
    private RoomType type;

    /**
     * 房间状态
     */
    @Enumerated(EnumType.STRING)
    @Column(length = 10, nullable = false)
    private RoomStatus status;

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public RoomType getType() {
        return type;
    }

    public void setType(RoomType type) {
        this.type = type;
    }

    public RoomStatus getStatus() {
        return status;
    }

    public void setStatus(RoomStatus status) {
        this.status = status;
    }
}
