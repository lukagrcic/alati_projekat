package rs.fon.laptopprodaja.controller;

import org.springframework.web.bind.annotation.*;
import rs.fon.laptopprodaja.entity.Kupac;
import rs.fon.laptopprodaja.service.KupacServis;

import java.util.List;

@RestController
@RequestMapping("/api/kupci")
public class KupacController {

    private final KupacServis kupacServis;

    public KupacController(KupacServis kupacServis) {
        this.kupacServis = kupacServis;
    }

    @PostMapping
    public Kupac kreiraj(@RequestBody Kupac kupac) {
        return kupacServis.kreirajKupca(kupac);
    }

    @PutMapping("/{id}")
    public Kupac izmeni(@PathVariable Long id, @RequestBody Kupac kupac) {
        return kupacServis.izmeniKupca(id, kupac);
    }

    @DeleteMapping("/{id}")
    public void obrisi(@PathVariable Long id) {
        kupacServis.obrisiKupca(id);
    }

    @GetMapping
    public List<Kupac> pretrazi() {
        return kupacServis.pretraziKupce();
    }

    @GetMapping("/{id}")
    public Kupac vrati(@PathVariable Long id) {
        return kupacServis.vratiKupca(id);
    }

    @GetMapping("/po-gradu/{idGrad}")
    public List<Kupac> poGradu(@PathVariable Long idGrad) {
        return kupacServis.kupciPoGradu(idGrad);
    }

    @GetMapping("/po-tipu")
    public List<Kupac> poTipu(@RequestParam String tip) {
        return kupacServis.kupciPoTipu(tip);
    }
}
