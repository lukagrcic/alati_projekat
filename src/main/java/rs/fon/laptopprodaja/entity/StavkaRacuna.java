package rs.fon.laptopprodaja.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.Objects;

/**
 * Predstavlja jednu stavku racuna, odnosno jedan laptop prodat u okviru racuna.
 *
 * Sadrzi redni broj stavke, prodajnu cenu, kolicinu i ukupan iznos stavke,
 * koji se racuna kao proizvod prodajne cene i kolicine.
 *
 * @author Luka Grcic
 * @version 1.0
 */
@Entity
@Table(name = "stavka_racuna")
public class StavkaRacuna {

    /** Identifikator stavke racuna u bazi podataka. */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /** Redni broj stavke u okviru racuna. */
    @Column(nullable = false)
    private int rb;

    /** Prodajna cena laptopa u okviru stavke kao BigDecimal. */
    @Column(nullable = false)
    private BigDecimal prodajnaCena;

    /** Kolicina prodatih laptopova u okviru stavke. */
    @Column(nullable = false)
    private int kolicina;

    /** Ukupan iznos stavke kao BigDecimal. */
    @Column(nullable = false)
    private BigDecimal iznos;

    /** Laptop na koji se stavka odnosi. */
    @ManyToOne(optional = false)
    @JoinColumn(name = "id_laptop")
    private Laptop laptop;

    /** Racun kome stavka pripada. */
    @ManyToOne(optional = false)
    @JoinColumn(name = "id_racun")
    @JsonBackReference("racun-stavke")
    private Racun racun;

    /**
     * Prazan konstruktor potreban za JPA.
     */
    public StavkaRacuna() {
    }

    /**
     * Kreira novu stavku racuna sa zadatim rednim brojem, prodajnom cenom,
     * kolicinom i laptopom. Iznos stavke se odmah racuna kao proizvod
     * prodajne cene i kolicine.
     *
     * @param rb Redni broj stavke.
     * @param prodajnaCena Prodajna cena laptopa.
     * @param kolicina Kolicina prodatih laptopova.
     * @param laptop Laptop na koji se stavka odnosi.
     */
    public StavkaRacuna(int rb, BigDecimal prodajnaCena, int kolicina, Laptop laptop) {
        this.rb = rb;
        this.prodajnaCena = prodajnaCena;
        this.kolicina = kolicina;
        this.laptop = laptop;
        this.iznos = prodajnaCena.multiply(BigDecimal.valueOf(kolicina));
    }

    /**
     * Vraca identifikator stavke racuna.
     *
     * @return Identifikator stavke racuna.
     */
    public Long getId() {
        return id;
    }

    /**
     * Vraca redni broj stavke.
     *
     * @return Redni broj stavke.
     */
    public int getRb() {
        return rb;
    }

    /**
     * Vraca prodajnu cenu laptopa u okviru stavke.
     *
     * @return Prodajna cena laptopa.
     */
    public BigDecimal getProdajnaCena() {
        return prodajnaCena;
    }

    /**
     * Vraca kolicinu prodatih laptopova u okviru stavke.
     *
     * @return Kolicina prodatih laptopova.
     */
    public int getKolicina() {
        return kolicina;
    }

    /**
     * Vraca ukupan iznos stavke.
     *
     * @return Iznos stavke.
     */
    public BigDecimal getIznos() {
        return iznos;
    }

    /**
     * Vraca laptop na koji se stavka odnosi.
     *
     * @return Laptop.
     */
    public Laptop getLaptop() {
        return laptop;
    }

    /**
     * Vraca racun kome stavka pripada.
     *
     * @return Racun.
     */
    public Racun getRacun() {
        return racun;
    }

    /**
     * Preracunava iznos stavke kao proizvod prodajne cene i kolicine.
     *
     * Rezultat mnozenja se dodeljuje polju iznos, zamenjujuci
     * njegovu prethodnu vrednost.
     */
    public void preracunajIznos() {
        this.iznos = prodajnaCena.multiply(BigDecimal.valueOf(kolicina));
    }

    /**
     * Postavlja identifikator stavke racuna na unetu vrednost.
     *
     * @param id Novi identifikator stavke racuna.
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Postavlja redni broj stavke na unetu vrednost.
     *
     * @param rb Novi redni broj stavke.
     * @throws java.lang.IllegalArgumentException Ako rb nije veci od 0.
     */
    public void setRb(int rb) {
        if (rb <= 0) throw new IllegalArgumentException("Rb mora biti veci od 0");
        this.rb = rb;
    }

