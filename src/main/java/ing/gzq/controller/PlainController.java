package ing.gzq.controller;

import ing.gzq.base.Result;
import ing.gzq.base.ResultCache;
import ing.gzq.model.User;
import ing.gzq.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by gzq on 17-7-20.
 */

@RestController
public class PlainController {

    @Autowired
    UserService userService;

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public Result register(User user) {
        if (userService.loadUserByUsername(user.getUsername()) != null) {
            return ResultCache.getFailureDetail("该账号已经被注册");
        }
        return userService.insertUser(user);
    }

    @RequestMapping(value = "/nologin", method = RequestMethod.GET)
    public Result nologin() {
        return ResultCache.PERMISSION_DENIED;
    }
}
