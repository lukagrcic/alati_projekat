package rs.fon.laptopprodaja.entity;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class LaptopTest {


    @Test
    @DisplayName("Konstruktor bez argumenata treba da ostavi sva polja na null")
    void konstruktorBezArgumenataOstavljaPoljaNaNull() {
        Laptop noviLaptop = new Laptop();
        assertAll("Sva polja praznog konstruktora moraju biti null",
                () -> assertNull(noviLaptop.getIdLaptop(), "idLaptop treba da bude null nakon praznog konstruktora"),
                () -> assertNull(noviLaptop.getNaziv(), "naziv treba da bude null nakon praznog konstruktora"),
                () -> assertNull(noviLaptop.getCena(), "cena treba da bude null nakon praznog konstruktora"),
                () -> assertNull(noviLaptop.getKarakteristike(), "karakteristike treba da bude null nakon praznog konstruktora")
        );
    }

    @Test
    @DisplayName("Konstruktor sa argumentima treba da postavi naziv, cenu i karakteristike")
    void konstruktorSaArgumentimaPostavljaPolja() {
        Laptop noviLaptop = new Laptop("Dell XPS", new BigDecimal("999.99"), "16GB RAM, 512GB SSD");
        assertAll("Sva polja konstruktora sa argumentima moraju biti postavljena",
                () -> assertEquals("Dell XPS", noviLaptop.getNaziv(), "Naziv nije postavljen preko konstruktora sa argumentima"),
                () -> assertEquals(0, new BigDecimal("999.99").compareTo(noviLaptop.getCena()),
                        "Cena nije postavljena preko konstruktora sa argumentima"),
                () -> assertEquals("16GB RAM, 512GB SSD", noviLaptop.getKarakteristike(),
                        "Karakteristike nisu postavljene preko konstruktora sa argumentima")
        );
    }

    @Test
    @DisplayName("Konstruktor sa argumentima ne postavlja idLaptop (ostaje null do perzistencije)")
    void konstruktorSaArgumentimaOstavljaIdLaptopNaNull() {
        Laptop noviLaptop = new Laptop("HP Pavilion", new BigDecimal("799.00"), "8GB RAM");
        assertNull(noviLaptop.getIdLaptop(), "idLaptop ne sme biti postavljen konstruktorom, dodeljuje ga baza");
    }



    private Laptop laptop;

    @BeforeEach
    void setUp() {
        laptop = new Laptop("Dell XPS", new BigDecimal("999.99"), "16GB RAM, 512GB SSD");
    }

    @AfterEach
    void tearDown() {
        laptop = null;
    }

    @Test
    @DisplayName("setIdLaptop i getIdLaptop treba da rade sa validnom vrednoscu")
    void setIdLaptopValidnaVrednost() {
        laptop.setIdLaptop(5L);
        assertEquals(5L, laptop.getIdLaptop(), "getIdLaptop ne vraca vrednost postavljenu preko setIdLaptop");
    }

    @Test
    @DisplayName("setIdLaptop prihvata null jer nema validaciju (dodeljuje ga baza pre perzistencije)")
    void setIdLaptopPrihvataNull() {
        laptop.setIdLaptop(null);
        assertNull(laptop.getIdLaptop(), "idLaptop treba da moze biti null pre nego sto je entitet sacuvan");
    }

    @Test
    @DisplayName("setNaziv treba da postavi validan naziv duzine bar 2 znaka")
    void setNazivValidnaVrednost() {
        laptop.setNaziv("Lenovo ThinkPad");
        assertEquals("Lenovo ThinkPad", laptop.getNaziv(), "getNaziv ne vraca vrednost postavljenu preko setNaziv");
    }

    @Test
    @DisplayName("setNaziv sa null vrednoscu baca NullPointerException")
    void setNazivNullBacaNullPointerException() {
        NullPointerException ex = assertThrows(NullPointerException.class,
                () -> laptop.setNaziv(null),
                "Ocekivan je NullPointerException kada je naziv null");
        assertEquals("Naziv ne sme biti null", ex.getMessage());
    }

    @Test
    @DisplayName("setNaziv sa manje od 2 znaka baca IllegalArgumentException")
    void setNazivKratakNazivBacaIllegalArgumentException() {
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
                () -> laptop.setNaziv("A"),
                "Ocekivan je IllegalArgumentException kada naziv ima manje od 2 znaka");
        assertEquals("Naziv mora imati bar 2 znaka", ex.getMessage());
    }

    @ParameterizedTest(name = "cena=\"{0}\" treba da bude prihvacena")
    @DisplayName("setCena prihvata razlicite validne pozitivne vrednosti")
    @CsvSource({
            "999.99",
            "1.00",
            "1500.50"
    })
    void setCenaValidneVrednosti(String cena) {
        BigDecimal ocekivanaCena = new BigDecimal(cena);
        laptop.setCena(ocekivanaCena);
        assertEquals(0, ocekivanaCena.compareTo(laptop.getCena()),
                "getCena ne vraca vrednost postavljenu preko setCena za " + cena);
    }

    @Test
    @DisplayName("setCena sa null vrednoscu baca NullPointerException")
    void setCenaNullBacaNullPointerException() {
        NullPointerException ex = assertThrows(NullPointerException.class,
                () -> laptop.setCena(null),
                "Ocekivan je NullPointerException kada je cena null");
        assertEquals("Cena ne sme biti null", ex.getMessage());
    }

    @Test
    @DisplayName("setCena sa vrednoscu tacno 0 baca IllegalArgumentException")
    void setCenaNulomBacaIllegalArgumentException() {
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
                () -> laptop.setCena(BigDecimal.ZERO),
                "Ocekivan je IllegalArgumentException kada je cena jednaka 0");
        assertEquals("Cena mora biti veca od 0", ex.getMessage());
    }

    @Test
    @DisplayName("setCena sa negativnom vrednoscu baca IllegalArgumentException")
    void setCenaNegativnomVrednoscuBacaIllegalArgumentException() {
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
                () -> laptop.setCena(new BigDecimal("-100.00")),
                "Ocekivan je IllegalArgumentException kada je cena negativna");
        assertEquals("Cena mora biti veca od 0", ex.getMessage());
    }

    @Test
    @DisplayName("setKarakteristike treba da postavi validnu vrednost")
    void setKarakteristikeValidnaVrednost() {
        laptop.setKarakteristike("32GB RAM, 1TB SSD");
        assertEquals("32GB RAM, 1TB SSD", laptop.getKarakteristike(),
                "getKarakteristike ne vraca vrednost postavljenu preko setKarakteristike");
    }

    @Test
    @DisplayName("setKarakteristike prihvata null jer nije obavezno polje")
    void setKarakteristikePrihvataNull() {
        laptop.setKarakteristike(null);
        assertNull(laptop.getKarakteristike(), "karakteristike treba da moze biti null jer polje nije obavezno");
    }

    @ParameterizedTest(name = "naziv1=\"{0}\", cena1={1}, naziv2=\"{2}\", cena2={3} -> jednako={4}")
    @DisplayName("equals poredi laptopove po nazivu i ceni")
    @CsvSource({
            "Dell XPS, 999.99, Dell XPS, 999.99, true",
            "Dell XPS, 999.99, HP Pavilion, 999.99, false",
            "Dell XPS, 999.99, Dell XPS, 1099.99, false",
            "Dell XPS, 999.99, dell xps, 999.99, false"
    })
    void equalsUporedjujeSamoPoNazivuICeni(String naziv1, String cena1, String naziv2, String cena2, boolean ocekivanoJednako) {
        Laptop prvi = new Laptop(naziv1, new BigDecimal(cena1), "opis1");
        Laptop drugi = new Laptop(naziv2, new BigDecimal(cena2), "opis2");
        assertEquals(ocekivanoJednako, prvi.equals(drugi),
                "equals nije vratio ocekivanu vrednost za naziv1=" + naziv1 + ", cena1=" + cena1
                        + ", naziv2=" + naziv2 + ", cena2=" + cena2);
    }

    @Test
    @DisplayName("equals vraca false kada se poredi sa null")
    void equalsSaNullVracaFalse() {
        assertNotEquals(null, laptop, "equals treba da vrati false kada se poredi sa null");
    }

    @Test
    @DisplayName("equals vraca false kada se poredi sa objektom druge klase")
    void equalsSaDrugomKlasomVracaFalse() {
        assertNotEquals(laptop, "Dell XPS", "equals treba da vrati false za objekat druge klase");
    }

    @Test
    @DisplayName("equals vraca true za istu referencu (refleksivnost)")
    void equalsIstaReferencaVracaTrue() {
        assertEquals(laptop, laptop, "equals treba da vrati true kada se objekat poredi sam sa sobom");
    }

    @Test
    @DisplayName("hashCode je jednak za laptopove sa istim nazivom i cenom")
    void hashCodeJednakZaIstiNazivICenu() {
        Laptop drugiLaptop = new Laptop("Dell XPS", new BigDecimal("999.99"), "drugaciji opis");
        assertEquals(laptop.hashCode(), drugiLaptop.hashCode(),
                "hashCode treba da bude isti za laptopove sa istim nazivom i cenom, bez obzira na karakteristike");
    }

    @Test
    @DisplayName("hashCode se razlikuje za laptopove sa razlicitim nazivom")
    void hashCodeRazlicitZaRazlicitNaziv() {
        Laptop drugiLaptop = new Laptop("HP Pavilion", new BigDecimal("999.99"), "16GB RAM, 512GB SSD");
        assertNotEquals(laptop.hashCode(), drugiLaptop.hashCode(),
                "hashCode ne bi trebalo da se poklapa za laptopove sa razlicitim nazivom");
    }

    @Test
    @DisplayName("hashCode se razlikuje za laptopove sa razlicitom cenom")
    void hashCodeRazlicitZaRazlicituCenu() {
        Laptop drugiLaptop = new Laptop("Dell XPS", new BigDecimal("1099.99"), "16GB RAM, 512GB SSD");
        assertNotEquals(laptop.hashCode(), drugiLaptop.hashCode(),
                "hashCode ne bi trebalo da se poklapa za laptopove sa razlicitom cenom");
    }

    @Test
    @DisplayName("toString vraca ocekivani format sa nazivom i cenom laptopa")
    void toStringVracaOcekivaniFormat() {
        assertEquals("Laptop{naziv='Dell XPS', cena=999.99}", laptop.toString(),
                "toString ne vraca ocekivani format Laptop{naziv='...', cena=...}");
    }
}
