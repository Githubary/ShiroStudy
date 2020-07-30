package by.start.shirostudy.common.PageHelper;

import by.start.shirostudy.mvc.Entity.SysUser;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author bystart
 * @date 2020/7/10 15:02
 * 仔细！坚持！
 * ❥(^_-))
 */

@Data
@EqualsAndHashCode(callSuper = false)
public class SysUserPageInfo extends BasePageInfo {
    private SysUser sysUser;
}
