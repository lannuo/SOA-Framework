package com.jun.soa.service.base;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.List;

public interface BaseService<T,S> {

    /**
     * 新增
     * @param t
     * @return
     */
    public T save(T t);

    /**
     * 修改
     * @param t
     * @return
     */
    public T update(T t);

    /**
     * 根据id删除对象（物理）
     * @param id
     */
    public void delete(S id);

    /**
     * 根据id 查询对象
     * @param id
     * @return
     */
    public T find(S id);

    /**
     * 查询所有
     * @return
     */
    public List<T> findAll();

    /**
     * 根据sort排序 查询所有
     * @param sort
     * @return
     */
    public Iterable<T> findAll(Sort sort);

    /**
     * 查询所有，并分页
     * @param pageable
     * @return
     */
    public Page<T> findAll(Pageable pageable);


}
