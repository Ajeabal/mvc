package com.spring.mvc.chap06.jdbc;

import com.spring.mvc.chap06.entity.Person;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class JdbcRepositoryTest {

    @Autowired
    JdbcRepository repository;

    @Test
    @DisplayName("데이터베이스 접속에 성공해야 한다.")
    void connectTest() {
        try {
            Connection connection = repository.getConnection();
            assertNotNull(connection);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Test
    @DisplayName("person 객체 정보를 DB에 INSERT해야 한다.")
    void saveTest() {
        Person p = new Person("1", "망둥이", 10);
        repository.save(p);

    }

    @Test
    @DisplayName("회원번호가 1인 회원의")
    void updateTest() {
        String id = "1";

        String newName = "개굴이";
        int newAge = 15;
        Person person = new Person(id, newName, newAge);
        repository.update(person);
    }

    @Test
    @DisplayName("회원번호가 1인 회원을 삭제해야 한다.")
    void  deleteTest() {
        String id = "1";
        repository.delete(id);
    }

    @Test
    @DisplayName("랜덤회원아이드를 가진 회원을 10명 등록해야한다.")
    void bulkInsertTest() {
        for (int i = 0; i < 10; i++) {
            Person person = new Person(""+Math.random(), "랄랄라"+i, i+10);
            repository.save(person);
        }
    }

    @Test
    @DisplayName("전체 회원을 조회하면 회원 리스트의 수가 10개이다.")
    void findAllTest() {
        List<Person> aLl = repository.findALl();
        aLl.forEach(System.out::println);
    }
    @Test
    @DisplayName("특정 아디이의 회원을 조회하면 하나의 회원이 조회된다.")
    void findOndTest() {
        String id = "0.16323881648353156";
        Person one = repository.findOne(id);
        System.out.println("one = " + one);
    }
}