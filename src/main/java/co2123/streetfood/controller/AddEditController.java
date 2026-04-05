package co2123.streetfood.controller;

import co2123.streetfood.StreetfoodApplication;
import co2123.streetfood.model.*;
import co2123.streetfood.repository.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


@Controller
public class AddEditController {
    private final ReviewRepository reviewRepository;
    private final VendorRepository vendorRepository;
    private final AwardRepository awardRepository;
    private final PhotoRepository photoRepository;
    private final TagRepository tagRepository;

    public AddEditController(ReviewRepository reviewRepository, VendorRepository vendorRepository, AwardRepository awardRepository, PhotoRepository photoRepository, TagRepository tagRepository) {
        this.reviewRepository = reviewRepository;
        this.vendorRepository = vendorRepository;
        this.awardRepository = awardRepository;
        this.photoRepository = photoRepository;
        this.tagRepository = tagRepository;
    }

    public Vendor findVendor(int id){
        return vendorRepository.findById(id).orElse(null);
    }

    @RequestMapping("editVendor")
    public String editVendorForm(@RequestParam Integer id, Model model) {
        Vendor foundVendor = findVendor(id);

        if(foundVendor == null) {
            return "redirect:/admin";
        }
        model.addAttribute("vendor", foundVendor);
        return "forms/editVendor";
    }


    @RequestMapping("editedVendor")
    public String submittedEditForm(@RequestParam Integer id, @ModelAttribute Vendor vendor, Model model) {
        Vendor foundVendor = findVendor(id);

        if(foundVendor == null) {
            return "redirect:/admin";
        }
        foundVendor.setName(vendor.getName());
        foundVendor.setLocation(vendor.getLocation());
        foundVendor.setCuisineType(vendor.getCuisineType());
        vendorRepository.save(foundVendor);

        model.addAttribute("vendor", foundVendor);
        return "redirect:/vendor?id=" + id;
    }



    @RequestMapping("editVendorProfile")
    public String editVendorProfileForm(@RequestParam Integer id, Model model) {
        Vendor foundVendor = findVendor(id);
        if (foundVendor == null) {
            return "redirect:/admin";
        }

        VendorProfile profile = foundVendor.getProfile();
        model.addAttribute("profile", profile);
        model.addAttribute("vendor", id);
        return "forms/editVendorProfile";
    }

    @RequestMapping("editedVendorProfile")
    public String submittedProfileEditForm(@RequestParam Integer id, @ModelAttribute VendorProfile profile, Model model) {
        Vendor foundVendor = findVendor(id);
        if (foundVendor == null) {
            return "redirect:/admin";
        }

        if(foundVendor.getProfile() == null){

            foundVendor.setProfile(profile);

        } else {

            foundVendor.getProfile().setBio(profile.getBio());
            foundVendor.getProfile().setSocialMediaHandle(profile.getSocialMediaHandle());
            foundVendor.getProfile().setWebsite(profile.getWebsite());
        }
        vendorRepository.save(foundVendor);

        model.addAttribute("vendor", foundVendor);
        return "redirect:/vendor?id=" + id;
    }


    @RequestMapping("newDish")
    public String newDishForm(@RequestParam Integer id, Model model) {
        Vendor foundVendor = findVendor(id);
        if (foundVendor == null) {
            return "redirect:/admin";
        }

        model.addAttribute("vendor", foundVendor);
        model.addAttribute("dish", new Dish());
        model.addAttribute("tags", tagRepository.findAll());
        return "forms/newDish";
    }

    @RequestMapping("addDish")
    public String addDish(@RequestParam Integer vendorid, @RequestParam List<Integer> tagIds, @ModelAttribute Dish dish, Model model) {
        Vendor foundVendor = findVendor(vendorid);
        if (foundVendor == null) {
            return "redirect:/admin";
        }

        if(foundVendor.getDishes().isEmpty()){
            foundVendor.setDishes(new ArrayList<>());
        }

        List<Tag> tags = (List<Tag>) tagRepository.findAllById(tagIds);
        dish.setTags(tags);


        dish.setReviews(new ArrayList<>());
        dish.setVendor(foundVendor);

        foundVendor.getDishes().add(dish);

        vendorRepository.save(foundVendor);

        model.addAttribute("vendor", foundVendor);
        return "redirect:/vendor?id=" + vendorid;
    }



