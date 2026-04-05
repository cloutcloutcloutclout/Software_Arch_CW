package co2123.streetfood.repository;

import co2123.streetfood.model.Photo;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.Repository;

public interface PhotoRepository extends CrudRepository<Photo, Integer> {
}
