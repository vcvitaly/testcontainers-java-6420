package com.github.vcvitaly.sbtestcontainersscratches;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller.
 *
 * @author Vitalii Chura
 */
@RestController
public class Controller {

    @GetMapping("/hello")
    public String getHello() {
        return "hello";
    }
}
