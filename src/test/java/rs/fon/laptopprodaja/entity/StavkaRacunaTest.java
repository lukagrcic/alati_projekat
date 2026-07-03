package rs.fon.laptopprodaja.entity;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class StavkaRacunaTest {


    private Laptop laptop;

    @BeforeEach
    void setUpLaptop() {
        laptop = new Laptop("Dell XPS", new BigDecimal("1000.00"), "16GB RAM");
    }

    @Test
    @DisplayName("Konstruktor bez argumenata treba da ostavi sva polja na null/podrazumevanu vrednost")
    void konstruktorBezArgumenataOstavljaPoljaNaNull() {
        StavkaRacuna novaStavka = new StavkaRacuna();
        assertAll("Sva polja praznog konstruktora moraju biti null",
                () -> assertNull(novaStavka.getId(), "id treba da bude null nakon praznog konstruktora"),
                () -> assertNull(novaStavka.getProdajnaCena(), "prodajnaCena treba da bude null nakon praznog konstruktora"),
                () -> assertNull(novaStavka.getIznos(), "iznos treba da bude null nakon praznog konstruktora"),
                () -> assertNull(novaStavka.getLaptop(), "laptop treba da bude null nakon praznog konstruktora"),
                () -> assertNull(novaStavka.getRacun(), "racun treba da bude null nakon praznog konstruktora")
        );
    }

    @Test
    @DisplayName("Konstruktor sa argumentima treba da postavi rb, prodajnu cenu, kolicinu i laptop")
    void konstruktorSaArgumentimaPostavljaPolja() {
        StavkaRacuna novaStavka = new StavkaRacuna(1, new BigDecimal("1000.00"), 3, laptop);
        assertAll("Sva polja konstruktora sa argumentima moraju biti postavljena",
                () -> assertEquals(1, novaStavka.getRb(), "Rb nije postavljen preko konstruktora sa argumentima"),
                () -> assertEquals(0, new BigDecimal("1000.00").compareTo(novaStavka.getProdajnaCena()),
                        "Prodajna cena nije postavljena preko konstruktora sa argumentima"),
                () -> assertEquals(3, novaStavka.getKolicina(), "Kolicina nije postavljena preko konstruktora sa argumentima"),
                () -> assertEquals(laptop, novaStavka.getLaptop(), "Laptop nije postavljen preko konstruktora sa argumentima")
        );
    }

    @Test
    @DisplayName("Konstruktor sa argumentima automatski izracunava iznos kao prodajnaCena * kolicina")
    void konstruktorSaArgumentimaIzracunavaIznosAutomatski() {
        StavkaRacuna novaStavka = new StavkaRacuna(1, new BigDecimal("1000.00"), 3, laptop);
        assertEquals(0, new BigDecimal("3000.00").compareTo(novaStavka.getIznos()),
                "Iznos nije automatski izracunat kao prodajnaCena * kolicina u konstruktoru");
    }

    @Test
    @DisplayName("Konstruktor sa argumentima ne postavlja id i racun (racun se postavlja preko dodajStavku)")
    void konstruktorSaArgumentimaOstavljaIdIRacunNaNull() {
        StavkaRacuna novaStavka = new StavkaRacuna(1, new BigDecimal("1000.00"), 3, laptop);
        assertAll(
                () -> assertNull(novaStavka.getId(), "id ne sme biti postavljen konstruktorom, dodeljuje ga baza"),
                () -> assertNull(novaStavka.getRacun(), "racun ne sme biti postavljen konstruktorom, postavlja se preko dodajStavku")
        );
    }



    private StavkaRacuna stavkaRacuna;

    @BeforeEach
    void setUp() {
        stavkaRacuna = new StavkaRacuna(1, new BigDecimal("1000.00"), 3, laptop);
    }

    @AfterEach
    void tearDown() {
        stavkaRacuna = null;
    }

    @Test
    @DisplayName("setRb treba da postavi validan rb veci od 0")
    void setRbValidnaVrednost() {
        stavkaRacuna.setRb(5);
        assertEquals(5, stavkaRacuna.getRb(), "getRb ne vraca vrednost postavljenu preko setRb");
    }

    @Test
    @DisplayName("setRb sa vrednoscu tacno 0 baca IllegalArgumentException")
    void setRbNulomBacaIllegalArgumentException() {
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
                () -> stavkaRacuna.setRb(0),
                "Ocekivan je IllegalArgumentException kada je rb jednak 0");
        assertEquals("Rb mora biti veci od 0", ex.getMessage());
    }

    @Test
    @DisplayName("setRb sa negativnom vrednoscu baca IllegalArgumentException")
    void setRbNegativnomVrednoscuBacaIllegalArgumentException() {
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
                () -> stavkaRacuna.setRb(-1),
                "Ocekivan je IllegalArgumentException kada je rb negativan");
        assertEquals("Rb mora biti veci od 0", ex.getMessage());
    }

    @Test
    @DisplayName("setProdajnaCena treba da postavi validnu pozitivnu vrednost")
    void setProdajnaCenaValidnaVrednost() {
        BigDecimal novaCena = new BigDecimal("1500.00");
        stavkaRacuna.setProdajnaCena(novaCena);
        assertEquals(0, novaCena.compareTo(stavkaRacuna.getProdajnaCena()),
                "getProdajnaCena ne vraca vrednost postavljenu preko setProdajnaCena");
    }

    @Test
    @DisplayName("setProdajnaCena sa null vrednoscu baca NullPointerException")
    void setProdajnaCenaNullBacaNullPointerException() {
        NullPointerException ex = assertThrows(NullPointerException.class,
                () -> stavkaRacuna.setProdajnaCena(null),
                "Ocekivan je NullPointerException kada je prodajna cena null");
        assertEquals("Prodajna cena ne sme biti null", ex.getMessage());
    }

    @Test
    @DisplayName("setProdajnaCena sa vrednoscu tacno 0 baca IllegalArgumentException")
    void setProdajnaCenaNulomBacaIllegalArgumentException() {
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
                () -> stavkaRacuna.setProdajnaCena(BigDecimal.ZERO),
                "Ocekivan je IllegalArgumentException kada je prodajna cena jednaka 0");
        assertEquals("Prodajna cena mora biti veca od 0", ex.getMessage());
    }

    @Test
    @DisplayName("setProdajnaCena sa negativnom vrednoscu baca IllegalArgumentException")
    void setProdajnaCenaNegativnomVrednoscuBacaIllegalArgumentException() {
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
                () -> stavkaRacuna.setProdajnaCena(new BigDecimal("-100.00")),
                "Ocekivan je IllegalArgumentException kada je prodajna cena negativna");
        assertEquals("Prodajna cena mora biti veca od 0", ex.getMessage());
    }

    @Test
    @DisplayName("setKolicina treba da postavi validnu kolicinu vecu od 0")
    void setKolicinaValidnaVrednost() {
        stavkaRacuna.setKolicina(7);
        assertEquals(7, stavkaRacuna.getKolicina(), "getKolicina ne vraca vrednost postavljenu preko setKolicina");
    }

    @Test
    @DisplayName("setKolicina sa vrednoscu tacno 0 baca IllegalArgumentException")
    void setKolicinaNulomBacaIllegalArgumentException() {
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
                () -> stavkaRacuna.setKolicina(0),
                "Ocekivan je IllegalArgumentException kada je kolicina jednaka 0");
        assertEquals("Kolicina mora biti veca od 0", ex.getMessage());
    }

    @Test
    @DisplayName("setKolicina sa negativnom vrednoscu baca IllegalArgumentException")
    void setKolicinaNegativnomVrednoscuBacaIllegalArgumentException() {
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
                () -> stavkaRacuna.setKolicina(-2),
                "Ocekivan je IllegalArgumentException kada je kolicina negativna");
        assertEquals("Kolicina mora biti veca od 0", ex.getMessage());
    }

    @Test
    @DisplayName("setIznos treba da postavi validan pozitivan iznos")
    void setIznosValidnaVrednost() {
        BigDecimal noviIznos = new BigDecimal("5000.00");
        stavkaRacuna.setIznos(noviIznos);
        assertEquals(0, noviIznos.compareTo(stavkaRacuna.getIznos()),
                "getIznos ne vraca vrednost postavljenu preko setIznos");
    }

    @Test
    @DisplayName("setIznos sa null vrednoscu baca NullPointerException")
    void setIznosNullBacaNullPointerException() {
        NullPointerException ex = assertThrows(NullPointerException.class,
                () -> stavkaRacuna.setIznos(null),
                "Ocekivan je NullPointerException kada je iznos null");
        assertEquals("Iznos ne sme biti null", ex.getMessage());
    }

    @Test
    @DisplayName("setIznos sa vrednoscu tacno 0 baca IllegalArgumentException")
    void setIznosNulomBacaIllegalArgumentException() {
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
                () -> stavkaRacuna.setIznos(BigDecimal.ZERO),
                "Ocekivan je IllegalArgumentException kada je iznos jednak 0");
        assertEquals("Iznos mora biti veci od 0", ex.getMessage());
    }

    @Test
    @DisplayName("setIznos sa negativnom vrednoscu baca IllegalArgumentException")
    void setIznosNegativnomVrednoscuBacaIllegalArgumentException() {
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
                () -> stavkaRacuna.setIznos(new BigDecimal("-500.00")),
                "Ocekivan je IllegalArgumentException kada je iznos negativan");
        assertEquals("Iznos mora biti veci od 0", ex.getMessage());
    }

    @Test
    @DisplayName("setLaptop treba da postavi validan laptop")
    void setLaptopValidnaVrednost() {
        Laptop drugiLaptop = new Laptop("HP Pavilion", new BigDecimal("800.00"), "8GB RAM");
        stavkaRacuna.setLaptop(drugiLaptop);
        assertEquals(drugiLaptop, stavkaRacuna.getLaptop(), "getLaptop ne vraca vrednost postavljenu preko setLaptop");
    }

    @Test
    @DisplayName("setLaptop sa null vrednoscu baca NullPointerException")
    void setLaptopNullBacaNullPointerException() {
        NullPointerException ex = assertThrows(NullPointerException.class,
                () -> stavkaRacuna.setLaptop(null),
                "Ocekivan je NullPointerException kada je laptop null");
        assertEquals("Laptop ne sme biti null", ex.getMessage());
    }

    @Test
    @DisplayName("setRacun treba da postavi validan racun")
    void setRacunValidnaVrednost() {
        Racun noviRacun = new Racun();
        stavkaRacuna.setRacun(noviRacun);
        assertEquals(noviRacun, stavkaRacuna.getRacun(), "getRacun ne vraca vrednost postavljenu preko setRacun");
    }

    @Test
    @DisplayName("setRacun sa null vrednoscu baca NullPointerException")
    void setRacunNullBacaNullPointerException() {
        NullPointerException ex = assertThrows(NullPointerException.class,
                () -> stavkaRacuna.setRacun(null),
                "Ocekivan je NullPointerException kada je racun null");
        assertEquals("Racun ne sme biti null", ex.getMessage());
    }

    @Test
    @DisplayName("preracunajIznos ponovo izracunava iznos na osnovu novih vrednosti prodajne cene i kolicine")
    void preracunajIznosPreracunavaNaOsnovuNovihVrednosti() {
        stavkaRacuna.setProdajnaCena(new BigDecimal("500.00"));
        stavkaRacuna.setKolicina(2);
        stavkaRacuna.preracunajIznos();
        assertEquals(0, new BigDecimal("1000.00").compareTo(stavkaRacuna.getIznos()),
                "preracunajIznos ne izracunava ispravno iznos na osnovu novih vrednosti prodajne cene i kolicine");
    }

    @ParameterizedTest(name = "rb2={0}, kolicina2={1} -> jednako={2}")
    @DisplayName("equals poredi stavke po rb-u i kolicini (uz istu prodajnu cenu i laptop)")
    @CsvSource({
            "1, 3, true",
            "2, 3, false",
            "1, 5, false"
    })
    void equalsUporedjujeRbIKolicinu(int rb2, int kolicina2, boolean ocekivanoJednako) {
        StavkaRacuna prva = new StavkaRacuna(1, new BigDecimal("1000.00"), 3, laptop);
        StavkaRacuna druga = new StavkaRacuna(rb2, new BigDecimal("1000.00"), kolicina2, laptop);
        assertEquals(ocekivanoJednako, prva.equals(druga),
                "equals nije vratio ocekivanu vrednost za rb2=" + rb2 + ", kolicina2=" + kolicina2);
    }

    @Test
    @DisplayName("equals vraca false kada se stavke razlikuju po laptopu")
    void equalsRazlicitLaptopVracaFalse() {
        Laptop drugiLaptop = new Laptop("HP Pavilion", new BigDecimal("1000.00"), "8GB RAM");
        StavkaRacuna prva = new StavkaRacuna(1, new BigDecimal("1000.00"), 3, laptop);
        StavkaRacuna druga = new StavkaRacuna(1, new BigDecimal("1000.00"), 3, drugiLaptop);
        assertNotEquals(prva, druga, "equals treba da vrati false kada se stavke razlikuju po laptopu");
    }

    @Test
    @DisplayName("equals vraca false kada se stavke razlikuju po prodajnoj ceni")
    void equalsRazlicitProdajnaCenaVracaFalse() {
        StavkaRacuna prva = new StavkaRacuna(1, new BigDecimal("1000.00"), 3, laptop);
        StavkaRacuna druga = new StavkaRacuna(1, new BigDecimal("2000.00"), 3, laptop);
        assertNotEquals(prva, druga, "equals treba da vrati false kada se stavke razlikuju po prodajnoj ceni");
    }

    @Test
    @DisplayName("equals vraca false kada se stavke razlikuju samo po iznosu (nakon rucne izmene preko setIznos)")
    void equalsRazlicitIznosVracaFalse() {
        StavkaRacuna prva = new StavkaRacuna(1, new BigDecimal("1000.00"), 3, laptop);
        StavkaRacuna druga = new StavkaRacuna(1, new BigDecimal("1000.00"), 3, laptop);
        druga.setIznos(new BigDecimal("9999.00"));
        assertNotEquals(prva, druga, "equals treba da vrati false kada se stavke razlikuju po iznosu");
    }

    @Test
    @DisplayName("equals vraca false kada se poredi sa null")
    void equalsSaNullVracaFalse() {
        assertNotEquals(null, stavkaRacuna, "equals treba da vrati false kada se poredi sa null");
    }

    @Test
    @DisplayName("equals vraca false kada se poredi sa objektom druge klase")
    void equalsSaDrugomKlasomVracaFalse() {
        assertNotEquals(stavkaRacuna, "StavkaRacuna", "equals treba da vrati false za objekat druge klase");
    }

    @Test
    @DisplayName("equals vraca true za istu referencu (refleksivnost)")
    void equalsIstaReferencaVracaTrue() {
        assertEquals(stavkaRacuna, stavkaRacuna, "equals treba da vrati true kada se objekat poredi sam sa sobom");
    }

    @Test
    @DisplayName("hashCode je jednak za stavke sa istim rb, laptopom, kolicinom, prodajnom cenom i iznosom")
    void hashCodeJednakZaIstaPolja() {
        StavkaRacuna drugaStavka = new StavkaRacuna(1, new BigDecimal("1000.00"), 3, laptop);
        assertEquals(stavkaRacuna.hashCode(), drugaStavka.hashCode(),
                "hashCode treba da bude isti za stavke sa istim rb, laptopom, kolicinom, prodajnom cenom i iznosom");
    }

    @Test
    @DisplayName("hashCode se razlikuje za stavke sa razlicitim rb")
    void hashCodeRazlicitZaRazlicitRb() {
        StavkaRacuna drugaStavka = new StavkaRacuna(2, new BigDecimal("1000.00"), 3, laptop);
        assertNotEquals(stavkaRacuna.hashCode(), drugaStavka.hashCode(),
                "hashCode ne bi trebalo da se poklapa za stavke sa razlicitim rb");
    }

    @Test
    @DisplayName("hashCode se razlikuje za stavke sa razlicitim laptopom")
    void hashCodeRazlicitZaRazlicitLaptop() {
        Laptop drugiLaptop = new Laptop("HP Pavilion", new BigDecimal("1000.00"), "8GB RAM");
        StavkaRacuna drugaStavka = new StavkaRacuna(1, new BigDecimal("1000.00"), 3, drugiLaptop);
        assertNotEquals(stavkaRacuna.hashCode(), drugaStavka.hashCode(),
                "hashCode ne bi trebalo da se poklapa za stavke sa razlicitim laptopom");
    }

    @Test
    @DisplayName("hashCode se razlikuje za stavke sa razlicitom kolicinom")
    void hashCodeRazlicitZaRazlicituKolicinu() {
        StavkaRacuna drugaStavka = new StavkaRacuna(1, new BigDecimal("1000.00"), 5, laptop);
        assertNotEquals(stavkaRacuna.hashCode(), drugaStavka.hashCode(),
                "hashCode ne bi trebalo da se poklapa za stavke sa razlicitom kolicinom");
    }

    @Test
    @DisplayName("hashCode se razlikuje za stavke sa razlicitom prodajnom cenom")
    void hashCodeRazlicitZaRazlicituProdajnuCenu() {
        StavkaRacuna drugaStavka = new StavkaRacuna(1, new BigDecimal("2000.00"), 3, laptop);
        assertNotEquals(stavkaRacuna.hashCode(), drugaStavka.hashCode(),
                "hashCode ne bi trebalo da se poklapa za stavke sa razlicitom prodajnom cenom");
    }

    @Test
    @DisplayName("hashCode se razlikuje za stavke sa razlicitim iznosom")
    void hashCodeRazlicitZaRazlicitIznos() {
        StavkaRacuna drugaStavka = new StavkaRacuna(1, new BigDecimal("1000.00"), 3, laptop);
        drugaStavka.setIznos(new BigDecimal("9999.00"));
        assertNotEquals(stavkaRacuna.hashCode(), drugaStavka.hashCode(),
                "hashCode ne bi trebalo da se poklapa za stavke sa razlicitim iznosom");
    }

    @Test
    @DisplayName("toString vraca ocekivani format sa rb, laptopom, kolicinom, prodajnom cenom i iznosom")
    void toStringVracaOcekivaniFormat() {
        assertEquals("StavkaRacuna{rb=1, laptop=Laptop{naziv='Dell XPS', cena=1000.00}, kolicina=3, prodajnaCena=1000.00, iznos=3000.00}",
                stavkaRacuna.toString(),
                "toString ne vraca ocekivani format StavkaRacuna{rb=..., laptop=..., kolicina=..., prodajnaCena=..., iznos=...}");
    }
}
