package project.hms.model;

import project.hms.model.enums.OrderStatus;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

/**
 * 数据库订单模型
 */
@Entity
@Table(name = "orders")
public class Order extends Base {

    /**
     * 房间
     */
    @ManyToOne
    private Room room;

    /**
     * 订单用户
     */
    @ManyToOne
    private User owner;

    /**
     * 价格
     */
    @Column(nullable = false)
    private Float price;

    /**
     * 入住时间
     */
    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false)
    private Date checkInTime;

    /**
     * 退房时间
     */
    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false)
    private Date checkOutTime;

    /**
     * 订单状态
     */
    @Enumerated(EnumType.STRING)
    @Column(length = 10, nullable = false)
    private OrderStatus status;

    /**
     * 入住用户列表
     */
    @ManyToMany
    @JoinTable(name = "stays")
    private Set<User> stays;

    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
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

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }

    public Set<User> getStays() {
        return stays;
    }

    public void setStays(Set<User> stays) {
        this.stays = stays;
    }
}
