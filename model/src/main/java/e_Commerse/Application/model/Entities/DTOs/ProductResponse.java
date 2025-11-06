package e_Commerse.Application.model.Entities.DTOs;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data

public class ProductResponse {
    private Long productId;
    private String name;
    private String description;
    private Double price;
    private Integer stock;

    public ProductResponse(Long productId, String name, String description, Double price, Integer stock, String imageUrl, String ownerName) {
        this.productId = productId;
        this.name = name;
        this.description = description;
        this.price = price;
        this.stock = stock;
        this.imageUrl = imageUrl;
        this.ownerName = ownerName;
    }

    private String imageUrl;
    private String ownerName;
}
