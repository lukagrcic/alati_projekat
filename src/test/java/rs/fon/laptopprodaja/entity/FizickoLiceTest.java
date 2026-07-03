package rs.fon.laptopprodaja.entity;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.*;

class FizickoLiceTest extends KupacTest {

    @Override
    protected Kupac getInstance() {
        return new FizickoLice("test@test.com", "0601234567", new Grad("Beograd", "11000"),
                "Petar", "Petrovic", "0101990123456");
    }


    @Test
    @DisplayName("Konstruktor sa argumentima treba da postavi sva polja, ukljucujuci nasledjena, i getTip vraca 'fizicko'")
    void konstruktorSaArgumentimaPostavljaPolja() {
        Grad grad = new Grad("Beograd", "11000");
        FizickoLice novoFizickoLice = new FizickoLice("test@test.com", "0601234567", grad,
                "Petar", "Petrovic", "0101990123456");
        assertAll("Sva polja konstruktora sa argumentima moraju biti postavljena",
                () -> assertEquals("test@test.com", novoFizickoLice.getEmail(), "Email nije postavljen preko konstruktora"),
                () -> assertEquals("0601234567", novoFizickoLice.getTelefon(), "Telefon nije postavljen preko konstruktora"),
                () -> assertEquals(grad, novoFizickoLice.getGrad(), "Grad nije postavljen preko konstruktora"),
                () -> assertEquals("Petar", novoFizickoLice.getIme(), "Ime nije postavljeno preko konstruktora"),
                () -> assertEquals("Petrovic", novoFizickoLice.getPrezime(), "Prezime nije postavljeno preko konstruktora"),
                () -> assertEquals("0101990123456", novoFizickoLice.getJmbg(), "JMBG nije postavljen preko konstruktora"),
                () -> assertEquals("fizicko", novoFizickoLice.getTip(), "getTip ne vraca ocekivanu vrednost 'fizicko'")
        );
    }

    @Test
    @DisplayName("Konstruktor sa argumentima ne postavlja idKupac (ostaje null do perzistencije)")
    void konstruktorSaArgumentimaOstavljaIdKupacNaNull() {
        FizickoLice novoFizickoLice = new FizickoLice("test@test.com", "0601234567", new Grad("Beograd", "11000"),
                "Petar", "Petrovic", "0101990123456");
        assertNull(novoFizickoLice.getIdKupac(), "idKupac ne sme biti postavljen konstruktorom, dodeljuje ga baza");
    }



    private FizickoLice fizickoLice;

    @BeforeEach
    void setUpFizickoLice() {
        fizickoLice = (FizickoLice) kupac;
    }

    @Test
    @DisplayName("setIme treba da postavi validno ime duzine bar 2 znaka")
    void setImeValidnaVrednost() {
        fizickoLice.setIme("Marko");
        assertEquals("Marko", fizickoLice.getIme(), "getIme ne vraca vrednost postavljenu preko setIme");
    }

    @Test
    @DisplayName("setIme sa null vrednoscu baca NullPointerException")
    void setImeNullBacaNullPointerException() {
        NullPointerException ex = assertThrows(NullPointerException.class,
                () -> fizickoLice.setIme(null),
                "Ocekivan je NullPointerException kada je ime null");
        assertEquals("Ime ne sme biti null", ex.getMessage());
    }

