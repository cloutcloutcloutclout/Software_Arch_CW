package co2123.streetfood.repository;

import co2123.streetfood.model.Tag;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.Repository;

public interface TagRepository extends CrudRepository<Tag, Integer> {
}
