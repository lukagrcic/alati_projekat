package rs.fon.laptopprodaja.dto;

import java.util.List;

/** Zahtev za kreiranje racuna zajedno sa stavkama. */
public record KreirajRacunZahtev(Long idProdavac, Long idKupac, List<StavkaZahtev> stavke) {
}
