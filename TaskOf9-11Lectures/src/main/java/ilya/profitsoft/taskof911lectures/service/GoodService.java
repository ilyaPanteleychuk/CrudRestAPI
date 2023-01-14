package ilya.profitsoft.taskof911lectures.service;

import ilya.profitsoft.taskof911lectures.dto.GoodQueryDto;
import ilya.profitsoft.taskof911lectures.dto.GoodSaveDto;
import ilya.profitsoft.taskof911lectures.exceptions.NotFoundException;
import ilya.profitsoft.taskof911lectures.model.Category;
import ilya.profitsoft.taskof911lectures.model.Good;
import ilya.profitsoft.taskof911lectures.repository.CategoryRepository;
import ilya.profitsoft.taskof911lectures.repository.GoodRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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
    
    public List<Good> findAllGoodsByRating(GoodQueryDto goodQueryDto) {
        return goodRepository
                .findAllByRating(goodQueryDto.getRating(),
                        setPageRequest(goodQueryDto.getFrom(),
                                goodQueryDto.getSize())).getContent();
    }
    
    public List<Good> findAllGoodsByCategory(GoodQueryDto goodQueryDto) {
        return goodRepository
                .findAllByCategory_Id(goodQueryDto.getCategoryId(),
                        setPageRequest(goodQueryDto.getFrom(),
                                goodQueryDto.getSize())).getContent();
    }
    
    public List<Good> findAllGoodsByCategoryAndManufacturer(GoodQueryDto goodQueryDto) {
        return goodRepository
                .findAllByCategory_IdAndManufacturer(goodQueryDto.getCategoryId(),
                        goodQueryDto.getManufacturer(),
                        setPageRequest(goodQueryDto.getFrom(), goodQueryDto.getSize()))
                .getContent();
    }
    
    public void createGood(GoodSaveDto dto) {
        Good newGood = new Good();
        updateGoodFormDto(newGood, dto);
        goodRepository.save(newGood);
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
        newGood.setQuantity(dto.getQuantity());
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
    
    private Pageable setPageRequest(Integer from, Integer size) {
        if (from == null || size == null) {
            return PageRequest.of(0, 10);
        }
        return PageRequest.of(from, size);
    }
}