    /**
     * Postavlja prodajnu cenu laptopa u okviru stavke na unetu vrednost.
     *
     * @param prodajnaCena Nova prodajna cena laptopa.
     * @throws java.lang.NullPointerException Ako je prodajna cena null.
     * @throws java.lang.IllegalArgumentException Ako prodajna cena nije veca od 0.
     */
    public void setProdajnaCena(BigDecimal prodajnaCena) {
        if (prodajnaCena == null) throw new NullPointerException("Prodajna cena ne sme biti null");
        if (prodajnaCena.compareTo(BigDecimal.ZERO) <= 0) throw new IllegalArgumentException("Prodajna cena mora biti veca od 0");
        this.prodajnaCena = prodajnaCena;
    }

    /**
     * Postavlja kolicinu prodatih laptopova u okviru stavke na unetu vrednost.
     *
     * @param kolicina Nova kolicina prodatih laptopova.
     * @throws java.lang.IllegalArgumentException Ako kolicina nije veca od 0.
     */
    public void setKolicina(int kolicina) {
        if (kolicina <= 0) throw new IllegalArgumentException("Kolicina mora biti veca od 0");
        this.kolicina = kolicina;
    }

    /**
     * Postavlja ukupan iznos stavke na unetu vrednost.
     *
     * @param iznos Novi iznos stavke.
     * @throws java.lang.NullPointerException Ako je iznos null.
     * @throws java.lang.IllegalArgumentException Ako iznos nije veci od 0.
     */
    public void setIznos(BigDecimal iznos) {
        if (iznos == null) throw new NullPointerException("Iznos ne sme biti null");
        if (iznos.compareTo(BigDecimal.ZERO) <= 0) throw new IllegalArgumentException("Iznos mora biti veci od 0");
        this.iznos = iznos;
    }

    /**
     * Postavlja laptop na koji se stavka odnosi na unetu vrednost.
     *
     * @param laptop Novi laptop stavke.
     * @throws java.lang.NullPointerException Ako je laptop null.
     */
    public void setLaptop(Laptop laptop) {
        if (laptop == null) throw new NullPointerException("Laptop ne sme biti null");
        this.laptop = laptop;
    }

    /**
     * Postavlja racun kome stavka pripada na unetu vrednost.
     *
     * @param racun Novi racun stavke.
     * @throws java.lang.NullPointerException Ako je racun null.
     */
    public void setRacun(Racun racun) {
        if (racun == null) throw new NullPointerException("Racun ne sme biti null");
        this.racun = racun;
    }

    /**
     * Poredi dve stavke racuna po rednom broju, kolicini, laptopu,
     * prodajnoj ceni i iznosu.
     *
     * @param o Drugi objekat sa kojim se poredi.
     * @return
     * <ul>
     * <li><b>true</b> - ako su oba objekta klase StavkaRacuna sa istim rednim
     * brojem, kolicinom, laptopom, prodajnom cenom i iznosom ili ako su
     * na istoj adresi.</li>
     * <li><b>false</b> - ako je drugi objekat null, ako je druge klase ili
     * ako se neko od navedenih polja razlikuje.</li>
     * </ul>
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        StavkaRacuna s = (StavkaRacuna) o;
        return rb == s.rb &&
               kolicina == s.kolicina &&
               Objects.equals(laptop, s.laptop) &&
               Objects.equals(prodajnaCena, s.prodajnaCena) &&
               Objects.equals(iznos, s.iznos);
    }

    /**
     * Racuna hash kod stavke racuna na osnovu rednog broja, laptopa,
     * kolicine, prodajne cene i iznosa.
     *
     * @return Hash kod stavke racuna.
     */
    @Override
    public int hashCode() {
        return Objects.hash(rb, laptop, kolicina, prodajnaCena, iznos);
    }

    /**
     * Vraca tekstualnu reprezentaciju stavke racuna.
     *
     * @return Tekstualna reprezentacija stavke racuna.
     */
    @Override
    public String toString() {
        return "StavkaRacuna{rb=" + rb + ", laptop=" + laptop + ", kolicina=" + kolicina +
               ", prodajnaCena=" + prodajnaCena + ", iznos=" + iznos + "}";
    }
}
