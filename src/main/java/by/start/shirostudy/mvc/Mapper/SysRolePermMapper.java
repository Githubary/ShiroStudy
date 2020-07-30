package by.start.shirostudy.mvc.Mapper;

import by.start.shirostudy.common.web.BaseMapper;
import by.start.shirostudy.mvc.Entity.SysRolePerm;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface SysRolePermMapper extends BaseMapper<SysRolePerm> {
}
