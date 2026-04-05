package co2123.streetfood.controller;

import co2123.streetfood.StreetfoodApplication;
import co2123.streetfood.model.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import co2123.streetfood.repository.*;
import javax.sound.sampled.ReverbType;

@Controller
public class DeleteController {
    private final ReviewRepository reviewRepository;
    private final PhotoRepository photoRepository;
    private final AwardRepository awardRepository;
    private final VendorRepository vendorRepository;

    private Vendor findVendor(Integer id) {
        return vendorRepository.findById(id).orElse(null);
    }

    public DeleteController(ReviewRepository reviewRepository, PhotoRepository photoRepository, AwardRepository awardRepository, VendorRepository vendorRepository) {
        this.reviewRepository = reviewRepository;
        this.photoRepository = photoRepository;
        this.awardRepository = awardRepository;
        this.vendorRepository = vendorRepository;
    }

    @RequestMapping("/deleteVendor")
    public String deleteVendor(@RequestParam("id") Integer id) {
        Vendor foundVendor = findVendor(id);
        if(foundVendor != null){
            //StreetfoodApplication.vendorList.remove(foundVendor);
            vendorRepository.delete(foundVendor);
        }
        return "redirect:/admin";
    }

    @RequestMapping("/deleteDish")
    public String deleteDish(@RequestParam Integer vendorid, @RequestParam Integer dishid) {
        Vendor foundVendor = findVendor(vendorid);
        if (foundVendor==null) {
            return "redirect:/admin";
        }

        Dish foundDish = null;
        for (Dish d : foundVendor.getDishes()) {
            if (d.getId() == dishid) {
                foundDish = d;
                break;
            }
        }

        if (foundDish == null) {
            return "redirect:/admin";
        }

        foundVendor.getDishes().remove(foundDish);
        foundDish.setVendor(null);
        vendorRepository.save(foundVendor);


        return "redirect:/vendor?id=" + vendorid;
    }

    @RequestMapping("/deleteReview")
    public String deleteReview(@RequestParam Integer vendorId, @RequestParam Integer reviewId) {
        Review foundReview = reviewRepository.findById(reviewId).orElse(null);


        Vendor foundVendor = findVendor(vendorId);
        if(foundVendor == null){
            return "redirect:/admin";
        }


        Dish dish = foundReview.getDish();
        if (dish != null && dish.getReviews() != null) {
            dish.getReviews().remove(foundReview);
            foundReview.setDish(null);
        }

        reviewRepository.delete(foundReview);

        return "redirect:/vendor?id=" + vendorId;
    }

    @RequestMapping("/deletePhoto")
    public String deletePhoto(@RequestParam Integer photoId) {
        Photo foundPhoto = photoRepository.findById(photoId).orElse(null);

        if(foundPhoto != null){
            //StreetfoodApplication.photoList.remove(foundPhoto);
        } else {
            return "redirect:/admin";
        }

        Vendor foundVendor = findVendor(foundPhoto.getVendor().getId());
        if(foundVendor == null){
            return "redirect:/admin";
        }

        if (foundVendor.getPhotos() != null) {
            foundVendor.getPhotos().remove(foundPhoto);
        }

        foundPhoto.setVendor(null);
        photoRepository.delete(foundPhoto);
        return "redirect:/vendor?id=" + foundVendor.getId();
    }

    @RequestMapping("/deleteAward")
    public String deleteAward(@RequestParam Integer awardId) {
        Award foundAward = awardRepository.findById(awardId).orElse(null);


        if(foundAward != null){
            //StreetfoodApplication.awardList.remove(foundAward);
        } else {
            return "redirect:/admin";
        }

        Vendor foundVendor = findVendor(foundAward.getVendor().getId());
        if(foundVendor == null){
            return "redirect:/admin";
        }

        if (foundVendor.getAwards() != null) {
            foundVendor.getAwards().remove(foundAward);
        }
        foundAward.setVendor(null);
        vendorRepository.save(foundVendor);

        return "redirect:/vendor?id=" + foundVendor.getId();
    }
}
