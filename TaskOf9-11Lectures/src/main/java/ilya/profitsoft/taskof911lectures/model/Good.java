package ilya.profitsoft.taskof911lectures.model;

import jakarta.persistence.*;
import lombok.*;


@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "good")
public class Good {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;
    
    @Column(name = "title", nullable = false, unique = true)
    private String title;
    
    @Column(name = "rating", nullable = false)
    private int rating;
    
    @Column(name = "quantity", nullable = false)
    private int quantity;
    
    @Column(name = "manufacturer")
    private String manufacturer;
    
    @ManyToOne()
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;
    
    public Good(String title, int rating, int quantity, String manufacturer, Category category) {
        this.title = title;
        this.rating = rating;
        this.quantity = quantity;
        this.manufacturer = manufacturer;
        this.category = category;
    }
}