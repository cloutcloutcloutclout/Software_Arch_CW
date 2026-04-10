# Run

1 * Add a SQL source in application properties
2 * clean and build again
3 * run project




## Mark scheme

This is what I got,

Total (out of 35): 32.25 


### Task 1

1.0/1.0: The application.properties configuration is correct (e.g. url username password etc.).

0.5/0.5: The application.properties configuration is correct (e.g. printing SQL to command line).

1.0/1.0: The build.gradle configuration is correct.



### Task 2

0.5/0.5: The @Entity and @Id annotations are correct.

1.0/1.0: @OneToMany correctly present on Vendor.dishes.

1.0/1.0: @OneToMany correctly present on Vendor.photos.

1.0/1.0: @OneToMany correctly present on Vendor.awards.

1.0/1.0: @OneToOne correctly present on Vendor.profile.

1.0/1.0: @ManyToOne correctly present on Award.vendor.

1.0/1.0: @ManyToOne correctly present on Dish.vendor.

1.0/1.0: @OneToMany correctly present on Dish.reviews.

1.0/1.0: @ManyToMany correctly present on Dish.tags.

1.0/1.0: @ManyToOne correctly present on Photo.vendor.

1.0/1.0: @ManyToOne correctly present on Review.dish.

2.0/2.0: The tables and their columns were found.



### Task 3

1.0/1.0: All repositories were found.

1.0/1.0: All repositories were interfaces.

1.0/1.0: All repositories extended CrudRepository.

1.0/1.0: All repositories had expected parameters.



### Task 4

1.0/1.0: Tables and columns set up as expected for task 4.

0.5/1.0: Some tables contain their expected data but not all. The following tables contained expected data: award dish photo review vendor vendor_profile

1.0/1.0: @JoinColumn correctly present on Award.vendor.

1.0/1.0: @JoinColumn correctly present on Dish.vendor.

1.0/1.0: @JoinColumn correctly present on Photo.vendor.

1.0/1.0: @JoinColumn correctly present on Vendor.profile.

1.0/1.0: @JoinColumn correctly present on Review.dish.

1.0/1.0: Vendor.awards correctly mapped with mappedBy="vendor".

1.0/1.0: Vendor.dishes correctly mapped with mappedBy="vendor".

1.0/1.0: Vendor.photos correctly mapped with mappedBy="vendor".

1.0/1.0: Dish.reviews correctly mapped with mappedBy="dish".

1.0/1.0: Tag.dishes correctly mapped with mappedBy="tags".



### Task 5

1.0/1.0: All id variables have @GeneratedValue



### Task 6 Task 6.1

1.0/1.0: ArrayLists have been successfully removed from the project.

0.5/0.5: Vendor's profile cascade type is ALL.



### Task 6.2

0.0/0.5: The findVendor method should be removed from AddEditController.

1.0/1.0: /vendors works as expected

1.0/1.0: /admin works as expected

1.0/1.0: /addVendor works as expected

1.0/1.0: /vendor works as expected

1.0/1.0: /editVendor works as expected

1.0/1.0: /editedVendor works as expected



### Task 6.3

1.0/1.0: /editVendorProfile works as expected

1.0/1.0: /editedVendorProfile works as expected



### Task 6.4

0.5/0.5: /newAward works as expected

0.5/0.5: /addAward works as expected

0.5/0.5: /editAward works as expected

0.5/0.5: /editedAward works as expected

0.5/0.5: /newPhoto works as expected

0.5/0.5: /addPhoto works as expected

0.5/0.5: /editPhoto works as expected

0.5/0.5: /editedPhoto works as expected



### Task 6.5

0.5/0.5: Vendor's dishes has CascadeType.ALL

1.0/1.0: /newDish works as expected

1.0/1.0: /addDish works as expected

1.0/1.0: /editDish works as expected

1.0/1.0: /editedDish works as expected

1.0/1.0: /newReview works as expected

1.0/1.0: /addReview works as expected

1.0/1.0: /editReview works as expected

1.0/1.0: /editedReview works as expected



### Task 6.6

0.5/0.5: Dish's reviews and Vendor's awards have CascadeType.ALL

0.0/0.5: Vendor's dishes should have orphanRemoval = true

1.0/1.0: /deleteVendor works as expected

0.0/1.0: /deleteDish does not work as expected

1.0/1.0: /deleteReview works as expected

1.0/1.0: /deletePhoto works as expected

1.0/1.0: /deleteAward works as expected



### Task 6.7

0.5/0.5: Empty names are rejected when adding a vendor

0.5/0.5: Empty locations are rejected when adding a vendor

0.5/0.5: Empty cuisine types are rejected when adding a vendor

0.5/0.5: Overly long cuisine types are rejected when adding a vendor

1.0/1.0: Already used names cannot be given to new vendors

1.0/1.0: findByName method is in VendorRepository

0.5/0.5: VendorValidator is configured as expected for database integration (like lab 9)



### Task 6.8

1.0/1.0: findByNameContains was not found in VendorRepository

0.0/1.0: /search1 does not work as expected

0.0/1.0: findByDishesNameContains was not found in VendorRepository

0.0/1.0: /search2 does not work as expected



### Task 7

0.5/0.5: Dish's tags are loaded using the FetchType EAGER

0.5/0.5: Vendor's photos have the expected CascadeType
