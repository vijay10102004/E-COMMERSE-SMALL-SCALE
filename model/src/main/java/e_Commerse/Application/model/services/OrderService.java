package e_Commerse.Application.model.services;



import e_Commerse.Application.model.Entities.DTOs.OrderRequest;

import e_Commerse.Application.model.Entities.DTOs.OrderResponse;


import e_Commerse.Application.model.exceptions.ResourceNotFoundException;
import e_Commerse.Application.model.Entities.Order;
import e_Commerse.Application.model.Entities.Product;
import e_Commerse.Application.model.Entities.User;
import e_Commerse.Application.model.repositories.OrderRepository;
import e_Commerse.Application.model.repositories.ProductRepository;
import e_Commerse.Application.model.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service

public class OrderService {

    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private UserRepository userRepository;

    // Place a new order
    public OrderResponse placeOrder(OrderRequest request, Long customerId) {
        User customer = userRepository.findById(customerId)
                .orElseThrow(() -> new ResourceNotFoundException("Customer not found"));

        Product product = productRepository.findById(request.getProductId())
                .orElseThrow(() -> new ResourceNotFoundException("Product not found"));

        if (product.getStock() < request.getQuantity()) {
            throw new IllegalArgumentException("Not enough stock available");
        }

        // Reduce stock
        product.setStock(product.getStock() - request.getQuantity());
        productRepository.save(product);

        Order order = new Order();
        order.setCustomer(customer);
        order.setProduct(product);
        order.setQuantity(request.getQuantity());
        order.setStatus("PENDING");
        order.setOrderDate(LocalDateTime.now());

        Order saved = orderRepository.save(order);

        return mapToResponse(saved);
    }

    // Get orders for a customer
    public List<OrderResponse> getCustomerOrders(Long customerId) {
        User customer = userRepository.findById(customerId)
                .orElseThrow(() -> new ResourceNotFoundException("Customer not found"));

        return orderRepository.findByCustomer(customer).stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    // Get all orders for owner's products
    public List<OrderResponse> getOwnerOrders(Long ownerId) {
        User owner = userRepository.findById(ownerId)
                .orElseThrow(() -> new ResourceNotFoundException("Owner not found"));

        return orderRepository.findByProductOwner(owner).stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    // ✅ Update order status (check owner)
    public OrderResponse updateOrderStatus(Long orderId, String status, Long ownerId) {
        // Step 1: Get order by ID
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found"));

        // Step 2: Get the owner of the product related to this order
        User productOwner = order.getProduct().getOwner();

        // Step 3: Verify that the logged-in user is the same owner
        if (!productOwner.getUserId().equals(ownerId)) {
            throw new SecurityException("You are not authorized to update this order");
        }

        // Step 4: Update order status
        order.setStatus(status);

        // Step 5: Save and return response
        Order updatedOrder = orderRepository.save(order);
        return mapToResponse(updatedOrder);
    }


    private OrderResponse mapToResponse(Order order) {
        return new OrderResponse(
                order.getOrderId(),
                order.getProduct().getName(),
                order.getQuantity(),
                order.getStatus(),
                order.getOrderDate(),
                order.getCustomer().getUsername()
        );
    }
}


