package ilya.profitsoft.taskof911lectures.repository;

import ilya.profitsoft.taskof911lectures.model.Good;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;


public interface GoodRepository extends JpaRepository<Good, Long>,
        JpaSpecificationExecutor<Good> {

    Page<Good> findAllByRating(int rating, Pageable pageable);

    Page<Good> findAllByCategory_Id(long id, Pageable pageable);
    
    Page<Good> findAllByCategory_IdAndManufacturer(long categoryId,
                                                   String manufacturer,
                                                   Pageable pageable);
}