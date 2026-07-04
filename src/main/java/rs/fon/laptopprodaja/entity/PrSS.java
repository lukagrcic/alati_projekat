package rs.fon.laptopprodaja.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;

import java.time.LocalDate;

/**
 * Predstavlja vezu izmedju prodavca i strucne spreme koju je stekao,
 * zajedno sa datumom sticanja.
 *
 * Sluzi kao spojna tabela u vezi vise-na-vise izmedju Prodavac i StrSprema.
 *
 * @author Luka Grcic
 * @version 1.0
 */
@Entity
@Table(name = "prodavac_str_sprema")
public class PrSS {

    /** Identifikator zapisa u bazi podataka. */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /** Prodavac koji je stekao strucnu spremu. */
    @ManyToOne(optional = false)
    @JoinColumn(name = "id_prodavac")
    @JsonBackReference("prodavac-prss")
    private Prodavac prodavac;

    /** Strucna sprema koju je prodavac stekao. */
    @ManyToOne(optional = false)
    @JoinColumn(name = "id_strucna_sprema")
    private StrSprema strucnaSprema;

    /** Datum sticanja strucne spreme. */
    @Column(nullable = false)
    private LocalDate datum;

    /**
     * Prazan konstruktor potreban za JPA.
     */
    public PrSS() {
    }

    /**
     * Kreira novu vezu izmedju prodavca i strucne spreme sa zadatim datumom.
     *
     * @param prodavac Prodavac koji je stekao strucnu spremu.
     * @param strucnaSprema Strucna sprema koju je prodavac stekao.
     * @param datum Datum sticanja strucne spreme.
     */
    public PrSS(Prodavac prodavac, StrSprema strucnaSprema, LocalDate datum) {
        this.prodavac = prodavac;
        this.strucnaSprema = strucnaSprema;
        this.datum = datum;
    }

    /**
     * Vraca identifikator zapisa.
     *
     * @return Identifikator zapisa.
     */
    public Long getId() {
        return id;
    }

    /**
     * Vraca prodavca koji je stekao strucnu spremu.
     *
     * @return Prodavac.
     */
    public Prodavac getProdavac() {
        return prodavac;
    }

    /**
     * Vraca strucnu spremu koju je prodavac stekao.
     *
     * @return Strucna sprema.
     */
    public StrSprema getStrucnaSprema() {
        return strucnaSprema;
    }

    /**
     * Vraca datum sticanja strucne spreme.
     *
     * @return Datum sticanja strucne spreme.
     */
    public LocalDate getDatum() {
        return datum;
    }

    /**
     * Postavlja identifikator zapisa na unetu vrednost.
     *
     * @param id Novi identifikator zapisa.
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Postavlja prodavca na unetu vrednost.
     *
     * @param prodavac Novi prodavac.
     * @throws java.lang.NullPointerException Ako je prodavac null.
     */
    public void setProdavac(Prodavac prodavac) {
        if (prodavac == null) throw new NullPointerException("Prodavac ne sme biti null");
        this.prodavac = prodavac;
    }

    /**
     * Postavlja strucnu spremu na unetu vrednost.
     *
     * @param strucnaSprema Nova strucna sprema.
     * @throws java.lang.NullPointerException Ako je strucna sprema null.
     */
    public void setStrucnaSprema(StrSprema strucnaSprema) {
        if (strucnaSprema == null) throw new NullPointerException("Strucna sprema ne sme biti null");
        this.strucnaSprema = strucnaSprema;
    }

    /**
     * Postavlja datum sticanja strucne spreme na unetu vrednost.
     *
     * @param datum Novi datum sticanja strucne spreme.
     * @throws java.lang.NullPointerException Ako je datum null.
     */
    public void setDatum(LocalDate datum) {
        if (datum == null) throw new NullPointerException("Datum ne sme biti null");
        this.datum = datum;
    }
}
