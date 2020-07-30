package by.start.shirostudy.mvc.Entity;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @author bystart
 * @date 2020/7/12 14:59
 * 仔细！坚持！
 * ❥(^_-))
 */

@Data
@Table(name = "sys_user_role")
public class UserRoles implements Serializable {

    private static final long serialVersionUID = -2607982034244670052L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "userId")
    private Integer userId;

    @Column(name = "roleId")
    private Integer roleId;
}
