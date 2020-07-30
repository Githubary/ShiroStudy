package by.start.shirostudy.mvc.Service;


import by.start.shirostudy.common.PageHelper.SysRolePageInfo;
import by.start.shirostudy.common.ResponseEntity.ResponseEntity;
import by.start.shirostudy.common.web.BaseService;
import by.start.shirostudy.mvc.Entity.SysRole;
import com.github.pagehelper.PageInfo;

import java.util.List;
import java.util.Set;

public interface SysRoleService extends BaseService<SysRole> {

    Set<String> getSysUserRoles(Integer id);

    List<SysRole> selectAllRoles(Integer id);

    PageInfo<SysRole> findAllRolesByPageInfo(SysRolePageInfo sr);

    SysRole getRolesByRoleName(String roleName);

    boolean updateUserMessage(SysRole sysRole);

    ResponseEntity addAssignResourceById(String id, List<String> resourceIds);
}
