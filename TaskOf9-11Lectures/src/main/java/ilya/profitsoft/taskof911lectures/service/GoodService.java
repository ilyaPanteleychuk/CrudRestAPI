package ilya.profitsoft.taskof911lectures.service;

import ilya.profitsoft.taskof911lectures.dto.GoodSaveDto;
import ilya.profitsoft.taskof911lectures.exceptions.NotFoundException;
import ilya.profitsoft.taskof911lectures.model.Category;
import ilya.profitsoft.taskof911lectures.model.Good;
import ilya.profitsoft.taskof911lectures.repository.CategoryRepository;
import ilya.profitsoft.taskof911lectures.repository.GoodRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;


@Service
public class GoodService {
    
    private final GoodRepository goodRepository;
    private final CategoryRepository categoryRepository;
    
    @Autowired
    public GoodService(GoodRepository goodRepository, CategoryRepository categoryRepository) {
        this.goodRepository = goodRepository;
        this.categoryRepository = categoryRepository;
    }
    
    public List<Good> findAllGoods() {
        return goodRepository.findAll();
    }
    
    public long createGood(GoodSaveDto dto) {
        Good newGood = new Good();
        updateGoodFormDto(newGood, dto);
        return goodRepository.save(newGood).getId();
    }
    
    public void updateGood(Long id, GoodSaveDto goodSaveDto) {
        Good existingGood = getOrThrow(id);
        updateGoodFormDto(existingGood, goodSaveDto);
        goodRepository.save(existingGood);
    }
    
    public void deleteGood(long id) {
        goodRepository.deleteById(id);
    }
    
    private void updateGoodFormDto(Good newGood, GoodSaveDto dto) {
        newGood.setTitle(dto.getTitle());
        newGood.setRating(dto.getRating());
        newGood.setManufacturer(dto.getManufacturer());
        newGood.setCategory(resolveCategory(dto.getCategoryId()));
    }
    
    private Category resolveCategory(Long categoryId) {
        if (categoryId == null) {
            return null;
        }
        return categoryRepository.findById(categoryId).orElseThrow(() ->
                new NotFoundException("Category with id " + categoryId + " does`t exist"));
        
    }
    
    private Good getOrThrow(long id) {
        return goodRepository.findById(id)
                .orElseThrow(() ->
                        new NotFoundException("Good with id " + id + " does`t exist"));
    }
}
