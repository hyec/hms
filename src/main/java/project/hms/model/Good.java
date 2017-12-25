package project.hms.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * 数据库商品模型
 */
@Entity
@Table(name = "goods")
public class Good extends Base {

    /**
     * 商品名称
     */
    @Column(length = 64, nullable = false)
    private String name;

    /**
     * 商品价格
     */
    @Column(nullable = false)
    private float price = 0;

    /**
     * 商品描述
     */
    @Column(length = 512)
    private String description;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
