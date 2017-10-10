package project.hms.mappers;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import project.hms.models.Good;

import java.util.List;

@Mapper
public interface GoodMapper {

    @Select("SELECT * FROM goods")
    List<Good> getAll();

    @Select("SELECT * FROM goods WHERE id = #{id}")
    Good getById(int id);

    @Select("SELECT * FROM goods WHERE name = #{name}")
    List<Good> getByName(String name);

}
