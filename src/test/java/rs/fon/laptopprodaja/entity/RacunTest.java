package rs.fon.laptopprodaja.entity;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class RacunTest {


    private Prodavac prodavac;
    private Kupac kupac;
    private Laptop laptop1;
    private Laptop laptop2;

    @BeforeEach
    void setUpZavisnosti() {
        prodavac = new Prodavac("Petar", "Petrovic", "ppetrovic", "sifra123");
        kupac = new FizickoLice("test@test.com", "0601234567", new Grad("Beograd", "11000"),
                "Marko", "Markovic", "0101990123456");
        laptop1 = new Laptop("Dell XPS", new BigDecimal("1000.00"), "16GB RAM");
        laptop2 = new Laptop("HP Pavilion", new BigDecimal("800.00"), "8GB RAM");
    }

    @Test
    @DisplayName("Konstruktor bez argumenata treba da ostavi polja na null, ukupanIznos na nulu, a stavke na praznu listu")
    void konstruktorBezArgumenataOstavljaPoljaNaNull() {
        Racun noviRacun = new Racun();
        assertAll("Sva polja praznog konstruktora moraju biti na ocekivanim podrazumevanim vrednostima",
                () -> assertNull(noviRacun.getIdRacun(), "idRacun treba da bude null nakon praznog konstruktora"),
                () -> assertNull(noviRacun.getDatum(), "datum treba da bude null nakon praznog konstruktora"),
                () -> assertNull(noviRacun.getProdavac(), "prodavac treba da bude null nakon praznog konstruktora"),
                () -> assertNull(noviRacun.getKupac(), "kupac treba da bude null nakon praznog konstruktora"),
                () -> assertEquals(0, BigDecimal.ZERO.compareTo(noviRacun.getUkupanIznos()),
                        "ukupanIznos treba da bude nula nakon praznog konstruktora"),
                () -> assertNotNull(noviRacun.getStavke(), "stavke ne sme biti null nakon praznog konstruktora"),
                () -> assertTrue(noviRacun.getStavke().isEmpty(), "stavke treba da bude prazna lista nakon praznog konstruktora")
        );
    }

    @Test
    @DisplayName("Konstruktor sa argumentima treba da postavi datum, prodavca i kupca, a ukupanIznos ostaje nula")
    void konstruktorSaArgumentimaPostavljaPolja() {
        LocalDate danasnjiDatum = LocalDate.now();
        Racun noviRacun = new Racun(danasnjiDatum, prodavac, kupac);
        assertAll("Sva polja konstruktora sa argumentima moraju biti postavljena",
                () -> assertEquals(danasnjiDatum, noviRacun.getDatum(), "Datum nije postavljen preko konstruktora sa argumentima"),
                () -> assertEquals(prodavac, noviRacun.getProdavac(), "Prodavac nije postavljen preko konstruktora sa argumentima"),
                () -> assertEquals(kupac, noviRacun.getKupac(), "Kupac nije postavljen preko konstruktora sa argumentima"),
                () -> assertEquals(0, BigDecimal.ZERO.compareTo(noviRacun.getUkupanIznos()),
                        "ukupanIznos mora biti nula odmah nakon konstrukcije, pre dodavanja stavki")
        );
    }

    @Test
    @DisplayName("Konstruktor sa argumentima ne postavlja idRacun (ostaje null do perzistencije)")
    void konstruktorSaArgumentimaOstavljaIdRacunNaNull() {
        Racun noviRacun = new Racun(LocalDate.now(), prodavac, kupac);
        assertNull(noviRacun.getIdRacun(), "idRacun ne sme biti postavljen konstruktorom, dodeljuje ga baza");
    }



    private Racun racun;

    @BeforeEach
    void setUp() {
        racun = new Racun(LocalDate.now(), prodavac, kupac);
    }

    @AfterEach
    void tearDown() {
        racun = null;
    }

    @Test
    @DisplayName("setDatum treba da postavi validan datum")
    void setDatumValidnaVrednost() {
        LocalDate noviDatum = LocalDate.now().minusDays(1);
        racun.setDatum(noviDatum);
        assertEquals(noviDatum, racun.getDatum(), "getDatum ne vraca vrednost postavljenu preko setDatum");
    }

    @Test
    @DisplayName("setDatum sa null vrednoscu baca NullPointerException")
    void setDatumNullBacaNullPointerException() {
        NullPointerException ex = assertThrows(NullPointerException.class,
                () -> racun.setDatum(null),
                "Ocekivan je NullPointerException kada je datum null");
        assertEquals("Datum ne sme biti null", ex.getMessage());
    }

    @Test
    @DisplayName("setUkupanIznos sa null vrednoscu baca NullPointerException")
    void setUkupanIznosNullBacaNullPointerException() {
        NullPointerException ex = assertThrows(NullPointerException.class,
                () -> racun.setUkupanIznos(null),
                "Ocekivan je NullPointerException kada je ukupan iznos null");
        assertEquals("Ukupan iznos ne sme biti null", ex.getMessage());
    }

    @Test
    @DisplayName("setUkupanIznos sa negativnom vrednoscu baca IllegalArgumentException")
    void setUkupanIznosNegativnomVrednoscuBacaIllegalArgumentException() {
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
                () -> racun.setUkupanIznos(new BigDecimal("-100.00")),
                "Ocekivan je IllegalArgumentException kada je ukupan iznos negativan");
        assertEquals("Ukupan iznos ne sme biti negativan", ex.getMessage());
    }

    @Test
    @DisplayName("setUkupanIznos prihvata vrednost nula (nula nije greska za ukupan iznos)")
    void setUkupanIznosPrihvataNulu() {
        racun.setUkupanIznos(BigDecimal.ZERO);
        assertEquals(0, BigDecimal.ZERO.compareTo(racun.getUkupanIznos()),
                "setUkupanIznos treba da prihvati vrednost nula bez izuzetka");
    }

    @Test
    @DisplayName("setUkupanIznos prihvata validnu pozitivnu vrednost")
    void setUkupanIznosPrihvataPozitivnuVrednost() {
        BigDecimal noviIznos = new BigDecimal("5000.00");
        racun.setUkupanIznos(noviIznos);
        assertEquals(0, noviIznos.compareTo(racun.getUkupanIznos()),
                "getUkupanIznos ne vraca vrednost postavljenu preko setUkupanIznos");
    }

    @Test
    @DisplayName("setProdavac treba da postavi validnog prodavca")
    void setProdavacValidnaVrednost() {
        Prodavac drugiProdavac = new Prodavac("Marko", "Markovic", "mmarkovic", "sifra456");
        racun.setProdavac(drugiProdavac);
        assertEquals(drugiProdavac, racun.getProdavac(), "getProdavac ne vraca vrednost postavljenu preko setProdavac");
    }

    @Test
    @DisplayName("setProdavac sa null vrednoscu baca NullPointerException")
    void setProdavacNullBacaNullPointerException() {
        NullPointerException ex = assertThrows(NullPointerException.class,
                () -> racun.setProdavac(null),
                "Ocekivan je NullPointerException kada je prodavac null");
        assertEquals("Prodavac ne sme biti null", ex.getMessage());
    }

    @Test
    @DisplayName("setKupac treba da postavi validnog kupca")
    void setKupacValidnaVrednost() {
        Kupac drugiKupac = new PravnoLice("firma@test.com", "0601234567", new Grad("Beograd", "11000"),
                "Firma DOO", "123456789", "12345678");
        racun.setKupac(drugiKupac);
        assertEquals(drugiKupac, racun.getKupac(), "getKupac ne vraca vrednost postavljenu preko setKupac");
    }

    @Test
    @DisplayName("setKupac sa null vrednoscu baca NullPointerException")
    void setKupacNullBacaNullPointerException() {
        NullPointerException ex = assertThrows(NullPointerException.class,
                () -> racun.setKupac(null),
                "Ocekivan je NullPointerException kada je kupac null");
        assertEquals("Kupac ne sme biti null", ex.getMessage());
    }

    @Test
    @DisplayName("dodajStavku dodaje stavku u listu stavki racuna")
    void dodajStavkuDodajeStavkuUListu() {
        StavkaRacuna stavka = new StavkaRacuna(1, new BigDecimal("1000.00"), 2, laptop1);
        racun.dodajStavku(stavka);
        assertTrue(racun.getStavke().contains(stavka), "dodajStavku treba da doda prosledjenu stavku u listu stavke");
    }

    @Test
    @DisplayName("dodajStavku automatski postavlja referencu nazad na racun preko stavka.getRacun()")
    void dodajStavkuPostavljaReferencuNazad() {
        StavkaRacuna stavka = new StavkaRacuna(1, new BigDecimal("1000.00"), 2, laptop1);
        racun.dodajStavku(stavka);
        assertSame(racun, stavka.getRacun(), "dodajStavku treba automatski da postavi referencu nazad na racun preko setRacun");
    }

    @Test
    @DisplayName("dodajStavku dodaje vise stavki zadrzavajuci ispravan redosled")
    void dodajStavkuVisePutaZadrzavaRedosled() {
        StavkaRacuna prvaStavka = new StavkaRacuna(1, new BigDecimal("1000.00"), 2, laptop1);
        StavkaRacuna drugaStavka = new StavkaRacuna(2, new BigDecimal("800.00"), 3, laptop2);
        racun.dodajStavku(prvaStavka);
        racun.dodajStavku(drugaStavka);
        assertAll("Sve dodate stavke moraju biti u listi u redosledu dodavanja",
                () -> assertEquals(2, racun.getStavke().size(), "Broj stavki u listi ne odgovara broju dodatih stavki"),
                () -> assertSame(prvaStavka, racun.getStavke().get(0), "Prva dodata stavka nije na prvom mestu u listi"),
                () -> assertSame(drugaStavka, racun.getStavke().get(1), "Druga dodata stavka nije na drugom mestu u listi")
        );
    }

    @Test
    @DisplayName("preracunajUkupanIznos izracunava ukupan iznos kao sumu iznosa svih dodatih stavki")
    void preracunajUkupanIznosSabiraIznoseSvihStavki() {
        StavkaRacuna prvaStavka = new StavkaRacuna(1, new BigDecimal("1000.00"), 2, laptop1);
        StavkaRacuna drugaStavka = new StavkaRacuna(2, new BigDecimal("800.00"), 3, laptop2);
        racun.dodajStavku(prvaStavka);
        racun.dodajStavku(drugaStavka);

        BigDecimal ocekivanUkupanIznos = prvaStavka.getIznos().add(drugaStavka.getIznos());

        racun.preracunajUkupanIznos();

        assertEquals(0, ocekivanUkupanIznos.compareTo(racun.getUkupanIznos()),
                "preracunajUkupanIznos ne izracunava ispravno sumu iznosa svih stavki (ocekivano " + ocekivanUkupanIznos + ")");
    }

    @Test
    @DisplayName("preracunajUkupanIznos vraca nulu kada racun nema nijednu stavku")
    void preracunajUkupanIznosBezStavkiVracaNulu() {
        racun.preracunajUkupanIznos();
        assertEquals(0, BigDecimal.ZERO.compareTo(racun.getUkupanIznos()),
                "preracunajUkupanIznos treba da postavi ukupanIznos na nulu kada racun nema nijednu stavku");
    }
}
