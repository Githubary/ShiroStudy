package by.start.shirostudy.common.Global.Exception;

/**
 * @author bystart
 * @date 2020/7/6 10:20
 * 仔细！坚持！
 * ❥(^_-))
 */

public class PrincipalIdNullException extends RuntimeException  {

    private static final String MESSAGE = "Principal Id shouldn't be null!";

    public PrincipalIdNullException(Class clazz, String idMethodName) {
        super(clazz + " id field: " +  idMethodName + ", value is null\n" + MESSAGE);
    }
}
