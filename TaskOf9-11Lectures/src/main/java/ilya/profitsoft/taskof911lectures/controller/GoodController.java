package ilya.profitsoft.taskof911lectures.controller;

import ilya.profitsoft.taskof911lectures.dto.GoodQueryDto;
import ilya.profitsoft.taskof911lectures.dto.GoodSaveDto;
import ilya.profitsoft.taskof911lectures.model.Good;
import ilya.profitsoft.taskof911lectures.service.GoodService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/v1/goods")
public class GoodController {
    
    private final GoodService goodService;
    
    @Autowired
    public GoodController(GoodService goodService) {
        this.goodService = goodService;
    }
    
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void createGood(@RequestBody GoodSaveDto saveDto){
        goodService.createGood(saveDto);
    }
    
    @PutMapping("/{id}")
    public void updateGood(@PathVariable long id, @RequestBody GoodSaveDto goodSaveDto){
        goodService.updateGood(id, goodSaveDto);
    }
    
    @DeleteMapping("/{id}")
    public void deleteGood(@PathVariable long id){
        goodService.deleteGood(id);
    }
    
    @GetMapping
    public List<Good> findAllGoods(){
        return goodService.findAllGoods();
    }
    
    @PostMapping("/_searchByRating")
    public List<Good> findAllGoodsByRating(@RequestBody GoodQueryDto goodQueryDto){
        return goodService.findAllGoodsByRating(goodQueryDto);
    }
    
    @PostMapping("/_searchByCategory")
    public List<Good> findAllGoodsByCategory(@RequestBody GoodQueryDto goodQueryDto){
        return goodService.findAllGoodsByCategory(goodQueryDto);
    }
    
    @PostMapping("/_searchByCategoryAndManufacturer")
    public List<Good> findAllGoodsByCategoryAndManufacturer(@RequestBody GoodQueryDto goodQueryDto){
        return goodService.findAllGoodsByCategoryAndManufacturer(goodQueryDto);
    }
}
