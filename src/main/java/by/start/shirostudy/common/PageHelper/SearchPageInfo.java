package by.start.shirostudy.common.PageHelper;

/**
 * @author bystart
 * @date 2020/7/12 21:01
 * 仔细！坚持！
 * ❥(^_-))
 */

import lombok.Data;
import lombok.EqualsAndHashCode;


@EqualsAndHashCode(callSuper = false)
@Data
public class SearchPageInfo extends BasePageInfo{


    private String roleName;
    private Integer sex;
    private Integer status;
    private String beginTime;
    private String endTime;
}
