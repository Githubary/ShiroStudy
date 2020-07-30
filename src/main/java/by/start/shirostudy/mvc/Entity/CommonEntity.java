package by.start.shirostudy.mvc.Entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.Date;

/**
 * @author bystart
 * @date 2020/7/13 14:11
 * 仔细！坚持！
 * ❥(^_-))
 */

@Data
public abstract class CommonEntity implements Serializable {

    private static final long serialVersionUID = -6426961904479859424L;

    /**编号，主键，资源表**/
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    /**创建时间**/
    @Column(name = "create_time")
    private Date createtime;
    /**修改时间**/
    @Column(name = "update_time")
    private Date updatetime;
}
