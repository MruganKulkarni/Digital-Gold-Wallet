package com.digitalgoldwallet.digital_gold_wallet.controller;

import com.digitalgoldwallet.digital_gold_wallet.dto.response.VendorResponseDto;

import com.digitalgoldwallet.digital_gold_wallet.entity.VendorBranch;

import com.digitalgoldwallet.digital_gold_wallet.repository.VendorBranchRepository;

import com.digitalgoldwallet.digital_gold_wallet.service.VendorService;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import org.springframework.stereotype.Controller;

import org.springframework.ui.Model;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

/*
 * ============================================================
 * VENDOR VIEW CONTROLLER
 * ============================================================
 */

@Controller
public class VendorViewController {

    @Autowired
    private VendorService vendorService;

    @Autowired
    private VendorBranchRepository vendorBranchRepository;

    /*
     * ============================================================
     * VENDOR MODULE PAGE
     * ============================================================
     */
    @GetMapping("/vendor-module")
    public String vendorModule() {

        return "vendor-module";
    }

    /*
     * ============================================================
     * VIEW ALL VENDORS
     * ============================================================
     */
    @GetMapping("/vendors")
    public String viewVendors(
            Model model
    ) {

        Pageable pageable =
                PageRequest.of(0, 10);

        Page<VendorResponseDto> vendorPage =
                vendorService.getAllVendors(pageable);

        model.addAttribute(
                "vendors",
                vendorPage.getContent()
        );

        return "vendors";
    }

    /*
     * ============================================================
     * CREATE VENDOR PAGE
     * ============================================================
     */
    @GetMapping("/vendors/create")
    public String createVendorPage() {

        return "create-vendor";
    }

    /*
     * ============================================================
     * VIEW SINGLE VENDOR
     * ============================================================
     */
    @GetMapping("/vendors/view/{id}")
    public String viewVendor(
            @PathVariable Integer id,
            Model model
    ) {

        VendorResponseDto vendor;

        try {

            vendor =
                    vendorService.getVendorById(id);

            System.out.println(
                    "VENDOR DATA = " + vendor
            );

        } catch (Exception e) {

            e.printStackTrace();

            vendor = null;
        }

        model.addAttribute(
                "vendor",
                vendor
        );

        return "view-vendor";
    }

    /*
     * ============================================================
     * EDIT VENDOR PAGE
     * ============================================================
     */
    @GetMapping("/vendors/update/{id}")
    public String editVendor(
            @PathVariable Integer id,
            Model model
    ) {

        VendorResponseDto vendor =
                vendorService.getVendorById(id);

        model.addAttribute(
                "vendor",
                vendor
        );

        return "edit-vendor";
    }

    /*
     * ============================================================
     * VENDOR PRICE PAGE
     * ============================================================
     */
    @GetMapping("/vendors/price/{id}")
    public String vendorPrice(
            @PathVariable Integer id,
            Model model
    ) {

        VendorResponseDto vendor =
                vendorService.getVendorById(id);

        model.addAttribute(
                "vendor",
                vendor
        );

        return "vendor-price";
    }

    /*
     * ============================================================
     * VIEW ALL BRANCHES
     * ============================================================
     */
    @GetMapping("/branches")
    public String viewBranches(
            Model model
    ) {

        List<VendorBranch> branches =
                vendorBranchRepository.findAll();

        model.addAttribute(
                "branches",
                branches
        );

        return "branches";
    }

    /*
     * ============================================================
     * CREATE BRANCH PAGE
     * ============================================================
     */
    @GetMapping("/branches/create")
    public String createBranchPage() {

        return "create-branch";
    }

    /*
     * ============================================================
     * VIEW BRANCH
     * ============================================================
     */
    @GetMapping("/branches/view/{id}")
    public String viewBranch(
            @PathVariable Integer id,
            Model model
    ) {

        VendorBranch branch =
                vendorBranchRepository
                        .findById(id)
                        .orElse(null);

        model.addAttribute(
                "branch",
                branch
        );

        return "view-branch";
    }

    /*
     * ============================================================
     * BRANCH INVENTORY
     * ============================================================
     */
    @GetMapping("/branches/inventory/{id}")
    public String branchInventory(
            @PathVariable Integer id,
            Model model
    ) {

        VendorBranch branch =
                vendorBranchRepository
                        .findById(id)
                        .orElse(null);

        model.addAttribute(
                "branch",
                branch
        );

        return "branch-inventory";
    }
}