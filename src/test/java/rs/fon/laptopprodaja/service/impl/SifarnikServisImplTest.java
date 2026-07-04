package rs.fon.laptopprodaja.service.impl;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import rs.fon.laptopprodaja.entity.Grad;
import rs.fon.laptopprodaja.entity.StrSprema;
import rs.fon.laptopprodaja.repository.GradRepository;
import rs.fon.laptopprodaja.repository.StrSpremaRepository;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class SifarnikServisImplTest {

    @Mock
    private GradRepository gradRepository;

    @Mock
    private StrSpremaRepository strSpremaRepository;

    @InjectMocks
    private SifarnikServisImpl sifarnikServis;

    @Test
    @DisplayName("ucitajGradove - poziva findAll i vraca rezultat")
    void ucitajGradove_pozivaFindAllIVracaRezultat() {
        List<Grad> gradovi = List.of(new Grad("Beograd", "11000"));
        when(gradRepository.findAll()).thenReturn(gradovi);

        List<Grad> rezultat = sifarnikServis.ucitajGradove();

        verify(gradRepository, times(1)).findAll();
        assertEquals(gradovi, rezultat);
    }

    @Test
    @DisplayName("kreirajGrad - null grad baca NullPointerException")
    void kreirajGrad_nullGrad_bacaException() {
        assertThrows(NullPointerException.class, () -> sifarnikServis.kreirajGrad(null));
        verify(gradRepository, never()).save(any());
    }

    @Test
    @DisplayName("kreirajGrad - validan grad se cuva preko repozitorijuma i vraca rezultat save-a")
    void kreirajGrad_validanGrad_pozivaSaveIVracaRezultat() {
        Grad grad = new Grad("Novi Sad", "21000");
        Grad sacuvaniGrad = new Grad("Novi Sad", "21000");
        sacuvaniGrad.setIdGrad(1L);

        when(gradRepository.save(any(Grad.class))).thenReturn(sacuvaniGrad);

        Grad rezultat = sifarnikServis.kreirajGrad(grad);

        verify(gradRepository, times(1)).save(grad);
        assertEquals(sacuvaniGrad, rezultat);
        assertEquals(1L, rezultat.getIdGrad());
    }

    @Test
    @DisplayName("ucitajStrucneSpreme - poziva findAll i vraca rezultat")
    void ucitajStrucneSpreme_pozivaFindAllIVracaRezultat() {
        List<StrSprema> strucneSpreme = List.of(new StrSprema("VSS"));
        when(strSpremaRepository.findAll()).thenReturn(strucneSpreme);

        List<StrSprema> rezultat = sifarnikServis.ucitajStrucneSpreme();

        verify(strSpremaRepository, times(1)).findAll();
        assertEquals(strucneSpreme, rezultat);
    }

    @Test
    @DisplayName("kreirajStrucnuSpremu - null strSprema baca NullPointerException")
    void kreirajStrucnuSpremu_nullStrSprema_bacaException() {
        assertThrows(NullPointerException.class, () -> sifarnikServis.kreirajStrucnuSpremu(null));
        verify(strSpremaRepository, never()).save(any());
    }

    @Test
    @DisplayName("kreirajStrucnuSpremu - validna strSprema se cuva preko repozitorijuma i vraca rezultat save-a")
    void kreirajStrucnuSpremu_validnaStrSprema_pozivaSaveIVracaRezultat() {
        StrSprema strSprema = new StrSprema("SSS");
        StrSprema sacuvanaStrSprema = new StrSprema("SSS");
        sacuvanaStrSprema.setIdStrucnaSprema(1L);

        when(strSpremaRepository.save(any(StrSprema.class))).thenReturn(sacuvanaStrSprema);

        StrSprema rezultat = sifarnikServis.kreirajStrucnuSpremu(strSprema);

        verify(strSpremaRepository, times(1)).save(strSprema);
        assertEquals(sacuvanaStrSprema, rezultat);
        assertEquals(1L, rezultat.getIdStrucnaSprema());
    }
}
