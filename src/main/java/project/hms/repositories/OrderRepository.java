package project.hms.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import project.hms.models.Order;
import project.hms.models.Room;
import project.hms.models.User;
import project.hms.models.enums.OrderStatus;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Integer> {

    List<Order> findAllByOwner(User owner);

    List<Order> findAllByRoom(Room room);

    List<Order> findAllByStatus(OrderStatus orderStatus);
}
