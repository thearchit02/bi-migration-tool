package com.bimigration.controller;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/migration")
public class MigrationController {

    @GetMapping("/health")
    public String health() {
        return "BI Migration Tool is running!";
    }

    @PostMapping("/test")
    public TestRequest test(@RequestBody TestRequest request) {
        return request;
    }

    record TestRequest(String message) {
    }
}