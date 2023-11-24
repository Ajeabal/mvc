package com.spring.mvc.chap04.controller;

import com.spring.mvc.chap04.entity.Score;
import com.spring.mvc.chap04.repository.ScoreRepository;
import com.spring.mvc.chap04.repository.ScoreRepositoryImpl;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/*
    # 컨트롤러
     - 클라이언트의 요청을 받아서 처리 및 응답을 처리하는 객체

     # 요청 URL Endpoint
      1. 학생의 성적 정보 등록 폼 화면을 보여주고
         동시에 지금까지 저장되어 있는 성적 정보 목록을 조회
         - /score/list  :   GET

      2. 학생의 입력된 성적정보를 데이터베이스에 저장하는 요청
         - /score/register  :   POST

      3. 성적정보 삭제 요청
         - /score/remove    :   GET or POST

      4. 성적 정보 상세 조회 요청
         - /score/detail    :   GET
 */

@Controller
@RequestMapping("/score")
@RequiredArgsConstructor // final이 붙은 필드를 초기화하는 생성자를 생성
//@AllArgsConstructor // 모든 필드를 초기화하는 생성자를 생성해준다.
public class ScoreController {
    // 저장소에 의존하여 데이터 처리를 위임한다.
    // 의존객체는 불변성을 가지는 것이 좋다.
    private final ScoreRepository repository;

//    @Autowired // 생성자 주입을 사용하고 생성자가 단 하나 -> Autowired 생략가능
//    public ScoreController(ScoreRepository repository) {
//        this.repository = repository;
//    }

    // 1. 성적 등록 폼 띄우기 + 목록 조회 요청
    @GetMapping("/list")
    public String list(Model model) {
        System.out.println("/score/list GET");
        List<Score> scoreList = repository.findAll();
        model.addAttribute("sList", scoreList);
        return "chap04/score-list";
    }

    // 2. 성적정보를 데이터베이스에 저장하는 요청
    @PostMapping("/register")
    public String register() {
        System.out.println("/score/register POST");
        return "";
    }

    // 3. 성적 삭제 요청
    @RequestMapping(value = "/remove", method = {RequestMethod.GET, RequestMethod.POST})
    public String remove(HttpServletRequest request) {
        System.out.printf("/score/remove %s\n", request.getMethod());
        return "";
    }

    // 4. 성적 상세 조회 요청
    @GetMapping("/detail")
    public String detail() {
        System.out.println("/score/detail GET");
        return "";
    }


}
