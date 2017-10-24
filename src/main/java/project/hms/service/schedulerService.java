package project.hms.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import project.hms.model.Order;
import project.hms.model.Room;
import project.hms.model.enums.OrderStatus;
import project.hms.model.enums.RoomStatus;
import project.hms.repository.OrderRepository;
import project.hms.repository.RoomRepository;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;


@Component
public class schedulerService {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final OrderRepository orderRepository;
    private final RoomRepository roomRepository;

    public schedulerService(OrderRepository orderRepository, RoomRepository roomRepository) {
        this.orderRepository = orderRepository;
        this.roomRepository = roomRepository;
    }

    @Scheduled(cron = "0 26 11 * * ?")
    public void testTasks() {
        List<Order> orders = orderRepository.findAll();
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        for (Order order : orders) {
            if (dateFormat.format(order.getCheckOutTime()).equals(dateFormat.format(Calendar.getInstance().getTime()))) {
                Room room = order.getRoom();
                room.setStatus(RoomStatus.DIRTY);
                order.setStatus(OrderStatus.COMPLETED);
                orderRepository.save(order);
                roomRepository.save(room);
            }
        }
    }
}
