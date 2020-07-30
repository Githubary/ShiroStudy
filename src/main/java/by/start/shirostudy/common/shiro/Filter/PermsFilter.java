package by.start.shirostudy.common.shiro.Filter;

import org.apache.shiro.web.filter.authz.PermissionsAuthorizationFilter;
import org.thymeleaf.util.StringUtils;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author bystart
 * @date 2020/7/9 17:06
 * 仔细！坚持！
 * ❥(^_-))
 */

public class PermsFilter extends PermissionsAuthorizationFilter {


    /**
     * 用来在用户认证后，但是访问perms资源时的情况
     * 如果没有权限，且是ajax请求，则在这边另做处理
     * 如果没有权限，是普通请求，则按照原来的进行
     * @param request
     * @param response
     * @return
     * @throws IOException
     */
    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws IOException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;
        String requestedWith = httpServletRequest.getHeader("X-Requested-With");
        if (requestedWith!=null &&
                StringUtils.equals(requestedWith, "XMLHttpRequest")) {//如果是ajax返回指定格式数据
            httpServletResponse.setCharacterEncoding("UTF-8");
            httpServletResponse.setContentType("application/json");
            httpServletResponse.getWriter().write("你没有权限去这里");
        } else {//如果是普通请求进行重定向
            httpServletResponse.sendRedirect("/401");
        }
        return false;
    }
}
