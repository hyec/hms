package project.hms.model;

import javax.persistence.*;

/**
 * 数据库商品订餐类，用于连接订餐和商品
 */
@Entity
@Table(name = "gincludes")
public class GoodInclude extends Base {

    /**
     * 商品
     */
    @ManyToOne
    @JoinColumn(name = "good_id", referencedColumnName = "id")
    private Good good;

    /**
     * 数量
     */
    @Column(nullable = false)
    private int amount = 0;

    /**
     * 总价
     */
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
