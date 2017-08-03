package com.jun.soa.controller;


import com.jun.soa.common.BeanMapper;
import com.jun.soa.entity.Student;
import com.jun.soa.http.HttpResult;
import com.jun.soa.service.StudentService;
import com.jun.soa.vo.StudentVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;


@RestController
@RequestMapping("/student")
public class StudentController {

    @Autowired
    private StudentService studentService;

    @RequestMapping(method = RequestMethod.GET)
    public HttpResult find(Pageable pageable) {
        System.out.println("pageable"+pageable.toString());

        return new HttpResult(studentService.findAll(pageable));
    }

    @RequestMapping(method=RequestMethod.POST)
    public HttpResult save(@Valid @RequestBody StudentVo studentVo){
        Student student= BeanMapper.map(studentVo,Student.class);
        return new HttpResult(studentService.save(student),Student.class);
    }

    @RequestMapping("/test")
    public void testException(){
        int a=5/0;
    }

    @RequestMapping(method = RequestMethod.DELETE)
    public HttpResult delete(Long id){
        studentService.delete(id);
        return new HttpResult("删除成功");
    }
    @RequestMapping(method=RequestMethod.PUT)
    public HttpResult update(@Valid @RequestBody StudentVo studentVo){
        Student student= BeanMapper.map(studentVo,Student.class);
        return new HttpResult(studentService.update(student),Student.class);
    }
}
