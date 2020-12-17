package com.zvuk.zvuk;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GreetingController {

    @GetMapping("/greeting")
    public ApiResponse greeting(@RequestParam(name="name", required=false, defaultValue="World") String name) {
        return new ApiResponse().accepted();
    }
}
