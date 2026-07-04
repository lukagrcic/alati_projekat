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
import rs.fon.laptopprodaja.entity.Laptop;
import rs.fon.laptopprodaja.repository.LaptopRepository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class LaptopServisImplTest {

    @Mock
    private LaptopRepository laptopRepository;

    @InjectMocks
    private LaptopServisImpl laptopServis;


    @Test
    @DisplayName("kreirajLaptop - null laptop baca NullPointerException")
    void kreirajLaptop_nullLaptop_bacaException() {
        assertThrows(NullPointerException.class, () -> laptopServis.kreirajLaptop(null));
        verify(laptopRepository, never()).save(any());
    }

    @Test
    @DisplayName("kreirajLaptop - validan laptop se cuva preko repozitorijuma i vraca rezultat save-a")
    void kreirajLaptop_validanLaptop_pozivaSaveIVracaRezultat() {
        Laptop laptop = new Laptop("Dell XPS", BigDecimal.valueOf(1000), "16GB RAM");
        Laptop sacuvaniLaptop = new Laptop("Dell XPS", BigDecimal.valueOf(1000), "16GB RAM");
        sacuvaniLaptop.setIdLaptop(1L);

        when(laptopRepository.save(any(Laptop.class))).thenReturn(sacuvaniLaptop);

        Laptop rezultat = laptopServis.kreirajLaptop(laptop);

        verify(laptopRepository, times(1)).save(laptop);
        assertEquals(sacuvaniLaptop, rezultat);
        assertEquals(1L, rezultat.getIdLaptop());
    }

    @Test
    @DisplayName("kreirajLaptop - id se eksplicitno resetuje na null pre poziva save")
    void kreirajLaptop_idSeResetujeNaNullPreSave() {
        Laptop laptop = new Laptop("Lenovo ThinkPad", BigDecimal.valueOf(1200), "32GB RAM");
        laptop.setIdLaptop(99L);

        when(laptopRepository.save(any(Laptop.class))).thenReturn(laptop);

        laptopServis.kreirajLaptop(laptop);

        ArgumentCaptor<Laptop> captor = ArgumentCaptor.forClass(Laptop.class);
        verify(laptopRepository).save(captor.capture());
        assertNull(captor.getValue().getIdLaptop());
    }


    @Test
    @DisplayName("izmeniLaptop - null id baca NullPointerException")
    void izmeniLaptop_nullId_bacaException() {
        Laptop izmene = new Laptop("Asus", BigDecimal.valueOf(500), "8GB RAM");
        assertThrows(NullPointerException.class, () -> laptopServis.izmeniLaptop(null, izmene));
        verify(laptopRepository, never()).save(any());
    }

    @Test
    @DisplayName("izmeniLaptop - null izmene baca NullPointerException")
    void izmeniLaptop_nullIzmene_bacaException() {
        assertThrows(NullPointerException.class, () -> laptopServis.izmeniLaptop(1L, null));
        verify(laptopRepository, never()).save(any());
    }

    @Test
    @DisplayName("izmeniLaptop - laptop ne postoji baca ResponseStatusException NOT_FOUND")
    void izmeniLaptop_laptopNePostoji_bacaResponseStatusException() {
        Laptop izmene = new Laptop("Asus", BigDecimal.valueOf(500), "8GB RAM");
        when(laptopRepository.findById(1L)).thenReturn(Optional.empty());

        ResponseStatusException ex = assertThrows(ResponseStatusException.class,
                () -> laptopServis.izmeniLaptop(1L, izmene));

        assertEquals(HttpStatus.NOT_FOUND, ex.getStatusCode());
        verify(laptopRepository, never()).save(any());
    }

    @Test
    @DisplayName("izmeniLaptop - validan slucaj azurira postojeci laptop i poziva save")
    void izmeniLaptop_validanSlucaj_azuriraIPozivaSave() {
        Laptop postojeciLaptop = new Laptop("Stari naziv", BigDecimal.valueOf(300), "Stare karakteristike");
        postojeciLaptop.setIdLaptop(5L);
        Laptop izmene = new Laptop("Novi naziv", BigDecimal.valueOf(700), "Nove karakteristike");

        when(laptopRepository.findById(5L)).thenReturn(Optional.of(postojeciLaptop));
        when(laptopRepository.save(any(Laptop.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Laptop rezultat = laptopServis.izmeniLaptop(5L, izmene);

        assertEquals("Novi naziv", rezultat.getNaziv());
        assertEquals(BigDecimal.valueOf(700), rezultat.getCena());
        assertEquals("Nove karakteristike", rezultat.getKarakteristike());
        assertEquals(5L, rezultat.getIdLaptop());
        verify(laptopRepository, times(1)).save(postojeciLaptop);
    }


    @Test
    @DisplayName("obrisiLaptop - null id baca NullPointerException")
    void obrisiLaptop_nullId_bacaException() {
        assertThrows(NullPointerException.class, () -> laptopServis.obrisiLaptop(null));
        verify(laptopRepository, never()).deleteById(any());
    }

    @Test
    @DisplayName("obrisiLaptop - laptop ne postoji baca ResponseStatusException NOT_FOUND")
    void obrisiLaptop_laptopNePostoji_bacaResponseStatusException() {
        when(laptopRepository.existsById(1L)).thenReturn(false);

        ResponseStatusException ex = assertThrows(ResponseStatusException.class,
                () -> laptopServis.obrisiLaptop(1L));

        assertEquals(HttpStatus.NOT_FOUND, ex.getStatusCode());
        verify(laptopRepository, never()).deleteById(any());
    }

    @Test
    @DisplayName("obrisiLaptop - laptop postoji poziva deleteById")
    void obrisiLaptop_laptopPostoji_pozivaDeleteById() {
        when(laptopRepository.existsById(1L)).thenReturn(true);

        laptopServis.obrisiLaptop(1L);

        verify(laptopRepository, times(1)).deleteById(1L);
    }


    @Test
    @DisplayName("pretraziLaptope - naziv je null poziva findAll")
    void pretraziLaptope_nazivNull_pozivaFindAll() {
        List<Laptop> svi = List.of(new Laptop("Dell", BigDecimal.valueOf(100), null));
        when(laptopRepository.findAll()).thenReturn(svi);

        List<Laptop> rezultat = laptopServis.pretraziLaptope(null);

        verify(laptopRepository, times(1)).findAll();
        verify(laptopRepository, never()).findByNazivContainingIgnoreCase(anyString());
        assertEquals(svi, rezultat);
    }

    @Test
    @DisplayName("pretraziLaptope - naziv je prazan/blank string poziva findAll")
    void pretraziLaptope_nazivBlank_pozivaFindAll() {
        List<Laptop> svi = List.of(new Laptop("Dell", BigDecimal.valueOf(100), null));
        when(laptopRepository.findAll()).thenReturn(svi);

        List<Laptop> rezultat = laptopServis.pretraziLaptope("   ");

        verify(laptopRepository, times(1)).findAll();
        verify(laptopRepository, never()).findByNazivContainingIgnoreCase(anyString());
        assertEquals(svi, rezultat);
    }

    @Test
    @DisplayName("pretraziLaptope - naziv je zadat poziva findByNazivContainingIgnoreCase sa tacnim argumentom")
    void pretraziLaptope_nazivZadat_pozivaFindByNaziv() {
        List<Laptop> pronadjeni = List.of(new Laptop("Dell XPS", BigDecimal.valueOf(1000), null));
        when(laptopRepository.findByNazivContainingIgnoreCase("Dell")).thenReturn(pronadjeni);

        List<Laptop> rezultat = laptopServis.pretraziLaptope("Dell");

        verify(laptopRepository, times(1)).findByNazivContainingIgnoreCase("Dell");
        verify(laptopRepository, never()).findAll();
        assertEquals(pronadjeni, rezultat);
    }


    @Test
    @DisplayName("vratiLaptop - null id baca NullPointerException")
    void vratiLaptop_nullId_bacaException() {
        assertThrows(NullPointerException.class, () -> laptopServis.vratiLaptop(null));
    }

    @Test
    @DisplayName("vratiLaptop - laptop pronadjen vraca ga")
    void vratiLaptop_laptopPronadjen_vracaGa() {
        Laptop laptop = new Laptop("HP", BigDecimal.valueOf(400), null);
        laptop.setIdLaptop(3L);
        when(laptopRepository.findById(3L)).thenReturn(Optional.of(laptop));

        Laptop rezultat = laptopServis.vratiLaptop(3L);

        assertEquals(laptop, rezultat);
    }

    @Test
    @DisplayName("vratiLaptop - laptop ne postoji baca ResponseStatusException NOT_FOUND")
    void vratiLaptop_laptopNePostoji_bacaResponseStatusException() {
        when(laptopRepository.findById(3L)).thenReturn(Optional.empty());

        ResponseStatusException ex = assertThrows(ResponseStatusException.class,
                () -> laptopServis.vratiLaptop(3L));

        assertEquals(HttpStatus.NOT_FOUND, ex.getStatusCode());
    }

    @Test
    @DisplayName("laptopPoRasponuCene - min null baca NullPointerException")
    void laptopPoRasponuCene_minNull_bacaException() {
        assertThrows(NullPointerException.class,
                () -> laptopServis.laptopPoRasponuCene(null, BigDecimal.valueOf(1000)));
    }

    @Test
    @DisplayName("laptopPoRasponuCene - max null baca NullPointerException")
    void laptopPoRasponuCene_maxNull_bacaException() {
        assertThrows(NullPointerException.class,
                () -> laptopServis.laptopPoRasponuCene(BigDecimal.valueOf(100), null));
    }

    @Test
    @DisplayName("laptopPoRasponuCene - validni min/max poziva findByCenaBetween sa tacnim argumentima")
    void laptopPoRasponuCene_validniArgumenti_pozivaFindByCenaBetween() {
        BigDecimal min = BigDecimal.valueOf(100);
        BigDecimal max = BigDecimal.valueOf(1000);
        List<Laptop> pronadjeni = List.of(new Laptop("Dell", BigDecimal.valueOf(500), null));
        when(laptopRepository.findByCenaBetween(min, max)).thenReturn(pronadjeni);

        List<Laptop> rezultat = laptopServis.laptopPoRasponuCene(min, max);

        verify(laptopRepository, times(1)).findByCenaBetween(min, max);
        assertEquals(pronadjeni, rezultat);
    }
}
