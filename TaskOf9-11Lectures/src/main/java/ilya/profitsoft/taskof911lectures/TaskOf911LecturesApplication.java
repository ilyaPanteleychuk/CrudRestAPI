package ilya.profitsoft.taskof911lectures;

import ilya.profitsoft.taskof911lectures.model.Good;
import ilya.profitsoft.taskof911lectures.model.Category;
import ilya.profitsoft.taskof911lectures.repository.CategoryRepository;
import ilya.profitsoft.taskof911lectures.repository.GoodRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import java.util.List;


@SpringBootApplication
public class TaskOf911LecturesApplication implements CommandLineRunner {
    
    @Autowired
    private GoodRepository goodRepository;
    
    @Autowired
    private CategoryRepository categoryRepository;
    
    public static void main(String[] args) {
        SpringApplication.run(TaskOf911LecturesApplication.class, args);
    }
    
    @Override
    public void run(String... args){
        Category hunting = new Category("Hunting");
        categoryRepository.save(hunting);
        Category sport = new Category("Sport");
        categoryRepository.save(sport);
        Category furniture = new Category("Furniture");
        categoryRepository.save(furniture);
        Category fishing = new Category("Fishing");
        categoryRepository.save(fishing);
        List<Good> goods = List.of(
                new Good("Super Rifle", 5,  "Uncle Sam", hunting),
                new Good("Super Dumbbell", 4,  "Big Ronny", sport),
                new Good("Super Frier", 4,  "Sanders", furniture),
                new Good("Super Bench Press", 4,  "Catler", sport),
                new Good("Super Rod", 3,  "Ktulhu", fishing));
        goodRepository.saveAll(goods);
    }
}
