package e_Commerse.Application.model.Entities.DTOs;

import java.time.LocalDateTime;

public class QueryResponseDTO {

    private Long queryId;
    private Long productId;
    private Long customerId;
    private String question;
    private String answer;
    private LocalDateTime askedAt;
    private LocalDateTime answeredAt;

    // ✅ All-args constructor
    public QueryResponseDTO(Long queryId, Long productId, Long customerId, String question,
                            String answer, LocalDateTime askedAt, LocalDateTime answeredAt) {
        this.queryId = queryId;
        this.productId = productId;
        this.customerId = customerId;
        this.question = question;
        this.answer = answer;
        this.askedAt = askedAt;
        this.answeredAt = answeredAt;
    }

    // Getters and setters
    public Long getQueryId() { return queryId; }
    public void setQueryId(Long queryId) { this.queryId = queryId; }
    public Long getProductId() { return productId; }
    public void setProductId(Long productId) { this.productId = productId; }
    public Long getCustomerId() { return customerId; }
    public void setCustomerId(Long customerId) { this.customerId = customerId; }
    public String getQuestion() { return question; }
    public void setQuestion(String question) { this.question = question; }
    public String getAnswer() { return answer; }
    public void setAnswer(String answer) { this.answer = answer; }
    public LocalDateTime getAskedAt() { return askedAt; }
    public void setAskedAt(LocalDateTime askedAt) { this.askedAt = askedAt; }
    public LocalDateTime getAnsweredAt() { return answeredAt; }
    public void setAnsweredAt(LocalDateTime answeredAt) { this.answeredAt = answeredAt; }
}
