package by.start.shirostudy.mvc.Mapper;


import by.start.shirostudy.common.PageHelper.SearchPageInfo;
import by.start.shirostudy.common.PageHelper.SysUserPageInfo;
import by.start.shirostudy.common.web.BaseMapper;
import by.start.shirostudy.mvc.Entity.SysUser;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

@Mapper
@Repository
public interface SysUserMapper extends BaseMapper<SysUser> {

    List<SysUser> findAllSysUserByPageInfo(SysUserPageInfo su);

    SysUser getSysUserByLoginName(String username);

    boolean updateUserMessage(SysUser sysUser);

    List<SysUser> searchSysUserByPageInfo(SearchPageInfo s);

    List<SysUser> getSysUserListById(Integer roleId);

    List<SysUser> finByRoleId(Integer id);
}
