package rs.fon.laptopprodaja.dto;

import java.time.LocalDate;

/** Zahtev za dodelu strucne spreme prodavcu. */
public record DodajStrucnuSpremuZahtev(Long idStrucnaSprema, LocalDate datum) {
}
