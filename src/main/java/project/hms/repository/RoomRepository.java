package project.hms.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import project.hms.model.Room;
import project.hms.model.enums.RoomStatus;
import project.hms.model.enums.RoomType;

import java.util.List;

public interface RoomRepository extends JpaRepository<Room, Integer> {

    List<Room> findAllByType(RoomType type);

    List<Room> findByNumber(int number);

    List<Room> findAllByStatus(RoomStatus status);

}
