package rs.fon.laptopprodaja.entity;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ProdavacTest {


    @Test
    @DisplayName("Konstruktor bez argumenata treba da ostavi sva polja na null, a strucneSpreme na praznu listu")
    void konstruktorBezArgumenataOstavljaPoljaNaNull() {
        Prodavac noviProdavac = new Prodavac();
        assertAll("Sva polja praznog konstruktora moraju biti null, a strucneSpreme prazna lista",
                () -> assertNull(noviProdavac.getIdProdavac(), "idProdavac treba da bude null nakon praznog konstruktora"),
                () -> assertNull(noviProdavac.getIme(), "ime treba da bude null nakon praznog konstruktora"),
                () -> assertNull(noviProdavac.getPrezime(), "prezime treba da bude null nakon praznog konstruktora"),
                () -> assertNull(noviProdavac.getKorisnickoIme(), "korisnickoIme treba da bude null nakon praznog konstruktora"),
                () -> assertNull(noviProdavac.getSifra(), "sifra treba da bude null nakon praznog konstruktora"),
                () -> assertNotNull(noviProdavac.getStrucneSpreme(), "strucneSpreme ne sme biti null nakon praznog konstruktora"),
                () -> assertTrue(noviProdavac.getStrucneSpreme().isEmpty(), "strucneSpreme treba da bude prazna lista nakon praznog konstruktora")
        );
    }

    @Test
    @DisplayName("Konstruktor sa argumentima treba da postavi ime, prezime, korisnicko ime i sifru")
    void konstruktorSaArgumentimaPostavljaPolja() {
        Prodavac noviProdavac = new Prodavac("Petar", "Petrovic", "ppetrovic", "sifra123");
        assertAll("Sva polja konstruktora sa argumentima moraju biti postavljena",
                () -> assertEquals("Petar", noviProdavac.getIme(), "Ime nije postavljeno preko konstruktora sa argumentima"),
                () -> assertEquals("Petrovic", noviProdavac.getPrezime(), "Prezime nije postavljeno preko konstruktora sa argumentima"),
                () -> assertEquals("ppetrovic", noviProdavac.getKorisnickoIme(), "Korisnicko ime nije postavljeno preko konstruktora sa argumentima"),
                () -> assertEquals("sifra123", noviProdavac.getSifra(), "Sifra nije postavljena preko konstruktora sa argumentima")
        );
    }

    @Test
    @DisplayName("Konstruktor sa argumentima ne postavlja idProdavac (ostaje null do perzistencije)")
    void konstruktorSaArgumentimaOstavljaIdProdavacNaNull() {
        Prodavac noviProdavac = new Prodavac("Petar", "Petrovic", "ppetrovic", "sifra123");
        assertNull(noviProdavac.getIdProdavac(), "idProdavac ne sme biti postavljen konstruktorom, dodeljuje ga baza");
    }



    private Prodavac prodavac;

    @BeforeEach
    void setUp() {
        prodavac = new Prodavac("Petar", "Petrovic", "ppetrovic", "sifra123");
    }

    @AfterEach
    void tearDown() {
        prodavac = null;
    }

    @Test
    @DisplayName("setIme treba da postavi validno ime duzine bar 2 znaka")
    void setImeValidnaVrednost() {
        prodavac.setIme("Marko");
        assertEquals("Marko", prodavac.getIme(), "getIme ne vraca vrednost postavljenu preko setIme");
    }

    @Test
    @DisplayName("setIme sa null vrednoscu baca NullPointerException")
    void setImeNullBacaNullPointerException() {
        NullPointerException ex = assertThrows(NullPointerException.class,
                () -> prodavac.setIme(null),
                "Ocekivan je NullPointerException kada je ime null");
        assertEquals("Ime ne sme biti null", ex.getMessage());
    }

    @Test
    @DisplayName("setIme sa manje od 2 znaka baca IllegalArgumentException")
    void setImeKratkoImeBacaIllegalArgumentException() {
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
                () -> prodavac.setIme("A"),
                "Ocekivan je IllegalArgumentException kada ime ima manje od 2 znaka");
        assertEquals("Ime mora imati bar 2 znaka", ex.getMessage());
    }

    @Test
    @DisplayName("setPrezime treba da postavi validno prezime duzine bar 2 znaka")
    void setPrezimeValidnaVrednost() {
        prodavac.setPrezime("Markovic");
        assertEquals("Markovic", prodavac.getPrezime(), "getPrezime ne vraca vrednost postavljenu preko setPrezime");
    }

    @Test
    @DisplayName("setPrezime sa null vrednoscu baca NullPointerException")
    void setPrezimeNullBacaNullPointerException() {
        NullPointerException ex = assertThrows(NullPointerException.class,
                () -> prodavac.setPrezime(null),
                "Ocekivan je NullPointerException kada je prezime null");
        assertEquals("Prezime ne sme biti null", ex.getMessage());
    }

    @Test
    @DisplayName("setPrezime sa manje od 2 znaka baca IllegalArgumentException")
    void setPrezimeKratkoPrezimeBacaIllegalArgumentException() {
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
                () -> prodavac.setPrezime("A"),
                "Ocekivan je IllegalArgumentException kada prezime ima manje od 2 znaka");
        assertEquals("Prezime mora imati bar 2 znaka", ex.getMessage());
    }

    @Test
    @DisplayName("setKorisnickoIme treba da postavi validno korisnicko ime duzine bar 3 znaka")
    void setKorisnickoImeValidnaVrednost() {
        prodavac.setKorisnickoIme("mmarkovic");
        assertEquals("mmarkovic", prodavac.getKorisnickoIme(), "getKorisnickoIme ne vraca vrednost postavljenu preko setKorisnickoIme");
    }

    @Test
    @DisplayName("setKorisnickoIme sa null vrednoscu baca NullPointerException")
    void setKorisnickoImeNullBacaNullPointerException() {
        NullPointerException ex = assertThrows(NullPointerException.class,
                () -> prodavac.setKorisnickoIme(null),
                "Ocekivan je NullPointerException kada je korisnicko ime null");
        assertEquals("Korisnicko ime ne sme biti null", ex.getMessage());
    }

    @Test
    @DisplayName("setKorisnickoIme sa manje od 3 znaka baca IllegalArgumentException")
    void setKorisnickoImeKratkoBacaIllegalArgumentException() {
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
                () -> prodavac.setKorisnickoIme("pp"),
                "Ocekivan je IllegalArgumentException kada korisnicko ime ima manje od 3 znaka");
        assertEquals("Korisnicko ime mora imati bar 3 znaka", ex.getMessage());
    }

    @Test
    @DisplayName("setSifra treba da postavi validnu sifru duzine bar 4 znaka")
    void setSifraValidnaVrednost() {
        prodavac.setSifra("novaSifra1");
        assertEquals("novaSifra1", prodavac.getSifra(), "getSifra ne vraca vrednost postavljenu preko setSifra");
    }

    @Test
    @DisplayName("setSifra sa null vrednoscu baca NullPointerException")
    void setSifraNullBacaNullPointerException() {
        NullPointerException ex = assertThrows(NullPointerException.class,
                () -> prodavac.setSifra(null),
                "Ocekivan je NullPointerException kada je sifra null");
        assertEquals("Sifra ne sme biti null", ex.getMessage());
    }

    @Test
    @DisplayName("setSifra sa manje od 4 znaka baca IllegalArgumentException")
    void setSifraKratkaBacaIllegalArgumentException() {
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
                () -> prodavac.setSifra("abc"),
                "Ocekivan je IllegalArgumentException kada sifra ima manje od 4 znaka");
        assertEquals("Sifra mora imati bar 4 znaka", ex.getMessage());
    }

    @Test
    @DisplayName("setStrucneSpreme treba da postavi praznu listu")
    void setStrucneSpremePraznaLista() {
        List<PrSS> praznaLista = new ArrayList<>();
        prodavac.setStrucneSpreme(praznaLista);
        assertEquals(praznaLista, prodavac.getStrucneSpreme(), "getStrucneSpreme ne vraca praznu listu postavljenu preko setStrucneSpreme");
    }

    @Test
    @DisplayName("setStrucneSpreme treba da postavi popunjenu listu")
    void setStrucneSpremePopunjenaLista() {
        StrSprema strSprema = new StrSprema("Master");
        PrSS prss = new PrSS(prodavac, strSprema, java.time.LocalDate.now());
        List<PrSS> popunjenaLista = new ArrayList<>(List.of(prss));
        prodavac.setStrucneSpreme(popunjenaLista);
        assertEquals(popunjenaLista, prodavac.getStrucneSpreme(), "getStrucneSpreme ne vraca popunjenu listu postavljenu preko setStrucneSpreme");
    }

    @ParameterizedTest(name = "korisnickoIme2=\"{0}\", ime2=\"{1}\", prezime2=\"{2}\" -> jednako={3}")
    @DisplayName("equals poredi prodavce po korisnickom imenu, imenu i prezimenu")
    @CsvSource({
            "ppetrovic, Petar, Petrovic, true",
            "mmarkovic, Petar, Petrovic, false",
            "ppetrovic, Marko, Petrovic, false",
            "ppetrovic, Petar, Markovic, false"
    })
    void equalsUporedjujeKorisnickoImeImePrezime(String korisnickoIme2, String ime2, String prezime2, boolean ocekivanoJednako) {
        Prodavac prvi = new Prodavac("Petar", "Petrovic", "ppetrovic", "sifra123");
        Prodavac drugi = new Prodavac(ime2, prezime2, korisnickoIme2, "drugaSifra");
        assertEquals(ocekivanoJednako, prvi.equals(drugi),
                "equals nije vratio ocekivanu vrednost za korisnickoIme2=" + korisnickoIme2
                        + ", ime2=" + ime2 + ", prezime2=" + prezime2);
    }

    @Test
    @DisplayName("equals vraca false kada se poredi sa null")
    void equalsSaNullVracaFalse() {
        assertNotEquals(null, prodavac, "equals treba da vrati false kada se poredi sa null");
    }

    @Test
    @DisplayName("equals vraca false kada se poredi sa objektom druge klase")
    void equalsSaDrugomKlasomVracaFalse() {
        assertNotEquals(prodavac, "Petar Petrovic", "equals treba da vrati false za objekat druge klase");
    }

    @Test
    @DisplayName("equals vraca true za istu referencu (refleksivnost)")
    void equalsIstaReferencaVracaTrue() {
        assertEquals(prodavac, prodavac, "equals treba da vrati true kada se objekat poredi sam sa sobom");
    }

    @Test
    @DisplayName("hashCode je jednak za prodavce sa istim korisnickim imenom, imenom i prezimenom")
    void hashCodeJednakZaIstaPolja() {
        Prodavac drugiProdavac = new Prodavac("Petar", "Petrovic", "ppetrovic", "razlicitaSifra");
        assertEquals(prodavac.hashCode(), drugiProdavac.hashCode(),
                "hashCode treba da bude isti za prodavce sa istim korisnickim imenom, imenom i prezimenom, bez obzira na sifru");
    }

    @Test
    @DisplayName("hashCode se razlikuje za prodavce sa razlicitim korisnickim imenom")
    void hashCodeRazlicitZaRazlicitoKorisnickoIme() {
        Prodavac drugiProdavac = new Prodavac("Petar", "Petrovic", "mmarkovic", "sifra123");
        assertNotEquals(prodavac.hashCode(), drugiProdavac.hashCode(),
                "hashCode ne bi trebalo da se poklapa za prodavce sa razlicitim korisnickim imenom");
    }

    @Test
    @DisplayName("hashCode se razlikuje za prodavce sa razlicitim imenom")
    void hashCodeRazlicitZaRazlicitoIme() {
        Prodavac drugiProdavac = new Prodavac("Marko", "Petrovic", "ppetrovic", "sifra123");
        assertNotEquals(prodavac.hashCode(), drugiProdavac.hashCode(),
                "hashCode ne bi trebalo da se poklapa za prodavce sa razlicitim imenom");
    }

    @Test
    @DisplayName("hashCode se razlikuje za prodavce sa razlicitim prezimenom")
    void hashCodeRazlicitZaRazlicitoPrezime() {
        Prodavac drugiProdavac = new Prodavac("Petar", "Markovic", "ppetrovic", "sifra123");
        assertNotEquals(prodavac.hashCode(), drugiProdavac.hashCode(),
                "hashCode ne bi trebalo da se poklapa za prodavce sa razlicitim prezimenom");
    }

    @Test
    @DisplayName("toString vraca ocekivani format sa korisnickim imenom, imenom i prezimenom")
    void toStringVracaOcekivaniFormat() {
        assertEquals("Prodavac{korisnickoIme='ppetrovic', ime='Petar', prezime='Petrovic'}", prodavac.toString(),
                "toString ne vraca ocekivani format Prodavac{korisnickoIme='...', ime='...', prezime='...'}");
    }
}
