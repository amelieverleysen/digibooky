package com.switchfully.digibooky.api;

import com.switchfully.digibooky.api.dtos.CreateUserDto;
import com.switchfully.digibooky.api.dtos.CreateMemberDto;
import com.switchfully.digibooky.api.dtos.MemberDto;
import com.switchfully.digibooky.api.dtos.UserDto;
import com.switchfully.digibooky.domain.security.Feature;
import com.switchfully.digibooky.services.SecurityService;
import com.switchfully.digibooky.services.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "users")
public class UserController {

    private final Logger myLogger = LoggerFactory.getLogger(UserController.class);
    private final UserService userService;
    private final SecurityService securityService;

    public UserController(UserService userService, SecurityService securityService) {
        this.userService = userService;
        this.securityService = securityService;
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public MemberDto createMember(@RequestBody CreateMemberDto createMemberDto) {
        return userService.createMember(createMemberDto);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public UserDto createLibrarian(@RequestBody CreateUserDto createUserDto, @RequestHeader String authorization) {
        securityService.validateAuthorisation(authorization, Feature.CREATE_LIBRARIAN);
        return userService.createUser(createUserDto);
    }
}
