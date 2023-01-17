package ilya.profitsoft.taskof911lectures.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import ilya.profitsoft.taskof911lectures.dto.GoodQueryDto;
import ilya.profitsoft.taskof911lectures.dto.GoodSaveDto;
import ilya.profitsoft.taskof911lectures.dto.RestResponse;
import ilya.profitsoft.taskof911lectures.model.Good;
import ilya.profitsoft.taskof911lectures.repository.GoodRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest()
@AutoConfigureMockMvc
@Sql(value = {"/create-good-before.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(value = {"/create-good-after.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
public class GoodControllerIntTest {
    
    @Autowired
    private ObjectMapper objectMapper;
    
    @Autowired
    private GoodRepository goodRepository;
    
    @Autowired
    private MockMvc mockMvc;
    
    @Test
    public void createGoodPost_shouldCreateNewGood_whenGoodDoesNotExist() throws Exception{
        GoodSaveDto goodSaveDto = GoodSaveDto.builder()
                .title("drums")
                .rating(3)
                .manufacturer("TAMA")
                .categoryId(100)
                .build();
        MvcResult mvcResult = mockMvc.perform(post("/api/v1/good")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(goodSaveDto)))
                .andExpect(status().isCreated())
                .andReturn();
        RestResponse response = parseResponse(mvcResult, RestResponse.class);
        long goodId = Integer.parseInt(response.getResult());
        assertThat(goodId).isGreaterThanOrEqualTo(1);
        Good good = goodRepository.findById(goodId).orElse(null);
        assertThat(good).isNotNull();
        assertThat(good.getTitle()).isEqualTo(goodSaveDto.getTitle());
        assertThat(good.getRating()).isEqualTo(goodSaveDto.getRating());
        assertThat(good.getManufacturer()).isEqualTo(goodSaveDto.getManufacturer());
        assertThat(good.getCategory().getId()).isEqualTo(goodSaveDto.getCategoryId());
    }
    
    @Test
    public void updateGoodPut_shouldUpdateValue_whenRowExists() throws Exception{
        Good existingGood = goodRepository.findById(1L).orElse(null);
        assertThat(existingGood).isNotNull();
        GoodSaveDto goodSaveDto = GoodSaveDto.builder()
                .title("shortgun")
                .rating(4)
                .manufacturer("Ukraine") //new manufacturer
                .categoryId(1)
                .build();
        MvcResult mvcResult = mockMvc.perform(put("/api/v1/good/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(goodSaveDto)))
                .andExpect(status().is2xxSuccessful()).andReturn();
        RestResponse response = parseResponse(mvcResult, RestResponse.class);
        String status = response.getResult();
        assertThat(status).isEqualTo("OK");
        Good updatedGood = goodRepository.findById(1L).orElse(null);
        assertThat(updatedGood).isNotNull();
        assertThat(updatedGood.getTitle()).isEqualTo(existingGood.getTitle());
        assertThat(updatedGood.getRating()).isEqualTo(existingGood.getRating());
        assertThat(updatedGood.getCategory().getId()).isEqualTo(existingGood.getCategory().getId());
        assertThat(updatedGood.getManufacturer()).isNotEqualTo(existingGood.getManufacturer());
        assertThat(updatedGood.getManufacturer()).isEqualTo(goodSaveDto.getManufacturer());
    }
    
    @Test
    public void findAllGet_shouldReturnListWithRightSize_whenRowsExist()
            throws Exception{
        MvcResult mvcResult = mockMvc.perform(get("/api/v1/good")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is2xxSuccessful())
                .andReturn();
        ArrayList<Good> goods = parseResponse(mvcResult, ArrayList.class);
        assertThat(goods.size()).isEqualTo(5);
    }
    
    @Test
    public void findAllByRating_shouldReturnObjectsWithRatingValue_whenSuchExist()
            throws Exception{
        GoodQueryDto goodQueryDto = GoodQueryDto.builder()
                .rating(4)
                .from(0)
                .size(3)
                .build();
        MvcResult mvcResult = mockMvc.perform
                        (post("/api/v1/good/_searchByRating")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(goodQueryDto)))
                .andExpect(status().is2xxSuccessful())
                .andReturn();
        ArrayList<Good> goods = parseResponse(mvcResult, ArrayList.class);
        assertThat(goods.size()).isEqualTo(3);
    }
    
    @Test
    public void findAllByCategory_shouldReturnObjectsWithCategory_whenSuchExist()
            throws Exception{
        GoodQueryDto goodQueryDto = GoodQueryDto.builder()
                .categoryId(100L)
                .from(0)
                .size(3)
                .build();
        MvcResult mvcResult = mockMvc.perform
                        (post("/api/v1/good/_searchByCategory")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(goodQueryDto)))
                .andExpect(status().is2xxSuccessful())
                .andReturn();
        ArrayList<Good> goods = parseResponse(mvcResult, ArrayList.class);
        assertThat(goods.size()).isEqualTo(1);
    }
    
    @Test
    public void findAllByCategoryAndManufacturer_shouldReturnRightObjects_whenSuchExist()
            throws Exception{
        GoodQueryDto goodQueryDto = GoodQueryDto.builder()
                .categoryId(1L)
                .manufacturer("USA")
                .from(0)
                .size(4)
                .build();
        MvcResult mvcResult = mockMvc.perform
                        (post("/api/v1/good/_searchByCategoryAndManufacturer")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(goodQueryDto)))
                .andExpect(status().is2xxSuccessful())
                .andReturn();
        ArrayList<Good> goods = parseResponse(mvcResult, ArrayList.class);
        assertThat(goods.size()).isEqualTo(4);
    }
    
    @Test
    public void deleteGood_shouldDeleteGood_whenSuchExist()
            throws Exception{
        Good existingGood = goodRepository.findById(1L).orElse(null);
        assertThat(existingGood).isNotNull();
        MvcResult mvcResult = mockMvc.perform(delete("/api/v1/good/1")
                        .contentType(MediaType.APPLICATION_JSON))
                        .andExpect(status().is2xxSuccessful())
                        .andReturn();
        RestResponse status = parseResponse(mvcResult, RestResponse.class);
        assertThat(status.getResult()).isEqualTo("OK");
        Good deletedGood = goodRepository.findById(1L).orElse(null);
        assertThat(deletedGood).isNull();
    }
    
    @Test
    public void createNewGood_shouldThrowClientError_whenFieldTitleIsBlank()
            throws Exception{
        GoodSaveDto goodSaveDto = GoodSaveDto.builder()
                .rating(4)
                .manufacturer("Ukraine")
                .categoryId(1)
                .build();
        mockMvc.perform(post("/api/v1/good")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(goodSaveDto)))
                        .andExpect(status().is4xxClientError()).andReturn();
    }
    
    @Test
    public void createNewGood_shouldThrowClientError_whenManufacturerIsBlank()
            throws Exception{
        GoodSaveDto goodSaveDto = GoodSaveDto.builder()
                .title("shortgun")
                .rating(4)
                .categoryId(1)
                .build();
        mockMvc.perform(post("/api/v1/good")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(goodSaveDto)))
                        .andExpect(status().is4xxClientError())
                        .andReturn();
    }
    
    private <T> T parseResponse (MvcResult mvcResult, Class<T> c){
        try {
            return objectMapper.readValue(mvcResult.getResponse()
                    .getContentAsString(), c);
        } catch (JsonProcessingException | UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }
}
