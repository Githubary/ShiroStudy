package by.start.shirostudy.mvc.Mapper;

import by.start.shirostudy.common.PageHelper.SysRolePageInfo;
import by.start.shirostudy.common.web.BaseMapper;
import by.start.shirostudy.mvc.Entity.SysRole;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Mapper
@Repository
public interface SysRoleMapper extends BaseMapper<SysRole> {

    Set<String> getSysUserRoles(Integer id);

    List<SysRole> selectAllRoles(Integer id);

    List<SysRole> findAllRolesByPageInfo(SysRolePageInfo sr);

    SysRole getRolesByRoleName(String role);

    boolean updateUserMessage(SysRole sysRole);
}
