package com.spring.mvc.chap06.mybatis;

import com.spring.mvc.chap06.entity.Person;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

// mybatis의 sql을 실행 시키기 위한 인터페이스
@Mapper
public interface PersonMapper {

    // CRUD를 명세
    boolean save(Person person); // INSERT

    boolean update(Person person); // UPDATE

    boolean delete(String id); // DELETE

    List<Person> findAll(); // SELECT ALL

    Person findOne(String id); // SELECT ONE


}
