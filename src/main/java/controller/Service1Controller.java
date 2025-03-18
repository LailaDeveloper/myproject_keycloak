package controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class Service1Controller {
    @GetMapping("/service1")
    public String service1() {
        return "dashboard"; // Cette vue est liée à la page de Service 1
    }
}
