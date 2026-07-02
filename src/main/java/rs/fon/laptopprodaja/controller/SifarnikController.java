package rs.fon.laptopprodaja.controller;

import org.springframework.web.bind.annotation.*;
import rs.fon.laptopprodaja.entity.Grad;
import rs.fon.laptopprodaja.entity.StrSprema;
import rs.fon.laptopprodaja.service.SifarnikServis;

import java.util.List;

@RestController
@RequestMapping("/api")
public class SifarnikController {

    private final SifarnikServis sifarnikServis;

    public SifarnikController(SifarnikServis sifarnikServis) {
        this.sifarnikServis = sifarnikServis;
    }

    @GetMapping("/gradovi")
    public List<Grad> gradovi() {
        return sifarnikServis.ucitajGradove();
    }

    @PostMapping("/gradovi")
    public Grad kreirajGrad(@RequestBody Grad grad) {
        return sifarnikServis.kreirajGrad(grad);
    }

    @GetMapping("/strucne-spreme")
    public List<StrSprema> strucneSpreme() {
        return sifarnikServis.ucitajStrucneSpreme();
    }

    @PostMapping("/strucne-spreme")
    public StrSprema kreirajStrucnuSpremu(@RequestBody StrSprema strSprema) {
        return sifarnikServis.kreirajStrucnuSpremu(strSprema);
    }
}
