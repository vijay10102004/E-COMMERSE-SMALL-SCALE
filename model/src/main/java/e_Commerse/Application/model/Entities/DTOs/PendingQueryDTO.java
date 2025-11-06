package e_Commerse.Application.model.Entities.DTOs;



import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class PendingQueryDTO {
    private Long queryId;
    private String productName;
    private String question;

    public Long getQueryId() {
        return queryId;
    }

    public void setQueryId(Long queryId) {
        this.queryId = queryId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getAskedBy() {
        return askedBy;
    }

    public void setAskedBy(String askedBy) {
        this.askedBy = askedBy;
    }

    public LocalDateTime getAskedAt() {
        return askedAt;
    }

    public void setAskedAt(LocalDateTime askedAt) {
        this.askedAt = askedAt;
    }

    public PendingQueryDTO(Long queryId, String productName, String question, String askedBy, LocalDateTime askedAt) {
        this.queryId = queryId;
        this.productName = productName;
        this.question = question;
        this.askedBy = askedBy;
        this.askedAt = askedAt;
    }

    private String askedBy;
    private LocalDateTime askedAt;
}

