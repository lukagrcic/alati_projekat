package rs.fon.laptopprodaja.entity;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class PrSSTest {


    @Test
    @DisplayName("Konstruktor bez argumenata treba da ostavi sva polja na null")
    void konstruktorBezArgumenataOstavljaPoljaNaNull() {
        PrSS noviPrSS = new PrSS();
        assertAll("Sva polja praznog konstruktora moraju biti null",
                () -> assertNull(noviPrSS.getId(), "id treba da bude null nakon praznog konstruktora"),
                () -> assertNull(noviPrSS.getProdavac(), "prodavac treba da bude null nakon praznog konstruktora"),
                () -> assertNull(noviPrSS.getStrucnaSprema(), "strucnaSprema treba da bude null nakon praznog konstruktora"),
                () -> assertNull(noviPrSS.getDatum(), "datum treba da bude null nakon praznog konstruktora")
        );
    }

    @Test
    @DisplayName("Konstruktor sa argumentima treba da postavi prodavca, strucnu spremu i datum")
    void konstruktorSaArgumentimaPostavljaPolja() {
        Prodavac noviProdavac = new Prodavac("Petar", "Petrovic", "ppetrovic", "sifra123");
        StrSprema novaStrSprema = new StrSprema("Master");
        LocalDate danasnjiDatum = LocalDate.now();
        PrSS noviPrSS = new PrSS(noviProdavac, novaStrSprema, danasnjiDatum);
        assertAll("Sva polja konstruktora sa argumentima moraju biti postavljena",
                () -> assertEquals(noviProdavac, noviPrSS.getProdavac(), "Prodavac nije postavljen preko konstruktora sa argumentima"),
                () -> assertEquals(novaStrSprema, noviPrSS.getStrucnaSprema(), "Strucna sprema nije postavljena preko konstruktora sa argumentima"),
                () -> assertEquals(danasnjiDatum, noviPrSS.getDatum(), "Datum nije postavljen preko konstruktora sa argumentima")
        );
    }

    @Test
    @DisplayName("Konstruktor sa argumentima ne postavlja id (ostaje null do perzistencije)")
    void konstruktorSaArgumentimaOstavljaIdNaNull() {
        Prodavac noviProdavac = new Prodavac("Petar", "Petrovic", "ppetrovic", "sifra123");
        StrSprema novaStrSprema = new StrSprema("Master");
        PrSS noviPrSS = new PrSS(noviProdavac, novaStrSprema, LocalDate.now());
        assertNull(noviPrSS.getId(), "id ne sme biti postavljen konstruktorom, dodeljuje ga baza");
    }



    private PrSS prSS;
    private Prodavac prodavac;
    private StrSprema strSprema;

    @BeforeEach
    void setUp() {
        prodavac = new Prodavac("Petar", "Petrovic", "ppetrovic", "sifra123");
        strSprema = new StrSprema("Master");
        prSS = new PrSS(prodavac, strSprema, LocalDate.now());
    }

    @AfterEach
    void tearDown() {
        prSS = null;
        prodavac = null;
        strSprema = null;
    }

    @Test
    @DisplayName("setProdavac treba da postavi validnog prodavca")
    void setProdavacValidnaVrednost() {
        Prodavac drugiProdavac = new Prodavac("Marko", "Markovic", "mmarkovic", "sifra456");
        prSS.setProdavac(drugiProdavac);
        assertEquals(drugiProdavac, prSS.getProdavac(), "getProdavac ne vraca vrednost postavljenu preko setProdavac");
    }

    @Test
    @DisplayName("setProdavac sa null vrednoscu baca NullPointerException")
    void setProdavacNullBacaNullPointerException() {
        NullPointerException ex = assertThrows(NullPointerException.class,
                () -> prSS.setProdavac(null),
                "Ocekivan je NullPointerException kada je prodavac null");
        assertEquals("Prodavac ne sme biti null", ex.getMessage());
    }

    @Test
    @DisplayName("setStrucnaSprema treba da postavi validnu strucnu spremu")
    void setStrucnaSpremaValidnaVrednost() {
        StrSprema drugaStrSprema = new StrSprema("Doktor nauka");
        prSS.setStrucnaSprema(drugaStrSprema);
        assertEquals(drugaStrSprema, prSS.getStrucnaSprema(), "getStrucnaSprema ne vraca vrednost postavljenu preko setStrucnaSprema");
    }

    @Test
    @DisplayName("setStrucnaSprema sa null vrednoscu baca NullPointerException")
    void setStrucnaSpremaNullBacaNullPointerException() {
        NullPointerException ex = assertThrows(NullPointerException.class,
                () -> prSS.setStrucnaSprema(null),
                "Ocekivan je NullPointerException kada je strucna sprema null");
        assertEquals("Strucna sprema ne sme biti null", ex.getMessage());
    }

    @Test
    @DisplayName("setDatum treba da postavi validan datum")
    void setDatumValidnaVrednost() {
        LocalDate noviDatum = LocalDate.now();
        prSS.setDatum(noviDatum);
        assertEquals(noviDatum, prSS.getDatum(), "getDatum ne vraca vrednost postavljenu preko setDatum");
    }

    @Test
    @DisplayName("setDatum sa null vrednoscu baca NullPointerException")
    void setDatumNullBacaNullPointerException() {
        NullPointerException ex = assertThrows(NullPointerException.class,
                () -> prSS.setDatum(null),
                "Ocekivan je NullPointerException kada je datum null");
        assertEquals("Datum ne sme biti null", ex.getMessage());
    }
}
