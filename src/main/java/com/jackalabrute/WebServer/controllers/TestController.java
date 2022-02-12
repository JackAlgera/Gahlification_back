package com.jackalabrute.WebServer.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    /**
     * Get test.
     *
     * @return
     */
    @GetMapping(path = "/test")
    public ResponseEntity<String> getTest(@RequestParam(value="name", defaultValue="World") String name) {
        return new ResponseEntity<>("Hello " + name + "!", HttpStatus.OK);
    }

}