    @RequestMapping("newReview")
    public String newReview(@RequestParam Integer vendorid, @RequestParam Integer dishid, Model model) {
        Vendor foundVendor = findVendor(vendorid);
        if (foundVendor == null) {
            return "redirect:/admin";
        }

        model.addAttribute("vendor", foundVendor);
        model.addAttribute("dishid",dishid);
        model.addAttribute("review", new Review());
        return "forms/newReview";
    }

    @RequestMapping("addReview")
    public String addReview(@RequestParam Integer vendorid, @RequestParam Integer dishid, @ModelAttribute Review review, Model model) {
        Vendor foundVendor = findVendor(vendorid);
        if (foundVendor == null) {
            return "redirect:/admin";
        }

        Dish foundDish = null;
        for (Dish dish : foundVendor.getDishes()) {
            if (dish.getId() == dishid) {
                foundDish = dish;
                break;
            }
        }

        if (foundDish == null) {
            return "redirect:/admin";
        }

        review.setReviewDate(LocalDateTime.now());
        review.setDish(foundDish);
        //review.setId(StreetfoodApplication.reviewList.size()+1);
        //StreetfoodApplication.reviewList.add(review);

        reviewRepository.save(review);

        if(foundDish.getReviews().isEmpty()){
            foundDish.setReviews(new ArrayList<>());
        }
        foundDish.getReviews().add(review);

        model.addAttribute("vendor", foundVendor);
        return "redirect:/vendor?id=" + vendorid;
    }

    @RequestMapping("newPhoto")
    public String newPhoto(@RequestParam Integer vendorid, Model model) {
        Vendor foundVendor = findVendor(vendorid);
        if (foundVendor == null) {
            return "redirect:/admin";
        }

        model.addAttribute("vendor", foundVendor);
        model.addAttribute("photo", new Photo());
        return "forms/newPhoto";
    }

    @RequestMapping("addPhoto")
    public String addPhoto(@RequestParam Integer vendorid, @ModelAttribute Photo photo, Model model) {
        Vendor foundVendor = findVendor(vendorid);
        if (foundVendor == null) {
            return "redirect:/admin";
        }

        photo.setVendor(foundVendor);
        //photo.setId(StreetfoodApplication.photoList.size()+1);
        //StreetfoodApplication.photoList.add(photo);

        foundVendor.getPhotos().add(photo);
        photoRepository.save(photo);

        model.addAttribute("vendor", foundVendor);
        return "redirect:/vendor?id=" + vendorid;
    }

    @RequestMapping("newAward")
    public String newAward(@RequestParam Integer vendorid, Model model) {
        Vendor foundVendor = findVendor(vendorid);
        if (foundVendor == null) {
            return "redirect:/admin";
        }

        model.addAttribute("vendor", foundVendor);
        model.addAttribute("award", new Award());
        return "forms/newAward";
    }

    @RequestMapping("addAward")
    public String addAward(@RequestParam Integer vendorid, @ModelAttribute Award award, Model model) {
        Vendor foundVendor = findVendor(vendorid);
        if (foundVendor == null) {
            return "redirect:/admin";
        }

        award.setVendor(foundVendor);
        //award.setId(StreetfoodApplication.awardList.size()+1);
        //StreetfoodApplication.awardList.add(award);

        foundVendor.getAwards().add(award);
        awardRepository.save(award);

        model.addAttribute("vendor", foundVendor);
        return "redirect:/vendor?id=" + vendorid;
    }



