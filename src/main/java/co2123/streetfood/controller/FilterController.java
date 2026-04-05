package co2123.streetfood.controller;

import co2123.streetfood.StreetfoodApplication;
import co2123.streetfood.model.Dish;
import co2123.streetfood.model.Vendor;
import co2123.streetfood.repository.VendorRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

@Controller
public class FilterController {


    private final VendorRepository vendorRepository;

    public FilterController(VendorRepository vendorRepository) {
        this.vendorRepository = vendorRepository;
    }

    @GetMapping("/search1")
    public String search1(@RequestParam String vendor, Model model) {
        List<Vendor> list = vendorRepository.findByNameContainingIgnoreCase(vendor);
        /*if(list.isEmpty()){
            model.addAttribute("vendors", StreetfoodApplication.vendorList);
        } else {
            model.addAttribute("vendors", list);
        }*/
        return "vendors";
    }



    @GetMapping("/search2")
    public String search2(@RequestParam String dish, Model model) {
        List<Vendor> list = vendorRepository.findDistinctByDishes_NameContainingIgnoreCase(dish);
        if(list.isEmpty()){
            //model.addAttribute("vendors", StreetfoodApplication.vendorList);
        } else {
            model.addAttribute("vendors", list);
        }
        return "vendors";
    }

}
