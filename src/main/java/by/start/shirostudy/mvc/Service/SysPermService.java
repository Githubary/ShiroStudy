package by.start.shirostudy.mvc.Service;

import by.start.shirostudy.common.web.BaseService;
import by.start.shirostudy.mvc.Entity.SysPerm;
import by.start.shirostudy.mvc.Entity.SysRole;

import java.util.List;
import java.util.Map;
import java.util.Set;

public interface SysPermService extends BaseService<SysPerm> {
    Set<String> getSysUserPerms(Integer id);

    List<Map<String, Object>> roleResourceTreeData(SysRole sysRole);

    List<SysPerm> selectResourceList(SysPerm sysPerm);
}
