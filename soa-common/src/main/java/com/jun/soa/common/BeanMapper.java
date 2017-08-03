package com.jun.soa.common;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import org.dozer.DozerBeanMapper;

import java.util.Collection;
import java.util.List;
import java.util.Set;

/**
 * 映射Bean属性类
 */
public class BeanMapper {

    /**
     * DozerBeanMapper 对象之间相同属性名赋值
     */
    private static DozerBeanMapper dozer=new DozerBeanMapper();

    /**
     * 基于Dozer转换对象的类型
     * @param source
     * @param destinationClass
     * @return
     */
    public static <T> T map(Object source,Class<T> destinationClass){
        if(source == null){
            return null;
        }
        return dozer.map(source, destinationClass);
    }

//    public static <T> T dubboMap(Object source,Class<T> destinationClass){
//        if(source==null){
//            return null;
//        }
//
//    }
    /**
     * mapList:(基于Dozer转换Collection中对象的类型). <br/>
     * @author JUN
     * @param sourceList
     * @param destinationClass
     * @return
     */
    public static <T> List<T> mapList(Collection<?> sourceList, Class<T> destinationClass){
        if(sourceList==null){
            return null;
        }
        List<T> destinationList= Lists.newArrayList();
        for(Object sourceObject:sourceList){
            T destinationObject=dozer.map(sourceObject, destinationClass);
            destinationList.add(destinationObject);
        }
        return destinationList;

    }

    /**
     * mapSet:(基于Dozer转换Set中对象的类型). <br/>
     *
     * @author JUN
     * @param sourceList
     * @param destinationClass
     * @return
     */
    public static <T> Set<T> mapSet(Collection<?> sourceList , Class<T> destinationClass){
        if(sourceList==null){
            return null;
        }
        Set<T> destinationList= Sets.newHashSet();
        for(Object sourceObject : sourceList){
            T destinationObject = dozer.map(sourceObject, destinationClass);
            destinationList.add(destinationObject);

        }
        return destinationList;
    }

    /**
     * copy:(基于Dozer将对象source的值拷贝到对象destinationObject中). <br/>
     *
     * @author JUN
     * @param source
     * @param destinationObject
     */
    public static void copy(Object source,Object destinationObject){
        dozer.map(source, destinationObject);
    }


}
