package project.hms.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import project.hms.model.Room;
import project.hms.model.enums.RoomStatus;
import project.hms.model.enums.RoomType;

import java.util.List;
import java.util.Optional;

public interface RoomRepository extends JpaRepository<Room, Integer> {

    Optional<Room> findById(int id);

    List<Room> findAllByType(RoomType type);

    Optional<Room> findByNumber(int number);

    List<Room> findAllByStatus(RoomStatus status);

}
