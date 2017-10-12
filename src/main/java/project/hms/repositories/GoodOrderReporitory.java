package project.hms.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import project.hms.models.GoodOrder;

import java.util.List;

public interface GoodOrderReporitory extends JpaRepository<GoodOrder, Integer> {

    List<GoodOrder> findById(int id);

}
