package by.start.shirostudy.mvc.Service.ServiceImpl;

import by.start.shirostudy.mvc.Entity.SysUser;
import by.start.shirostudy.mvc.Security.MyRealm;
import by.start.shirostudy.mvc.Service.ShiroService;
import by.start.shirostudy.mvc.Service.SysUserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.mgt.RealmSecurityManager;
import org.apache.shiro.subject.SimplePrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * @author bystart
 * @date 2020/7/13 20:15
 * 仔细！坚持！
 * ❥(^_-))
 */

@Service
public class ShiroServiceImpl implements ShiroService {

    private static final Logger logger= LoggerFactory.getLogger(ShiroServiceImpl.class);

    @Autowired
    private SysUserService sysUserService;


    /**一个用户拥有对应的角色
     * 不同的角色拥有不同的资源
     * 只需要更新角色拥有的资源，
     * 那么用户所拥有的资源也即发生改变
     */
    @Override
    public void reloadAuthorizingByUserId(SysUser user) {
        RealmSecurityManager realmSecurityManager=(RealmSecurityManager) SecurityUtils.getSecurityManager();
        MyRealm myRealm=(MyRealm)realmSecurityManager.getRealms().iterator().next();
        Subject subject=SecurityUtils.getSubject();
        String realmName=subject.getPrincipals().getRealmNames().iterator().next();
        SimplePrincipalCollection simplePrincipalCollection=new SimplePrincipalCollection(user,realmName);
        subject.runAs(simplePrincipalCollection);
        myRealm.getAuthorizationCache().remove(subject.getPrincipals());
        subject.releaseRunAs();
        logger.info("[以下用户权限更新成功！]-[{}]",user.getUsername());
    }

    @Override
    public void reloadAuthorizingByRoleId(Integer roleId) {
        /**寻找到拥有当前角色的用户们*/
        List<SysUser> userList=sysUserService.getSysUserListById(roleId);
        if (CollectionUtils.isEmpty(userList)){
            return;
        }
        /**更新每一个用户*/
        for (SysUser user:userList){
            reloadAuthorizingByUserId(user);
        }
    }
}
