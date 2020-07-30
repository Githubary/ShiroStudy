package by.start.shirostudy.common.PageHelper;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * @author bystart
 * @date 2020/7/10 16:23
 * 仔细！坚持！
 * ❥(^_-))
 */

@Data
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class PageInfoReturn {
    private Long total;
    private List rows;

    public PageInfoReturn() {}
}
