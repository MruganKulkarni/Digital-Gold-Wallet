package com.digitalgoldwallet.digital_gold_wallet.controller;

import com.digitalgoldwallet.digital_gold_wallet.dto.request.UserRequestDto;

import com.digitalgoldwallet.digital_gold_wallet.dto.response.UserResponseDto;

import com.digitalgoldwallet.digital_gold_wallet.service.UserService;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Controller;

import org.springframework.ui.Model;

import org.springframework.validation.BindingResult;

import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.bind.annotation.ModelAttribute;

import org.springframework.web.bind.annotation.PathVariable;

import org.springframework.web.bind.annotation.PostMapping;

import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigDecimal;

import java.util.List;

/*
 * ============================================================
 * USER THYMELEAF MVC CONTROLLER
 * ============================================================
 */

@Controller
@RequestMapping("/users")
public class UserViewController {

    /*
     * ============================================================
     * SERVICE INJECTION
     * ============================================================
     */
    @Autowired
    private UserService userService;

    /*
     * ============================================================
     * GET ALL USERS
     * ============================================================
     */
    @GetMapping
    public String getAllUsers(Model model) {

        List<UserResponseDto> users =
                userService.getAllUsers();

        model.addAttribute(
                "users",
                users
        );

        return "users";
    }

    /*
     * ============================================================
     * CREATE FORM
     * ============================================================
     */
    @GetMapping("/create")
    public String showCreateForm(Model model) {

        model.addAttribute(
                "userRequestDto",
                new UserRequestDto()
        );

        return "create-user";
    }

    /*
     * ============================================================
     * SAVE USER
     * ============================================================
     */
    @PostMapping("/save")
    public String saveUser(

            @Valid

            @ModelAttribute
            UserRequestDto userRequestDto,

            BindingResult bindingResult
    ) {

        if (bindingResult.hasErrors()) {

            return "create-user";
        }

        userService.createUser(userRequestDto);

        return "redirect:/users";
    }

    /*
     * ============================================================
     * VIEW USER BY ID
     * ============================================================
     */
    @GetMapping("/view")
    public String viewUser(

            @RequestParam
            Integer id,

            Model model
    ) {

        UserResponseDto user =
                userService.getUserById(id);

        model.addAttribute(
                "user",
                user
        );

        return "view-user";
    }

    /*
     * ============================================================
     * EDIT USER FORM
     * ============================================================
     */
    @GetMapping("/edit")
    public String editUser(

            @RequestParam
            Integer id,

            Model model
    ) {

        /*
         * Get user from database
         */
        UserResponseDto user =
                userService.getUserById(id);

        /*
         * Create DTO object
         */
        UserRequestDto dto =
                new UserRequestDto();

        /*
         * Set values
         */
        dto.setName(
                user.getName()
        );

        dto.setEmail(
                user.getEmail()
        );

        dto.setBalance(
                user.getBalance()
        );

        /*
         * Send values to HTML
         */
        model.addAttribute(
                "userId",
                id
        );

        model.addAttribute(
                "userRequestDto",
                dto
        );

        /*
         * Return edit page
         */
        return "edit-user";
    }

    /*
     * ============================================================
     * UPDATE USER
     * ============================================================
     */
    @PostMapping("/update/{id}")
    public String updateUser(

            @PathVariable
            Integer id,

            @ModelAttribute
            UserRequestDto userRequestDto
    ) {

        userService.updateUser(
                id,
                userRequestDto
        );

        return "redirect:/users";
    }

    /*
     * ============================================================
     * DELETE USER
     * ============================================================
     */
    @GetMapping("/delete")
    public String deleteUser(

            @RequestParam
            Integer id
    ) {

        userService.deleteUser(id);

        return "redirect:/users";
    }

    /*
     * ============================================================
     * USER BALANCE
     * ============================================================
     */
    @GetMapping("/balance")
    public String viewBalance(

            @RequestParam
            Integer id,

            Model model
    ) {

        BigDecimal balance =
                userService.getUserBalance(id);

        model.addAttribute(
                "balance",
                balance
        );

        model.addAttribute(
                "userId",
                id
        );

        return "user-balance";
    }
}