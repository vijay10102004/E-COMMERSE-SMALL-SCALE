package e_Commerse.Application.model.services;


import e_Commerse.Application.model.Entities.DTOs.ProductRequest;

import e_Commerse.Application.model.Entities.DTOs.ProductResponse;

import e_Commerse.Application.model.exceptions.ResourceNotFoundException;

import e_Commerse.Application.model.Entities.Product;

import e_Commerse.Application.model.Entities.User;

import e_Commerse.Application.model.repositories.ProductRepository;

import e_Commerse.Application.model.repositories.UserRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductService {

    @Autowired
    private  ProductRepository productRepository;
    @Autowired
    private  UserRepository userRepository;

    public ProductResponse addProduct(ProductRequest request, Long ownerId) {
        User owner = userRepository.findById(ownerId)
                .orElseThrow(() -> new ResourceNotFoundException("Owner not found"));

        Product product = new Product();
        product.setName(request.getName());
        product.setDescription(request.getDescription());
        product.setPrice(request.getPrice());
        product.setStock(request.getStock());
        product.setImageUrl(request.getImageUrl());
        product.setOwner(owner);

        Product saved = productRepository.save(product);

        return mapToResponse(saved);
    }

    public List<ProductResponse> getAllProducts() {
        return productRepository.findAll().stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    public ProductResponse getProductById(Long productId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found"));
        return mapToResponse(product);
    }

    public ProductResponse updateProduct(Long productId, ProductRequest request) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found"));

        product.setName(request.getName());
        product.setDescription(request.getDescription());
        product.setPrice(request.getPrice());
        product.setStock(request.getStock());
        product.setImageUrl(request.getImageUrl());

        return mapToResponse(productRepository.save(product));
    }

    public String deleteProduct(Long productId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found"));
        productRepository.delete(product);
        return "Product deleted successfully";
    }

    private ProductResponse mapToResponse(Product product) {
        return new ProductResponse(
                product.getProductId(),
                product.getName(),
                product.getDescription(),
                product.getPrice(),
                product.getStock(),
                product.getImageUrl(),
                product.getOwner().getUsername()
        );
    }
}

