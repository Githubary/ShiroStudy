package by.start.shirostudy.mvc.Service.ServiceImpl;

import by.start.shirostudy.common.PageHelper.SysRolePageInfo;
import by.start.shirostudy.common.ResponseEntity.ResponseEntity;
import by.start.shirostudy.common.web.BaseServiceImpl;
import by.start.shirostudy.mvc.Entity.SysRole;
import by.start.shirostudy.mvc.Entity.SysRolePerm;
import by.start.shirostudy.mvc.Mapper.SysRoleMapper;
import by.start.shirostudy.mvc.Mapper.SysRolePermMapper;
import by.start.shirostudy.mvc.Service.SysRoleService;
import by.start.shirostudy.utils.ResultFormat;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Set;

/**
 * @author bystart
 * @date 2020/7/11 9:48
 * 仔细！坚持！
 * ❥(^_-))
 */

@Service
public class SysRoleServiceImpl extends BaseServiceImpl<SysRole> implements SysRoleService {

    @Autowired
    @Qualifier("sysRoleMapper")
    private SysRoleMapper sysRoleMapper;

    @Autowired
    private SysRolePermMapper sysRolePermMapper;

    @Override
    public Set<String> getSysUserRoles(Integer id) {
        return sysRoleMapper.getSysUserRoles(id);
    }

    @Override
    public List<SysRole> selectAllRoles(Integer id) {
        List<SysRole> rolesList = sysRoleMapper.selectAllRoles(id);
        if(!CollectionUtils.isEmpty(rolesList)){
            System.out.println(rolesList);
            return rolesList;
        }else{
            return null;
        }
    }

    @Override
    public PageInfo<SysRole> findAllRolesByPageInfo(SysRolePageInfo sr) {
        PageHelper.startPage(sr.getPageNum(), sr.getPageSize());
        List<SysRole> rolesList = sysRoleMapper.findAllRolesByPageInfo(sr);
        if (CollectionUtils.isEmpty(rolesList)){
            return null;
        }
        PageInfo pageInfo = new PageInfo<>(rolesList);
        return pageInfo;
    }

    @Override
    public SysRole getRolesByRoleName(String roleName) {
        SysRole sysRole = sysRoleMapper.getRolesByRoleName(roleName);
        return sysRole;
    }

    @Override
    public boolean updateUserMessage(SysRole sysRole) {
        return sysRoleMapper.updateUserMessage(sysRole);
    }

    @Override
    public ResponseEntity addAssignResourceById(String roleId, List<String> resourceIds) {
        try {
            SysRolePerm sysRolePerm=new SysRolePerm();
            sysRolePerm.setRoleId(roleId);
            sysRolePermMapper.delete(sysRolePerm);
            for (String resourceId:resourceIds) {
                sysRolePerm.setId(null);
                sysRolePerm.setResourceId(resourceId);
                sysRolePermMapper.insert(sysRolePerm);
            }
            return ResultFormat.success("分配资源成功！");
        }catch (Exception e){
            return ResultFormat.error("分配资源失败！");
        }
    }
}
