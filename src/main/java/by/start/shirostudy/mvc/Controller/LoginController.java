package by.start.shirostudy.mvc.Controller;

import by.start.shirostudy.common.ResponseEntity.ResponseEntity;
import by.start.shirostudy.common.web.BaseController;
import by.start.shirostudy.utils.ResultFormat;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;


/**
 * @author bystart
 * @date 2020/7/4 15:57
 * 仔细！坚持！
 * ❥(^_-))
 */

@Controller
public class LoginController extends BaseController {

    @RequestMapping(value = "/login",method = RequestMethod.GET)
    public String login(){
        Subject currentUser = SecurityUtils.getSubject();
        if(currentUser.isAuthenticated()){
            return "redirect:/index";
        }
       return "modules/login";
    }

    /**
     * 最直接的写法，在这边写登录逻辑,普通一点的
     * 登录失败了
     * @param model
     * @return
     */
    @RequestMapping(value = "/login",method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity login(@RequestParam("username") String username,
                                @RequestParam("password") String password , HttpServletRequest request,
                                Model model){

        System.out.println("---获取到的---" +
                            "名字:"+username+
                            "---密码:"+password);
        Subject currentUser = SecurityUtils.getSubject();
        if(!currentUser.isAuthenticated()){
            /**验证当前是谁持有会话*/
            Session session = currentUser.getSession();
            session.setAttribute("current",currentUser);
            System.out.println("当前持有会话的对象是："+session.getAttribute("current"));

            /**生成用于登录的令牌*/
            UsernamePasswordToken token = new UsernamePasswordToken(username,password);
            Object rememberMe = request.getParameter("rememberMe");
            if(rememberMe.equals("on")){
                System.out.println("是否RememberMe:---"+rememberMe);
                SecurityUtils.getSubject().getSession().setTimeout(1000*60*30L);
                token.setRememberMe(true);
            }
            System.out.println();
            try{

                /**执行登录*/
                /**执行了这个后，经过一系列的逻辑，来到doGetAuthenticationInfo实现方法*/
                currentUser.login(token);

            }catch (UnknownAccountException e){
                logger.info("未知的用户账号！");
                return ResultFormat.error("该用户不存在");
            }catch (IncorrectCredentialsException e){
                logger.info("密码验证出错了！");
                return ResultFormat.error("密码输入错误");
            }catch (LockedAccountException e){
                logger.info("账号被锁定了！");
                return ResultFormat.error("你的账号被锁定了");
            }catch (ExcessiveAttemptsException e){
                logger.info("认证次数过多！");
                return ResultFormat.error("认证次数过多，请稍后再试");
            }catch (Exception e){
                return ResultFormat.error("没有一个认证成功");
            }
            return ResultFormat.success("登录成功");
        }else{
            return ResultFormat.success("登录成功");
        }
    }




    @RequestMapping("/index")
    public String index(){
        return "modules/index";
    }

    @RequestMapping("/bystart")
    public String bystart(){
        Boolean a=SecurityUtils.getSubject().isAuthenticated();
        if(a){
            return "modules/bystart";
        }else{
            return "modules/login";
        }
    }

    /**
     * 退出登录
     * @return
     */
    @GetMapping("/logout")
    public String logout() {
        return "modules/login";
    }


}