    @Test
    @DisplayName("setIme sa manje od 2 znaka baca IllegalArgumentException")
    void setImeKratkoImeBacaIllegalArgumentException() {
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
                () -> fizickoLice.setIme("A"),
                "Ocekivan je IllegalArgumentException kada ime ima manje od 2 znaka");
        assertEquals("Ime mora imati bar 2 znaka", ex.getMessage());
    }

    @Test
    @DisplayName("setPrezime treba da postavi validno prezime duzine bar 2 znaka")
    void setPrezimeValidnaVrednost() {
        fizickoLice.setPrezime("Markovic");
        assertEquals("Markovic", fizickoLice.getPrezime(), "getPrezime ne vraca vrednost postavljenu preko setPrezime");
    }

    @Test
    @DisplayName("setPrezime sa null vrednoscu baca NullPointerException")
    void setPrezimeNullBacaNullPointerException() {
        NullPointerException ex = assertThrows(NullPointerException.class,
                () -> fizickoLice.setPrezime(null),
                "Ocekivan je NullPointerException kada je prezime null");
        assertEquals("Prezime ne sme biti null", ex.getMessage());
    }

    @Test
    @DisplayName("setPrezime sa manje od 2 znaka baca IllegalArgumentException")
    void setPrezimeKratkoPrezimeBacaIllegalArgumentException() {
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
                () -> fizickoLice.setPrezime("A"),
                "Ocekivan je IllegalArgumentException kada prezime ima manje od 2 znaka");
        assertEquals("Prezime mora imati bar 2 znaka", ex.getMessage());
    }

    @Test
    @DisplayName("setJmbg treba da postavi validan 13-cifreni jmbg")
    void setJmbgValidnaVrednost() {
        fizickoLice.setJmbg("1234567890123");
        assertEquals("1234567890123", fizickoLice.getJmbg(), "getJmbg ne vraca vrednost postavljenu preko setJmbg");
    }

    @Test
    @DisplayName("setJmbg sa null vrednoscu baca NullPointerException")
    void setJmbgNullBacaNullPointerException() {
        NullPointerException ex = assertThrows(NullPointerException.class,
                () -> fizickoLice.setJmbg(null),
                "Ocekivan je NullPointerException kada je jmbg null");
        assertEquals("JMBG ne sme biti null", ex.getMessage());
    }

    @ParameterizedTest(name = "jmbg=\"{0}\" treba da baci IllegalArgumentException")
    @DisplayName("setJmbg odbacuje vrednosti koje nemaju tacno 13 cifara")
    @CsvSource({
            "123456789012",
            "12345678901234"
    })
    void setJmbgNevalidnaDuzinaBacaIllegalArgumentException(String nevalidanJmbg) {
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
                () -> fizickoLice.setJmbg(nevalidanJmbg),
                "Ocekivan je IllegalArgumentException za jmbg koji nema tacno 13 cifara: " + nevalidanJmbg);
        assertEquals("JMBG mora imati tacno 13 cifara", ex.getMessage());
    }

    @ParameterizedTest(name = "ime2=\"{0}\", prezime2=\"{1}\", jmbg2=\"{2}\" -> jednako={3}")
    @DisplayName("equals poredi FizickoLice po jmbg-u, imenu i prezimenu (email i telefon fiksni, grad se ne uzima u obzir)")
    @CsvSource({
            "Petar, Petrovic, 0101990123456, true",
            "Marko, Petrovic, 0101990123456, false",
            "Petar, Markovic, 0101990123456, false",
            "Petar, Petrovic, 9999999999999, false"
    })
    void equalsUporedjujeImePrezimeJmbg(String ime2, String prezime2, String jmbg2, boolean ocekivanoJednako) {
        FizickoLice prvo = new FizickoLice("test@test.com", "0601234567", new Grad("Beograd", "11000"),
                "Petar", "Petrovic", "0101990123456");
        FizickoLice drugo = new FizickoLice("test@test.com", "0601234567", new Grad("Novi Sad", "21000"),
                ime2, prezime2, jmbg2);
        assertEquals(ocekivanoJednako, prvo.equals(drugo),
                "equals nije vratio ocekivanu vrednost za ime2=" + ime2 + ", prezime2=" + prezime2 + ", jmbg2=" + jmbg2);
    }

    @Test
    @DisplayName("equals vraca false kada se FizickoLice objekti razlikuju po email-u")
    void equalsRazlicitEmailVracaFalse() {
        FizickoLice prvo = new FizickoLice("prvi@test.com", "0601234567", new Grad("Beograd", "11000"),
                "Petar", "Petrovic", "0101990123456");
        FizickoLice drugo = new FizickoLice("drugi@test.com", "0601234567", new Grad("Beograd", "11000"),
                "Petar", "Petrovic", "0101990123456");
        assertNotEquals(prvo, drugo, "equals treba da vrati false kada se FizickoLice objekti razlikuju po email-u");
    }

    @Test
    @DisplayName("equals vraca false kada se FizickoLice objekti razlikuju po telefonu")
    void equalsRazlicitTelefonVracaFalse() {
        FizickoLice prvo = new FizickoLice("test@test.com", "0601111111", new Grad("Beograd", "11000"),
                "Petar", "Petrovic", "0101990123456");
        FizickoLice drugo = new FizickoLice("test@test.com", "0602222222", new Grad("Beograd", "11000"),
                "Petar", "Petrovic", "0101990123456");
        assertNotEquals(prvo, drugo, "equals treba da vrati false kada se FizickoLice objekti razlikuju po telefonu");
    }

    @Test
    @DisplayName("equals vraca false kada se poredi sa null")
    void equalsSaNullVracaFalse() {
        assertNotEquals(null, fizickoLice, "equals treba da vrati false kada se poredi sa null");
    }

    @Test
    @DisplayName("equals vraca false kada se poredi sa objektom druge klase")
    void equalsSaDrugomKlasomVracaFalse() {
        assertNotEquals(fizickoLice, "Petar Petrovic", "equals treba da vrati false za objekat druge klase");
    }

    @Test
    @DisplayName("equals vraca true za istu referencu (refleksivnost)")
    void equalsIstaReferencaVracaTrue() {
        assertEquals(fizickoLice, fizickoLice, "equals treba da vrati true kada se objekat poredi sam sa sobom");
    }

    @Test
    @DisplayName("hashCode je jednak za FizickoLice objekte sa istim jmbg-om, imenom, prezimenom, email-om i telefonom")
    void hashCodeJednakZaIstaPolja() {
        FizickoLice drugoFizickoLice = new FizickoLice("test@test.com", "0601234567", new Grad("Novi Sad", "21000"),
                "Petar", "Petrovic", "0101990123456");
        assertEquals(fizickoLice.hashCode(), drugoFizickoLice.hashCode(),
                "hashCode treba da bude isti za FizickoLice objekte sa istim relevantnim poljima, bez obzira na grad");
    }

    @Test
    @DisplayName("hashCode se razlikuje za FizickoLice objekte sa razlicitim jmbg-om")
    void hashCodeRazlicitZaRazlicitJmbg() {
        FizickoLice drugoFizickoLice = new FizickoLice("test@test.com", "0601234567", new Grad("Beograd", "11000"),
                "Petar", "Petrovic", "9999999999999");
        assertNotEquals(fizickoLice.hashCode(), drugoFizickoLice.hashCode(),
                "hashCode ne bi trebalo da se poklapa za FizickoLice objekte sa razlicitim jmbg-om");
    }

    @Test
    @DisplayName("hashCode se razlikuje za FizickoLice objekte sa razlicitim imenom")
    void hashCodeRazlicitZaRazlicitoIme() {
        FizickoLice drugoFizickoLice = new FizickoLice("test@test.com", "0601234567", new Grad("Beograd", "11000"),
                "Marko", "Petrovic", "0101990123456");
        assertNotEquals(fizickoLice.hashCode(), drugoFizickoLice.hashCode(),
                "hashCode ne bi trebalo da se poklapa za FizickoLice objekte sa razlicitim imenom");
    }

    @Test
    @DisplayName("hashCode se razlikuje za FizickoLice objekte sa razlicitim prezimenom")
    void hashCodeRazlicitZaRazlicitoPrezime() {
        FizickoLice drugoFizickoLice = new FizickoLice("test@test.com", "0601234567", new Grad("Beograd", "11000"),
                "Petar", "Markovic", "0101990123456");
        assertNotEquals(fizickoLice.hashCode(), drugoFizickoLice.hashCode(),
                "hashCode ne bi trebalo da se poklapa za FizickoLice objekte sa razlicitim prezimenom");
    }

    @Test
    @DisplayName("hashCode se razlikuje za FizickoLice objekte sa razlicitim email-om")
    void hashCodeRazlicitZaRazlicitEmail() {
        FizickoLice drugoFizickoLice = new FizickoLice("drugi@test.com", "0601234567", new Grad("Beograd", "11000"),
                "Petar", "Petrovic", "0101990123456");
        assertNotEquals(fizickoLice.hashCode(), drugoFizickoLice.hashCode(),
                "hashCode ne bi trebalo da se poklapa za FizickoLice objekte sa razlicitim email-om");
    }

    @Test
    @DisplayName("hashCode se razlikuje za FizickoLice objekte sa razlicitim telefonom")
    void hashCodeRazlicitZaRazlicitTelefon() {
        FizickoLice drugoFizickoLice = new FizickoLice("test@test.com", "0602222222", new Grad("Beograd", "11000"),
                "Petar", "Petrovic", "0101990123456");
        assertNotEquals(fizickoLice.hashCode(), drugoFizickoLice.hashCode(),
                "hashCode ne bi trebalo da se poklapa za FizickoLice objekte sa razlicitim telefonom");
    }

    @Test
    @DisplayName("toString vraca ocekivani format sa jmbg-om, imenom, prezimenom, email-om i telefonom")
    void toStringVracaOcekivaniFormat() {
        assertEquals("FizickoLice{jmbg='0101990123456', ime='Petar', prezime='Petrovic', email='test@test.com', telefon='0601234567'}",
                fizickoLice.toString(),
                "toString ne vraca ocekivani format FizickoLice{jmbg='...', ime='...', prezime='...', email='...', telefon='...'}");
    }
}
