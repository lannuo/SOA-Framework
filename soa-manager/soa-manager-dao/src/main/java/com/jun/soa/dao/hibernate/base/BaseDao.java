package com.jun.soa.dao.hibernate.base;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.Repository;

import java.io.Serializable;

/**
 * 注意：在Repository子接口中声明方法
 * 1、查询方法以find | read |get 开头
 * 2、涉及条件查询时，条件的属性用条件关键字连接，且条件属性以首字母大写
 *
 * 在查询时，通常需要同时根据多个属性进行查询，且查询的条件也格式各样（大于某个值、在某个范围等等），Spring Data JPA 为此提供了一些表达条件查询的关键字，大致如下：
 And --- 等价于 SQL 中的 and 关键字，比如 findByUsernameAndPassword(String user, Striang pwd)；
 Or --- 等价于 SQL 中的 or 关键字，比如 findByUsernameOrAddress(String user, String addr)；
 Between --- 等价于 SQL 中的 between 关键字，比如 findBySalaryBetween(int max, int min)；
 LessThan --- 等价于 SQL 中的 "<"，比如 findBySalaryLessThan(int max)；
 GreaterThan --- 等价于 SQL 中的">"，比如 findBySalaryGreaterThan(int min)；
 IsNull --- 等价于 SQL 中的 "is null"，比如 findByUsernameIsNull()；
 IsNotNull --- 等价于 SQL 中的 "is not null"，比如 findByUsernameIsNotNull()；
 NotNull --- 与 IsNotNull 等价；
 Like --- 等价于 SQL 中的 "like"，比如 findByUsernameLike(String user)；
 NotLike --- 等价于 SQL 中的 "not like"，比如 findByUsernameNotLike(String user)；
 OrderBy --- 等价于 SQL 中的 "order by"，比如 findByUsernameOrderBySalaryAsc(String user)；
 Not --- 等价于 SQL 中的 "！ ="，比如 findByUsernameNot(String user)；
 In --- 等价于 SQL 中的 "in"，比如 findByUsernameIn(Collection<String> userList) ，方法的参数可以是 Collection 类型，也可以是数组或者不定长参数；
 NotIn --- 等价于 SQL 中的 "not in"，比如 findByUsernameNotIn(Collection<String> userList) ，方法的参数可以是 Collection 类型，也可以是数组或者不定长参数；
 * @param <T>
 */
@NoRepositoryBean
public interface BaseDao<T, ID extends Serializable> extends Repository<T, ID>  {

    /**
     * 新增
     * @param s
     * @param <S>
     * @return
     */
    public <S extends T> S save(S s);

    /**
     * 批量新增
     * @param ss
     * @param <S>
     * @return
     */
    public <S extends T> Iterable<S> save(Iterable<S> ss);

    /**
     * 根据id获取对象
     * @param id
     * @return
     */
    public T findOne(ID id);

    /**
     * 根据id判断是否存在该对象
     * @param id
     * @return
     */
    public boolean exists(ID id);

    /**
     * 查询所有（不分页）
     * @return
     */
    public Iterable<T> findAll();

    /**
     * 根据ids 批量查询
     * @param ids
     * @return
     */
    public Iterable<T> findAll(Iterable<ID> ids);

    /**
     * 根据sort排序 查询所有
     * @param sort
     * @return
     */
//   public Iterable<T> findAll(Sort sort);

    /**
     * 查询所有，并分页
     * @param pageable
     * @return
     */
    public Page<T> findAll(Pageable pageable);
    /**
     * 计算总数
     * @return
     */
    public long count();

    /**
     * 根据id删除(物理)
     * @param id
     */
    public void delete(ID id);

    /**
     * 根据对象删除（物理）
     * @param t
     */
    public void delete(T t);

    /**
     * 批量删（物理）
     * @param t
     */
    public void delete(Iterable<? extends T> t);

    /**
     * 删除所有（物理）
     */
    public void deleteAll();


}
