package project.hms.model;

import project.hms.model.enums.GoodsOrderStatus;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "gorders")
public class GoodOrder extends Base {

    @ManyToOne
    private User owner;

    @Column(nullable = false)
    private float cost;

    @OneToMany(cascade = {CascadeType.ALL})
    @JoinColumn(name = "gorder_id")
    private List<GoodInclude> gincludes;

    @Enumerated(EnumType.STRING)
    @Column(length = 10, nullable = false)
    private GoodsOrderStatus status;

    @ManyToOne
    private Room room;

    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }

    public GoodsOrderStatus getStatus() {
        return status;
    }

    public void setStatus(GoodsOrderStatus status) {
        this.status = status;
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    public float getCost() {
        return cost;
    }

    public void setCost(float cost) {
        this.cost = cost;
    }

    public List<GoodInclude> getGincludes() {
        return gincludes;
    }

    public void setGincludes(List<GoodInclude> gincludes) {
        this.gincludes = gincludes;
    }
}
