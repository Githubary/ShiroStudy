package by.start.shirostudy.mvc.Service.ServiceImpl;

import by.start.shirostudy.common.web.BaseServiceImpl;
import by.start.shirostudy.mvc.Entity.SysPerm;
import by.start.shirostudy.mvc.Entity.SysRole;
import by.start.shirostudy.mvc.Mapper.SysPermMapper;
import by.start.shirostudy.mvc.Service.SysPermService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @author bystart
 * @date 2020/7/11 9:49
 * 仔细！坚持！
 * ❥(^_-))
 */
@Service
public class SysPermServiceImpl extends BaseServiceImpl<SysPerm> implements SysPermService {

    @Autowired
    @Qualifier("sysPermMapper")
    private SysPermMapper sysPermMapper;

    @Override
    public Set<String> getSysUserPerms(Integer id) {
        return sysPermMapper.getSysUserPerms(id);
    }


    @Override
    public List<SysPerm> selectResourceList(SysPerm sysPerm) {
        return sysPermMapper.selectResourceList(sysPerm);
    }


    /**
     * 获取资源树
     * @param sysRole
     * @return
     */
    @Override
    public List<Map<String, Object>> roleResourceTreeData(SysRole sysRole) {
        /**获取对应行id*/
        Integer roleId=sysRole.getId();
        List<Map<String,Object>> trees=new ArrayList<>();
        /**查出资源表里的所有资源*/
        List<SysPerm> resourceList=sysPermMapper.selectAlls();
        if(null != roleId){
            //根据角色id查询
            List<String> roleResourceList=sysPermMapper.selectResourceTree(roleId);
            trees=getTrees(resourceList,true,roleResourceList,true);
        }else {
            trees=getTrees(resourceList,false,null,true);
        }
        return trees;
    }


    /***
     *
     * @param resourceList 菜单列表
     * @param isCheck 是否选中
     * @param roleResourceList 角色已存在菜单列表
     * @param permsFlag 是否显示权限标识符
     * @return
     */
    public List<Map<String,Object>>  getTrees(List<SysPerm> resourceList,boolean isCheck,List<String> roleResourceList,boolean permsFlag){
        List<Map<String,Object>> trees = new ArrayList<Map<String, Object>>();
        for (SysPerm resource:resourceList) {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("id", resource.getId());
            map.put("pId", resource.getParentId());
            map.put("name", MenuName(resource, roleResourceList, permsFlag));
            map.put("title",resource.getName());
            if (isCheck){
                map.put("checked",roleResourceList.contains(resource.getId() + resource.getPermission()));
            }else{
                map.put("checked",false);
            }
            trees.add(map);
        }
        return trees;
    }
    /***
     * 菜单名称
     * @param resource
     * @param roleMenuList
     * @param permsFlag
     * @return
     */
    public String MenuName(SysPerm resource,List<String> roleMenuList,boolean permsFlag){
        StringBuilder sb = new StringBuilder();
        sb.append(resource.getName());
        if(permsFlag){
            sb.append("<font color=\"#888\">&nbsp;&nbsp;&nbsp;" + resource.getPermission() + "</font>");
        }
        return sb.toString();
    }
}
