package ilya.profitsoft.taskof911lectures.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.extern.jackson.Jacksonized;


@Getter
@Builder
@Jacksonized
public class GoodSaveDto {
    
    private String title;
    
    private int rating;

    private int quantity;
    
    private String manufacturer;
    
    private long categoryId;

}
