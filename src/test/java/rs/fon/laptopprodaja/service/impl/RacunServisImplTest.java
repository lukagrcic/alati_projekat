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
import rs.fon.laptopprodaja.dto.KreirajRacunZahtev;
import rs.fon.laptopprodaja.dto.StavkaZahtev;
import rs.fon.laptopprodaja.entity.FizickoLice;
import rs.fon.laptopprodaja.entity.Grad;
import rs.fon.laptopprodaja.entity.Kupac;
import rs.fon.laptopprodaja.entity.Laptop;
import rs.fon.laptopprodaja.entity.Prodavac;
import rs.fon.laptopprodaja.entity.Racun;
import rs.fon.laptopprodaja.repository.KupacRepository;
import rs.fon.laptopprodaja.repository.LaptopRepository;
import rs.fon.laptopprodaja.repository.ProdavacRepository;
import rs.fon.laptopprodaja.repository.RacunRepository;

import java.math.BigDecimal;
import java.util.Collections;
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
class RacunServisImplTest {

    @Mock
    private RacunRepository racunRepository;

    @Mock
    private ProdavacRepository prodavacRepository;

    @Mock
    private KupacRepository kupacRepository;

    @Mock
    private LaptopRepository laptopRepository;

    @InjectMocks
    private RacunServisImpl racunServis;

    private Prodavac noviProdavac(Long id) {
        Prodavac prodavac = new Prodavac("Marko", "Markovic", "marko", "sifra123");
        prodavac.setIdProdavac(id);
        return prodavac;
    }

    private Kupac noviKupac(Long id) {
        Grad grad = new Grad("Beograd", "11000");
        FizickoLice kupac = new FizickoLice("marko@gmail.com", "0641234567", grad, "Marko", "Markovic", "1234567890123");
        kupac.setIdKupac(id);
        return kupac;
    }

    private Laptop noviLaptop(Long id, BigDecimal cena) {
        Laptop laptop = new Laptop("Dell XPS", cena, "16GB RAM");
        laptop.setIdLaptop(id);
        return laptop;
    }


    @Test
    @DisplayName("kreirajRacun - null zahtev baca NullPointerException")
    void kreirajRacun_nullZahtev_bacaException() {
        assertThrows(NullPointerException.class, () -> racunServis.kreirajRacun(null));
        verify(racunRepository, never()).save(any());
    }

    @Test
    @DisplayName("kreirajRacun - prodavac ne postoji baca ResponseStatusException NOT_FOUND")
    void kreirajRacun_prodavacNePostoji_bacaResponseStatusException() {
        KreirajRacunZahtev zahtev = new KreirajRacunZahtev(1L, 2L, List.of(new StavkaZahtev(3L, 1, null)));
        when(prodavacRepository.findById(1L)).thenReturn(Optional.empty());

        ResponseStatusException ex = assertThrows(ResponseStatusException.class,
                () -> racunServis.kreirajRacun(zahtev));

        assertEquals(HttpStatus.NOT_FOUND, ex.getStatusCode());
        verify(racunRepository, never()).save(any());
    }

    @Test
    @DisplayName("kreirajRacun - kupac ne postoji baca ResponseStatusException NOT_FOUND")
    void kreirajRacun_kupacNePostoji_bacaResponseStatusException() {
        KreirajRacunZahtev zahtev = new KreirajRacunZahtev(1L, 2L, List.of(new StavkaZahtev(3L, 1, null)));
        when(prodavacRepository.findById(1L)).thenReturn(Optional.of(noviProdavac(1L)));
        when(kupacRepository.findById(2L)).thenReturn(Optional.empty());

        ResponseStatusException ex = assertThrows(ResponseStatusException.class,
                () -> racunServis.kreirajRacun(zahtev));

        assertEquals(HttpStatus.NOT_FOUND, ex.getStatusCode());
        verify(racunRepository, never()).save(any());
    }

    @Test
    @DisplayName("kreirajRacun - null stavke baca ResponseStatusException BAD_REQUEST")
    void kreirajRacun_nullStavke_bacaResponseStatusException() {
        KreirajRacunZahtev zahtev = new KreirajRacunZahtev(1L, 2L, null);
        when(prodavacRepository.findById(1L)).thenReturn(Optional.of(noviProdavac(1L)));
        when(kupacRepository.findById(2L)).thenReturn(Optional.of(noviKupac(2L)));

        ResponseStatusException ex = assertThrows(ResponseStatusException.class,
                () -> racunServis.kreirajRacun(zahtev));

        assertEquals(HttpStatus.BAD_REQUEST, ex.getStatusCode());
        verify(racunRepository, never()).save(any());
    }

