package rs.fon.laptopprodaja.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Predstavlja racun izdat kupcu od strane prodavca.
 *
 * Sadrzi datum izdavanja, ukupan iznos i listu stavki racuna od kojih
 * se ukupan iznos racuna.
 *
 * @author Luka Grcic
 * @version 1.0
 */
@Entity
@Table(name = "racun")
public class Racun {

    /** Identifikator racuna u bazi podataka. */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idRacun;

    /** Datum izdavanja racuna. */
    @Column(nullable = false)
    private LocalDate datum;

    /** Ukupan iznos racuna kao BigDecimal. */
    @Column(nullable = false)
    private BigDecimal ukupanIznos = BigDecimal.ZERO;

    /** Prodavac koji je izdao racun. */
    @ManyToOne(optional = false)
    @JoinColumn(name = "id_prodavac")
    private Prodavac prodavac;

    /** Kupac kome je racun izdat. */
    @ManyToOne(optional = false)
    @JoinColumn(name = "id_kupac")
    private Kupac kupac;

    /** Lista stavki od kojih se sastoji racun. */
    @OneToMany(mappedBy = "racun", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference("racun-stavke")
    private List<StavkaRacuna> stavke = new ArrayList<>();

    /**
     * Prazan konstruktor potreban za JPA.
     */
    public Racun() {
    }

    /**
     * Kreira novi racun sa zadatim datumom, prodavcem i kupcem.
     *
     * @param datum Datum izdavanja racuna.
     * @param prodavac Prodavac koji izdaje racun.
     * @param kupac Kupac kome se racun izdaje.
     */
    public Racun(LocalDate datum, Prodavac prodavac, Kupac kupac) {
        this.datum = datum;
        this.prodavac = prodavac;
        this.kupac = kupac;
    }

    /**
     * Vraca identifikator racuna.
     *
     * @return Identifikator racuna.
     */
    public Long getIdRacun() {
        return idRacun;
    }

    /**
     * Vraca datum izdavanja racuna.
     *
     * @return Datum izdavanja racuna.
     */
    public LocalDate getDatum() {
        return datum;
    }

    /**
     * Vraca ukupan iznos racuna.
     *
     * @return Ukupan iznos racuna.
     */
    public BigDecimal getUkupanIznos() {
        return ukupanIznos;
    }

    /**
     * Vraca prodavca koji je izdao racun.
     *
     * @return Prodavac.
     */
    public Prodavac getProdavac() {
        return prodavac;
    }

    /**
     * Vraca kupca kome je racun izdat.
     *
     * @return Kupac.
     */
    public Kupac getKupac() {
        return kupac;
    }

    /**
     * Vraca listu stavki racuna.
     *
     * @return Lista stavki racuna.
     */
    public List<StavkaRacuna> getStavke() {
        return stavke;
    }

    /**
     * Postavlja identifikator racuna na unetu vrednost.
     *
     * @param idRacun Novi identifikator racuna.
     */
    public void setIdRacun(Long idRacun) {
        this.idRacun = idRacun;
    }

    /**
     * Postavlja datum izdavanja racuna na unetu vrednost.
     *
     * @param datum Novi datum izdavanja racuna.
     * @throws java.lang.NullPointerException Ako je datum null.
     */
    public void setDatum(LocalDate datum) {
        if (datum == null) throw new NullPointerException("Datum ne sme biti null");
        this.datum = datum;
    }

    /**
     * Postavlja ukupan iznos racuna na unetu vrednost.
     *
     * @param ukupanIznos Novi ukupan iznos racuna.
     * @throws java.lang.NullPointerException Ako je ukupan iznos null.
     * @throws java.lang.IllegalArgumentException Ako je ukupan iznos negativan.
     */
    public void setUkupanIznos(BigDecimal ukupanIznos) {
        if (ukupanIznos == null) throw new NullPointerException("Ukupan iznos ne sme biti null");
        if (ukupanIznos.compareTo(BigDecimal.ZERO) < 0) throw new IllegalArgumentException("Ukupan iznos ne sme biti negativan");
        this.ukupanIznos = ukupanIznos;
    }

    /**
     * Postavlja prodavca racuna na unetu vrednost.
     *
     * @param prodavac Novi prodavac racuna.
     * @throws java.lang.NullPointerException Ako je prodavac null.
     */
    public void setProdavac(Prodavac prodavac) {
        if (prodavac == null) throw new NullPointerException("Prodavac ne sme biti null");
        this.prodavac = prodavac;
    }

    /**
     * Postavlja kupca racuna na unetu vrednost.
     *
     * @param kupac Novi kupac racuna.
     * @throws java.lang.NullPointerException Ako je kupac null.
     */
    public void setKupac(Kupac kupac) {
        if (kupac == null) throw new NullPointerException("Kupac ne sme biti null");
        this.kupac = kupac;
    }

    /**
     * Postavlja listu stavki racuna na unetu vrednost.
     *
     * @param stavke Nova lista stavki racuna.
     */
    public void setStavke(List<StavkaRacuna> stavke) {
        this.stavke = stavke;
    }

    /**
     * Dodaje novu stavku u racun.
     *
     * Stavci se prvo postavlja referenca na ovaj racun, a zatim se
     * ona dodaje u listu stavki racuna. Ne vrsi ponovno preracunavanje
     * ukupnog iznosa racuna - za to je potrebno pozvati preracunajUkupanIznos.
     *
     * @param stavka Stavka koja se dodaje na racun.
     */
    public void dodajStavku(StavkaRacuna stavka) {
        stavka.setRacun(this);
        this.stavke.add(stavka);
    }

    /**
     * Preracunava ukupan iznos racuna kao zbir iznosa svih stavki racuna.
     *
     * Rezultat sabiranja se dodeljuje polju ukupanIznos, zamenjujuci
     * njegovu prethodnu vrednost.
     */
    public void preracunajUkupanIznos() {
        this.ukupanIznos = stavke.stream()
                .map(StavkaRacuna::getIznos)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}
