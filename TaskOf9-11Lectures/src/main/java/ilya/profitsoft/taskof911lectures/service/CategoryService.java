package ilya.profitsoft.taskof911lectures.service;

import ilya.profitsoft.taskof911lectures.model.Category;
import ilya.profitsoft.taskof911lectures.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;


@Service
public class CategoryService {
    
    private final CategoryRepository categoryRepository;
    
    @Autowired
    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }
    
    public List<Category> findAllCategories(){
        return categoryRepository.findAll();
    }
}
