package rs.fon.laptopprodaja.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "prodavac_str_sprema")
public class PrSS {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "id_prodavac")
    @JsonBackReference("prodavac-prss")
    private Prodavac prodavac;

    @ManyToOne(optional = false)
    @JoinColumn(name = "id_strucna_sprema")
    private StrSprema strucnaSprema;

    @Column(nullable = false)
    private LocalDate datum;

    public PrSS() {
    }

    public PrSS(Prodavac prodavac, StrSprema strucnaSprema, LocalDate datum) {
        this.prodavac = prodavac;
        this.strucnaSprema = strucnaSprema;
        this.datum = datum;
    }

    public Long getId() {
        return id;
    }

    public Prodavac getProdavac() {
        return prodavac;
    }

    public StrSprema getStrucnaSprema() {
        return strucnaSprema;
    }

    public LocalDate getDatum() {
        return datum;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setProdavac(Prodavac prodavac) {
        if (prodavac == null) throw new NullPointerException("Prodavac ne sme biti null");
        this.prodavac = prodavac;
    }

    public void setStrucnaSprema(StrSprema strucnaSprema) {
        if (strucnaSprema == null) throw new NullPointerException("Strucna sprema ne sme biti null");
        this.strucnaSprema = strucnaSprema;
    }

    public void setDatum(LocalDate datum) {
        if (datum == null) throw new NullPointerException("Datum ne sme biti null");
        this.datum = datum;
    }
}
