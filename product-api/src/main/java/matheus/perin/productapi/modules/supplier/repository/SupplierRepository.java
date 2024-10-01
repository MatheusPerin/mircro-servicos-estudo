package matheus.perin.productapi.modules.supplier.repository;

import matheus.perin.productapi.modules.supplier.model.Supplier;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SupplierRepository extends JpaRepository<Supplier, Long> {

    List<Supplier> findByNameIgnoreCaseContaining(String name);

}
