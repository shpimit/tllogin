package mio.tllogin.web;

import javax.validation.Valid;

import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import mio.tllogin.service.impl.UserDetailsImpl;
import mio.tllogin.service.impl.UserDetailsServiceImpl;

import java.util.List;

@RequiredArgsConstructor
@Controller
public class UserController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final UserDetailsServiceImpl userDetailsServiceImpl;

    @RequestMapping(value = {"/", "/login"}, method = {RequestMethod.GET, RequestMethod.POST})
    public String login(Model model) {
        return "/auth/login";
    }

    @RequestMapping(value = "/registration", method = RequestMethod.GET)
    public String registration(Model model) {
        model.addAttribute("userDetailsImpl", new UserDetailsImpl());
        return "/auth/registration";
    }

    @RequestMapping(value = "/registration", method = RequestMethod.POST)
    public String createNewUser(Model model, @Valid UserDetailsImpl userDetailsImpl, BindingResult bindingResult) {
        try {


            logger.info("## userDetailsImpl.getUsername() = "+ userDetailsImpl.getUsername());
            logger.info("## userDetailsImpl.getUsername() = "+ userDetailsImpl.getUsername());
            logger.info("## userDetailsImpl.getUsername() = "+ userDetailsImpl.getUsername());



            //< check the user name already exist or not
            UserDetails userExists = userDetailsServiceImpl.loadUserByUsername(userDetailsImpl.getUsername());
            if(userExists != null) {
                bindingResult.rejectValue("username", "error.user", "There is already a user registered with the user name provided");
            }

            //< check the password
            if(!userDetailsImpl.getPassword().equals(userDetailsImpl.getConfirmPassword())) {
                bindingResult.rejectValue("confirmPassword", "error.user", "Password not matched");
            }

            if(bindingResult.hasErrors()) {
                logger.error("[Error] : " + bindingResult.getFieldError().toString());
            } else {
                //< save the user information
                userDetailsServiceImpl.insertUser(userDetailsImpl);

                //< set the user information
                model.addAttribute("user", new UserDetailsImpl());
                model.addAttribute("successMessage", "User has been registered successfully");
            }
        } catch (Exception e) {
            logger.error("createNewUser : " + e.getMessage());
            model.addAttribute("successMessage", "FAIL : " + e.getMessage());
        }

        return "/auth/registration";
    }

    @RequestMapping(value = "/home", method = RequestMethod.GET)
    public String home(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserDetailsImpl userDetailsImpl = null;
        try {
            userDetailsImpl = (UserDetailsImpl) userDetailsServiceImpl.loadUserByUsername(auth.getName());
        } catch (Exception e) {
            logger.error("[ykson]" + e.getMessage());
        }

        model.addAttribute("username", "" + userDetailsImpl.getUsername() + "(" + userDetailsImpl.getEmail() + ")");
        model.addAttribute("adminMessage", "Content Available Only for Users with Admin Role");

        return "/index";
    }

    /**
     * Administration Home
     */
    @RequestMapping(value = "/home/admin", method = RequestMethod.GET)
    public String adminHome(Model model) {
        return "/home/admin";
    }

    /**
     * User Home
     */
    @RequestMapping(value = "/home/user", method = RequestMethod.GET)
    public String userHome(Model model) {
        return "/home/user";
    }

    /**
     * Guest Home
     */
    @RequestMapping(value = "/home/guest", method = RequestMethod.GET)
    public String guestHome(Model model) {
        return "/home/guest";
    }

    @ApiOperation(value = "사용자", notes = "사용자 등록한다.")
    @PostMapping(value = "/account/users")
    public String create(UserDetailsImpl user) {

        logger.info("#### create");

        return "success";
    }
//
//    @ApiOperation(value = "사용자", notes = "사용자 삭제한다.")
//    @DeleteMapping(value = "/user/{user_id}")
//    public String delete(User user) {
//
//        userService.delete(user);
//
//        return "redirect:/";
//    }

    @ApiOperation(value = "사용자", notes = "사용자 정보 조회한다.")
    @GetMapping(value = "/account/users")
    public List<UserDetailsImpl> findAllUser() {

        logger.info("#### findAllUser");

        return userDetailsServiceImpl.findAll();
    }
}
