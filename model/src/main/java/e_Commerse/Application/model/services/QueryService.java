package e_Commerse.Application.model.services;

import e_Commerse.Application.model.Entities.DTOs.QueryResponseDTO;
import e_Commerse.Application.model.Entities.Product;
import e_Commerse.Application.model.Entities.ProductQuery;
import e_Commerse.Application.model.Entities.User;
import e_Commerse.Application.model.repositories.ProductQueryRepository;
import e_Commerse.Application.model.repositories.ProductRepository;
import e_Commerse.Application.model.repositories.UserRepository;
import e_Commerse.Application.model.exceptions.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service

public class QueryService {

    @Autowired
    private ProductQueryRepository productQueryRepository;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private UserRepository userRepository;

    // 🔹 Customer asks a question about a product
    public ProductQuery askQuestion(Long productId, Long customerId, String question) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with ID: " + productId));

        User customer = userRepository.findById(customerId)
                .orElseThrow(() -> new ResourceNotFoundException("Customer not found with ID: " + customerId));



        ProductQuery query = new ProductQuery(customer,product,question);

        return productQueryRepository.save(query);
    }

    // 🔹 Owner replies to a customer’s query
    public ProductQuery replyToQuery(Long queryId, String answer) {
        ProductQuery query = productQueryRepository.findById(queryId)
                .orElseThrow(() -> new ResourceNotFoundException("Query not found with ID: " + queryId));

        query.setAnswer(answer);
        query.setAnsweredAt(LocalDateTime.now());
        return productQueryRepository.save(query);
    }

    // 🔹 Get all queries for a specific product
    public List<ProductQuery> getQueriesForProduct(Long productId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with ID: " + productId));

        return productQueryRepository.findByProduct(product);
    }

    // 🔹 Get all queries asked by a specific customer
    public List<ProductQuery> getQueriesByCustomer(Long customerId) {
        User customer = userRepository.findById(customerId)
                .orElseThrow(() -> new ResourceNotFoundException("Customer not found with ID: " + customerId));

        return productQueryRepository.findByCustomer(customer);
    }

    // 🔹 Get all unanswered queries (useful for owner's dashboard)
    public List<ProductQuery> getUnansweredQueries() {
        return productQueryRepository.findByAnswerIsNull();
    }

    public QueryResponseDTO toDTO(ProductQuery query) {
        return new QueryResponseDTO(
                query.getQueryId(),
                query.getProduct().getProductId(),
                query.getCustomer().getUserId(),
                query.getQuestion(),
                query.getAnswer(),
                query.getCreatedAt(),
                query.getAnsweredAt()
        );
    }


    public List<QueryResponseDTO> toDTOList(List<ProductQuery> queries) {
        return queries.stream().map(this::toDTO).collect(Collectors.toList());
    }


}
