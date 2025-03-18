package controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class Service2Controller {
    @GetMapping("/service2")
    public String service2() {
        return "products";
    }
}
