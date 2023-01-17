package ilya.profitsoft.taskof911lectures.repository;

import ilya.profitsoft.taskof911lectures.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;


public interface CategoryRepository extends JpaRepository<Category, Long> {

}