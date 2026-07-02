package rs.fon.laptopprodaja.controller;

import org.springframework.web.bind.annotation.*;
import rs.fon.laptopprodaja.entity.Laptop;
import rs.fon.laptopprodaja.service.LaptopServis;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/laptopovi")
public class LaptopController {

    private final LaptopServis laptopServis;

    public LaptopController(LaptopServis laptopServis) {
        this.laptopServis = laptopServis;
    }

    @PostMapping
    public Laptop kreiraj(@RequestBody Laptop laptop) {
        return laptopServis.kreirajLaptop(laptop);
    }

    @PutMapping("/{id}")
    public Laptop izmeni(@PathVariable Long id, @RequestBody Laptop laptop) {
        return laptopServis.izmeniLaptop(id, laptop);
    }

    @DeleteMapping("/{id}")
    public void obrisi(@PathVariable Long id) {
        laptopServis.obrisiLaptop(id);
    }

    @GetMapping
    public List<Laptop> pretrazi(@RequestParam(required = false) String naziv) {
        return laptopServis.pretraziLaptope(naziv);
    }

    @GetMapping("/{id}")
    public Laptop vrati(@PathVariable Long id) {
        return laptopServis.vratiLaptop(id);
    }

    @GetMapping("/po-ceni")
    public List<Laptop> poRasponuCene(@RequestParam BigDecimal min, @RequestParam BigDecimal max) {
        return laptopServis.laptopPoRasponuCene(min, max);
    }

    @GetMapping("/do-cene")
    public List<Laptop> doMaksimalneCene(@RequestParam BigDecimal max) {
        return laptopServis.laptopDoMaksimalneCene(max);
    }
}
