package e_Commerse.Application.model.controller;

import e_Commerse.Application.model.Entities.DTOs.QueryResponseDTO;
import e_Commerse.Application.model.Entities.ProductQuery;
import e_Commerse.Application.model.services.QueryService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/queries")
@CrossOrigin(origins = "http://localhost:8080") // Allow React frontend
public class QueryController {

    @Autowired
    private QueryService queryService;

    @PostMapping("/ask")
    public ResponseEntity<QueryResponseDTO> askQuestion(
            @RequestParam Long productId,
            @RequestParam Long customerId,
            @RequestParam String question
    ) {
        ProductQuery query = queryService.askQuestion(productId, customerId, question);
        return ResponseEntity.ok(queryService.toDTO(query));
    }

    @PostMapping("/reply")
    public ResponseEntity<QueryResponseDTO> replyToQuery(
            @RequestParam Long queryId,
            @RequestParam String answer
    ) {
        ProductQuery query = queryService.replyToQuery(queryId, answer);
        return ResponseEntity.ok(queryService.toDTO(query));
    }

    @GetMapping("/product/{productId}")
    public ResponseEntity<List<QueryResponseDTO>> getQueriesForProduct(@PathVariable Long productId) {
        List<ProductQuery> queries = queryService.getQueriesForProduct(productId);
        return ResponseEntity.ok(queryService.toDTOList(queries));
    }

    @GetMapping("/customer/{customerId}")
    public ResponseEntity<List<QueryResponseDTO>> getQueriesByCustomer(@PathVariable Long customerId) {
        List<ProductQuery> queries = queryService.getQueriesByCustomer(customerId);
        return ResponseEntity.ok(queryService.toDTOList(queries));
    }

    @GetMapping("/unanswered")
    public ResponseEntity<List<QueryResponseDTO>> getUnansweredQueries() {
        List<ProductQuery> queries = queryService.getUnansweredQueries();
        return ResponseEntity.ok(queryService.toDTOList(queries));
    }

}
