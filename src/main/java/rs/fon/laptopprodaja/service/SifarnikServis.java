package rs.fon.laptopprodaja.service;

import rs.fon.laptopprodaja.entity.Grad;
import rs.fon.laptopprodaja.entity.StrSprema;

import java.util.List;

/**
 * Interfejs koji definise poslovnu logiku za rad sa sifarnicima (gradovi i
 * strucne spreme).
 *
 * @author Luka Grcic
 * @version 1.0
 */
public interface SifarnikServis {

    /**
     * Vraca sve gradove.
     *
     * @return Lista svih gradova.
     */
    List<Grad> ucitajGradove();

    /**
     * Kreira novi grad.
     *
     * @param grad Grad koji se kreira.
     * @return Novokreirani grad.
     * @throws java.lang.NullPointerException Ako je grad null.
     */
    Grad kreirajGrad(Grad grad);

    /**
     * Vraca sve strucne spreme.
     *
     * @return Lista svih strucnih sprema.
     */
    List<StrSprema> ucitajStrucneSpreme();

    /**
     * Kreira novu strucnu spremu.
     *
     * @param strSprema Strucna sprema koja se kreira.
     * @return Novokreirana strucna sprema.
     * @throws java.lang.NullPointerException Ako je strSprema null.
     */
    StrSprema kreirajStrucnuSpremu(StrSprema strSprema);
}
