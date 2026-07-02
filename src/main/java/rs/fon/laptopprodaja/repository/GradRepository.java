package rs.fon.laptopprodaja.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import rs.fon.laptopprodaja.entity.Grad;

public interface GradRepository extends JpaRepository<Grad, Long> {
}
