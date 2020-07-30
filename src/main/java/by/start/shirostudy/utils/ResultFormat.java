package by.start.shirostudy.utils;

import by.start.shirostudy.common.PageHelper.PageInfoReturn;
import by.start.shirostudy.common.ResponseEntity.ResponseEntity;
import by.start.shirostudy.mvc.Entity.SysRole;
import com.github.pagehelper.PageInfo;
import com.google.gson.Gson;
import org.springframework.web.servlet.ModelAndView;

import java.util.*;

/**
 * @author bystart
 * @date 2020/7/9 14:38
 * 仔细！坚持！
 * ❥(^_-))
 */

public class ResultFormat {

    public static String a="1";

    private static Gson gson;

    public static ModelAndView formatModelAndView(String msg){
        ModelAndView model =new ModelAndView();
        return model;
    }

    private static ResponseEntity re(int code, String msg, Object data) {
        return new ResponseEntity(code,msg,data);
    }


    public static ResponseEntity success(String msg){
        return success(msg,null);
    }

    public static ResponseEntity success(String msg,Object data){
        return re(ResponseEntity.DEFAULT_SUCCESS_CODE,msg,data);
    }

    public static ResponseEntity error(String msg) {
        return error(msg,null);
    }

    public static ResponseEntity error(String msg,Object data) {
        return re(ResponseEntity.DEFAULT_SERVERERROR_CODE,msg,data);
    }

    public static PageInfoReturn formatSysUserPageInfo(PageInfo pageInfo){
        return  formatSysUserPageInfo(pageInfo.getTotal(),pageInfo.getList());
    }

    public static PageInfoReturn formatSysUserPageInfo(Long l, List list){
        PageInfoReturn sysUseReturn = new PageInfoReturn();
        sysUseReturn.setTotal(l);
        sysUseReturn.setRows(list);
        return sysUseReturn;
    }

    public static ResponseEntity formatListToMap(String msg,List<SysRole> list){
        List<Map<String,Object>> mapList = new ArrayList<>();
        Map<String,Object> map=null;
        for (SysRole role:list){
            map=new HashMap<>(3);
            map.put("id",role.getId());
            map.put("pId", 0);
            map.put("checked", role.getSelected() != null && role.getSelected() == 1);
            map.put("name", role.getRole());
            mapList.add(map);
        }
        System.out.println("map角色:"+mapList);
        Iterator iterator1 = mapList.iterator();
        while(iterator1.hasNext()){
            System.out.println(iterator1.next());
        }
        return formatListToMap(ResponseEntity.DEFAULT_SUCCESS_CODE,msg,mapList);
    }

    private static ResponseEntity formatListToMap(int code, String msg, List<Map<String, Object>> mapList) {
        return re(code,msg,mapList);
    }

}
