package rs.fon.laptopprodaja.service.impl;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;
import rs.fon.laptopprodaja.entity.FizickoLice;
import rs.fon.laptopprodaja.entity.Grad;
import rs.fon.laptopprodaja.entity.Kupac;
import rs.fon.laptopprodaja.entity.PravnoLice;
import rs.fon.laptopprodaja.repository.KupacRepository;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class KupacServisImplTest {

    @Mock
    private KupacRepository kupacRepository;

    @InjectMocks
    private KupacServisImpl kupacServis;


    @Test
    @DisplayName("kreirajKupca - null kupac baca NullPointerException")
    void kreirajKupca_nullKupac_bacaException() {
        assertThrows(NullPointerException.class, () -> kupacServis.kreirajKupca(null));
        verify(kupacRepository, never()).save(any());
    }

    @Test
    @DisplayName("kreirajKupca - validan FizickoLice resetuje id na null pre save i vraca rezultat")
    void kreirajKupca_validanFizickoLice_resetujeIdIPozivaSave() {
        Grad grad = new Grad("Beograd", "11000");
        FizickoLice fizickoLice = new FizickoLice("marko@gmail.com", "0641234567", grad, "Marko", "Markovic", "1234567890123");
        fizickoLice.setIdKupac(99L);

        when(kupacRepository.save(any(Kupac.class))).thenReturn(fizickoLice);

        Kupac rezultat = kupacServis.kreirajKupca(fizickoLice);

        ArgumentCaptor<Kupac> captor = ArgumentCaptor.forClass(Kupac.class);
        verify(kupacRepository, times(1)).save(captor.capture());
        assertNull(captor.getValue().getIdKupac());
        assertEquals(fizickoLice, rezultat);
    }


    @Test
    @DisplayName("izmeniKupca - null id baca NullPointerException")
    void izmeniKupca_nullId_bacaException() {
        Grad grad = new Grad("Beograd", "11000");
        FizickoLice izmene = new FizickoLice("marko@gmail.com", "0641234567", grad, "Marko", "Markovic", "1234567890123");
        assertThrows(NullPointerException.class, () -> kupacServis.izmeniKupca(null, izmene));
        verify(kupacRepository, never()).save(any());
    }

    @Test
    @DisplayName("izmeniKupca - null izmene baca NullPointerException")
    void izmeniKupca_nullIzmene_bacaException() {
        assertThrows(NullPointerException.class, () -> kupacServis.izmeniKupca(1L, null));
        verify(kupacRepository, never()).save(any());
    }

    @Test
    @DisplayName("izmeniKupca - kupac ne postoji baca ResponseStatusException NOT_FOUND")
    void izmeniKupca_kupacNePostoji_bacaResponseStatusException() {
        Grad grad = new Grad("Beograd", "11000");
        FizickoLice izmene = new FizickoLice("marko@gmail.com", "0641234567", grad, "Marko", "Markovic", "1234567890123");
        when(kupacRepository.findById(1L)).thenReturn(Optional.empty());

        ResponseStatusException ex = assertThrows(ResponseStatusException.class,
                () -> kupacServis.izmeniKupca(1L, izmene));

        assertEquals(HttpStatus.NOT_FOUND, ex.getStatusCode());
        verify(kupacRepository, never()).save(any());
    }

    @Test
    @DisplayName("izmeniKupca - tip mismatch (FizickoLice -> PravnoLice) baca ResponseStatusException BAD_REQUEST")
    void izmeniKupca_tipMismatch_bacaResponseStatusException() {
        Grad grad = new Grad("Beograd", "11000");
        FizickoLice postojeci = new FizickoLice("marko@gmail.com", "0641234567", grad, "Marko", "Markovic", "1234567890123");
        PravnoLice izmene = new PravnoLice("firma@gmail.com", "0651234567", grad, "Firma DOO", "123456789", "12345678");

        when(kupacRepository.findById(1L)).thenReturn(Optional.of(postojeci));

        ResponseStatusException ex = assertThrows(ResponseStatusException.class,
                () -> kupacServis.izmeniKupca(1L, izmene));

        assertEquals(HttpStatus.BAD_REQUEST, ex.getStatusCode());
        verify(kupacRepository, never()).save(any());
    }

    @Test
    @DisplayName("izmeniKupca - validan slucaj za FizickoLice azurira sva polja i poziva save")
    void izmeniKupca_validanFizickoLice_azuriraIPozivaSave() {
        Grad stariGrad = new Grad("Beograd", "11000");
        Grad noviGrad = new Grad("Novi Sad", "21000");
        FizickoLice postojeci = new FizickoLice("stari@gmail.com", "0640000000", stariGrad, "Stari", "Staric", "1111111111111");
        postojeci.setIdKupac(5L);
        FizickoLice izmene = new FizickoLice("novi@gmail.com", "0659999999", noviGrad, "Novi", "Novic", "2222222222222");

        when(kupacRepository.findById(5L)).thenReturn(Optional.of(postojeci));
        when(kupacRepository.save(any(Kupac.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Kupac rezultat = kupacServis.izmeniKupca(5L, izmene);

        FizickoLice fizickoRezultat = (FizickoLice) rezultat;
        assertEquals("novi@gmail.com", fizickoRezultat.getEmail());
        assertEquals("0659999999", fizickoRezultat.getTelefon());
        assertEquals(noviGrad, fizickoRezultat.getGrad());
        assertEquals("Novi", fizickoRezultat.getIme());
        assertEquals("Novic", fizickoRezultat.getPrezime());
        assertEquals("2222222222222", fizickoRezultat.getJmbg());
        assertEquals(5L, fizickoRezultat.getIdKupac());
        verify(kupacRepository, times(1)).save(postojeci);
    }

    @Test
    @DisplayName("izmeniKupca - validan slucaj za PravnoLice azurira sva polja i poziva save")
    void izmeniKupca_validanPravnoLice_azuriraIPozivaSave() {
        Grad stariGrad = new Grad("Beograd", "11000");
        Grad noviGrad = new Grad("Novi Sad", "21000");
        PravnoLice postojeci = new PravnoLice("stara@firma.com", "0640000000", stariGrad, "Stara Firma", "111111111", "11111111");
        postojeci.setIdKupac(7L);
        PravnoLice izmene = new PravnoLice("nova@firma.com", "0659999999", noviGrad, "Nova Firma", "222222222", "22222222");

        when(kupacRepository.findById(7L)).thenReturn(Optional.of(postojeci));
        when(kupacRepository.save(any(Kupac.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Kupac rezultat = kupacServis.izmeniKupca(7L, izmene);

        PravnoLice pravnoRezultat = (PravnoLice) rezultat;
        assertEquals("nova@firma.com", pravnoRezultat.getEmail());
        assertEquals("0659999999", pravnoRezultat.getTelefon());
        assertEquals(noviGrad, pravnoRezultat.getGrad());
        assertEquals("Nova Firma", pravnoRezultat.getNaziv());
        assertEquals("222222222", pravnoRezultat.getPib());
        assertEquals("22222222", pravnoRezultat.getMaticniBroj());
        assertEquals(7L, pravnoRezultat.getIdKupac());
        verify(kupacRepository, times(1)).save(postojeci);
    }


    @Test
    @DisplayName("obrisiKupca - null id baca NullPointerException")
    void obrisiKupca_nullId_bacaException() {
        assertThrows(NullPointerException.class, () -> kupacServis.obrisiKupca(null));
        verify(kupacRepository, never()).deleteById(any());
    }

    @Test
    @DisplayName("obrisiKupca - kupac ne postoji baca ResponseStatusException NOT_FOUND")
    void obrisiKupca_kupacNePostoji_bacaResponseStatusException() {
        when(kupacRepository.existsById(1L)).thenReturn(false);

        ResponseStatusException ex = assertThrows(ResponseStatusException.class,
                () -> kupacServis.obrisiKupca(1L));

        assertEquals(HttpStatus.NOT_FOUND, ex.getStatusCode());
        verify(kupacRepository, never()).deleteById(any());
    }

    @Test
    @DisplayName("obrisiKupca - kupac postoji poziva deleteById")
    void obrisiKupca_kupacPostoji_pozivaDeleteById() {
        when(kupacRepository.existsById(1L)).thenReturn(true);

        kupacServis.obrisiKupca(1L);

        verify(kupacRepository, times(1)).deleteById(1L);
    }


    @Test
    @DisplayName("pretraziKupce - poziva findAll i vraca rezultat")
    void pretraziKupce_pozivaFindAllIVracaRezultat() {
        Grad grad = new Grad("Beograd", "11000");
        List<Kupac> kupci = List.of(new FizickoLice("marko@gmail.com", "0641234567", grad, "Marko", "Markovic", "1234567890123"));
        when(kupacRepository.findAll()).thenReturn(kupci);

        List<Kupac> rezultat = kupacServis.pretraziKupce();

        verify(kupacRepository, times(1)).findAll();
        assertEquals(kupci, rezultat);
    }


    @Test
    @DisplayName("vratiKupca - null id baca NullPointerException")
    void vratiKupca_nullId_bacaException() {
        assertThrows(NullPointerException.class, () -> kupacServis.vratiKupca(null));
    }

    @Test
    @DisplayName("vratiKupca - kupac pronadjen vraca ga")
    void vratiKupca_kupacPronadjen_vracaGa() {
        Grad grad = new Grad("Beograd", "11000");
        FizickoLice kupac = new FizickoLice("marko@gmail.com", "0641234567", grad, "Marko", "Markovic", "1234567890123");
        kupac.setIdKupac(3L);
        when(kupacRepository.findById(3L)).thenReturn(Optional.of(kupac));

        Kupac rezultat = kupacServis.vratiKupca(3L);

        assertEquals(kupac, rezultat);
    }

    @Test
    @DisplayName("vratiKupca - kupac ne postoji baca ResponseStatusException NOT_FOUND")
    void vratiKupca_kupacNePostoji_bacaResponseStatusException() {
        when(kupacRepository.findById(3L)).thenReturn(Optional.empty());

        ResponseStatusException ex = assertThrows(ResponseStatusException.class,
                () -> kupacServis.vratiKupca(3L));

        assertEquals(HttpStatus.NOT_FOUND, ex.getStatusCode());
    }


    @Test
    @DisplayName("kupciPoGradu - null idGrad baca NullPointerException")
    void kupciPoGradu_nullIdGrad_bacaException() {
        assertThrows(NullPointerException.class, () -> kupacServis.kupciPoGradu(null));
    }

    @Test
    @DisplayName("kupciPoGradu - validan idGrad poziva findByGrad_IdGrad sa tacnim argumentom")
    void kupciPoGradu_validanIdGrad_pozivaFindByGradIdGrad() {
        Grad grad = new Grad("Beograd", "11000");
        List<Kupac> kupci = List.of(new FizickoLice("marko@gmail.com", "0641234567", grad, "Marko", "Markovic", "1234567890123"));
        when(kupacRepository.findByGrad_IdGrad(1L)).thenReturn(kupci);

        List<Kupac> rezultat = kupacServis.kupciPoGradu(1L);

        verify(kupacRepository, times(1)).findByGrad_IdGrad(1L);
        assertEquals(kupci, rezultat);
    }


    @Test
    @DisplayName("kupciPoTipu - null tip baca NullPointerException")
    void kupciPoTipu_nullTip_bacaException() {
        assertThrows(NullPointerException.class, () -> kupacServis.kupciPoTipu(null));
    }

    @Test
    @DisplayName("kupciPoTipu - validan tip poziva findByTipKupca sa tacnim argumentom")
    void kupciPoTipu_validanTip_pozivaFindByTipKupca() {
        Grad grad = new Grad("Beograd", "11000");
        List<Kupac> kupci = List.of(new FizickoLice("marko@gmail.com", "0641234567", grad, "Marko", "Markovic", "1234567890123"));
        when(kupacRepository.findByTipKupca("fizicko")).thenReturn(kupci);

        List<Kupac> rezultat = kupacServis.kupciPoTipu("fizicko");

        verify(kupacRepository, times(1)).findByTipKupca("fizicko");
        assertEquals(kupci, rezultat);
    }
}
