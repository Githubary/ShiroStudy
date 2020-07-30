package by.start.shirostudy.mvc.Entity;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @author bystart
 * @date 2020/7/13 20:30
 * 仔细！坚持！
 * ❥(^_-))
 */

@Data
@Table(name = "sys_role_resource")
public class SysRolePerm implements Serializable {
    private static final long serialVersionUID = 125690822062738909L;
    /**编号，主键，资源表**/
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    /**角色id**/
    @Column(name = "roleId")
    private String roleId;
    /**资源id**/
    @Column(name = "resourceId")
    private String resourceId;
}
