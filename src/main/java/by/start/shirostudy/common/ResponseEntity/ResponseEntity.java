package by.start.shirostudy.common.ResponseEntity;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author bystart
 * @date 2020/7/9 20:35
 * 仔细！坚持！
 * ❥(^_-))
 */

@Data
@AllArgsConstructor
public class ResponseEntity<T> {

    public static final int DEFAULT_SUCCESS_CODE=200;
    public static final int DEFAULT_SERVERERROR_CODE=500;
    public static final int DEFAULT_NOAUTH_CODE=401;
    public static final int DEFAULT_NOTFOUNDSOURCE_CODE=404;

    private Integer status;
    private String message;
    private T data;


}
