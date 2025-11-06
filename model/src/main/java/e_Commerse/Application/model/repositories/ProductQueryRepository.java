package e_Commerse.Application.model.repositories;

import e_Commerse.Application.model.Entities.ProductQuery;
import e_Commerse.Application.model.Entities.Product;
import e_Commerse.Application.model.Entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductQueryRepository extends JpaRepository<ProductQuery, Long> {

    // 🔹 Get all queries asked for a specific product
    List<ProductQuery> findByProduct(Product product);

    // 🔹 Get all queries asked by a specific customer
    List<ProductQuery> findByCustomer(User customer);

    // 🔹 Get all unanswered queries (for owner's dashboard)
    List<ProductQuery> findByAnswerIsNull();

    List<ProductQuery> findByProductOwnerUserIdAndAnswerIsNull(Long ownerId);
}
