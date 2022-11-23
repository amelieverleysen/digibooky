package com.switchfully.digibooky.api;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "users")
public class UserController {
    private final Logger myLogger = LoggerFactory.getLogger(UserController.class);
}