    @Test
    @DisplayName("kreirajRacun - prazna lista stavki baca ResponseStatusException BAD_REQUEST")
    void kreirajRacun_praznaListaStavki_bacaResponseStatusException() {
        KreirajRacunZahtev zahtev = new KreirajRacunZahtev(1L, 2L, Collections.emptyList());
        when(prodavacRepository.findById(1L)).thenReturn(Optional.of(noviProdavac(1L)));
        when(kupacRepository.findById(2L)).thenReturn(Optional.of(noviKupac(2L)));

        ResponseStatusException ex = assertThrows(ResponseStatusException.class,
                () -> racunServis.kreirajRacun(zahtev));

        assertEquals(HttpStatus.BAD_REQUEST, ex.getStatusCode());
        verify(racunRepository, never()).save(any());
    }

    @Test
    @DisplayName("kreirajRacun - laptop iz stavke ne postoji baca ResponseStatusException NOT_FOUND")
    void kreirajRacun_laptopNePostoji_bacaResponseStatusException() {
        KreirajRacunZahtev zahtev = new KreirajRacunZahtev(1L, 2L, List.of(new StavkaZahtev(99L, 1, null)));
        when(prodavacRepository.findById(1L)).thenReturn(Optional.of(noviProdavac(1L)));
        when(kupacRepository.findById(2L)).thenReturn(Optional.of(noviKupac(2L)));
        when(laptopRepository.findById(99L)).thenReturn(Optional.empty());

        ResponseStatusException ex = assertThrows(ResponseStatusException.class,
                () -> racunServis.kreirajRacun(zahtev));

        assertEquals(HttpStatus.NOT_FOUND, ex.getStatusCode());
        verify(racunRepository, never()).save(any());
    }

