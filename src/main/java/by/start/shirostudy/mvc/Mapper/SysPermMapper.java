package by.start.shirostudy.mvc.Mapper;

import by.start.shirostudy.common.web.BaseMapper;
import by.start.shirostudy.mvc.Entity.SysPerm;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Mapper
@Repository
public interface SysPermMapper extends BaseMapper<SysPerm> {
    Set<String> getSysUserPerms(Integer id);

    List<String> selectResourceTree(Integer roleId);

    List<SysPerm> selectAlls();

    List<SysPerm> selectResourceList(SysPerm sysPerm);
}
