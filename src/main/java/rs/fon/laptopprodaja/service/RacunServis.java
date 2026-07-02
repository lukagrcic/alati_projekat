package rs.fon.laptopprodaja.service;

import rs.fon.laptopprodaja.dto.KreirajRacunZahtev;
import rs.fon.laptopprodaja.entity.Racun;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public interface RacunServis {
    Racun kreirajRacun(KreirajRacunZahtev zahtev);
    List<Racun> pretraziRacune();
    Racun vratiRacun(Long id);
    void obrisiRacun(Long id);
    List<Racun> racuniZaProdavca(Long idProdavac);
    List<Racun> racuniZaKupca(Long idKupac);
    List<Racun> racuniUPeriodu(LocalDate od, LocalDate kraj);
    List<Racun> racuniNadIznosom(BigDecimal iznos);
}
