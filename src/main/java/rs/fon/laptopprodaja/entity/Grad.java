package rs.fon.laptopprodaja.entity;

import jakarta.persistence.*;

import java.util.Objects;

/**
 * Predstavlja grad u kome se nalazi kupac ili prodavac.
 *
 * Sadrzi naziv grada i pripadajuci postanski broj.
 *
 * @author Luka Grcic
 * @version 1.0
 */
@Entity
@Table(name = "grad")
public class Grad {

    /** Identifikator grada u bazi podataka. */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idGrad;

    /** Naziv grada kao String. */
    @Column(nullable = false)
    private String naziv;

    /** Postanski broj grada kao String. */
    @Column(nullable = false)
    private String postanskiBroj;

    /**
     * Prazan konstruktor potreban za JPA.
     */
    public Grad() {
    }

    /**
     * Kreira novi grad sa zadatim nazivom i postanskim brojem.
     *
     * @param naziv Naziv grada.
     * @param postanskiBroj Postanski broj grada.
     */
    public Grad(String naziv, String postanskiBroj) {
        this.naziv = naziv;
        this.postanskiBroj = postanskiBroj;
    }

    /**
     * Vraca identifikator grada.
     *
     * @return Identifikator grada.
     */
    public Long getIdGrad() {
        return idGrad;
    }

    /**
     * Vraca naziv grada.
     *
     * @return Naziv grada.
     */
    public String getNaziv() {
        return naziv;
    }

    /**
     * Vraca postanski broj grada.
     *
     * @return Postanski broj grada.
     */
    public String getPostanskiBroj() {
        return postanskiBroj;
    }

    /**
     * Postavlja identifikator grada na unetu vrednost.
     *
     * @param idGrad Novi identifikator grada.
     */
    public void setIdGrad(Long idGrad) {
        this.idGrad = idGrad;
    }

    /**
     * Postavlja naziv grada na unetu vrednost.
     *
     * @param naziv Novi naziv grada.
     * @throws java.lang.NullPointerException Ako je naziv null.
     * @throws java.lang.IllegalArgumentException Ako naziv ima manje od 2 znaka.
     */
    public void setNaziv(String naziv) {
        if (naziv == null) throw new NullPointerException("Naziv ne sme biti null");
        if (naziv.length() < 2) throw new IllegalArgumentException("Naziv mora imati bar 2 znaka");
        this.naziv = naziv;
    }

    /**
     * Postavlja postanski broj grada na unetu vrednost.
     *
     * @param postanskiBroj Novi postanski broj grada.
     * @throws java.lang.NullPointerException Ako je postanski broj null.
     * @throws java.lang.IllegalArgumentException Ako postanski broj ne sadrzi tacno 5 cifara.
     */
    public void setPostanskiBroj(String postanskiBroj) {
        if (postanskiBroj == null) throw new NullPointerException("Postanski broj ne sme biti null");
        if (!postanskiBroj.matches("\\d{5}")) throw new IllegalArgumentException("Postanski broj mora imati tacno 5 cifara");
        this.postanskiBroj = postanskiBroj;
    }

    /**
     * Poredi dva grada po nazivu.
     *
     * @param o Drugi objekat sa kojim se poredi.
     * @return
     * <ul>
     * <li><b>true</b> - ako su oba objekta klase Grad sa istim nazivom
     * ili ako su na istoj adresi.</li>
     * <li><b>false</b> - ako je drugi objekat null, ako je druge klase ili
     * ako naziv nije isti.</li>
     * </ul>
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Grad g = (Grad) o;
        return Objects.equals(naziv, g.naziv);
    }

    /**
     * Racuna hash kod grada na osnovu naziva.
     *
     * @return Hash kod grada.
     */
    @Override
    public int hashCode() {
        return Objects.hash(naziv);
    }

    /**
     * Vraca tekstualnu reprezentaciju grada.
     *
     * @return Tekstualna reprezentacija grada.
     */
    @Override
    public String toString() {
        return "Grad{naziv='" + naziv + "'}";
    }
}
