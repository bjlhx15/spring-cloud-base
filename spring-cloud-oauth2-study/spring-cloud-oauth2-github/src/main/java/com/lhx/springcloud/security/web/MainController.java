package com.lhx.springcloud.security.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;

/**
 * @author lihongxu
 * @desc @link(https://github.com/bjlhx15/spring-cloud-base)
 * @since 2018/10/30 下午6:08
 */
@Controller
public class MainController {

    @GetMapping("/")
    public String root() {
        return "redirect:/index";
    }

    @GetMapping("/index")
    public String index(Principal principal, Model model) {
        if(principal == null ){
            return "index";
        }
        System.out.println(principal.toString());
        model.addAttribute("principal", principal);
        return "index";
    }

    @GetMapping("/403")
    public String accesssDenied() {
        return "403";
    }
}
