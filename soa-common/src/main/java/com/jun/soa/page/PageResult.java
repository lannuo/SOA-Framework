package com.jun.soa.page;


import com.jun.soa.page.dubbo.DubboPageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * 查询内容传递体，用于查询JSON数据分页
 * @author JUN
 *
 */
public class PageResult<T> extends DubboPageImpl<T> {
    private static final long serialVersionUID = -8090890508770240787L;

    public PageResult(List<T> content, Pageable pageable, long total) {
        super(content, pageable, total);
    }

    public PageResult(List<T> content) {
        super(content);
    }


    /**
     * @return 总页数
     */
    public int getTotalPage(){
        return getTotalPages();
    }

    /**
     * @return 总条数
     */
    public long getTotalElement(){
        return getTotalElements();
    }

    /**
     * @return 当前页码
     */
    public long getCurrentPage(){
        return getNumber();
    }
}
