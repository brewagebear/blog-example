package io.brewagebear.asyncserver.controller;


import io.brewagebear.asyncserver.dto.UserMenuRequest;
import io.brewagebear.asyncserver.dto.UserMenuResponse;
import io.brewagebear.asyncserver.service.UserMenuService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
public class UserMenuController {

    private final UserMenuService userMenuService;

    public UserMenuController(UserMenuService userMenuService) {
        this.userMenuService = userMenuService;
    }

    @PostMapping("/users/menus")
    public ResponseEntity<UserMenuResponse> menus(@RequestBody UserMenuRequest request) throws InterruptedException {

        UserMenuResponse userMenuResponse = userMenuService.userMenu(request);
        return ResponseEntity.ok().body(userMenuResponse);
    }

}
