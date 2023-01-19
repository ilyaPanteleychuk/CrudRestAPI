package ilya.profitsoft.taskof911lectures.searchmodule;

import ilya.profitsoft.taskof911lectures.dto.GoodQueryDto;
import ilya.profitsoft.taskof911lectures.model.Good;
import ilya.profitsoft.taskof911lectures.repository.GoodRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import java.util.List;


@Service
public class SearchGoodService {

    private final GoodRepository goodRepository;
    
    @Autowired
    public SearchGoodService(GoodRepository goodRepository) {
        this.goodRepository = goodRepository;
    }
    
    public List<Good> searchGood(GoodQueryDto goodQueryDto) {
        if (goodQueryDto.getFrom() == null){
            goodQueryDto.setFrom(0);
        }
        if (goodQueryDto.getSize() == null){
            goodQueryDto.setSize(10);
        }
        return goodRepository.findAll
                (createSpecification(goodQueryDto), PageRequest.of(goodQueryDto.getFrom(), goodQueryDto.getSize()))
                .getContent();
    }
    
    private Specification<Good> createSpecification(GoodQueryDto goodQueryDto) {
        SearchSpecification searchSpecification = new SearchSpecification();
        if(goodQueryDto.getTitle() != null){
            searchSpecification.addCriteria(new SearchCriteria
                    ("title", goodQueryDto.getTitle(), SearchOperation.EQUAL));
        }
        if (goodQueryDto.getCategoryId() != null){
            searchSpecification.addCriteria(new SearchCriteria
                    ("category", goodQueryDto.getCategoryId(), SearchOperation.EQUAL_JOIN));
        }
        if (goodQueryDto.getManufacturer() != null){
            searchSpecification.addCriteria(new SearchCriteria
                    ("manufacturer", goodQueryDto.getManufacturer(), SearchOperation.EQUAL));
        }
        if (goodQueryDto.getRating() != null){
            searchSpecification.addCriteria(
                    new SearchCriteria("rating", goodQueryDto.getRating(), SearchOperation.EQUAL));
        }
        return searchSpecification;
    }
}
