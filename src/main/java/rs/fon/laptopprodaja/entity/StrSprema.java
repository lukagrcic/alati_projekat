package rs.fon.laptopprodaja.entity;

import jakarta.persistence.*;

import java.util.Objects;

/**
 * Predstavlja strucnu spremu koju prodavac moze da poseduje.
 *
 * Koristi se u vezi sa klasom PrSS radi evidencije sticanja strucne spreme.
 *
 * @author Luka Grcic
 * @version 1.0
 */
@Entity
@Table(name = "str_sprema")
public class StrSprema {

    /** Identifikator strucne spreme u bazi podataka. */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idStrucnaSprema;

    /** Naziv strucne spreme kao String. */
    @Column(nullable = false)
    private String naziv;

    /**
     * Prazan konstruktor potreban za JPA.
     */
    public StrSprema() {
    }

    /**
     * Kreira novu strucnu spremu sa zadatim nazivom.
     *
     * @param naziv Naziv strucne spreme.
     */
    public StrSprema(String naziv) {
        this.naziv = naziv;
    }

    /**
     * Vraca identifikator strucne spreme.
     *
     * @return Identifikator strucne spreme.
     */
    public Long getIdStrucnaSprema() {
        return idStrucnaSprema;
    }

    /**
     * Vraca naziv strucne spreme.
     *
     * @return Naziv strucne spreme.
     */
    public String getNaziv() {
        return naziv;
    }

    /**
     * Postavlja identifikator strucne spreme na unetu vrednost.
     *
     * @param idStrucnaSprema Novi identifikator strucne spreme.
     */
    public void setIdStrucnaSprema(Long idStrucnaSprema) {
        this.idStrucnaSprema = idStrucnaSprema;
    }

    /**
     * Postavlja naziv strucne spreme na unetu vrednost.
     *
     * @param naziv Novi naziv strucne spreme.
     * @throws java.lang.NullPointerException Ako je naziv null.
     * @throws java.lang.IllegalArgumentException Ako naziv ima manje od 2 znaka.
     */
    public void setNaziv(String naziv) {
        if (naziv == null) throw new NullPointerException("Naziv ne sme biti null");
        if (naziv.length() < 2) throw new IllegalArgumentException("Naziv mora imati bar 2 znaka");
        this.naziv = naziv;
    }

    /**
     * Poredi dve strucne spreme po nazivu.
     *
     * @param o Drugi objekat sa kojim se poredi.
     * @return
     * <ul>
     * <li><b>true</b> - ako su oba objekta klase StrSprema sa istim nazivom
     * ili ako su na istoj adresi.</li>
     * <li><b>false</b> - ako je drugi objekat null, ako je druge klase ili
     * ako naziv nije isti.</li>
     * </ul>
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        StrSprema s = (StrSprema) o;
        return Objects.equals(naziv, s.naziv);
    }

    /**
     * Racuna hash kod strucne spreme na osnovu naziva.
     *
     * @return Hash kod strucne spreme.
     */
    @Override
    public int hashCode() {
        return Objects.hash(naziv);
    }

    /**
     * Vraca tekstualnu reprezentaciju strucne spreme.
     *
     * @return Tekstualna reprezentacija strucne spreme.
     */
    @Override
    public String toString() {
        return "StrSprema{naziv='" + naziv + "'}";
    }
}
