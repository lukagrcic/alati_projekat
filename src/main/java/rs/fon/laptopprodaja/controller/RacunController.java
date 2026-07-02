package rs.fon.laptopprodaja.controller;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;
import rs.fon.laptopprodaja.dto.KreirajRacunZahtev;
import rs.fon.laptopprodaja.entity.Racun;
import rs.fon.laptopprodaja.service.RacunServis;

import java.math.BigDecimal;
import java.time.LocalDate;
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

    @DeleteMapping("/{id}")
    public void obrisi(@PathVariable Long id) {
        racunServis.obrisiRacun(id);
    }

    @GetMapping("/po-prodavcu/{idProdavac}")
    public List<Racun> poProddavcu(@PathVariable Long idProdavac) {
        return racunServis.racuniZaProdavca(idProdavac);
    }

    @GetMapping("/po-kupcu/{idKupac}")
    public List<Racun> poKupcu(@PathVariable Long idKupac) {
        return racunServis.racuniZaKupca(idKupac);
    }

    @GetMapping("/po-periodu")
    public List<Racun> poPeriodu(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate od,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate kraj) {
        return racunServis.racuniUPeriodu(od, kraj);
    }

    @GetMapping("/nad-iznosom")
    public List<Racun> nadIznosom(@RequestParam BigDecimal iznos) {
        return racunServis.racuniNadIznosom(iznos);
    }
}
