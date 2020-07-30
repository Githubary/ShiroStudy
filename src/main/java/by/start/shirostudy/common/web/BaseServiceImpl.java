package by.start.shirostudy.common.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author bystart
 * @date 2020/7/10 23:25
 * 仔细！坚持！
 * ❥(^_-))
 */

@Service
public abstract class BaseServiceImpl<T> implements BaseService<T>{


    @Autowired(required = false)
    private BaseMapper<T> baseMapper;


    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean insert(T entity) {
        return baseMapper.insert(entity)>0;
    }

    @Transactional(rollbackFor = Exception.class)

    @Override
    public boolean deleteById(Object key) {
        return baseMapper.deleteByPrimaryKey(key)>0;
    }

    @Override
    public T selectById(Object key) {
        return baseMapper.selectByPrimaryKey(key);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean updateById(T entity) {
        return baseMapper.updateByPrimaryKey(entity)>0;
    }



    @Override
    public List<T> listAll() {
        return this.baseMapper.selectAll();
    }

    @Override
    public List<T> select(T record) {
        return this.baseMapper.select(record);
    }

    @Override
    public T selectOne(T record) {
        return this.baseMapper.selectOne(record);
    }


}
