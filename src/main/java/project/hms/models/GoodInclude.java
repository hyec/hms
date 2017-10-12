package project.hms.models;

import javax.persistence.*;

@Entity
@Table(name = "gincludes")
public class GoodInclude extends Base {

    @ManyToOne
    @JoinColumn(name = "good_id", referencedColumnName = "id")
    private Good good;

    @Column(nullable = false)
    private int amount = 0;

    @Column(nullable = false)
    private float cost = 0;

    public Good getGood() {
        return good;
    }

    public void setGood(Good good) {
        this.good = good;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public float getCost() {
        return cost;
    }

    public void setCost(float cost) {
        this.cost = cost;
    }
}
