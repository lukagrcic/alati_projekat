package rs.fon.laptopprodaja.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import rs.fon.laptopprodaja.entity.Laptop;
import java.math.BigDecimal;
import java.util.List;

public interface LaptopRepository extends JpaRepository<Laptop, Long> {

    List<Laptop> findByNazivContainingIgnoreCase(String naziv);
    List<Laptop> findByCenaBetween(BigDecimal min, BigDecimal max);

}
