package rs.fon.laptopprodaja.controller;

import org.springframework.web.bind.annotation.*;
import rs.fon.laptopprodaja.dto.DodajStrucnuSpremuZahtev;
import rs.fon.laptopprodaja.dto.PrijavaZahtev;
import rs.fon.laptopprodaja.entity.Prodavac;
import rs.fon.laptopprodaja.service.ProdavacServis;

import java.util.List;

@RestController
@RequestMapping("/api/prodavci")
public class ProdavacController {

    private final ProdavacServis prodavacServis;

    public ProdavacController(ProdavacServis prodavacServis) {
        this.prodavacServis = prodavacServis;
    }

    @PostMapping("/prijava")
    public Prodavac prijava(@RequestBody PrijavaZahtev zahtev) {
        return prodavacServis.prijaviProdavca(zahtev.korisnickoIme(), zahtev.sifra());
    }

    @PostMapping
    public Prodavac kreiraj(@RequestBody Prodavac prodavac) {
        return prodavacServis.sacuvajProdavca(prodavac);
    }

    @PostMapping("/{id}/strucne-spreme")
    public Prodavac dodajStrucnuSpremu(@PathVariable Long id, @RequestBody DodajStrucnuSpremuZahtev zahtev) {
        return prodavacServis.dodajStrucnuSpremuProdavcu(id, zahtev.idStrucnaSprema(), zahtev.datum());
    }

    @GetMapping
    public List<Prodavac> pretrazi(@RequestParam(required = false) String pretraga) {
        return prodavacServis.pretraziProdavce(pretraga);
    }
}
