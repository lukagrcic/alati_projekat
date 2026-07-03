package rs.fon.laptopprodaja.entity;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.*;

class GradTest {


    @Test
    @DisplayName("Konstruktor bez argumenata treba da ostavi sva polja na null")
    void konstruktorBezArgumenataOstavljaPoljaNaNull() {
        Grad noviGrad = new Grad();
        assertAll("Sva polja praznog konstruktora moraju biti null",
                () -> assertNull(noviGrad.getIdGrad(), "idGrad treba da bude null nakon praznog konstruktora"),
                () -> assertNull(noviGrad.getNaziv(), "naziv treba da bude null nakon praznog konstruktora"),
                () -> assertNull(noviGrad.getPostanskiBroj(), "postanskiBroj treba da bude null nakon praznog konstruktora")
        );
    }

    @Test
    @DisplayName("Konstruktor sa argumentima treba da postavi naziv i postanski broj")
    void konstruktorSaArgumentimaPostavljaPolja() {
        Grad noviGrad = new Grad("Beograd", "11000");
        assertEquals("Beograd", noviGrad.getNaziv(), "Naziv nije postavljen preko konstruktora sa argumentima");
        assertEquals("11000", noviGrad.getPostanskiBroj(), "Postanski broj nije postavljen preko konstruktora sa argumentima");
    }

    @Test
    @DisplayName("Konstruktor sa argumentima ne postavlja idGrad (ostaje null do perzistencije)")
    void konstruktorSaArgumentimaOstavljaIdGradNaNull() {
        Grad noviGrad = new Grad("Novi Sad", "21000");
        assertNull(noviGrad.getIdGrad(), "idGrad ne sme biti postavljen konstruktorom, dodeljuje ga baza");
    }



    private Grad grad;

    @BeforeEach
    void setUp() {
        grad = new Grad("Beograd", "11000");
    }

    @AfterEach
    void tearDown() {
        grad = null;
    }

    @Test
    @DisplayName("setIdGrad i getIdGrad treba da rade sa validnom vrednoscu")
    void setIdGradValidnaVrednost() {
        grad.setIdGrad(5L);
        assertEquals(5L, grad.getIdGrad(), "getIdGrad ne vraca vrednost postavljenu preko setIdGrad");
    }

    @Test
    @DisplayName("setIdGrad prihvata null jer nema validaciju (dodeljuje ga baza pre perzistencije)")
    void setIdGradPrihvataNull() {
        grad.setIdGrad(null);
        assertNull(grad.getIdGrad(), "idGrad treba da moze biti null pre nego sto je entitet sacuvan");
    }

    @Test
    @DisplayName("setNaziv treba da postavi validan naziv duzine bar 2 znaka")
    void setNazivValidnaVrednost() {
        grad.setNaziv("Nis");
        assertEquals("Nis", grad.getNaziv(), "getNaziv ne vraca vrednost postavljenu preko setNaziv");
    }

    @Test
    @DisplayName("setNaziv sa null vrednoscu baca NullPointerException")
    void setNazivNullBacaNullPointerException() {
        NullPointerException ex = assertThrows(NullPointerException.class,
                () -> grad.setNaziv(null),
                "Ocekivan je NullPointerException kada je naziv null");
        assertEquals("Naziv ne sme biti null", ex.getMessage());
    }

