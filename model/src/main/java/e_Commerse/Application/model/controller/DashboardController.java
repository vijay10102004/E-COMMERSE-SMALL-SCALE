package e_Commerse.Application.model.controller;

import e_Commerse.Application.model.Entities.DTOs.*;
import e_Commerse.Application.model.Entities.User;
import e_Commerse.Application.model.services.DashboardService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/dashboard")
@RequiredArgsConstructor
public class DashboardController {

    @Autowired
    private DashboardService dashboardService;

    // 1️⃣ Get Owner Summary
    @GetMapping("/summary")
    public DashboardSummaryDTO getSummary(@AuthenticationPrincipal User loggedInUser) {
        checkOwnerAccess(loggedInUser);
        return dashboardService.getOwnerSummary(loggedInUser.getUserId());
    }

    // 2️⃣ Get Recent Orders
    @GetMapping("/recent-orders")
    public List<RecentOrderDTO> getRecentOrders(@AuthenticationPrincipal User loggedInUser) {
        checkOwnerAccess(loggedInUser);
        return dashboardService.getRecentOrders(loggedInUser.getUserId());
    }

    // 3️⃣ Get Pending Queries
    @GetMapping("/pending-queries")
    public List<PendingQueryDTO> getPendingQueries(@AuthenticationPrincipal User loggedInUser) {
        checkOwnerAccess(loggedInUser);
        return dashboardService.getPendingQueries(loggedInUser.getUserId());
    }

    // 4️⃣ Get Product Sales
    @GetMapping("/sales")
    public List<ProductSaleDTO> getProductSales(@AuthenticationPrincipal User loggedInUser) {
        checkOwnerAccess(loggedInUser);
        return dashboardService.getProductSales(loggedInUser.getUserId());
    }

    // 🧠 Private helper to enforce role-based access
    protected void checkOwnerAccess(User user) {
        if (user == null || !"OWNER".equalsIgnoreCase(user.getRole())) {
            throw new AccessDeniedException("Only owners can access the dashboard");
        }
    }
}
