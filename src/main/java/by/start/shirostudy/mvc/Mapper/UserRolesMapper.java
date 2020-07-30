package by.start.shirostudy.mvc.Mapper;

import by.start.shirostudy.common.web.BaseMapper;
import by.start.shirostudy.mvc.Entity.UserRoles;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface UserRolesMapper extends BaseMapper<UserRoles> {

}
