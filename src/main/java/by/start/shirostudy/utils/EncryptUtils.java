package by.start.shirostudy.utils;

import by.start.shirostudy.mvc.Entity.SysUser;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.util.ByteSource;

/**
 * @author bystart
 * @date 2020/7/10 22:13
 * 仔细！坚持！
 * ❥(^_-))
 */


public class EncryptUtils {

    private static  String algorithmName1 = "SHA-1";
    private static  String algorithmName2 = "MD5";
    private static int hashIterations = 1024;

    /**
     * 用来加密的工具类
     */
    public static SysUser setPasswordAndSalt(SysUser sysUser){
        String password = sysUser.getPassword();
        Object noSalt=new SimpleHash(algorithmName1,password.toCharArray(),null,hashIterations);
        ByteSource salt = ByteSource.Util.bytes(noSalt.toString().substring(0,16));
        Object EncryptPassword = new SimpleHash(algorithmName1,password,salt,hashIterations);
        sysUser.setPassword(EncryptPassword.toString());
        sysUser.setSalt(noSalt.toString().substring(0,16));
        return sysUser;
    }

}
