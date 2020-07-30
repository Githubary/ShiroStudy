package by.start.shirostudy.mvc.Controller;

import by.start.shirostudy.common.web.BaseController;
import by.start.shirostudy.mvc.Entity.SysPerm;
import by.start.shirostudy.mvc.Entity.SysRole;
import by.start.shirostudy.mvc.Service.SysPermService;
import by.start.shirostudy.utils.SubjectUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;

/**
 * @author bystart
 * @date 2020/7/10 10:24
 * 仔细！坚持！
 * ❥(^_-))
 */

@Controller
public class ResourceController extends BaseController {

    @Autowired
    private SysPermService sysPermService;

    @RequestMapping("/resource")
    public String ResourceManage(){
        if(SubjectUtils.isAuthenticated()){
            return "/admin/resource/resource";
        }else{
            return "modules/login";
        }
    }

    @RequiresPermissions("resource:list")
    @GetMapping("/resource/list")
    @ResponseBody
    public List<SysPerm> list(SysPerm sysPerm){
        List<SysPerm> resourceList=sysPermService.selectResourceList(sysPerm);
        return  resourceList;
    }


    /**
     * 加载角色资源列表树
     * @param sysRole
     * @return
     */
    @GetMapping("/resource/roleResourceTreeData")
    @ResponseBody
    public List<Map<String,Object>> roleResourceTreeData(SysRole sysRole){
        List<Map<String,Object>> trees=sysPermService.roleResourceTreeData(sysRole);
        return trees;
    }
}
