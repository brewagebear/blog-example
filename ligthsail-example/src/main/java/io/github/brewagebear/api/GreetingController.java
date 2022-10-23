package io.github.brewagebear.api;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/greeting")
public class GreetingController
{
    @GetMapping
    public ResponseEntity<String> hello()
    {
        return ResponseEntity.ok("안녕하세요");
    }
}
