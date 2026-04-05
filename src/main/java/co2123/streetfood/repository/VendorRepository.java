package co2123.streetfood.repository;

import co2123.streetfood.model.Vendor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.Repository;

public interface VendorRepository extends CrudRepository<Vendor, Integer> {

    Vendor findByName(String name);

    java.util.List<Vendor> findByNameContainingIgnoreCase(String text);
    java.util.List<Vendor> findDistinctByDishes_NameContainingIgnoreCase(String text);

}
