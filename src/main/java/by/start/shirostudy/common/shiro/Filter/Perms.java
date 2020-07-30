package by.start.shirostudy.common.shiro.Filter;

import org.apache.shiro.SecurityUtils;
import org.springframework.stereotype.Component;

/**
 * @author bystart
 * @date 2020/7/4 20:23
 * 仔细！坚持！
 * ❥(^_-))
 */
@Component("perms")
public class Perms {
    public boolean hasPerm(String permission){
        return SecurityUtils.getSubject().isPermitted(permission);
    }
}
