package rs.fon.laptopprodaja.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "racun")
public class Racun {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idRacun;

    @Column(nullable = false)
    private LocalDate datum;

    @Column(nullable = false)
    private BigDecimal ukupanIznos = BigDecimal.ZERO;

    @ManyToOne(optional = false)
    @JoinColumn(name = "id_prodavac")
    private Prodavac prodavac;

    @ManyToOne(optional = false)
    @JoinColumn(name = "id_kupac")
    private Kupac kupac;

    @OneToMany(mappedBy = "racun", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference("racun-stavke")
    private List<StavkaRacuna> stavke = new ArrayList<>();

    public Racun() {
    }

    public Racun(LocalDate datum, Prodavac prodavac, Kupac kupac) {
        this.datum = datum;
        this.prodavac = prodavac;
        this.kupac = kupac;
    }

    public Long getIdRacun() {
        return idRacun;
    }

    public LocalDate getDatum() {
        return datum;
    }

    public BigDecimal getUkupanIznos() {
        return ukupanIznos;
    }

    public Prodavac getProdavac() {
        return prodavac;
    }

    public Kupac getKupac() {
        return kupac;
    }

    public List<StavkaRacuna> getStavke() {
        return stavke;
    }

    public void setIdRacun(Long idRacun) {
        this.idRacun = idRacun;
    }

    public void setDatum(LocalDate datum) {
        if (datum == null) throw new NullPointerException("Datum ne sme biti null");
        this.datum = datum;
    }

    public void setUkupanIznos(BigDecimal ukupanIznos) {
        if (ukupanIznos == null) throw new NullPointerException("Ukupan iznos ne sme biti null");
        if (ukupanIznos.compareTo(BigDecimal.ZERO) < 0) throw new IllegalArgumentException("Ukupan iznos ne sme biti negativan");
        this.ukupanIznos = ukupanIznos;
    }

    public void setProdavac(Prodavac prodavac) {
        if (prodavac == null) throw new NullPointerException("Prodavac ne sme biti null");
        this.prodavac = prodavac;
    }

    public void setKupac(Kupac kupac) {
        if (kupac == null) throw new NullPointerException("Kupac ne sme biti null");
        this.kupac = kupac;
    }

    public void setStavke(List<StavkaRacuna> stavke) {
        this.stavke = stavke;
    }

    public void dodajStavku(StavkaRacuna stavka) {
        stavka.setRacun(this);
        this.stavke.add(stavka);
    }

    public void preracunajUkupanIznos() {
        this.ukupanIznos = stavke.stream()
                .map(StavkaRacuna::getIznos)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}
