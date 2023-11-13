package com.example.TraceLogDemo;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class TraceController {

    @GetMapping("trace")
    public ResponseEntity<Void> trace() {
        log.info("enter controler");

        FiledIgnoreObj filedIgnoreObj = new FiledIgnoreObj();
        filedIgnoreObj.setUsername("jason");
        filedIgnoreObj.setPassword("123456");
        log.info("obj: {}", filedIgnoreObj);

        return ResponseEntity.ok().build();
    }

}
