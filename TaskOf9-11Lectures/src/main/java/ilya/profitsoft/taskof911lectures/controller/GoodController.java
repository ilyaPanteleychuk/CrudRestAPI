package ilya.profitsoft.taskof911lectures.controller;

import ilya.profitsoft.taskof911lectures.dto.GoodQueryDto;
import ilya.profitsoft.taskof911lectures.dto.GoodSaveDto;
import ilya.profitsoft.taskof911lectures.dto.RestResponse;
import ilya.profitsoft.taskof911lectures.model.Good;
import ilya.profitsoft.taskof911lectures.searchmodule.SearchGoodService;
import ilya.profitsoft.taskof911lectures.service.GoodService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.DeleteMapping;
import java.util.List;


@RestController
@RequestMapping("/api/v1/good")
public class GoodController {
    
    private final GoodService goodService;
    private final SearchGoodService searchGoodService;
    
    @Autowired
    public GoodController(GoodService goodService, SearchGoodService searchGoodService) {
        this.goodService = goodService;
        this.searchGoodService = searchGoodService;
    }
    
    @GetMapping
    public List<Good> findAllGoods(){
        return goodService.findAllGoods();
    }
    
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public RestResponse createGood(@Valid @RequestBody GoodSaveDto saveDto){
        long id = goodService.createGood(saveDto);
        return new RestResponse(String.valueOf(id));
    }
    
    @PutMapping("/{id}")
    public RestResponse updateGood(@Valid @PathVariable long id, @RequestBody GoodSaveDto goodSaveDto){
        goodService.updateGood(id, goodSaveDto);
        return new RestResponse("OK");
    }
    
    @DeleteMapping("/{id}")
    public RestResponse deleteGood(@PathVariable long id){
        goodService.deleteGood(id);
        return new RestResponse("OK");
    }
    
    @PostMapping("_search")
    public List<Good> searchGoods(@RequestBody GoodQueryDto goodQueryDto){
        return searchGoodService.searchGood(goodQueryDto);
    }
}
