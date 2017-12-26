package project.hms.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import project.hms.model.Good;

import java.util.List;
import java.util.Optional;

/**
 * 商品的仓库类，用于查询商品
 */
public interface GoodRepository extends JpaRepository<Good, Integer> {

    /**
     * 按id查找商品
     */
    Optional<Good> findById(int id);

    /**
     * 通过名称查找
     */
    List<Good> findAllByNameLike(String name);

}
