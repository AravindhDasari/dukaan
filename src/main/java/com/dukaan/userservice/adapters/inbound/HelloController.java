package com.dukaan.userservice.adapters.inbound;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping()
@RestController
public class HelloController {

    @GetMapping("/hello")
    public String hello() {
        return "Hello World, Lets start our Dukaan";
    }
}
