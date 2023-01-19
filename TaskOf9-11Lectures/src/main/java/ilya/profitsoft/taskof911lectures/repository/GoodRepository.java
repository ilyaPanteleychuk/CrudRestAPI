package ilya.profitsoft.taskof911lectures.repository;

import ilya.profitsoft.taskof911lectures.model.Good;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;


public interface GoodRepository extends JpaRepository<Good, Long>,
        JpaSpecificationExecutor<Good> {
    
}