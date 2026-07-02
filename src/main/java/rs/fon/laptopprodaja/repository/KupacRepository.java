package rs.fon.laptopprodaja.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import rs.fon.laptopprodaja.entity.Kupac;
import java.util.List;

public interface KupacRepository extends JpaRepository<Kupac, Long> {
    List<Kupac> findByGrad_IdGrad(Long idGrad);

    @Query(value = "SELECT * FROM kupac WHERE UPPER(tip_kupca) = UPPER(:tip)", nativeQuery = true)
    List<Kupac> findByTipKupca(@Param("tip") String tip);
}
