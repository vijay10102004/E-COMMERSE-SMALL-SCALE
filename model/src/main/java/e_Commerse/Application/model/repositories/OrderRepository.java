package e_Commerse.Application.model.repositories;

import e_Commerse.Application.model.Entities.Order;
import e_Commerse.Application.model.Entities.Product;
import e_Commerse.Application.model.Entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findByCustomer(User customer);
    List<Order> findByProductOwner(User owner);
    List<Order> findAllByProductOwnerUserId(Long ownerId);
    List<Order> findTop10ByProductOwnerUserIdOrderByOrderDateDesc(Long ownerId);
    List<Order> findByProductAndStatus(Product product, String status);
}
