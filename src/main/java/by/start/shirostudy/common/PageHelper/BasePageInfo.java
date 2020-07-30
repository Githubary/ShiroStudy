package by.start.shirostudy.common.PageHelper;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Transient;
import java.util.Date;

/**
 * @author bystart
 * @date 2020/7/10 14:39
 * 仔细！坚持！
 * ❥(^_-))
 */


@Data
public class BasePageInfo {
    /**
     * 这个类的作用是自己写了一个PageInfo，
     * 用来提供PageHelper的初始属性。
     */

    public final static int DEFAULT_PAGE_SIZE=10;

    @Transient
    private int pageNum=1;

    @Transient
    private int pageSize=0;

    @Transient
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date startDate;

    @Transient
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date endDate;

    public int getPageSize(){
        return pageSize>0?pageSize:DEFAULT_PAGE_SIZE;
    }

}
