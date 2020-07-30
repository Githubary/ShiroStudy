package by.start.shirostudy.utils;

import org.apache.shiro.SecurityUtils;

/**
 * @author bystart
 * @date 2020/7/10 10:15
 * 仔细！坚持！
 * ❥(^_-))
 */

public class SubjectUtils{

    public static boolean isAuthenticated(){
       return SecurityUtils.getSubject().isAuthenticated();
    }

    public static boolean isRemembered(){
        return SecurityUtils.getSubject().isRemembered();
    }

    public static boolean isPermitted(String perm){
        return SecurityUtils.getSubject().isPermitted(perm);
    }

}
