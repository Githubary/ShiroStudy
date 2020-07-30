package by.start.shirostudy.mvc.Entity;

import by.start.shirostudy.common.PageHelper.BasePageInfo;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * @author bystart
 * @date 2020/7/11 9:57
 * 仔细！坚持！
 * ❥(^_-))
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Table(name = "sys_role")
public class SysRole extends CommonEntity {

    /**装逼的id**/
    @Column(name = "roleId")
    private String roleId;
    /**角色名称**/
    @Column(name = "role")
    private String role;
    /**角色描述**/
    private String description;
    /**是否可用：1有效2删除**/
    private Integer status;

    /**是否选中**/
    @Transient
    private Integer selected;
}
