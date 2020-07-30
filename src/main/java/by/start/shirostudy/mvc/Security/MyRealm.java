package by.start.shirostudy.mvc.Security;

import by.start.shirostudy.mvc.Entity.SysUser;
import by.start.shirostudy.mvc.Service.SysPermService;
import by.start.shirostudy.mvc.Service.SysRoleService;
import by.start.shirostudy.mvc.Service.SysUserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.ByteSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import java.util.Iterator;
import java.util.Set;

/**
 * @author bystart
 * @date 2020/7/4 16:09
 * 仔细！坚持！
 * ❥(^_-))
 */


public class MyRealm extends AuthorizingRealm {

    private static Logger logger = LoggerFactory.getLogger(MyRealm.class);

    @Autowired
    @Qualifier("sysUserServiceImpl")
    private SysUserService sysUserService;

    @Autowired
    @Qualifier("sysRoleServiceImpl")
    private SysRoleService sysRoleService;

    @Autowired
    @Qualifier("sysPermServiceImpl")
    private SysPermService sysPermService;

    /**
     * 处理授权消息的
     * @param principals
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {

        if (principals == null) {
            return null;
        }

        logger.info("即将进行授权操作");

        /**
         * 这个是AuthorizationInfo的一个实现类
         */
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        /**
         * 利用getPrimaryPrincipal的方法获取第一个，因为principal
         * 其实是有顺序的，这个方法会直接获取Iterator的第一个principal
         *
         * 第一步 利用PrincipalCollection获取登录的用户的信息
         * 这个Principal即我们在认证的时候，返回的那个地方添加的principal。
         */
//        Principal principal = (Principal) principals.getPrimaryPrincipal();

        /**
         * 第二步，利用登录的用户信息来查询数据库，获取到对应的用户的权限表里的权限
         * 但在这边因为不添加数据库，所以写一个假的操作。
         */
//        if(("123").equals(principal.getName())){
            /**
             * 可以在这边直接加一个权限，也可以利用set集合添加
             */

            info.addStringPermission("user:user");
//        }
        SysUser sysUser=(SysUser) principals.getPrimaryPrincipal();
//        Set<String> roles = sysRoleService.getSysUserRoles(sysUser.getId());
        Set<String> perms = sysPermService.getSysUserPerms(sysUser.getId());
        System.out.println(perms);
        Iterator iterator = perms.iterator();
        while(iterator.hasNext()){
            System.out.println("权限"+iterator.next());
        }
//        info.setRoles(roles);
        info.setStringPermissions(perms);
        logger.info("授权完成，并存入缓存");
        doClearCache(principals);
        clearCache(principals);
        return info;
    }

    /**
     * 处理认证消息的
     * 这里的authenticationToken其实就是我们刚刚创建好的令牌的内容
     * @param authenticationToken
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        System.out.println("这是第一个Realm的认证");

        /**先将当前令牌转成UsernamPasswordToken,操作方法会多一点*/
        UsernamePasswordToken token = (UsernamePasswordToken) authenticationToken;

        /**再在这边验证一下会话是谁的好吧*/
        Subject currentUser = SecurityUtils.getSubject();
        Session session = currentUser.getSession();
        System.out.println("当前的会话ID"+session.getId());

        /**如果想要进行验证码的验证，这边建议
         * 写一个前端的验证表单，把验证放在前端。
         * 或者在后端写一个表单验证类，绑定在前端上，多次登录错误后出现
         * if(isNeedValidate Model参数，在登录失败时增加该参数){
         *     showValidate(类)
         * }
         * 在这边先不实现
         * /

        /**直接利用username去查找数据库有无该用户*/
        SysUser sysUser = sysUserService.getSysUserByLoginName(token.getUsername());
        /**
         * 这边暂时以一个假的数据来代替
         * 下面这个user是密码为123456经过加密后的值
         * 下面这个user2是密码为abc123经过加密后的值
         */
//        SysUser sysUser = new SysUser("123","01d4398bb1dbe64af18c43b17447d06be6b99777");

        if(sysUser !=null){
            /**
             * 开始进入密码的验证！密码的验证由shiro完成，
             * 我们要做的就是返回一个SimpleAuthenticationInfo
             * 但由于我们存在服务器中的密码应该不是明文的，所以
             * sysUser.getPassword()应该得到的是一个经过加密的一个密码
             * 而加密有两种方式
             * 1.一种是直接利用SHA-1或者利用MD5进行加密
             * 2.还有一种，则是利用加盐（salt），再利用SHA-1,MD5进行加密。
             * 加盐的意思就是在密码头部加上一些随意的字符，那么在后面对密码进行hash计算的时候，得到的密码就更复杂
             */

            /**
             * 接下来提供几种设置盐的方法
             * 是的，你可以拿你的用户名做这个盐
             * 也可以拿你的密码（在数据库中是加密后密码），我们可以取其前16位（可以自定义）作为这个盐
             */

            /**如果有数据库了则从用户本身数据取值*/
            System.out.println("从数据库获取到的密码："+sysUser.getPassword());
            ByteSource salt= ByteSource.Util.bytes(sysUser.getSalt());
            System.out.println("从数据库获取到的盐："+salt);

            /**-----1------
             * 使用的是shiro提供的一个方法，最后是调用了String的encode方法
             * */
//            ByteSource salt = ByteSource.Util.bytes("a94d5cd0079cfc8d");
//            System.out.println("这是数据库中加的盐"+salt);
            /**-----2------*/

            /**-----3------*/
            /**-----4------*/
            /**-----5------*/

            /**
             * 点击进入该方法，可以看到该方法有多重方式进行创建，我们选择最复杂的一种，有加salt的那一种
             * 参数分析：
             * 1.principle 标识用户的唯一，可以是唯一id，可以是整个user。
             * 2.hashedCredentials 传入的待验证的密码
             * 3.salt 盐
             * 4.默认使用getName即可
             *
             * 问题：这里只是传入了一个用户数据库中的密码，那么是怎么进行比较的呢
             * 答案：其实这里的SimpleAuthenticationInfo会和之前存储的UsernamePasswordToken二者进行密码比对，
             * 至于逻辑在哪找到，请看 README
             */
            return new SimpleAuthenticationInfo(sysUser, sysUser.getPassword(),salt,getName());
        }else{
            /**
             * 如果用户为空，直接返回一个空值即可
             */
            return null;
        }
    }

    /**
     * 为了方便我写出密码的简单加密后的密文
     * @param args
     */
    public static void main(String[] args) {
        String password = "123456";
        String algorithmName = "SHA-1";
        int hashIterations=1024;
        /**
         * shiro提供的一种加密方法
         */
        Object noSalt=new SimpleHash(algorithmName,password.toCharArray(),null,hashIterations);
        System.out.println("对原始密码进行加密后的密文----"+noSalt);
        System.out.println("取到该密文的前16位作为盐值"+noSalt.toString().substring(0,16));
        ByteSource salt = ByteSource.Util.bytes(noSalt.toString().substring(0,16));
        Object HaveSalt = new SimpleHash(algorithmName,password,salt,hashIterations);
        System.out.println("最后得到的密文"+HaveSalt);
    }


    @Override
    protected void doClearCache(PrincipalCollection principals) {
        super.doClearCache(principals);
    }

    @Override
    protected void clearCachedAuthenticationInfo(PrincipalCollection principals) {
        super.clearCachedAuthenticationInfo(principals);
    }

    @Override
    public boolean isCachingEnabled() {
        return super.isCachingEnabled();
    }

    @Override
    protected void clearCache(PrincipalCollection principals) {
        super.clearCache(principals);
    }





}
