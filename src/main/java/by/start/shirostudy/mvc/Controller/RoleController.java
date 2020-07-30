package by.start.shirostudy.mvc.Controller;

import by.start.shirostudy.common.PageHelper.PageInfoReturn;
import by.start.shirostudy.common.PageHelper.SysRolePageInfo;
import by.start.shirostudy.common.ResponseEntity.ResponseEntity;
import by.start.shirostudy.common.web.BaseController;
import by.start.shirostudy.mvc.Entity.SysRole;
import by.start.shirostudy.mvc.Service.ShiroService;
import by.start.shirostudy.mvc.Service.SysRoleService;
import by.start.shirostudy.mvc.Service.SysUserService;
import by.start.shirostudy.utils.ResultFormat;
import by.start.shirostudy.utils.SubjectUtils;
import by.start.shirostudy.utils.UniqueID;
import cn.hutool.core.convert.Convert;
import cn.hutool.core.date.DateUtil;
import com.github.pagehelper.PageInfo;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

/**
 * @author bystart
 * @date 2020/7/10 10:24
 * 仔细！坚持！
 * ❥(^_-))
 */

@Controller
public class RoleController extends BaseController {

    @Autowired
    private SysRoleService sysRoleService;

    @Autowired
    private ShiroService shiroService;

    @Autowired
    private SysUserService sysUserService;

    @RequestMapping("/role")
    public String RoleManager(){
        if(SubjectUtils.isAuthenticated()){
            return "admin/role/role";
        }else{
            return "modules/login";
        }
    }


    @RequiresPermissions("role:list")
    @RequestMapping("/role/list")
    @ResponseBody
    public PageInfoReturn list(SysRolePageInfo sr){
        PageInfo<SysRole> pageInfo= sysRoleService.findAllRolesByPageInfo(sr);
        if(pageInfo==null){
            return null;
        }
        System.out.println("============1"+pageInfo.toString());
        /**获取用户的列表*/
        return ResultFormat.formatSysUserPageInfo(pageInfo);
    }

    @GetMapping("/role/add")
    public String add(){
        return "admin/role/add";
    }

    @RequiresPermissions("role:add")
    @PostMapping("/role/add")
    @ResponseBody
    public ResponseEntity add(SysRole sysRole){
        try {
            SysRole role = sysRoleService.getRolesByRoleName(sysRole.getRole());
            if(role!=null){
                return ResultFormat.error("该角色名称已经存在");
            }else{
                sysRole.setRoleId(UniqueID.getUUID().substring(0,6));
                sysRole.setCreatetime(DateUtil.date());
                sysRole.setUpdatetime(DateUtil.date());
                System.out.println("看看要插入的是个啥"+ sysRole.toString());
                if(sysRoleService.insert(sysRole)){
                    return ResultFormat.success("添加角色成功");
                }else{
                    return ResultFormat.error("添加角色失败，请重试");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResultFormat.error("添加用户失败，请重试");
        }
    }


    @GetMapping("/role/edit/{id}")
    public String edit(@PathVariable("id") Integer id, ModelMap map){
        map.addAttribute("role", sysRoleService.selectById(id));
        return "admin/role/edit";
    }

    @RequiresPermissions("role:edit")
    @PostMapping("/role/edit")
    @ResponseBody
    public ResponseEntity edit(SysRole sysRole){
        if(sysRoleService.updateUserMessage(sysRole)){
            return ResultFormat.success("更改信息成功！");
        }else{
            return ResultFormat.error("更改信息失败");
        }
    }

    /**
     * 返回出rule页面，修改用户资源的页面。
     * @param id
     * @param model
     * @return
     */
    @GetMapping("/role/rule/{id}")
    public String openRoleRule(@PathVariable("id") Integer id, Model model){
        model.addAttribute("id",id);
        return "admin/role/rule";
    }

    @RequiresPermissions("role:assign")
    @PostMapping("/role/assign")
    @ResponseBody
    public ResponseEntity assign(String id, String[] menuIds){
        System.out.println("菜单id们"+menuIds+"--------"+id);
        List<String> resourceIds=new ArrayList<>();
        if (menuIds.length!=0){
            /**拿String[]数组接收后就可以直接用数组的方法来将其转化成集合形式*/
            resourceIds= Arrays.asList(menuIds);
        }
        /**传入对应角色id，和要加入的资源们*/
        ResponseEntity responseEntity=sysRoleService.addAssignResourceById(id,resourceIds);
        //重新加载拥有角色的资源权限
        shiroService.reloadAuthorizingByRoleId(Convert.convert(Integer.class,id));
        return ResultFormat.success("分配资源成功");
    }

    /***
     * 删除角色
     * @param id
     * @return
     */
    @RequiresPermissions("role:delete")
    @PostMapping("/role/delete/{id}")
    @ResponseBody
    public ResponseEntity delete(@PathVariable("id") Integer id){
        if (sysUserService.findByRoleId(id).size()>0){
            return ResultFormat.error("当前角色存在用户，不能删除！");
        }
        if (sysRoleService.deleteById(id)){
            return ResultFormat.success("角色删除成功！");
        }else {
            return ResultFormat.error("角色删除失败！");
        }
    }


    //    {"removeIds":"6,8,9"}
    @RequiresPermissions("role:delete")
    @PostMapping("/role/removeAll")
    @ResponseBody
    public ResponseEntity removeAll(String ids){
        System.out.println("获取到的要删除的角色id"+ ids);
        String[] IDArray= ids.split(",");
        List<Integer> errorRemove=new ArrayList<>();
        for (String id:IDArray){
            if (sysUserService.findByRoleId(Integer.parseInt(id)).size()>0){
                return ResultFormat.error("当前角色存在用户，不能删除！");
            }else if(sysRoleService.deleteById(Integer.parseInt(id))){
                continue;
            }else{
                errorRemove.add(Integer.parseInt(id));
            }
        }
        if(CollectionUtils.isEmpty(errorRemove)){
            return ResultFormat.success("角色删除成功！");
        }else{
            Iterator iterator = errorRemove.iterator();
            String result=null;
            while(iterator.hasNext()){
                result=result+iterator.next();
            }
            return ResultFormat.error("角色id为"+result+"的删除失败！");
        }
    }

}
