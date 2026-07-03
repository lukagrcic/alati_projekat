package rs.fon.laptopprodaja.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import rs.fon.laptopprodaja.entity.Racun;
import java.util.List;

public interface RacunRepository extends JpaRepository<Racun, Long> {
    List<Racun> findByProdavac_IdProdavac(Long idProdavac);
    List<Racun> findByKupac_IdKupac(Long idKupac);
}
