package project.hms.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import project.hms.models.GoodOrder;
import project.hms.models.User;

import java.util.List;

public interface GoodOrderRepository extends JpaRepository<GoodOrder, Integer> {

    List<GoodOrder> findAllByOwner(User owner);

}
