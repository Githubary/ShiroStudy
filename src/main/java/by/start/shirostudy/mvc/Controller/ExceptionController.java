package by.start.shirostudy.mvc.Controller;

import by.start.shirostudy.common.web.BaseController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author bystart
 * @date 2020/7/9 17:11
 * 仔细！坚持！
 * ❥(^_-))
 */

@Controller
public class ExceptionController extends BaseController {


    @RequestMapping("/401")
    public String UnAuthorized(){
        logger.info("未获得授权");
        return "error/401";
    }

    @RequestMapping("/404")
    public String NoResourcesFound(){
        logger.info("资源找不到了");
        return "error/404";
    }

    @RequestMapping("/500")
    public String ServerError(){
        logger.info("代码出错了");
        return "error/500";
    }
}
