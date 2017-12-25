package project.hms.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import project.hms.model.Order;
import project.hms.model.Room;
import project.hms.model.User;
import project.hms.model.enums.OrderStatus;

import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * Order仓库类，用于订单的查询操作
 */
public interface OrderRepository extends JpaRepository<Order, Integer> {
    /**
     * 通过id查找订单
     */
    Optional<Order> findById(int id);

    /**
     * 通过owner查找订单
     */
    List<Order> findAllByOwner(User owner);

    /**
     * 通过房间查找订单
     */
    List<Order> findAllByRoom(Room room);

    /**
     * 通过订单状态查找订单
     */
    List<Order> findAllByStatus(OrderStatus orderStatus);

    /**
     * 通过时间段查找订单
     */
    @Query("select o from Order o " +
            "where o.checkOutTime > :startTime and o.checkInTime <= :startTime " +
            "or o.checkOutTime >= :endTime and o.checkInTime < :endTime ")
    List<Order> findAllByTime(@Param("startTime") Date startTime, @Param("endTime") Date endTime);
}
