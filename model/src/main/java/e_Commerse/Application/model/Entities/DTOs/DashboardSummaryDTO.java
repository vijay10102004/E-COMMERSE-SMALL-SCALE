package e_Commerse.Application.model.Entities.DTOs;



import lombok.AllArgsConstructor;
import lombok.Data;

@Data
public class DashboardSummaryDTO {
    private Long ownerId;
    private Integer totalProducts;

    public DashboardSummaryDTO(Long ownerId, Integer totalProducts, Integer totalOrders, Integer pendingOrders, Integer shippedOrders, Integer deliveredOrders, Double totalRevenue) {
        this.ownerId = ownerId;
        this.totalProducts = totalProducts;
        this.totalOrders = totalOrders;
        this.pendingOrders = pendingOrders;
        this.shippedOrders = shippedOrders;
        this.deliveredOrders = deliveredOrders;
        this.totalRevenue = totalRevenue;
    }

    private Integer totalOrders;
    private Integer pendingOrders;
    private Integer shippedOrders;
    private Integer deliveredOrders;
    private Double totalRevenue;
}

