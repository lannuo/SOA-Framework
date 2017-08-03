package com.jun.soa.service.impl;

import com.jun.soa.dao.hibernate.StudentDao;
import com.jun.soa.entity.Student;
import com.jun.soa.page.dubbo.DubboPageImpl;
import com.jun.soa.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class StudentServiceImpl implements StudentService {

    @Autowired
    private StudentDao studentDao;

    @Override
    @Transactional
    public Student save(Student student) {
        return studentDao.save(student);
    }

    @Override
    @Transactional
    public Student update(Student student) {
        return studentDao.save(student);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        studentDao.delete(id);
    }

    @Override
    public Student find(Long id) {
        return studentDao.findOne(id);
    }

    @Override
    public List<Student> findAll() {
        return (List<Student>) studentDao.findAll();
    }

    @Override
    public Iterable<Student> findAll(Sort sort) {
        return null;
    }

    @Override
    public Page<Student> findAll(Pageable pageable) {
        Page<Student> pi=studentDao.findAll(pageable);
        DubboPageImpl dpi=new DubboPageImpl(pi.getContent(),pageable,pi.getTotalElements());
        return dpi;
    }

}
