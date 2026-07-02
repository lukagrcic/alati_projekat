package rs.fon.laptopprodaja.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import rs.fon.laptopprodaja.entity.Prodavac;
import java.util.List;
import java.util.Optional;

public interface ProdavacRepository extends JpaRepository<Prodavac, Long> {

    Optional<Prodavac> findByKorisnickoIme(String korisnickoIme);
    List<Prodavac> findByImeContainingIgnoreCaseOrPrezimeContainingIgnoreCase(String ime, String prezime);
}
