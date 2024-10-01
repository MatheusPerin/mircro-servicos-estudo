package matheus.perin.productapi.modules.produto.repository;

import matheus.perin.productapi.modules.produto.model.Product;
import matheus.perin.productapi.modules.supplier.model.Supplier;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {

    List<Product> findByNameIgnoreCaseContaining(String name);

    List<Product> findByCategoryId(Long categoryId);

    List<Product> findBySupplierId(Long supplierId);

    Boolean existsByCategoryId(Long categoryId);

    Boolean existsBySupplierId(Long supplierId);

}
