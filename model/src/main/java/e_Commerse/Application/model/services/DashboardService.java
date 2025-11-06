package e_Commerse.Application.model.services;


import e_Commerse.Application.model.Entities.*;
import e_Commerse.Application.model.Entities.DTOs.*;
import e_Commerse.Application.model.repositories.OrderRepository;
import e_Commerse.Application.model.repositories.ProductQueryRepository;
import e_Commerse.Application.model.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class DashboardService {
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private ProductQueryRepository queryRepository;

    // 1. Owner Summary
    public DashboardSummaryDTO getOwnerSummary(Long ownerId) {
        List<Product> products = productRepository.findByOwnerUserId(ownerId);
        int totalProducts = products.size();

        List<Order> orders = orderRepository.findAllByProductOwnerUserId(ownerId);
        int totalOrders = orders.size();
        int pendingOrders = (int) orders.stream().filter(o -> o.getStatus().equals("PENDING")).count();
        int shippedOrders = (int) orders.stream().filter(o -> o.getStatus().equals("SHIPPED")).count();
        int deliveredOrders = (int) orders.stream().filter(o -> o.getStatus().equals("DELIVERED")).count();
        double totalRevenue = orders.stream()
                .filter(o -> o.getStatus().equals("DELIVERED"))
                .mapToDouble(o -> o.getQuantity() * o.getProduct().getPrice())
                .sum();

        return new DashboardSummaryDTO(ownerId, totalProducts, totalOrders, pendingOrders, shippedOrders, deliveredOrders, totalRevenue);
    }

    // 2. Recent Orders
    public List<RecentOrderDTO> getRecentOrders(Long ownerId) {
        return orderRepository.findTop10ByProductOwnerUserIdOrderByOrderDateDesc(ownerId)
                .stream()
                .map(o -> new RecentOrderDTO(
                        o.getOrderId(),
                        o.getProduct().getName(),
                        o.getCustomer().getUsername(),
                        o.getQuantity(),
                        o.getStatus(),
                        o.getOrderDate()
                ))
                .collect(Collectors.toList());
    }

    // 3. Pending Queries
    public List<PendingQueryDTO> getPendingQueries(Long ownerId) {
        return queryRepository.findByProductOwnerUserIdAndAnswerIsNull(ownerId)
                .stream()
                .map(q -> new PendingQueryDTO(
                        q.getQueryId(),
                        q.getProduct().getName(),
                        q.getQuestion(),
                        q.getCustomer().getUsername(),
                        q.getAnsweredAt()
                ))
                .collect(Collectors.toList());
    }

    // 4. Product Sales
    public List<ProductSaleDTO> getProductSales(Long ownerId) {
        List<Product> products = productRepository.findByOwnerUserId(ownerId);
        return products.stream()
                .map(p -> {
                    List<Order> deliveredOrders = orderRepository.findByProductAndStatus(p, "DELIVERED");
                    int unitsSold = deliveredOrders.stream().mapToInt(Order::getQuantity).sum();
                    double revenue = deliveredOrders.stream().mapToDouble(o -> o.getQuantity() * o.getProduct().getPrice()).sum();
                    return new ProductSaleDTO(p.getProductId(), p.getName(), unitsSold, revenue);
                })
                .collect(Collectors.toList());
    }
}

