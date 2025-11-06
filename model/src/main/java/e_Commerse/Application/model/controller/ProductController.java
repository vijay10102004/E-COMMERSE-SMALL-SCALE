package e_Commerse.Application.model.controller;



import e_Commerse.Application.model.Entities.DTOs.ProductRequest;

import e_Commerse.Application.model.Entities.DTOs.ProductResponse;
import e_Commerse.Application.model.Entities.Product;
import e_Commerse.Application.model.Entities.User;

import e_Commerse.Application.model.repositories.ProductRepository;
import e_Commerse.Application.model.services.ProductService;

import io.jsonwebtoken.io.IOException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.ssl.SslProperties;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
public class ProductController {

    @Autowired
    private ProductService productService;

    @Autowired
    private ProductRepository productRepository;

    @PostMapping
    public ProductResponse addProduct(@RequestBody ProductRequest request,
                                      @AuthenticationPrincipal User owner) {
        return productService.addProduct(request, owner.getUserId());
    }

    @GetMapping
    public List<ProductResponse> getAllProducts() {
        return productService.getAllProducts();
    }

    @GetMapping("/{id}")
    public ProductResponse getProductById(@PathVariable Long id) {
        return productService.getProductById(id);
    }

    @PutMapping("/{id}")
    public ProductResponse updateProduct(@PathVariable Long id,
                                         @RequestBody ProductRequest request,
                                         @AuthenticationPrincipal User owner) {
        // Optional: check if owner matches
        return productService.updateProduct(id, request);
    }

    @DeleteMapping("/{id}")
    public String deleteProduct(@PathVariable Long id,
                                @AuthenticationPrincipal User owner) {
        // Optional: check if owner matches
        return productService.deleteProduct(id);
    }

    @Value("${file.upload-dir}")
    private String uploadDir;

    // Upload Product Image
    @PostMapping("/{productId}/upload-image")
    public ResponseEntity<String> uploadImage(
            @PathVariable Long productId,
            @RequestParam("image") MultipartFile file,@AuthenticationPrincipal User owner) throws IOException, java.io.IOException {

        checkOwnerAccess(owner);
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        // Create folder if not exists
        File folder = new File(uploadDir);
        if (!folder.exists()) folder.mkdirs();

        // Save file locally
        String filename = productId + "_" + file.getOriginalFilename();
        Path filepath = Paths.get(uploadDir, filename);
        Files.write(filepath, file.getBytes());

        // Update product image path
        product.setImageUrl(filename);
        productRepository.save(product);

        return ResponseEntity.ok("Image uploaded successfully: " + filename);
    }

    @GetMapping("/images/{filename:.+}")
    public ResponseEntity<Resource> getImage(@PathVariable String filename) {
        try {
            Path filePath = Paths.get(uploadDir).resolve(filename).normalize();
            System.out.println("Fetching image from path: " + filePath.toAbsolutePath());

            if (!Files.exists(filePath)) {
                System.out.println("File not found: " + filePath);
                return ResponseEntity.notFound().build();
            }

            Resource resource = new UrlResource(filePath.toUri());
            String contentType = Files.probeContentType(filePath);

            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_TYPE, contentType != null ? contentType : "application/octet-stream")
                    .body(resource);

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().build();
        }
    }



    // 🧠 Private helper to enforce role-based access
    private void checkOwnerAccess(User user) {
        if (user == null || !"OWNER".equalsIgnoreCase(user.getRole())) {
            throw new AccessDeniedException("Only owners can access the dashboard");
        }
    }
}

