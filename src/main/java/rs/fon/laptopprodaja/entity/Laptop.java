package rs.fon.laptopprodaja.entity;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.Objects;

/**
 * Predstavlja laptop koji se prodaje.
 *
 * Sadrzi naziv, cenu i opis karakteristika laptopa.
 *
 * @author Luka Grcic
 * @version 1.0
 */
@Entity
@Table(name = "laptop")
public class Laptop {

    /** Identifikator laptopa u bazi podataka. */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idLaptop;

    /** Naziv laptopa kao String. */
    @Column(nullable = false)
    private String naziv;

    /** Cena laptopa kao BigDecimal. */
    @Column(nullable = false)
    private BigDecimal cena;

    /** Opis karakteristika laptopa kao String. */
    @Column(length = 1000)
    private String karakteristike;

    /**
     * Prazan konstruktor potreban za JPA.
     */
    public Laptop() {
    }

    /**
     * Kreira novi laptop sa zadatim nazivom, cenom i karakteristikama.
     *
     * @param naziv Naziv laptopa.
     * @param cena Cena laptopa.
     * @param karakteristike Opis karakteristika laptopa.
     */
    public Laptop(String naziv, BigDecimal cena, String karakteristike) {
        this.naziv = naziv;
        this.cena = cena;
        this.karakteristike = karakteristike;
    }

    /**
     * Vraca identifikator laptopa.
     *
     * @return Identifikator laptopa.
     */
    public Long getIdLaptop() {
        return idLaptop;
    }

    /**
     * Vraca naziv laptopa.
     *
     * @return Naziv laptopa.
     */
    public String getNaziv() {
        return naziv;
    }

    /**
     * Vraca cenu laptopa.
     *
     * @return Cena laptopa.
     */
    public BigDecimal getCena() {
        return cena;
    }

    /**
     * Vraca opis karakteristika laptopa.
     *
     * @return Opis karakteristika laptopa.
     */
    public String getKarakteristike() {
        return karakteristike;
    }

    /**
     * Postavlja identifikator laptopa na unetu vrednost.
     *
     * @param idLaptop Novi identifikator laptopa.
     */
    public void setIdLaptop(Long idLaptop) {
        this.idLaptop = idLaptop;
    }

    /**
     * Postavlja naziv laptopa na unetu vrednost.
     *
     * @param naziv Novi naziv laptopa.
     * @throws java.lang.NullPointerException Ako je naziv null.
     * @throws java.lang.IllegalArgumentException Ako naziv ima manje od 2 znaka.
     */
    public void setNaziv(String naziv) {
        if (naziv == null) throw new NullPointerException("Naziv ne sme biti null");
        if (naziv.length() < 2) throw new IllegalArgumentException("Naziv mora imati bar 2 znaka");
        this.naziv = naziv;
    }

    /**
     * Postavlja cenu laptopa na unetu vrednost.
     *
     * @param cena Nova cena laptopa.
     * @throws java.lang.NullPointerException Ako je cena null.
     * @throws java.lang.IllegalArgumentException Ako cena nije veca od 0.
     */
    public void setCena(BigDecimal cena) {
        if (cena == null) throw new NullPointerException("Cena ne sme biti null");
        if (cena.compareTo(BigDecimal.ZERO) <= 0) throw new IllegalArgumentException("Cena mora biti veca od 0");
        this.cena = cena;
    }

    /**
     * Postavlja opis karakteristika laptopa na unetu vrednost.
     *
     * @param karakteristike Novi opis karakteristika laptopa.
     */
    public void setKarakteristike(String karakteristike) {
        this.karakteristike = karakteristike;
    }

    /**
     * Poredi dva laptopa po nazivu i ceni.
     *
     * @param o Drugi objekat sa kojim se poredi.
     * @return
     * <ul>
     * <li><b>true</b> - ako su oba objekta klase Laptop sa istim nazivom i cenom
     * ili ako su na istoj adresi.</li>
     * <li><b>false</b> - ako je drugi objekat null, ako je druge klase ili
     * ako naziv ili cena nisu isti.</li>
     * </ul>
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Laptop l = (Laptop) o;
        return Objects.equals(naziv, l.naziv) &&
               Objects.equals(cena, l.cena);
    }

    /**
     * Racuna hash kod laptopa na osnovu naziva i cene.
     *
     * @return Hash kod laptopa.
     */
    @Override
    public int hashCode() {
        return Objects.hash(naziv, cena);
    }

    /**
     * Vraca tekstualnu reprezentaciju laptopa.
     *
     * @return Tekstualna reprezentacija laptopa.
     */
    @Override
    public String toString() {
        return "Laptop{naziv='" + naziv + "', cena=" + cena + "}";
    }
}
