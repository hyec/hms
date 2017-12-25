package project.hms.service;

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

/**
 * 定时任务服务
 */
@Component
public class SchedulerService {
    private final OrderRepository orderRepository;
    private final RoomRepository roomRepository;

    public SchedulerService(OrderRepository orderRepository, RoomRepository roomRepository) {
        this.orderRepository = orderRepository;
        this.roomRepository = roomRepository;
    }

    /**
     * 定时执行的函数，用于每天12点自动将订单退房日期为今天的订单设置为完成状态，房间设置为等待打扫
     */
    @Scheduled(cron = "0 0 12 * * ?")
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
