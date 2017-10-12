package project.hms.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import project.hms.models.Good;

import java.util.List;

public interface GoodRepository extends JpaRepository<Good, Integer> {

    List<Good> findAllByNameLike(String name);

}