    @Test
    @DisplayName("kreirajRacun - prodajnaCena null koristi cenu laptopa")
    void kreirajRacun_prodajnaCenaNull_koristiCenuLaptopa() {
        Laptop laptop = noviLaptop(3L, BigDecimal.valueOf(1000));
        KreirajRacunZahtev zahtev = new KreirajRacunZahtev(1L, 2L, List.of(new StavkaZahtev(3L, 2, null)));
        when(prodavacRepository.findById(1L)).thenReturn(Optional.of(noviProdavac(1L)));
        when(kupacRepository.findById(2L)).thenReturn(Optional.of(noviKupac(2L)));
        when(laptopRepository.findById(3L)).thenReturn(Optional.of(laptop));
        when(racunRepository.save(any(Racun.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Racun rezultat = racunServis.kreirajRacun(zahtev);

        assertEquals(1, rezultat.getStavke().size());
        assertEquals(0, BigDecimal.valueOf(1000).compareTo(rezultat.getStavke().get(0).getProdajnaCena()));
        assertEquals(0, BigDecimal.valueOf(2000).compareTo(rezultat.getStavke().get(0).getIznos()));
        verify(racunRepository, times(1)).save(any(Racun.class));
    }

    @Test
    @DisplayName("kreirajRacun - zadata prodajnaCena se koristi umesto cene laptopa")
    void kreirajRacun_zadataProdajnaCena_koristiSeUmestoCeneLaptopa() {
        Laptop laptop = noviLaptop(3L, BigDecimal.valueOf(1000));
        KreirajRacunZahtev zahtev = new KreirajRacunZahtev(1L, 2L, List.of(new StavkaZahtev(3L, 3, BigDecimal.valueOf(500))));
        when(prodavacRepository.findById(1L)).thenReturn(Optional.of(noviProdavac(1L)));
        when(kupacRepository.findById(2L)).thenReturn(Optional.of(noviKupac(2L)));
        when(laptopRepository.findById(3L)).thenReturn(Optional.of(laptop));
        when(racunRepository.save(any(Racun.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Racun rezultat = racunServis.kreirajRacun(zahtev);

        assertEquals(0, BigDecimal.valueOf(500).compareTo(rezultat.getStavke().get(0).getProdajnaCena()));
        assertEquals(0, BigDecimal.valueOf(1500).compareTo(rezultat.getStavke().get(0).getIznos()));
    }

    @Test
    @DisplayName("kreirajRacun - vise stavki dobija ispravan redni broj i ukupan iznos")
    void kreirajRacun_viseStavki_ispravanRedniBrojIUkupanIznos() {
        Laptop laptop1 = noviLaptop(3L, BigDecimal.valueOf(1000));
        Laptop laptop2 = noviLaptop(4L, BigDecimal.valueOf(300));
        StavkaZahtev stavka1 = new StavkaZahtev(3L, 2, null);
        StavkaZahtev stavka2 = new StavkaZahtev(4L, 1, BigDecimal.valueOf(500));
        KreirajRacunZahtev zahtev = new KreirajRacunZahtev(1L, 2L, List.of(stavka1, stavka2));

        when(prodavacRepository.findById(1L)).thenReturn(Optional.of(noviProdavac(1L)));
        when(kupacRepository.findById(2L)).thenReturn(Optional.of(noviKupac(2L)));
        when(laptopRepository.findById(3L)).thenReturn(Optional.of(laptop1));
        when(laptopRepository.findById(4L)).thenReturn(Optional.of(laptop2));

        ArgumentCaptor<Racun> captor = ArgumentCaptor.forClass(Racun.class);
        when(racunRepository.save(captor.capture())).thenAnswer(invocation -> invocation.getArgument(0));

        Racun rezultat = racunServis.kreirajRacun(zahtev);

        assertEquals(2, rezultat.getStavke().size());
        assertEquals(1, rezultat.getStavke().get(0).getRb());
        assertEquals(2, rezultat.getStavke().get(1).getRb());

        BigDecimal ocekivanoUkupno = BigDecimal.valueOf(1000).multiply(BigDecimal.valueOf(2))
                .add(BigDecimal.valueOf(500).multiply(BigDecimal.valueOf(1)));
        assertEquals(0, ocekivanoUkupno.compareTo(rezultat.getUkupanIznos()));
        assertEquals(rezultat, captor.getValue());
        verify(racunRepository, times(1)).save(any(Racun.class));
    }


    @Test
    @DisplayName("pretraziRacune - poziva findAll i vraca rezultat")
    void pretraziRacune_pozivaFindAllIVracaRezultat() {
        List<Racun> racuni = List.of(new Racun(java.time.LocalDate.now(), noviProdavac(1L), noviKupac(2L)));
        when(racunRepository.findAll()).thenReturn(racuni);

        List<Racun> rezultat = racunServis.pretraziRacune();

        verify(racunRepository, times(1)).findAll();
        assertEquals(racuni, rezultat);
    }


    @Test
    @DisplayName("vratiRacun - null id baca NullPointerException")
    void vratiRacun_nullId_bacaException() {
        assertThrows(NullPointerException.class, () -> racunServis.vratiRacun(null));
    }

    @Test
    @DisplayName("vratiRacun - racun pronadjen vraca ga")
    void vratiRacun_racunPronadjen_vracaGa() {
        Racun racun = new Racun(java.time.LocalDate.now(), noviProdavac(1L), noviKupac(2L));
        racun.setIdRacun(10L);
        when(racunRepository.findById(10L)).thenReturn(Optional.of(racun));

        Racun rezultat = racunServis.vratiRacun(10L);

        assertEquals(racun, rezultat);
    }

    @Test
    @DisplayName("vratiRacun - racun ne postoji baca ResponseStatusException NOT_FOUND")
    void vratiRacun_racunNePostoji_bacaResponseStatusException() {
        when(racunRepository.findById(10L)).thenReturn(Optional.empty());

        ResponseStatusException ex = assertThrows(ResponseStatusException.class,
                () -> racunServis.vratiRacun(10L));

        assertEquals(HttpStatus.NOT_FOUND, ex.getStatusCode());
    }


    @Test
    @DisplayName("racuniZaProdavca - null idProdavac baca NullPointerException")
    void racuniZaProdavca_nullIdProdavac_bacaException() {
        assertThrows(NullPointerException.class, () -> racunServis.racuniZaProdavca(null));
    }

    @Test
    @DisplayName("racuniZaProdavca - validan idProdavac poziva findByProdavac_IdProdavac")
    void racuniZaProdavca_validanIdProdavac_pozivaFindByProdavacIdProdavac() {
        List<Racun> racuni = List.of(new Racun(java.time.LocalDate.now(), noviProdavac(1L), noviKupac(2L)));
        when(racunRepository.findByProdavac_IdProdavac(1L)).thenReturn(racuni);

        List<Racun> rezultat = racunServis.racuniZaProdavca(1L);

        verify(racunRepository, times(1)).findByProdavac_IdProdavac(1L);
        assertEquals(racuni, rezultat);
    }


    @Test
    @DisplayName("racuniZaKupca - null idKupac baca NullPointerException")
    void racuniZaKupca_nullIdKupac_bacaException() {
        assertThrows(NullPointerException.class, () -> racunServis.racuniZaKupca(null));
    }

    @Test
    @DisplayName("racuniZaKupca - validan idKupac poziva findByKupac_IdKupac")
    void racuniZaKupca_validanIdKupac_pozivaFindByKupacIdKupac() {
        List<Racun> racuni = List.of(new Racun(java.time.LocalDate.now(), noviProdavac(1L), noviKupac(2L)));
        when(racunRepository.findByKupac_IdKupac(2L)).thenReturn(racuni);

        List<Racun> rezultat = racunServis.racuniZaKupca(2L);

        verify(racunRepository, times(1)).findByKupac_IdKupac(2L);
        assertEquals(racuni, rezultat);
    }
}
