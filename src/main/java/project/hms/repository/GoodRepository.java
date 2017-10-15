package project.hms.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import project.hms.model.Good;

import java.util.List;
import java.util.Optional;

public interface GoodRepository extends JpaRepository<Good, Integer> {

    Optional<Good> findById(int id);

    List<Good> findAllByNameLike(String name);

}
