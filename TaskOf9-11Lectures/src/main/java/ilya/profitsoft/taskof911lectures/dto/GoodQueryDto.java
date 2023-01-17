package ilya.profitsoft.taskof911lectures.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.extern.jackson.Jacksonized;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.bind.DefaultValue;


@Getter
@Builder
@Jacksonized
public class GoodQueryDto {
    
    private String title;
    
    private Long categoryId;
    
    private int quantity;
    
    private int rating;
    
    private String manufacturer;
    
    private int from;
    
    private int size;
}
