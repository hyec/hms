package project.hms.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import project.hms.models.Room;
import project.hms.models.enums.RoomStatus;
import project.hms.models.enums.RoomType;

import java.util.List;

public interface RoomRepository extends JpaRepository<Room, Integer> {

    List<Room> findAllByType(RoomType type);

    List<Room> findByNumber(int number);

    List<Room> findAllByStatus(RoomStatus status);

}
