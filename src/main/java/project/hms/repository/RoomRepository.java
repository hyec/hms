package project.hms.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import project.hms.model.Room;
import project.hms.model.enums.RoomStatus;
import project.hms.model.enums.RoomType;

import java.util.List;
import java.util.Optional;

public interface RoomRepository extends JpaRepository<Room, Integer> {
    /**
     * 按id查找房间
     */
    Optional<Room> findById(int id);

    /**
     * 按类型查找房间
     */
    List<Room> findAllByType(RoomType type);

    /**
     *按房号查找房间
     */
    Optional<Room> findByNumber(int number);

    /**
     *按状态查找房间
     */
    List<Room> findAllByStatus(RoomStatus status);

}
