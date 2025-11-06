package e_Commerse.Application.model.Entities.DTOs;



import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class RecentOrderDTO {
    public RecentOrderDTO(Long orderId, String productName, String customerName, Integer quantity, String status, String orderDate) {
        this.orderId = orderId;
        this.productName = productName;
        this.customerName = customerName;
        this.quantity = quantity;
        this.status = status;
        this.orderDate = orderDate;
    }

    private Long orderId;
    private String productName;
    private String customerName;
    private Integer quantity;
    private String status;
    private String orderDate;
}

