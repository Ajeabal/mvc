package com.spring.mvc;

import com.spring.mvc.chap06.entity.Person;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/rests")
public class RestApiController {

    @GetMapping("/hello")
    public String hello() {
        log.info("/rest/hello GET");
        return "hello apple banana";
    }

    @GetMapping("/food")
    public List<String> food() {
        return List.of("짜장면", "볶음밥", "짬뽕");
    }

    @GetMapping("/person")
    public Person person() {
        return new Person("334", "맖포이", 50);
    }


    /*
        RestController에서 리턴타입을 ResponseEntity를 쓰는 이유
         - 클라이언트에게 응답할 때는 메세지 바디 안에 들어 있는 데이터도 중요하지만
           상태코드와 헤더정보를 포함해야 한다
         - 하지만 일반 리턴타입은 상대 코드와 헤더 정보를 전송하기 어렵다.
     */
    @GetMapping("/person-list")
    public ResponseEntity<?> personList() {
        Person person1 = new Person("111", "A", 10);
        Person person2 = new Person("222", "B", 20);
        Person person3 = new Person("333", "C", 30);
        List<Person> personList = List.of(person1, person2, person3);

        return ResponseEntity.ok().body(personList);
    }
    @GetMapping("/bmi")
    public ResponseEntity<Double> bmi(@RequestParam(required = false) Double cm,
                                      @RequestParam(required = false) Double kg) {
        double bmi = kg / ((cm/100)*(cm/100));
        HttpHeaders headers = new HttpHeaders();
        headers.add("fruits", "melon");
        headers.add("pet", "cat");
        return ResponseEntity.ok().headers(headers).body(bmi);
    }
}
