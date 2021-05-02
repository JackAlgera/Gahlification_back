package com.jackalabrute.WebServer.controllers;

import com.jackalabrute.WebServer.handlers.BaseHandler;
import com.jackalabrute.WebServer.models.HelloMessage;
import com.jackalabrute.WebServer.statuscodes.NotFoundException;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

@RestController
public class BaseController {

    @Autowired
    private BaseHandler baseHandler;

    /**
     * Basic endpoint that returns fixed message.
     * @return
     */
    @GetMapping(path = "/basicMessage")
    public ResponseEntity<HelloMessage> getBasicMessage() {
        return ResponseEntity.ok(new HelloMessage("Some Title", "Coucou !"));
    }

    /**
     * Return message with title using the path variable messageTitle.
     * @param title
     * @return
     */
    @GetMapping(path = "/messages/{messageTitle}")
    public ResponseEntity<HelloMessage> getMessageWithPathVariable(@PathVariable(name = "messageTitle") String title) {
        HelloMessage helloMessage = new HelloMessage(title, String.format("Coucou with the title : %s", title));
        return ResponseEntity.ok(helloMessage);
    }

    /**
     * Return message with title and message using request params.
     * @param title
     * @return
     */
    @GetMapping(path = "/message")
    public ResponseEntity<HelloMessage> getMessageWith(@RequestParam(required = true, name = "title") String title,
                                                       @RequestParam(required = true, name = "message") String message) {
        if (title == null || message == null) {
            throw new NotFoundException("Title or message not provided");
        }

        return ResponseEntity.ok(new HelloMessage(title, message));
    }

}
