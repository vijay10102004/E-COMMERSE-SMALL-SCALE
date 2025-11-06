package e_Commerse.Application.model.Entities.DTOs;



import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class OrderResponse {
    private Long orderId;
    private String productName;
    private Integer quantity;
    private String status;
    private String orderDate;
    private String customerName;

    public OrderResponse(Long orderId, String productName, Integer quantity, String status, String orderDate, String customerName) {
        this.orderId = orderId;
        this.productName = productName;
        this.quantity = quantity;
        this.status = status;
        this.orderDate = orderDate;
        this.customerName = customerName;
    }
}

