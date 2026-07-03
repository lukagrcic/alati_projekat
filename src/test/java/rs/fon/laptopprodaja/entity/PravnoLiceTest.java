package rs.fon.laptopprodaja.entity;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.*;

class PravnoLiceTest extends KupacTest {

    @Override
    protected Kupac getInstance() {
        return new PravnoLice("firma@test.com", "0601234567", new Grad("Beograd", "11000"),
                "Firma DOO", "123456789", "12345678");
    }


    @Test
    @DisplayName("Konstruktor sa argumentima treba da postavi sva polja, ukljucujuci nasledjena, i getTip vraca 'pravno'")
    void konstruktorSaArgumentimaPostavljaPolja() {
        Grad grad = new Grad("Beograd", "11000");
        PravnoLice novoPravnoLice = new PravnoLice("firma@test.com", "0601234567", grad,
                "Firma DOO", "123456789", "12345678");
        assertAll("Sva polja konstruktora sa argumentima moraju biti postavljena",
                () -> assertEquals("firma@test.com", novoPravnoLice.getEmail(), "Email nije postavljen preko konstruktora"),
                () -> assertEquals("0601234567", novoPravnoLice.getTelefon(), "Telefon nije postavljen preko konstruktora"),
                () -> assertEquals(grad, novoPravnoLice.getGrad(), "Grad nije postavljen preko konstruktora"),
                () -> assertEquals("Firma DOO", novoPravnoLice.getNaziv(), "Naziv nije postavljen preko konstruktora"),
                () -> assertEquals("123456789", novoPravnoLice.getPib(), "PIB nije postavljen preko konstruktora"),
                () -> assertEquals("12345678", novoPravnoLice.getMaticniBroj(), "Maticni broj nije postavljen preko konstruktora"),
                () -> assertEquals("pravno", novoPravnoLice.getTip(), "getTip ne vraca ocekivanu vrednost 'pravno'")
        );
    }

    @Test
    @DisplayName("Konstruktor sa argumentima ne postavlja idKupac (ostaje null do perzistencije)")
    void konstruktorSaArgumentimaOstavljaIdKupacNaNull() {
        PravnoLice novoPravnoLice = new PravnoLice("firma@test.com", "0601234567", new Grad("Beograd", "11000"),
                "Firma DOO", "123456789", "12345678");
        assertNull(novoPravnoLice.getIdKupac(), "idKupac ne sme biti postavljen konstruktorom, dodeljuje ga baza");
    }



    private PravnoLice pravnoLice;

    @BeforeEach
    void setUpPravnoLice() {
        pravnoLice = (PravnoLice) kupac;
    }

    @Test
    @DisplayName("setNaziv treba da postavi validan naziv duzine bar 2 znaka")
    void setNazivValidnaVrednost() {
        pravnoLice.setNaziv("Druga Firma DOO");
        assertEquals("Druga Firma DOO", pravnoLice.getNaziv(), "getNaziv ne vraca vrednost postavljenu preko setNaziv");
    }

    @Test
    @DisplayName("setNaziv sa null vrednoscu baca NullPointerException")
    void setNazivNullBacaNullPointerException() {
        NullPointerException ex = assertThrows(NullPointerException.class,
                () -> pravnoLice.setNaziv(null),
                "Ocekivan je NullPointerException kada je naziv null");
        assertEquals("Naziv ne sme biti null", ex.getMessage());
    }

