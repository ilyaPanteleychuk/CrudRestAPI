package ilya.profitsoft.taskof911lectures.searchmodule;

import ilya.profitsoft.taskof911lectures.model.Category;
import ilya.profitsoft.taskof911lectures.model.Good;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import jakarta.persistence.criteria.Join;
import org.springframework.data.jpa.domain.Specification;
import java.util.ArrayList;
import java.util.List;


public class SearchSpecification implements Specification<Good> {
    
    private final List<SearchCriteria> criteriaList;
    
    public SearchSpecification() {
        this.criteriaList = new ArrayList<>();
    }
    
    public void addCriteria(SearchCriteria searchCriteria){
        criteriaList.add(searchCriteria);
    }
    
    @Override
    public Predicate toPredicate(Root<Good> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
        List<Predicate> predicateList = new ArrayList<>();
        for(SearchCriteria criteria : criteriaList){
            switch (criteria.getSearchOperation()){
                case EQUAL -> predicateList.add(builder.equal(root.get(criteria.getKey()),
                        criteria.getValue().toString()));
                case EQUAL_JOIN -> {
                    Join<Good, Category> goodWithCategory = root.join("category");
                    predicateList.add(builder.equal(goodWithCategory.get("id"),
                            criteria.getValue().toString()));
                }
            }
        }
        return builder.and(predicateList.toArray(new Predicate[0]));
    }
}
