package rs.fon.laptopprodaja.service;

import rs.fon.laptopprodaja.entity.Grad;
import rs.fon.laptopprodaja.entity.StrSprema;

import java.util.List;

public interface SifarnikServis {
    List<Grad> ucitajGradove();
    Grad kreirajGrad(Grad grad);
    List<StrSprema> ucitajStrucneSpreme();
    StrSprema kreirajStrucnuSpremu(StrSprema strSprema);
}
