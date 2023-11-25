package com.spring.mvc.chap04.controller;

import com.spring.mvc.chap04.dto.ScoreRequestDTO;
import com.spring.mvc.chap04.dto.ScoreResponseDTO;
import com.spring.mvc.chap04.entity.Score;
import com.spring.mvc.chap04.repository.ScoreRepository;
import com.spring.mvc.chap04.service.ScoreService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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
    private final ScoreService service;

//    @Autowired // 생성자 주입을 사용하고 생성자가 단 하나 -> Autowired 생략가능
//    public ScoreController(ScoreRepository repository) {
//        this.repository = repository;
//    }

    // 1. 성적 등록 폼 띄우기 + 목록 조회 요청
    @GetMapping("/list")
    public String list(Model model, @RequestParam(defaultValue = "num") String sort) {
        System.out.println("/score/list GET");
//
//        // db에서 조회한 모든 데이터
//        List<Score> scoreList = repository.findAll(sort);

        // 클라이언트가 필요한 일부 데이터
//        List<ScoreResponseDTO> dtoList = new ArrayList<>();
//        for (Score score : scoreList) {
//            dtoList.add(new ScoreResponseDTO(score));
//        }
        List<ScoreResponseDTO> dtoList = service.getList(sort);
        model.addAttribute("sList", dtoList);
        return "chap04/score-list";
    }

    // 2. 성적정보를 데이터베이스에 저장하는 요청
    @PostMapping("/register")
    public String register(ScoreRequestDTO score) {
        System.out.println("/score/register POST");
        // DTO를 entity로 변환하면서 데이터의 생성도 해야함
        service.insertScore(score);
        /*
            Forward
             - forward는 요청 리소스를 그대로 전달함
             - 따라서 URL은 변경되지 않고 한번의 요청과 한번의 응답만 이뤄짐
            Redirect
             - redirect는 요청 후에 자동응답이 나가고 2번째 자동요청이 들어오며서 2번째 응답을 내보냄
             - 따라서 2번째 요청의 URL로 자동 변경됨.
         */

        // forward 할 때는 포워딩할 파일의 경로를 적는 것
        // ex) /WEB-INF/views/chap04/score-list.jsp

        // redirect 할 때는 리다이렉트 요청 URL을 적는 것
        // ex)localhost
        return "redirect:/score/list";
    }

    // 3. 성적 삭제 요청
    @RequestMapping(value = "/remove/{stuNum}", method = {RequestMethod.GET, RequestMethod.POST})
    public String remove(HttpServletRequest request, @PathVariable int stuNum) {
        System.out.printf("/score/remove %s\n", request.getMethod());
        System.out.println("삭제할 학번: " + stuNum);
        service.deleteScore(stuNum);
        return "redirect:/score/list";
    }

    // 4. 성적 상세 조회 요청
    @GetMapping("/detail")
    public String detail(Model model, int stuNum) {
        System.out.println("/score/detail GET");
        retrieve(model, stuNum);
        return "chap04/score-detail";
    }

    @GetMapping("/edit")
    public String edit(Model model, int stuNum) {
        System.out.println("/score/editShow GET");
        retrieve(model, stuNum);
        return "chap04/score-edit";
    }
    @PostMapping("/edit")
    public String edit(Model model, int stuNum, ScoreRequestDTO score) {
        Score editedScore = service.editScore(stuNum, score);
        model.addAttribute("s", editedScore);
        return "chap04/score-detail";
    }

    private void retrieve(Model model, int stuNum) {
        Score score = service.retrieve(stuNum);
        model.addAttribute("s", score);
    }
}
