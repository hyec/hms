package project.hms.model;

import project.hms.model.enums.RoomStatus;
import project.hms.model.enums.RoomType;

import javax.persistence.*;

@Entity
@Table(name = "rooms")
public class Room extends Base {

    @Column(nullable = false)
    private float price;

    @Column(unique = true, nullable = false)
    private int number;

    @Enumerated(EnumType.STRING)
    @Column(length = 10, nullable = false)
    private RoomType type;

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
