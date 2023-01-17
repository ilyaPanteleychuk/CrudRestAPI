package ilya.profitsoft.taskof911lectures.controller;

import ilya.profitsoft.taskof911lectures.model.Category;
import ilya.profitsoft.taskof911lectures.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@RequestMapping("api/v1/category")
public class CategoryController {
    
    private final CategoryService categoryService;
    
    @Autowired
    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }
    
    @GetMapping()
    public List<Category> findAllCategories(){
        return categoryService.findAllCategories();
    }
}
