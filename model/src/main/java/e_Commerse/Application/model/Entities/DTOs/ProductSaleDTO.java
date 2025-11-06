package e_Commerse.Application.model.Entities.DTOs;



import lombok.AllArgsConstructor;
import lombok.Data;

@Data
public class ProductSaleDTO {
    private Long productId;
    private String productName;

    public ProductSaleDTO(Long productId, String productName, Integer unitsSold, Double revenue) {
        this.productId = productId;
        this.productName = productName;
        this.unitsSold = unitsSold;
        this.revenue = revenue;
    }

    private Integer unitsSold;
    private Double revenue;
}

