package project.hms.models;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "gorders")
public class GoodOrder extends Base {

    @ManyToOne
    private User owner;

    @Column(nullable = false)
    private float cost;

    @OneToMany()
    @JoinColumn(name = "gorder_id")
    private Set<GoodInclude> gincludes;

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

    public Set<GoodInclude> getGincludes() {
        return gincludes;
    }

    public void setGincludes(Set<GoodInclude> gincludes) {
        this.gincludes = gincludes;
    }
}