package project.hms.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import project.hms.model.Order;
import project.hms.model.Room;
import project.hms.model.enums.OrderStatus;
import project.hms.model.enums.RoomType;
import project.hms.repository.OrderRepository;
import project.hms.repository.RoomRepository;

import java.util.*;

@Service
public class OrderService {

    private final RoomRepository roomRepository;

    private final OrderRepository orderRepository;

    @Autowired
    public OrderService(RoomRepository roomRepository, OrderRepository orderRepository) {
        this.roomRepository = roomRepository;
        this.orderRepository = orderRepository;
    }

    public List<Room> getAvailableRooms(RoomType type, Date start, Date end) {
        Calendar cal = Calendar.getInstance();

        cal.setTime(start);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        start = cal.getTime();

        cal.setTime(end);
        cal.set(Calendar.HOUR_OF_DAY, 23);
        cal.set(Calendar.MINUTE, 59);
        cal.set(Calendar.SECOND, 59);
        end = cal.getTime();

        List<Room> rooms = roomRepository.findAllByType(type);
        List<Order> orders = orderRepository.findAllByTime(start, end);

        Set<Room> unavailRooms = new HashSet<>();

        orders.forEach((order -> {
            if (order.getStatus() != OrderStatus.CANCEL &&
                    order.getStatus() != OrderStatus.COMPLETED)
                unavailRooms.add(order.getRoom());
        }));
        rooms.removeAll(unavailRooms);

        return rooms;
    }

}
