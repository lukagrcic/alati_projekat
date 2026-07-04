package rs.fon.laptopprodaja.service.impl;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;
import rs.fon.laptopprodaja.entity.Prodavac;
import rs.fon.laptopprodaja.repository.ProdavacRepository;
import rs.fon.laptopprodaja.repository.StrSpremaRepository;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ProdavacServisImplTest {

    @Mock
    private ProdavacRepository prodavacRepository;

    @Mock
    private StrSpremaRepository strSpremaRepository;

    @InjectMocks
    private ProdavacServisImpl prodavacServis;


    @Test
    @DisplayName("prijaviProdavca - null korisnicko ime baca NullPointerException")
    void prijaviProdavca_nullKorisnickoIme_bacaException() {
        assertThrows(NullPointerException.class, () -> prodavacServis.prijaviProdavca(null, "sifra123"));
    }

    @Test
    @DisplayName("prijaviProdavca - null sifra baca NullPointerException")
    void prijaviProdavca_nullSifra_bacaException() {
        assertThrows(NullPointerException.class, () -> prodavacServis.prijaviProdavca("marko", null));
    }

    @Test
    @DisplayName("prijaviProdavca - korisnik ne postoji baca ResponseStatusException UNAUTHORIZED")
    void prijaviProdavca_korisnikNePostoji_bacaResponseStatusException() {
        when(prodavacRepository.findByKorisnickoIme("marko")).thenReturn(Optional.empty());

        ResponseStatusException ex = assertThrows(ResponseStatusException.class,
                () -> prodavacServis.prijaviProdavca("marko", "sifra123"));

        assertEquals(HttpStatus.UNAUTHORIZED, ex.getStatusCode());
        assertEquals("Pogresno korisnicko ime ili sifra", ex.getReason());
    }

    @Test
    @DisplayName("prijaviProdavca - pogresna sifra baca ResponseStatusException UNAUTHORIZED")
    void prijaviProdavca_pogresnaSifra_bacaResponseStatusException() {
        Prodavac prodavac = new Prodavac("Marko", "Markovic", "marko", "tacnaSifra");
        when(prodavacRepository.findByKorisnickoIme("marko")).thenReturn(Optional.of(prodavac));

        ResponseStatusException ex = assertThrows(ResponseStatusException.class,
                () -> prodavacServis.prijaviProdavca("marko", "pogresnaSifra"));

        assertEquals(HttpStatus.UNAUTHORIZED, ex.getStatusCode());
        assertEquals("Pogresno korisnicko ime ili sifra", ex.getReason());
    }

    @Test
    @DisplayName("prijaviProdavca - tacno korisnicko ime i sifra vraca prodavca")
    void prijaviProdavca_tacniPodaci_vracaProdavca() {
        Prodavac prodavac = new Prodavac("Marko", "Markovic", "marko", "tacnaSifra");
        when(prodavacRepository.findByKorisnickoIme("marko")).thenReturn(Optional.of(prodavac));

        Prodavac rezultat = prodavacServis.prijaviProdavca("marko", "tacnaSifra");

        assertEquals(prodavac, rezultat);
    }


    @Test
    @DisplayName("sacuvajProdavca - null prodavac baca NullPointerException")
    void sacuvajProdavca_nullProdavac_bacaException() {
        assertThrows(NullPointerException.class, () -> prodavacServis.sacuvajProdavca(null));
        verify(prodavacRepository, never()).save(any());
    }

    @Test
    @DisplayName("sacuvajProdavca - validan prodavac poziva save i vraca rezultat")
    void sacuvajProdavca_validanProdavac_pozivaSaveIVracaRezultat() {
        Prodavac prodavac = new Prodavac("Marko", "Markovic", "marko", "sifra123");
        Prodavac sacuvaniProdavac = new Prodavac("Marko", "Markovic", "marko", "sifra123");
        sacuvaniProdavac.setIdProdavac(1L);

        when(prodavacRepository.save(prodavac)).thenReturn(sacuvaniProdavac);

        Prodavac rezultat = prodavacServis.sacuvajProdavca(prodavac);

        verify(prodavacRepository, times(1)).save(prodavac);
        assertEquals(sacuvaniProdavac, rezultat);
        assertEquals(1L, rezultat.getIdProdavac());
    }


    @Test
    @DisplayName("pretraziProdavce - pretraga je null poziva findAll")
    void pretraziProdavce_pretragaNull_pozivaFindAll() {
        List<Prodavac> svi = List.of(new Prodavac("Marko", "Markovic", "marko", "sifra123"));
        when(prodavacRepository.findAll()).thenReturn(svi);

        List<Prodavac> rezultat = prodavacServis.pretraziProdavce(null);

        verify(prodavacRepository, times(1)).findAll();
        verify(prodavacRepository, never()).findByImeContainingIgnoreCaseOrPrezimeContainingIgnoreCase(any(), any());
        assertEquals(svi, rezultat);
    }

    @Test
    @DisplayName("pretraziProdavce - pretraga je prazan/blank string poziva findAll")
    void pretraziProdavce_pretragaBlank_pozivaFindAll() {
        List<Prodavac> svi = List.of(new Prodavac("Marko", "Markovic", "marko", "sifra123"));
        when(prodavacRepository.findAll()).thenReturn(svi);

        List<Prodavac> rezultat = prodavacServis.pretraziProdavce("   ");

        verify(prodavacRepository, times(1)).findAll();
        verify(prodavacRepository, never()).findByImeContainingIgnoreCaseOrPrezimeContainingIgnoreCase(any(), any());
        assertEquals(svi, rezultat);
    }

    @Test
    @DisplayName("pretraziProdavce - pretraga je zadata poziva findByImeContainingIgnoreCaseOrPrezimeContainingIgnoreCase")
    void pretraziProdavce_pretragaZadata_pozivaFindByImeOrPrezime() {
        List<Prodavac> pronadjeni = List.of(new Prodavac("Marko", "Markovic", "marko", "sifra123"));
        when(prodavacRepository.findByImeContainingIgnoreCaseOrPrezimeContainingIgnoreCase("Marko", "Marko"))
                .thenReturn(pronadjeni);

        List<Prodavac> rezultat = prodavacServis.pretraziProdavce("Marko");

        verify(prodavacRepository, times(1))
                .findByImeContainingIgnoreCaseOrPrezimeContainingIgnoreCase("Marko", "Marko");
        verify(prodavacRepository, never()).findAll();
        assertEquals(pronadjeni, rezultat);
    }
}
