package com.spring.mvc.chap06.spring_jdbc;

import com.spring.mvc.chap06.entity.Person;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest // 스프링이 관리하는 빈을 주입 받기 위한 이노테이션
class SpringJdbcRepositoryTest {

    @Autowired
    SpringJdbcRepository repository;

    @Test
    @DisplayName("사람 정보를 DB에 저장한다")
    void saveTest() {
        //given
        Person person = new Person("99", "말똥이", 30);
        //when
        repository.save(person);
        //then
    }
    @Test
    @DisplayName("99번 회원의 이름과 나이를 수정한다")
    void modifyTest() {
        //given
        String id = "99";
        String newName = "수정";
        int newAge = 50;
        Person person = new Person(id, newName, newAge);
        //when
        repository.modify(person);
        //then
    }

    @Test
    @DisplayName("99번 회원을 삭제한다")
    void removeTest() {
        //given
        String id = "99";
        //when
        repository.remove(id);
        //then
    }

    @Test
    @DisplayName("전체조회를 해야한다.")
    void findAllTest() {
        //given

        //when
        List<Person> all = repository.findAll();
        //then
        all.forEach(System.out::println);
    }

    @Test
    @DisplayName("30회원을 등록한 후 조회")
    void findOneTest() {
        //given
        Person person = new Person("30", "두껍", 23);
        repository.save(person);
        //when
        Person one = repository.findOne(person.getId());
        assertEquals("두껍", one.getPersonName());
        System.out.println("one = " + one);
        //then
    }
}