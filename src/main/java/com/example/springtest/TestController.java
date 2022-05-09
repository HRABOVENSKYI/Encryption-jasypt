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

    @PutMapping("/{id}")
    public String updateMessage(@PathVariable(name = "id") Long id, @RequestBody TestModel testModel) {
        return testService.updateMessage(id, testModel);
    }

    @GetMapping("/{id}")
    public TestModel getTestModelById(@PathVariable(name = "id") Long id) {
        return testService.getTestModelByIdDecrypted(id);
    }
}
