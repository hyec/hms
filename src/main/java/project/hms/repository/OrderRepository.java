package project.hms.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import project.hms.model.Order;
import project.hms.model.Room;
import project.hms.model.User;
import project.hms.model.enums.OrderStatus;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Integer> {

    List<Order> findAllByOwner(User owner);

    List<Order> findAllByRoom(Room room);

    List<Order> findAllByStatus(OrderStatus orderStatus);
}
