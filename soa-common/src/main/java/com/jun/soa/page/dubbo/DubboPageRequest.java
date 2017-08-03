package com.jun.soa.page.dubbo;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

public class DubboPageRequest extends PageRequest {
    public DubboPageRequest(){
        super(0,20);
    }

    public DubboPageRequest(int page, int size) {
        super(page, size);
    }

    public DubboPageRequest(int page, int size, Sort.Direction direction, String... properties) {
        super(page, size, direction, properties);
    }

    public DubboPageRequest(int page, int size, Sort sort) {
        super(page, size, sort);
    }
}
