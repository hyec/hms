package project.hms.models;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "gorders")
public class GoodOrder extends Base {

    @Column(nullable = false)
    private float cost;

    @OneToMany()
    @JoinColumn(name = "gorder_id")
    private Set<GoodInclude> gincludes;

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
