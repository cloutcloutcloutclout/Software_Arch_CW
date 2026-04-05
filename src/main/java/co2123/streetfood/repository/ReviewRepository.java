package co2123.streetfood.repository;

import co2123.streetfood.model.Review;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.Repository;

public interface ReviewRepository extends CrudRepository<Review, Integer> {
}
