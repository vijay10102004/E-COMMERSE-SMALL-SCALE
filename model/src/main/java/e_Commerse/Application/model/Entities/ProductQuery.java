package e_Commerse.Application.model.Entities;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "product_queries")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductQuery {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long queryId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id", nullable = false)
    private User customer;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @Column(nullable = false, length = 500)
    private String question;

    @Column(length = 500)
    private String answer;  // Owner's reply (nullable until replied)

    @Column(nullable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    private LocalDateTime answeredAt;

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public Long getQueryId() {
        return queryId;
    }

    public void setQueryId(Long queryId) {
        this.queryId = queryId;
    }

    public User getCustomer() {
        return customer;
    }

    public void setCustomer(User customer) {
        this.customer = customer;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getAnsweredAt() {
        return answeredAt;
    }

    public void setAnsweredAt(LocalDateTime answeredAt) {
        this.answeredAt = answeredAt;
    }

    public ProductQuery(User customer, Product product, String question) {
        this.customer = customer;
        this.product = product;
        this.question = question;
    }
}
