package by.start.shirostudy.mvc.Service;

import by.start.shirostudy.mvc.Entity.SysUser;

public interface ShiroService {

    /***
     * 重新加载用户权限
     * @param user
     */
    void reloadAuthorizingByUserId(SysUser user);


    /***
     * 重新加载所有拥有roleId角色的用户权限
     * @param roleId
     */
    void reloadAuthorizingByRoleId(Integer roleId);
}
