package by.start.shirostudy.common.PageHelper;

import by.start.shirostudy.mvc.Entity.SysRole;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author bystart
 * @date 2020/7/13 14:08
 * 仔细！坚持！
 * ❥(^_-))
 */

@Data
@EqualsAndHashCode(callSuper = false)
public class SysRolePageInfo extends BasePageInfo {
    private SysRole sysRole;
}
