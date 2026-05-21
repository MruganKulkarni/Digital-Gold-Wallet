package com.digitalgoldwallet.digital_gold_wallet.controller;

import com.digitalgoldwallet.digital_gold_wallet.dto.response.VendorResponseDto; // response DTO for vendor

import com.digitalgoldwallet.digital_gold_wallet.entity.VendorBranch; // VendorBranch entity

import com.digitalgoldwallet.digital_gold_wallet.repository.VendorBranchRepository; // repository for branch operations

import com.digitalgoldwallet.digital_gold_wallet.service.VendorService; // service interface for vendor operations

import org.springframework.beans.factory.annotation.Autowired; // used for dependency injection

import org.springframework.stereotype.Controller; // marks this as a Thymeleaf controller

import org.springframework.ui.Model; // used to pass data to Thymeleaf templates

import org.springframework.web.bind.annotation.GetMapping; // maps GET requests
import org.springframework.web.bind.annotation.PathVariable; // binds URL path variable to method parameter

import java.util.List; // used for list of vendors and branches

/*
 * ============================================================
 * VENDOR VIEW CONTROLLER
 * ============================================================
 */

@Controller // marks this as a Thymeleaf controller — returns view names not JSON
public class VendorViewController {

    @Autowired
    private VendorService vendorService; // injects VendorService for vendor operations

    @Autowired
    private VendorBranchRepository vendorBranchRepository; // injects VendorBranchRepository for branch operations

    /*
     * ============================================================
     * VENDOR MODULE PAGE
     * ============================================================
     */
    @GetMapping("/vendor-module") // maps GET /vendor-module
    public String vendorModule() {

        return "vendor-module"; // returns vendor-module.html template
    }

    /*
     * ============================================================
     * VIEW ALL VENDORS
     * ============================================================
     */
    @GetMapping("/vendors") // maps GET /vendors
    public String viewVendors(
            Model model
    ) {

        List<VendorResponseDto> vendors =
                vendorService.getAllVendors(); // calls updated service method — no pageable needed — returns all vendors

        model.addAttribute(
                "vendors",
                vendors // passes complete vendor list directly to Thymeleaf model
        );

        return "vendors"; // returns vendors.html template
    }

    /*
     * ============================================================
     * CREATE VENDOR PAGE
     * ============================================================
     */
    @GetMapping("/vendors/create") // maps GET /vendors/create
    public String createVendorPage() {

        return "create-vendor"; // returns create-vendor.html template
    }

    /*
     * ============================================================
     * VIEW SINGLE VENDOR
     * ============================================================
     */
    @GetMapping("/vendors/view/{id}") // maps GET /vendors/view/{id}
    public String viewVendor(
            @PathVariable Integer id, // binds {id} from URL
            Model model
    ) {

        VendorResponseDto vendor;

        try {

            vendor =
                    vendorService.getVendorById(id); // fetches vendor by ID

            System.out.println(
                    "VENDOR DATA = " + vendor
            );

        } catch (Exception e) {

            e.printStackTrace();

            vendor = null; // sets vendor to null if not found
        }

        model.addAttribute(
                "vendor",
                vendor // passes vendor to Thymeleaf model
        );

        return "view-vendor"; // returns view-vendor.html template
    }

    /*
     * ============================================================
     * EDIT VENDOR PAGE
     * ============================================================
     */
    @GetMapping("/vendors/update/{id}") // maps GET /vendors/update/{id}
    public String editVendor(
            @PathVariable Integer id, // binds {id} from URL
            Model model
    ) {

        VendorResponseDto vendor =
                vendorService.getVendorById(id); // fetches vendor by ID for pre-filling edit form

        model.addAttribute(
                "vendor",
                vendor // passes vendor to Thymeleaf model
        );

        return "edit-vendor"; // returns edit-vendor.html template
    }

    /*
     * ============================================================
     * VENDOR PRICE PAGE
     * ============================================================
     */
    @GetMapping("/vendors/price/{id}") // maps GET /vendors/price/{id}
    public String vendorPrice(
            @PathVariable Integer id, // binds {id} from URL
            Model model
    ) {

        VendorResponseDto vendor =
                vendorService.getVendorById(id); // fetches vendor by ID for price display

        model.addAttribute(
                "vendor",
                vendor // passes vendor to Thymeleaf model
        );

        return "vendor-price"; // returns vendor-price.html template
    }

    /*
     * ============================================================
     * VIEW ALL BRANCHES
     * ============================================================
     */
    @GetMapping("/branches") // maps GET /branches
    public String viewBranches(
            Model model
    ) {

        List<VendorBranch> branches =
                vendorBranchRepository.findAll(); // fetches all branches from DB

        model.addAttribute(
                "branches",
                branches // passes branch list to Thymeleaf model
        );

        return "branches"; // returns branches.html template
    }

    /*
     * ============================================================
     * CREATE BRANCH PAGE
     * ============================================================
     */
    @GetMapping("/branches/create") // maps GET /branches/create
    public String createBranchPage() {

        return "create-branch"; // returns create-branch.html template
    }

    /*
     * ============================================================
     * VIEW BRANCH
     * ============================================================
     */
    @GetMapping("/branches/view/{id}") // maps GET /branches/view/{id}
    public String viewBranch(
            @PathVariable Integer id, // binds {id} from URL
            Model model
    ) {

        VendorBranch branch =
                vendorBranchRepository
                        .findById(id)
                        .orElse(null); // fetches branch by ID — returns null if not found

        model.addAttribute(
                "branch",
                branch // passes branch to Thymeleaf model
        );

        return "view-branch"; // returns view-branch.html template
    }

    /*
     * ============================================================
     * BRANCH INVENTORY
     * ============================================================
     */
    @GetMapping("/branches/inventory/{id}") // maps GET /branches/inventory/{id}
    public String branchInventory(
            @PathVariable Integer id, // binds {id} from URL
            Model model
    ) {

        VendorBranch branch =
                vendorBranchRepository
                        .findById(id)
                        .orElse(null); // fetches branch by ID — returns null if not found

        model.addAttribute(
                "branch",
                branch // passes branch to Thymeleaf model
        );

        return "branch-inventory"; // returns branch-inventory.html template
    }
}