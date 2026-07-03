package rs.fon.laptopprodaja.service;

import rs.fon.laptopprodaja.dto.KreirajRacunZahtev;
import rs.fon.laptopprodaja.entity.Racun;

import java.util.List;

public interface RacunServis {
    Racun kreirajRacun(KreirajRacunZahtev zahtev);
    List<Racun> pretraziRacune();
    Racun vratiRacun(Long id);
    List<Racun> racuniZaProdavca(Long idProdavac);
    List<Racun> racuniZaKupca(Long idKupac);
}
