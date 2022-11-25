package com.switchfully.digibooky.api;

import com.switchfully.digibooky.api.dtos.LendItemDto;
import com.switchfully.digibooky.domain.security.Feature;
import com.switchfully.digibooky.services.LendingService;
import com.switchfully.digibooky.services.SecurityService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "lending")
public class LendingController {
    SecurityService securityService;
    LendingService lendingService;

    public LendingController(SecurityService securityService, LendingService lendingService) {
        this.securityService = securityService;
        this.lendingService = lendingService;
    }

    @PostMapping(path = "book", params = "isbn", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public LendItemDto lendBook(@RequestParam String isbn, @RequestHeader String authorization){
        securityService.validateAuthorisation(authorization, Feature.LEND_ITEM);
        return lendingService.lendBook(isbn, authorization);
    }
}
