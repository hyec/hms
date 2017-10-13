package project.hms.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import project.hms.model.GoodOrder;
import project.hms.model.User;

import java.util.List;

public interface GoodOrderRepository extends JpaRepository<GoodOrder, Integer> {

    List<GoodOrder> findAllByOwner(User owner);

}
