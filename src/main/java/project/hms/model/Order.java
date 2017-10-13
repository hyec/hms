package project.hms.model;

import project.hms.model.enums.OrderStatus;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "orders")
public class Order extends Base {

    @ManyToOne
    private Room room;

    @ManyToOne
    private User owner;

    @Column(nullable = false)
    private float price;

    @Enumerated(EnumType.STRING)
    @Column(length = 10, nullable = false)
    private OrderStatus status;

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
