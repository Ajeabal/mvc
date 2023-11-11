package com.spring.mvc.chap02;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/model")
public class ControllerV2 {

    /*

    */
    @GetMapping("/hobbies1")
    public String hobbies(Model model) {
        System.out.println("취미1");
        String name = "짹짹이";
        List<String> hobbyList = List.of("전깃줄 앉아 있기", "좁쌀 주워 먹기", "하하호호", "날아다니기", "짹짹거리기");
        model.addAttribute("userName", name);
        model.addAttribute("hobbies", hobbyList);
        return "chap02/hobbies";
    }
    @GetMapping("/hobbies2")
    public String hobbies2() {
        System.out.println("취미2");
        return "";
    }
}
