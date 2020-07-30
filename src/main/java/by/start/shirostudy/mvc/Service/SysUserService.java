package by.start.shirostudy.mvc.Service;

import by.start.shirostudy.common.PageHelper.SearchPageInfo;
import by.start.shirostudy.common.PageHelper.SysUserPageInfo;
import by.start.shirostudy.common.web.BaseService;
import by.start.shirostudy.mvc.Entity.SysUser;
import com.github.pagehelper.PageInfo;

import java.util.Collection;
import java.util.List;


/**
 * @author bystart
 * @date 2020/7/10 15:04
 * 仔细！坚持！
 * ❥(^_-))
 */
public interface SysUserService extends BaseService<SysUser> {

    SysUser getSysUserByLoginName(String username);

    PageInfo<SysUser> findAllSysUserByPageInfo(SysUserPageInfo su);


    boolean updateUserMessage(SysUser sysUser);

    void saveUserRoles(Integer userId, String roleIds);

    PageInfo<SysUser> searchSysUserByPageInfo(SearchPageInfo s);

    List<SysUser> getSysUserListById(Integer roleId);

    List<SysUser> findByRoleId(Integer id);
}
