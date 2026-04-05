package co2123.streetfood.controller;

import co2123.streetfood.StreetfoodApplication;
import co2123.streetfood.model.Vendor;
import co2123.streetfood.repository.VendorRepository;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;

@Controller
public class AdminController {

    private final VendorRepository vendorRepository;

    public AdminController(VendorRepository vendorRepository) {
        this.vendorRepository = vendorRepository;
    }

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.addValidators(new VendorValidator(vendorRepository));
    }

    @RequestMapping("/admin")
    public String showAdminPage(Model model) {
        model.addAttribute("vendors", vendorRepository.findAll());
        return "admin";
    }


    @RequestMapping("/newVendor")
    public String newVendor(Model model) {
        model.addAttribute("vendor", new Vendor());
        return "forms/newVendor";
    }


    @RequestMapping("/addVendor")
    public String addVendor(@Valid @ModelAttribute Vendor vendor, BindingResult result) {
        if (result.hasErrors()) {
            return "forms/newVendor";
        }
        //System.out.println(StreetfoodApplication.vendorList.size()+1);
        //vendor.setId(StreetfoodApplication.vendorList.size());
        vendorRepository.save(vendor);
        return "redirect:/admin";
    }

    @RequestMapping("/vendor")
    public String listVendor(@RequestParam Integer id, Model model) {

        Optional<Vendor> foundVendor = vendorRepository.findById(id);


        if(foundVendor.isEmpty()) {
            return "redirect:/admin";
        }

        model.addAttribute("vendor", foundVendor.get());
        return "vendorSummary";
    }
}
