package ilya.profitsoft.taskof911lectures.searchmodule;

import lombok.Data;


@Data
public class SearchCriteria {
    
    private String key;
    private Object value;
    private SearchOperation searchOperation;
    
    public SearchCriteria(String key, Object value, SearchOperation searchOperation) {
        this.key = key;
        this.value = value;
        this.searchOperation = searchOperation;
    }
}