    @Test
    @DisplayName("setNaziv sa manje od 2 znaka baca IllegalArgumentException")
    void setNazivKratakNazivBacaIllegalArgumentException() {
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
                () -> pravnoLice.setNaziv("A"),
                "Ocekivan je IllegalArgumentException kada naziv ima manje od 2 znaka");
        assertEquals("Naziv mora imati bar 2 znaka", ex.getMessage());
    }

    @Test
    @DisplayName("setPib treba da postavi validan 9-cifreni pib")
    void setPibValidnaVrednost() {
        pravnoLice.setPib("987654321");
        assertEquals("987654321", pravnoLice.getPib(), "getPib ne vraca vrednost postavljenu preko setPib");
    }

    @Test
    @DisplayName("setPib sa null vrednoscu baca NullPointerException")
    void setPibNullBacaNullPointerException() {
        NullPointerException ex = assertThrows(NullPointerException.class,
                () -> pravnoLice.setPib(null),
                "Ocekivan je NullPointerException kada je pib null");
        assertEquals("PIB ne sme biti null", ex.getMessage());
    }

    @ParameterizedTest(name = "pib=\"{0}\" treba da baci IllegalArgumentException")
    @DisplayName("setPib odbacuje vrednosti koje nemaju tacno 9 cifara")
    @CsvSource({
            "12345678",
            "1234567890"
    })
    void setPibNevalidnaDuzinaBacaIllegalArgumentException(String nevalidanPib) {
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
                () -> pravnoLice.setPib(nevalidanPib),
                "Ocekivan je IllegalArgumentException za pib koji nema tacno 9 cifara: " + nevalidanPib);
        assertEquals("PIB mora imati tacno 9 cifara", ex.getMessage());
    }

    @Test
    @DisplayName("setMaticniBroj treba da postavi validan 8-cifreni maticni broj")
    void setMaticniBrojValidnaVrednost() {
        pravnoLice.setMaticniBroj("87654321");
        assertEquals("87654321", pravnoLice.getMaticniBroj(), "getMaticniBroj ne vraca vrednost postavljenu preko setMaticniBroj");
    }

    @Test
    @DisplayName("setMaticniBroj sa null vrednoscu baca NullPointerException")
    void setMaticniBrojNullBacaNullPointerException() {
        NullPointerException ex = assertThrows(NullPointerException.class,
                () -> pravnoLice.setMaticniBroj(null),
                "Ocekivan je NullPointerException kada je maticni broj null");
        assertEquals("Maticni broj ne sme biti null", ex.getMessage());
    }

    @ParameterizedTest(name = "maticniBroj=\"{0}\" treba da baci IllegalArgumentException")
    @DisplayName("setMaticniBroj odbacuje vrednosti koje nemaju tacno 8 cifara")
    @CsvSource({
            "1234567",
            "123456789"
    })
    void setMaticniBrojNevalidnaDuzinaBacaIllegalArgumentException(String nevalidanMaticniBroj) {
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
                () -> pravnoLice.setMaticniBroj(nevalidanMaticniBroj),
                "Ocekivan je IllegalArgumentException za maticni broj koji nema tacno 8 cifara: " + nevalidanMaticniBroj);
        assertEquals("Maticni broj mora imati tacno 8 cifara", ex.getMessage());
    }

    @ParameterizedTest(name = "naziv2=\"{0}\", pib2=\"{1}\" -> jednako={2}")
    @DisplayName("equals poredi PravnoLice po nazivu i pib-u (email i telefon fiksni, grad i maticni broj se ne uzimaju u obzir)")
    @CsvSource({
            "Firma DOO, 123456789, true",
            "Druga Firma, 123456789, false",
            "Firma DOO, 987654321, false"
    })
    void equalsUporedjujeNazivIPib(String naziv2, String pib2, boolean ocekivanoJednako) {
        PravnoLice prvo = new PravnoLice("firma@test.com", "0601234567", new Grad("Beograd", "11000"),
                "Firma DOO", "123456789", "12345678");
        PravnoLice drugo = new PravnoLice("firma@test.com", "0601234567", new Grad("Novi Sad", "21000"),
                naziv2, pib2, "87654321");
        assertEquals(ocekivanoJednako, prvo.equals(drugo),
                "equals nije vratio ocekivanu vrednost za naziv2=" + naziv2 + ", pib2=" + pib2);
    }

    @Test
    @DisplayName("equals vraca true za PravnoLice objekte sa razlicitim maticnim brojem ako su ostala relevantna polja ista")
    void equalsIgnorisMaticniBroj() {
        PravnoLice prvo = new PravnoLice("firma@test.com", "0601234567", new Grad("Beograd", "11000"),
                "Firma DOO", "123456789", "11111111");
        PravnoLice drugo = new PravnoLice("firma@test.com", "0601234567", new Grad("Novi Sad", "21000"),
                "Firma DOO", "123456789", "22222222");
        assertEquals(prvo, drugo, "equals treba da vrati true bez obzira na maticni broj jer on ne ucestvuje u poredjenju");
    }

    @Test
    @DisplayName("equals vraca false kada se PravnoLice objekti razlikuju po email-u")
    void equalsRazlicitEmailVracaFalse() {
        PravnoLice prvo = new PravnoLice("prva@test.com", "0601234567", new Grad("Beograd", "11000"),
                "Firma DOO", "123456789", "12345678");
        PravnoLice drugo = new PravnoLice("druga@test.com", "0601234567", new Grad("Beograd", "11000"),
                "Firma DOO", "123456789", "12345678");
        assertNotEquals(prvo, drugo, "equals treba da vrati false kada se PravnoLice objekti razlikuju po email-u");
    }

    @Test
    @DisplayName("equals vraca false kada se PravnoLice objekti razlikuju po telefonu")
    void equalsRazlicitTelefonVracaFalse() {
        PravnoLice prvo = new PravnoLice("firma@test.com", "0601111111", new Grad("Beograd", "11000"),
                "Firma DOO", "123456789", "12345678");
        PravnoLice drugo = new PravnoLice("firma@test.com", "0602222222", new Grad("Beograd", "11000"),
                "Firma DOO", "123456789", "12345678");
        assertNotEquals(prvo, drugo, "equals treba da vrati false kada se PravnoLice objekti razlikuju po telefonu");
    }

    @Test
    @DisplayName("equals vraca false kada se poredi sa null")
    void equalsSaNullVracaFalse() {
        assertNotEquals(null, pravnoLice, "equals treba da vrati false kada se poredi sa null");
    }

    @Test
    @DisplayName("equals vraca false kada se poredi sa objektom druge klase")
    void equalsSaDrugomKlasomVracaFalse() {
        assertNotEquals(pravnoLice, "Firma DOO", "equals treba da vrati false za objekat druge klase");
    }

    @Test
    @DisplayName("equals vraca true za istu referencu (refleksivnost)")
    void equalsIstaReferencaVracaTrue() {
        assertEquals(pravnoLice, pravnoLice, "equals treba da vrati true kada se objekat poredi sam sa sobom");
    }

    @Test
    @DisplayName("hashCode je jednak za PravnoLice objekte sa istim nazivom, pib-om, email-om i telefonom")
    void hashCodeJednakZaIstaPolja() {
        PravnoLice drugoPravnoLice = new PravnoLice("firma@test.com", "0601234567", new Grad("Novi Sad", "21000"),
                "Firma DOO", "123456789", "99999999");
        assertEquals(pravnoLice.hashCode(), drugoPravnoLice.hashCode(),
                "hashCode treba da bude isti za PravnoLice objekte sa istim relevantnim poljima, bez obzira na grad i maticni broj");
    }

    @Test
    @DisplayName("hashCode se razlikuje za PravnoLice objekte sa razlicitim nazivom")
    void hashCodeRazlicitZaRazlicitNaziv() {
        PravnoLice drugoPravnoLice = new PravnoLice("firma@test.com", "0601234567", new Grad("Beograd", "11000"),
                "Druga Firma", "123456789", "12345678");
        assertNotEquals(pravnoLice.hashCode(), drugoPravnoLice.hashCode(),
                "hashCode ne bi trebalo da se poklapa za PravnoLice objekte sa razlicitim nazivom");
    }

    @Test
    @DisplayName("hashCode se razlikuje za PravnoLice objekte sa razlicitim pib-om")
    void hashCodeRazlicitZaRazlicitPib() {
        PravnoLice drugoPravnoLice = new PravnoLice("firma@test.com", "0601234567", new Grad("Beograd", "11000"),
                "Firma DOO", "987654321", "12345678");
        assertNotEquals(pravnoLice.hashCode(), drugoPravnoLice.hashCode(),
                "hashCode ne bi trebalo da se poklapa za PravnoLice objekte sa razlicitim pib-om");
    }

    @Test
    @DisplayName("hashCode se razlikuje za PravnoLice objekte sa razlicitim email-om")
    void hashCodeRazlicitZaRazlicitEmail() {
        PravnoLice drugoPravnoLice = new PravnoLice("druga@test.com", "0601234567", new Grad("Beograd", "11000"),
                "Firma DOO", "123456789", "12345678");
        assertNotEquals(pravnoLice.hashCode(), drugoPravnoLice.hashCode(),
                "hashCode ne bi trebalo da se poklapa za PravnoLice objekte sa razlicitim email-om");
    }

    @Test
    @DisplayName("hashCode se razlikuje za PravnoLice objekte sa razlicitim telefonom")
    void hashCodeRazlicitZaRazlicitTelefon() {
        PravnoLice drugoPravnoLice = new PravnoLice("firma@test.com", "0602222222", new Grad("Beograd", "11000"),
                "Firma DOO", "123456789", "12345678");
        assertNotEquals(pravnoLice.hashCode(), drugoPravnoLice.hashCode(),
                "hashCode ne bi trebalo da se poklapa za PravnoLice objekte sa razlicitim telefonom");
    }

    @Test
    @DisplayName("toString vraca ocekivani format sa pib-om, nazivom, email-om i telefonom")
    void toStringVracaOcekivaniFormat() {
        assertEquals("PravnoLice{pib='123456789', naziv='Firma DOO', email='firma@test.com', telefon='0601234567'}",
                pravnoLice.toString(),
                "toString ne vraca ocekivani format PravnoLice{pib='...', naziv='...', email='...', telefon='...'}");
    }
}