    @RequestMapping("editDish")
    public String editDishForm(@RequestParam Integer vendorid, @RequestParam Integer dishid, Model model) {
        Vendor foundVendor = findVendor(vendorid);
        if (foundVendor == null) {
            return "redirect:/admin";
        }

        Dish foundDish = null;
        for (Dish dish : foundVendor.getDishes()) {
            if (dish.getId() == dishid) {
                foundDish = dish;
                break;
            }
        }

        if (foundDish == null) {
            return "redirect:/admin";
        }

        model.addAttribute("vendor", foundVendor);
        model.addAttribute("dish", foundDish);
        model.addAttribute("tags", tagRepository.findAll());
        return "forms/editDish";
    }
    @RequestMapping("editedDish")
    public String submittedEditDishForm(@RequestParam Integer vendorid, @RequestParam Integer dishid, @RequestParam(required = false) List<Integer> tagIds, @ModelAttribute Dish dish, Model model) {
        Vendor foundVendor = findVendor(vendorid);
        if (foundVendor == null) {
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

        foundDish.setName(dish.getName());
        foundDish.setPrice(dish.getPrice());

        foundDish.setDescription(dish.getDescription());
        foundDish.setSpiceLevel(dish.getSpiceLevel());

        if (tagIds != null) {
            List<Tag> tags = (List<Tag>) tagRepository.findAllById(tagIds);
            foundDish.setTags(tags);
        } else {
            foundDish.setTags(new ArrayList<>());
        }

        vendorRepository.save(foundVendor);

        model.addAttribute("vendor", foundVendor);
        return "redirect:/vendor?id=" + vendorid;
    }

    @RequestMapping("editReview")
    public String editReview(@RequestParam Integer vendorId, @RequestParam Integer reviewId, Model model) {
        Vendor foundVendor = findVendor(vendorId);
        if (foundVendor == null) {
            return "redirect:/admin";
        }

        Review foundReview = reviewRepository.findById(reviewId).orElse(null);


        if (foundReview == null) {
            return "redirect:/admin";
        }

        model.addAttribute("vendor", foundVendor);
        model.addAttribute("review", foundReview);
        return "forms/editReview";
    }

    @RequestMapping("editedReview")
    public String editedReview(@RequestParam Integer vendorId, @RequestParam Integer reviewId, @ModelAttribute Review review, Model model) {
        Review foundReview = reviewRepository.findById(reviewId).orElse(null);


        if (foundReview == null) {
            return "redirect:/admin";
        }

        foundReview.setReviewerName(review.getReviewerName());
        foundReview.setComment(review.getComment());
        foundReview.setRating(review.getRating());
        reviewRepository.save(foundReview);

        Vendor foundVendor = findVendor(vendorId);
        if (foundVendor == null) {
            return "redirect:/admin";
        }

        model.addAttribute("vendor", foundVendor);
        return "redirect:/vendor?id=" + vendorId;
    }

    @RequestMapping("editPhoto")
    public String editPhoto(@RequestParam Integer photoId, Model model) {
        Photo foundPhoto = photoRepository.findById(photoId).orElse(null);

        if (foundPhoto == null) {
            return "redirect:/admin";
        }

        model.addAttribute("photo", foundPhoto);
        return "forms/editPhoto";
    }

    @RequestMapping("editedPhoto")
    public String editedPhoto(@RequestParam Integer photoId, @ModelAttribute Photo photo, Model model) {
        Photo foundPhoto = photoRepository.findById(photoId).orElse(null);

        if (foundPhoto == null) {
            return "redirect:/admin";
        }

        foundPhoto.setDescription(photo.getDescription());
        foundPhoto.setUrl(photo.getUrl());
        photoRepository.save(foundPhoto);

        Vendor foundVendor = findVendor(foundPhoto.getVendor().getId());
        if (foundVendor == null) {
            return "redirect:/admin";
        }

        model.addAttribute("vendor", foundVendor);
        return "redirect:/vendor?id=" + foundVendor.getId();
    }

    @RequestMapping("editAward")
    public String editAward(@RequestParam Integer awardId, Model model) {
        Award foundAward = awardRepository.findById(awardId).orElse(null);

        if (foundAward == null) {
            return "redirect:/admin";
        }

        model.addAttribute("award", foundAward);
        return "forms/editAward";
    }

    @RequestMapping("editedAward")
    public String editedAward(@RequestParam Integer awardId, @ModelAttribute Award award, Model model) {
        Award foundAward = awardRepository.findById(awardId).orElse(null);

        if (foundAward == null) {
            return "redirect:/admin";
        }

        foundAward.setTitle(award.getTitle());
        foundAward.setYear(award.getYear());
        awardRepository.save(foundAward);

        Vendor foundVendor = findVendor(foundAward.getVendor().getId());
        if (foundVendor == null) {
            return "redirect:/admin";
        }

        model.addAttribute("vendor", foundVendor);
        return "redirect:/vendor?id=" + foundVendor.getId();
    }
}
