package project.hms.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import project.hms.model.GoodOrder;
import project.hms.model.User;

import java.util.List;

/**
 * 商品订单的仓库类，用于查找商品订单
 */
public interface GoodOrderRepository extends JpaRepository<GoodOrder, Integer> {

    /**
     * 按owner查找
     */
    List<GoodOrder> findAllByOwner(User owner);

}
