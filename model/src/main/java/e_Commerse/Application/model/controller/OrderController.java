package e_Commerse.Application.model.controller;


import e_Commerse.Application.model.Entities.DTOs.OrderRequest;
import e_Commerse.Application.model.Entities.DTOs.OrderResponse;
import e_Commerse.Application.model.Entities.User;
import e_Commerse.Application.model.services.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    // Customer places an order
    @PostMapping
    public OrderResponse placeOrder(@RequestBody OrderRequest request,
                                    @AuthenticationPrincipal User customer) {
        return orderService.placeOrder(request, customer.getUserId());
    }

    // Customer views own orders
    @GetMapping("/my-orders")
    public List<OrderResponse> getCustomerOrders(@AuthenticationPrincipal User customer) {
        return orderService.getCustomerOrders(customer.getUserId());
    }

    // Owner views orders for their products
    @GetMapping("/owner-orders")
    public List<OrderResponse> getOwnerOrders(@AuthenticationPrincipal User owner) {
        return orderService.getOwnerOrders(owner.getUserId());
    }

    // ✅ Owner updates order status (only if order belongs to their product)
    @PutMapping("/{orderId}/status")
    public OrderResponse updateOrderStatus(@PathVariable Long orderId,
                                           @RequestParam String status,
                                           @AuthenticationPrincipal User owner) {
        return orderService.updateOrderStatus(orderId, status, owner.getUserId());
    }
}


