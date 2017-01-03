package com.zgulde;

import com.zgulde.users.User;
import org.springframework.security.core.context.SecurityContextHolder;

public class BaseController {
    protected User loggedInUser() {
        return (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }
}
