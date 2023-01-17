package ilya.profitsoft.taskof911lectures.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.extern.jackson.Jacksonized;


@Getter
@Builder
@Jacksonized
public class GoodSaveDto {
    
    @NotBlank(message = "title is required")
    private String title;
    
    private int rating;
    
    @NotBlank(message = "manufacturer is required")
    private String manufacturer;

    @NotNull(message = "categoryId is required")
    private long categoryId;

}
