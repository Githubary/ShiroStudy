package by.start.shirostudy.mvc.Service.ServiceImpl;

import by.start.shirostudy.common.PageHelper.SearchPageInfo;
import by.start.shirostudy.common.PageHelper.SysUserPageInfo;
import by.start.shirostudy.common.web.BaseServiceImpl;
import by.start.shirostudy.mvc.Entity.SysUser;
import by.start.shirostudy.mvc.Entity.UserRoles;
import by.start.shirostudy.mvc.Mapper.SysUserMapper;
import by.start.shirostudy.mvc.Mapper.UserRolesMapper;
import by.start.shirostudy.mvc.Service.SysUserService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import tk.mybatis.mapper.entity.Example;

import java.util.Collection;
import java.util.List;

/**
 * @author bystart
 * @date 2020/7/10 15:05
 * 仔细！坚持！
 * ❥(^_-))
 */

@Service
public class SysUserServiceImpl extends BaseServiceImpl<SysUser> implements SysUserService {

    @Autowired
    @Qualifier("sysUserMapper")
    private SysUserMapper sysUserMapper;

    @Autowired
    @Qualifier("userRolesMapper")
    private UserRolesMapper userRolesMapper;

    @Override
    public SysUser getSysUserByLoginName(String username) {
        SysUser sysUser = sysUserMapper.getSysUserByLoginName(username);
        return sysUser;
    }

    /**
     * 用户管理页面
     * 查询所有的用户
     * @param su
     * @return
     */
    @Override
    public PageInfo<SysUser> findAllSysUserByPageInfo(SysUserPageInfo su) {
        PageHelper.startPage(su.getPageNum(),su.getPageSize());
        List<SysUser> sysUserList = sysUserMapper.findAllSysUserByPageInfo(su);
        if (CollectionUtils.isEmpty(sysUserList)){
            return null;
        }
        PageInfo pageInfo = new PageInfo<>(sysUserList);
        return pageInfo;
    }

    @Override
    public boolean updateUserMessage(SysUser sysUser) {
       return sysUserMapper.updateUserMessage(sysUser);
    }

    @Override
    public void saveUserRoles(Integer userId, String roleIds) {
        //一个是Integer，一个是String，要把权限里的一个个拿出来，改成Integer型。然后插入到sys_user_role
        String[] roleId= roleIds.split(",");
        Example example =new Example(UserRoles.class);
        Example.Criteria criteria=example.createCriteria();
        criteria.andEqualTo("userId",userId);
        userRolesMapper.deleteByExample(example);
        for (String id:roleId){
            UserRoles userRoles = new UserRoles();
            userRoles.setUserId(userId);
            userRoles.setRoleId(Integer.parseInt(id));
            userRolesMapper.insert(userRoles);
        }
    }

    @Override
    public PageInfo<SysUser> searchSysUserByPageInfo(SearchPageInfo s) {
        PageHelper.startPage(s.getPageNum(),s.getPageSize());
        List<SysUser> sysUserList = sysUserMapper.searchSysUserByPageInfo(s);
        if (CollectionUtils.isEmpty(sysUserList)){
            return null;
        }
        PageInfo pageInfo = new PageInfo<>(sysUserList);
        return pageInfo;
    }

    @Override
    public List<SysUser> getSysUserListById(Integer roleId) {
        return sysUserMapper.getSysUserListById(roleId);
    }

    @Override
    public List<SysUser> findByRoleId(Integer id) {
        return sysUserMapper.finByRoleId(id);
    }


}
