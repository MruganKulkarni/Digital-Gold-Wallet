package com.digitalgoldwallet.digital_gold_wallet.controller;

import com.digitalgoldwallet.digital_gold_wallet.dto.request.AddressRequestDto;
import com.digitalgoldwallet.digital_gold_wallet.dto.request.UserRequestDto;
import com.digitalgoldwallet.digital_gold_wallet.dto.response.AddressResponseDto;
import com.digitalgoldwallet.digital_gold_wallet.dto.response.UserResponseDto;
import com.digitalgoldwallet.digital_gold_wallet.service.AddressService;
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

    @Autowired
    private AddressService addressService;

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
     * CREATE USER FORM
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
     * VIEW USER
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

        UserResponseDto user =
                userService.getUserById(id);

        UserRequestDto dto =
                new UserRequestDto();

        dto.setName(
                user.getName()
        );

        dto.setEmail(
                user.getEmail()
        );

        dto.setBalance(
                user.getBalance()
        );

        model.addAttribute(
                "userId",
                id
        );

        model.addAttribute(
                "userRequestDto",
                dto
        );

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

    /*
     * ============================================================
     * CREATE ADDRESS PAGE
     * ============================================================
     */

    @GetMapping("/addresses/create")
    public String createAddressPage(Model model) {

        model.addAttribute(
                "address",
                new AddressRequestDto()
        );

        return "create-address";
    }

    /*
     * ============================================================
     * SAVE ADDRESS
     * ============================================================
     */

    @PostMapping("/addresses/save")
    public String saveAddress(
            @ModelAttribute AddressRequestDto addressRequestDto
    ) {

        addressService.createAddress(
                addressRequestDto
        );

        return "redirect:/user-module";
    }

    /*
     * ============================================================
     * VIEW ADDRESS
     * ============================================================
     */

    @GetMapping("/addresses/view/{id}")
    public String viewAddress(

            @PathVariable
            Integer id,

            Model model
    ) {

        AddressResponseDto address =
                addressService.getAddressById(id);

        model.addAttribute(
                "address",
                address
        );

        return "view-address";
    }

    /*
     * ============================================================
     * EDIT ADDRESS PAGE
     * ============================================================
     */

    @GetMapping("/addresses/update/{id}")
    public String editAddressPage(

            @PathVariable
            Integer id,

            Model model
    ) {

        AddressResponseDto address =
                addressService.getAddressById(id);

        model.addAttribute(
                "address",
                address
        );

        return "edit-address";
    }

    /*
     * ============================================================
     * UPDATE ADDRESS
     * ============================================================
     */

    @PostMapping("/addresses/update/{id}")
    public String updateAddress(

            @PathVariable
            Integer id,

            @ModelAttribute
            AddressRequestDto addressRequestDto
    ) {

        addressService.updateAddress(
                id,
                addressRequestDto
        );

        return "redirect:/users/addresses/view/" + id;
    }
}