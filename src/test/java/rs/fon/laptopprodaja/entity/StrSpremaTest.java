package rs.fon.laptopprodaja.entity;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.*;

class StrSpremaTest {


    @Test
    @DisplayName("Konstruktor bez argumenata treba da ostavi sva polja na null")
    void konstruktorBezArgumenataOstavljaPoljaNaNull() {
        StrSprema novaStrSprema = new StrSprema();
        assertAll("Sva polja praznog konstruktora moraju biti null",
                () -> assertNull(novaStrSprema.getIdStrucnaSprema(), "idStrucnaSprema treba da bude null nakon praznog konstruktora"),
                () -> assertNull(novaStrSprema.getNaziv(), "naziv treba da bude null nakon praznog konstruktora")
        );
    }

    @Test
    @DisplayName("Konstruktor sa argumentom treba da postavi naziv")
    void konstruktorSaArgumentomPostavljaNaziv() {
        StrSprema novaStrSprema = new StrSprema("Master");
        assertEquals("Master", novaStrSprema.getNaziv(), "Naziv nije postavljen preko konstruktora sa argumentom");
    }

    @Test
    @DisplayName("Konstruktor sa argumentom ne postavlja idStrucnaSprema (ostaje null do perzistencije)")
    void konstruktorSaArgumentomOstavljaIdStrucnaSpremaNaNull() {
        StrSprema novaStrSprema = new StrSprema("Doktor nauka");
        assertNull(novaStrSprema.getIdStrucnaSprema(), "idStrucnaSprema ne sme biti postavljen konstruktorom, dodeljuje ga baza");
    }



    private StrSprema strSprema;

    @BeforeEach
    void setUp() {
        strSprema = new StrSprema("Master");
    }

    @AfterEach
    void tearDown() {
        strSprema = null;
    }

    @Test
    @DisplayName("setIdStrucnaSprema i getIdStrucnaSprema treba da rade sa validnom vrednoscu")
    void setIdStrucnaSpremaValidnaVrednost() {
        strSprema.setIdStrucnaSprema(5L);
        assertEquals(5L, strSprema.getIdStrucnaSprema(), "getIdStrucnaSprema ne vraca vrednost postavljenu preko setIdStrucnaSprema");
    }

    @Test
    @DisplayName("setIdStrucnaSprema prihvata null jer nema validaciju (dodeljuje ga baza pre perzistencije)")
    void setIdStrucnaSpremaPrihvataNull() {
        strSprema.setIdStrucnaSprema(null);
        assertNull(strSprema.getIdStrucnaSprema(), "idStrucnaSprema treba da moze biti null pre nego sto je entitet sacuvan");
    }

    @Test
    @DisplayName("setNaziv treba da postavi validan naziv duzine bar 2 znaka")
    void setNazivValidnaVrednost() {
        strSprema.setNaziv("Osnovne studije");
        assertEquals("Osnovne studije", strSprema.getNaziv(), "getNaziv ne vraca vrednost postavljenu preko setNaziv");
    }

    @Test
    @DisplayName("setNaziv sa null vrednoscu baca NullPointerException")
    void setNazivNullBacaNullPointerException() {
        NullPointerException ex = assertThrows(NullPointerException.class,
                () -> strSprema.setNaziv(null),
                "Ocekivan je NullPointerException kada je naziv null");
        assertEquals("Naziv ne sme biti null", ex.getMessage());
    }

    @Test
    @DisplayName("setNaziv sa manje od 2 znaka baca IllegalArgumentException")
    void setNazivKratakNazivBacaIllegalArgumentException() {
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
                () -> strSprema.setNaziv("A"),
                "Ocekivan je IllegalArgumentException kada naziv ima manje od 2 znaka");
        assertEquals("Naziv mora imati bar 2 znaka", ex.getMessage());
    }

    @ParameterizedTest(name = "naziv1=\"{0}\", naziv2=\"{1}\" -> jednako={2}")
    @DisplayName("equals poredi strucne spreme iskljucivo po nazivu")
    @CsvSource({
            "Master, Master, true",
            "Master, master, false",
            "Master, Doktor nauka, false",
            "Osnovne studije, Osnovne studije, true"
    })
    void equalsUporedjujeSamoPoNazivu(String naziv1, String naziv2, boolean ocekivanoJednako) {
        StrSprema prva = new StrSprema(naziv1);
        StrSprema druga = new StrSprema(naziv2);
        assertEquals(ocekivanoJednako, prva.equals(druga),
                "equals nije vratio ocekivanu vrednost za naziv1=" + naziv1 + ", naziv2=" + naziv2);
    }

    @Test
    @DisplayName("equals vraca false kada se poredi sa null")
    void equalsSaNullVracaFalse() {
        assertNotEquals(null, strSprema, "equals treba da vrati false kada se poredi sa null");
    }

    @Test
    @DisplayName("equals vraca false kada se poredi sa objektom druge klase")
    void equalsSaDrugomKlasomVracaFalse() {
        assertNotEquals(strSprema, "Master", "equals treba da vrati false za objekat druge klase");
    }

    @Test
    @DisplayName("equals vraca true za istu referencu (refleksivnost)")
    void equalsIstaReferencaVracaTrue() {
        assertEquals(strSprema, strSprema, "equals treba da vrati true kada se objekat poredi sam sa sobom");
    }

    @Test
    @DisplayName("hashCode je jednak za strucne spreme sa istim nazivom")
    void hashCodeJednakZaIstiNaziv() {
        StrSprema drugaStrSprema = new StrSprema("Master");
        assertEquals(strSprema.hashCode(), drugaStrSprema.hashCode(),
                "hashCode treba da bude isti za strucne spreme sa istim nazivom");
    }

    @Test
    @DisplayName("hashCode se razlikuje za strucne spreme sa razlicitim nazivom")
    void hashCodeRazlicitZaRazlicitNaziv() {
        StrSprema drugaStrSprema = new StrSprema("Doktor nauka");
        assertNotEquals(strSprema.hashCode(), drugaStrSprema.hashCode(),
                "hashCode ne bi trebalo da se poklapa za strucne spreme sa razlicitim nazivom");
    }

    @Test
    @DisplayName("toString vraca ocekivani format sa nazivom strucne spreme")
    void toStringVracaOcekivaniFormat() {
        assertEquals("StrSprema{naziv='Master'}", strSprema.toString(),
                "toString ne vraca ocekivani format StrSprema{naziv='...'}");
    }
}