    @Test
    @DisplayName("setNaziv sa manje od 2 znaka baca IllegalArgumentException")
    void setNazivKratakNazivBacaIllegalArgumentException() {
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
                () -> grad.setNaziv("A"),
                "Ocekivan je IllegalArgumentException kada naziv ima manje od 2 znaka");
        assertEquals("Naziv mora imati bar 2 znaka", ex.getMessage());
    }

    @ParameterizedTest(name = "postanskiBroj=\"{0}\" treba da bude prihvacen")
    @DisplayName("setPostanskiBroj prihvata razlicite validne petocifrene brojeve")
    @CsvSource({
            "11000",
            "21000",
            "18000"
    })
    void setPostanskiBrojValidneVrednosti(String postanskiBroj) {
        grad.setPostanskiBroj(postanskiBroj);
        assertEquals(postanskiBroj, grad.getPostanskiBroj(),
                "getPostanskiBroj ne vraca vrednost postavljenu preko setPostanskiBroj za " + postanskiBroj);
    }

    @Test
    @DisplayName("setPostanskiBroj sa null vrednoscu baca NullPointerException")
    void setPostanskiBrojNullBacaNullPointerException() {
        NullPointerException ex = assertThrows(NullPointerException.class,
                () -> grad.setPostanskiBroj(null),
                "Ocekivan je NullPointerException kada je postanski broj null");
        assertEquals("Postanski broj ne sme biti null", ex.getMessage());
    }

    @ParameterizedTest(name = "postanskiBroj=\"{0}\" treba da baci IllegalArgumentException")
    @DisplayName("setPostanskiBroj odbacuje formate koji nisu tacno 5 cifara")
    @CsvSource({
            "1100",
            "110000",
            "1100A",
            "abcde"
    })
    void setPostanskiBrojNevalidanFormatBacaIllegalArgumentException(String nevalidan) {
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
                () -> grad.setPostanskiBroj(nevalidan),
                "Ocekivan je IllegalArgumentException za nevalidan postanski broj: " + nevalidan);
        assertEquals("Postanski broj mora imati tacno 5 cifara", ex.getMessage());
    }

    @ParameterizedTest(name = "naziv1=\"{0}\", naziv2=\"{1}\" -> jednako={2}")
    @DisplayName("equals poredi gradove iskljucivo po nazivu")
    @CsvSource({
            "Beograd, Beograd, true",
            "Beograd, beograd, false",
            "Beograd, Novi Sad, false",
            "Nis, Nis, true"
    })
    void equalsUporedjujeSamoPoNazivu(String naziv1, String naziv2, boolean ocekivanoJednako) {
        Grad prvi = new Grad(naziv1, "11000");
        Grad drugi = new Grad(naziv2, "21000");
        assertEquals(ocekivanoJednako, prvi.equals(drugi),
                "equals nije vratio ocekivanu vrednost za naziv1=" + naziv1 + ", naziv2=" + naziv2);
    }

    @Test
    @DisplayName("equals vraca false kada se poredi sa null")
    void equalsSaNullVracaFalse() {
        assertNotEquals(null, grad, "equals treba da vrati false kada se poredi sa null");
    }

    @Test
    @DisplayName("equals vraca false kada se poredi sa objektom druge klase")
    void equalsSaDrugomKlasomVracaFalse() {
        assertNotEquals(grad, "Beograd", "equals treba da vrati false za objekat druge klase");
    }

    @Test
    @DisplayName("equals vraca true za istu referencu (refleksivnost)")
    void equalsIstaReferencaVracaTrue() {
        assertEquals(grad, grad, "equals treba da vrati true kada se objekat poredi sam sa sobom");
    }

    @Test
    @DisplayName("hashCode je jednak za gradove sa istim nazivom")
    void hashCodeJednakZaIstiNaziv() {
        Grad drugiGrad = new Grad("Beograd", "99999");
        assertEquals(grad.hashCode(), drugiGrad.hashCode(),
                "hashCode treba da bude isti za gradove sa istim nazivom, bez obzira na postanski broj");
    }

    @Test
    @DisplayName("hashCode se razlikuje za gradove sa razlicitim nazivom")
    void hashCodeRazlicitZaRazlicitNaziv() {
        Grad drugiGrad = new Grad("Novi Sad", "11000");
        assertNotEquals(grad.hashCode(), drugiGrad.hashCode(),
                "hashCode ne bi trebalo da se poklapa za gradove sa razlicitim nazivom");
    }

    @Test
    @DisplayName("toString vraca ocekivani format sa nazivom grada")
    void toStringVracaOcekivaniFormat() {
        assertEquals("Grad{naziv='Beograd'}", grad.toString(),
                "toString ne vraca ocekivani format Grad{naziv='...'}");
    }
}
