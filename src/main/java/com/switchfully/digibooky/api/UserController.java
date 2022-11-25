package com.switchfully.digibooky.api;

import com.switchfully.digibooky.api.dtos.*;
import com.switchfully.digibooky.domain.security.Feature;
import com.switchfully.digibooky.services.SecurityService;
import com.switchfully.digibooky.services.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "users")
public class UserController {
    private final UserService userService;
    private final SecurityService securityService;

    public UserController(UserService userService, SecurityService securityService) {
        this.userService = userService;
        this.securityService = securityService;
    }

    @PostMapping(path = "members", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public MemberDto createMember(@RequestBody CreateMemberDto createMemberDto) {
        return userService.createMember(createMemberDto);
    }

    @PostMapping(path = "librarians", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public UserDto createLibrarian(@RequestBody CreateUserDto createUserDto, @RequestHeader String authorization) {
        securityService.validateAuthorisation(authorization, Feature.CREATE_LIBRARIAN);
        return userService.createLibrarian(createUserDto);
    }

    @GetMapping(path = "members", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<MemberAdminDto> getAllMembers(@RequestHeader String authorization) {
        securityService.validateAuthorisation(authorization, Feature.GET_ALL_MEMBERS);
        return userService.getAllMembers();
    }
}
