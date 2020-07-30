package by.start.shirostudy.mvc.Controller;

import by.start.shirostudy.common.PageHelper.SearchPageInfo;
import by.start.shirostudy.common.PageHelper.SysUserPageInfo;
import by.start.shirostudy.common.PageHelper.PageInfoReturn;
import by.start.shirostudy.common.ResponseEntity.ResponseEntity;
import by.start.shirostudy.common.web.BaseController;
import by.start.shirostudy.mvc.Entity.SysRole;
import by.start.shirostudy.mvc.Entity.SysUser;
import by.start.shirostudy.mvc.Service.SysRoleService;
import by.start.shirostudy.mvc.Service.SysUserService;
import by.start.shirostudy.utils.EncryptUtils;
import by.start.shirostudy.utils.ResultFormat;
import by.start.shirostudy.utils.SubjectUtils;
import by.start.shirostudy.utils.UniqueID;
import cn.hutool.core.date.DateUtil;
import com.github.pagehelper.PageInfo;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * @author bystart
 * @date 2020/7/10 10:14
 * 仔细！坚持！
 * ❥(^_-))
 */

@Controller
public class UserController extends BaseController {


    @Autowired
    private SysUserService sysUserService;

    @Autowired
    private SysRoleService sysRoleService;

    @RequiresPermissions("user:user:view")
    @RequestMapping("/sysUser")
    public String UserManager(){
        if(SubjectUtils.isAuthenticated()){
            return "admin/user/user";
        }else{
            return "modules/login";
        }
    }

    @RequiresPermissions("user:list")
    @RequestMapping("/sysUser/list")
    @ResponseBody
    public PageInfoReturn list(SysUserPageInfo su){
        PageInfo<SysUser> pageInfo=sysUserService.findAllSysUserByPageInfo(su);
        if(pageInfo==null){
            return null;
        }
        System.out.println("============1"+pageInfo.toString());
        /**获取用户的列表*/
        return ResultFormat.formatSysUserPageInfo(pageInfo);
    }

    @GetMapping("/sysUser/add")
    public String add(){
            return "admin/user/add";
    }

    @RequiresPermissions("user:add")
    @PostMapping("/sysUser/add")
    @ResponseBody
    public ResponseEntity add(SysUser sysUser){
        try {
            SysUser user = sysUserService.getSysUserByLoginName(sysUser.getUsername());
            if(user!=null){
                return ResultFormat.error("该用户名已经存在");
            }else{
                sysUser.setUserId(UniqueID.getUUID().substring(0,6));
                sysUser=EncryptUtils.setPasswordAndSalt(sysUser);
                sysUser.setCreatetime(DateUtil.date());
                sysUser.setUpdatetime(DateUtil.date());
                System.out.println("看看sysUser插入的"+sysUser.toString());
                if(sysUserService.insert(sysUser)){
                    return ResultFormat.success("添加成功");
                }else{
                    return ResultFormat.error("添加用户失败，请重试");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResultFormat.error("添加用户失败，请重试");
        }
    }


    @GetMapping("/sysUser/edit/{id}")
    public String edit(@PathVariable("id") Integer id, ModelMap map){
        map.addAttribute("user",sysUserService.selectById(id));
        return "admin/user/edit";
    }

    @RequiresPermissions("user:edit")
    @PostMapping("/sysUser/edit")
    @ResponseBody
    public ResponseEntity edit(SysUser sysUser){
        if(sysUserService.updateUserMessage(sysUser)){
            return ResultFormat.success("更改信息成功！");
        }else{
            return ResultFormat.error("更改信息失败");
        }
    }


    @PostMapping("/sysUser/assigningRoles")
    @ResponseBody
    public ResponseEntity<List<SysRole>> assigningRolesForSysUser(Integer userId){
        logger.info("获取到的id"+userId);
        List<SysRole> data = sysRoleService.selectAllRoles(userId);
        Iterator iterator = data.iterator();
        while(iterator.hasNext()){
            System.out.println("角色："+iterator.next());
        }
        return ResultFormat.formatListToMap(null,data);
    }

//    @RequiresPermissions("user:saveUserRoles")
    @PostMapping("/sysUser/saveUserRoles")
    @ResponseBody
    public ResponseEntity saveUserRoles(Integer userId,String roleIds){
        try {
            sysUserService.saveUserRoles(userId,roleIds);
            return ResultFormat.error("保存权限成功");
        } catch (Exception e) {
            e.printStackTrace();
            return ResultFormat.error("程序可能出了点小问题哦");
        }
    }


    /***
     * 删除用户
     * @param id
     * @return
     */
    @RequiresPermissions("user:delete")
    @PostMapping("/sysUser/delete/{id}")
    @ResponseBody
    public ResponseEntity delete(@PathVariable("id") Integer id){
        if (sysUserService.deleteById(id)){
            return ResultFormat.success("用户删除成功！");
        }else {
            return ResultFormat.error("用户删除失败！");
        }
    }


//    {"removeIds":"6,8,9"}
    @RequiresPermissions("user:delete")
    @PostMapping("/sysUser/removeAll")
    @ResponseBody
    public ResponseEntity removeAll(String ids){
        System.out.println("获取到的要删除的id"+ ids);
        String[] IDArray= ids.split(",");
        List<Integer> errorRemove=new ArrayList<>();
        for (String id:IDArray){
            if(sysUserService.deleteById(Integer.parseInt(id))){
                continue;
            }else{
                errorRemove.add(Integer.parseInt(id));
            }
        }
        if(CollectionUtils.isEmpty(errorRemove)){
            return ResultFormat.success("用户删除成功！");
        }else{
            Iterator iterator = errorRemove.iterator();
            String result=null;
            while(iterator.hasNext()){
                result=result+iterator.next();
            }
            return ResultFormat.error("用户id为"+result+"的删除失败！");
        }
    }


    @RequiresPermissions("user:search")
    @RequestMapping("/sysUser/selectUserByForm")
    @ResponseBody
    public PageInfoReturn search(SearchPageInfo s){
        System.out.println(s.getRoleName());
        System.out.println("看看值"+s.toString());
        PageInfo<SysUser> pageInfo=sysUserService.searchSysUserByPageInfo(s);
        if(pageInfo==null){
            return null;
        }
        System.out.println("==========2"+pageInfo.toString());
        /**获取用户的列表*/
        return ResultFormat.formatSysUserPageInfo(pageInfo);
    }

}
