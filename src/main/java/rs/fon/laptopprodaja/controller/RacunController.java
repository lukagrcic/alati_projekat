package rs.fon.laptopprodaja.controller;

import org.springframework.web.bind.annotation.*;
import rs.fon.laptopprodaja.dto.KreirajRacunZahtev;
import rs.fon.laptopprodaja.entity.Racun;
import rs.fon.laptopprodaja.service.RacunServis;

import java.util.List;

@RestController
@RequestMapping("/api/racuni")
public class RacunController {

    private final RacunServis racunServis;

    public RacunController(RacunServis racunServis) {
        this.racunServis = racunServis;
    }

    @PostMapping
    public Racun kreiraj(@RequestBody KreirajRacunZahtev zahtev) {
        return racunServis.kreirajRacun(zahtev);
    }

    @GetMapping
    public List<Racun> pretrazi() {
        return racunServis.pretraziRacune();
    }

    @GetMapping("/{id}")
    public Racun vrati(@PathVariable Long id) {
        return racunServis.vratiRacun(id);
    }

    @GetMapping("/po-prodavcu/{idProdavac}")
    public List<Racun> poProddavcu(@PathVariable Long idProdavac) {
        return racunServis.racuniZaProdavca(idProdavac);
    }

    @GetMapping("/po-kupcu/{idKupac}")
    public List<Racun> poKupcu(@PathVariable Long idKupac) {
        return racunServis.racuniZaKupca(idKupac);
    }


}
