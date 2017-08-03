package com.jun.soa.vo;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.io.Serializable;

public class StudentVo implements Serializable{

    private Long id;

    @NotNull(message = "名称不能为空")
    @Pattern(regexp = "^[0-9,a-z,A-Z,\u4E00-\u9FA5]{2,30}$", message = "名称只能由2-30数字、字母、中文组成")
    private String name;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
