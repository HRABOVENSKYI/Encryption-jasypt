package com.example.springtest;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/")
public class TestController {

    private final TestService testService;

    @PostMapping
    public String addMessage(@RequestBody TestModel testModel) {
        return testService.addMessage(testModel);
    }

    @GetMapping
    public List<TestModel> getAllMessages() {
        return testService.getMessages();
    }

}
