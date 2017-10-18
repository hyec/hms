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

public interface OrderRepository extends JpaRepository<Order, Integer> {

    Optional<Order> findById(int id);

    List<Order> findAllByOwner(User owner);

    List<Order> findAllByRoom(Room room);

    List<Order> findAllByStatus(OrderStatus orderStatus);

    @Query("select o from Order o " +
            "where o.checkInTime >= :startTime and o.checkInTime <= :endTime " +
            "or o.checkOutTime >= :startTime and o.checkOutTime <= :endTime ")
    List<Order> findAllByTime(@Param("startTime") Date startTime, @Param("endTime") Date endTime);
}
