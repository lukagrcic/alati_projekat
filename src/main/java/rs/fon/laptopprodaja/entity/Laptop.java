package rs.fon.laptopprodaja.entity;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.Objects;

@Entity
@Table(name = "laptop")
public class Laptop {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idLaptop;

    @Column(nullable = false)
    private String naziv;

    @Column(nullable = false)
    private BigDecimal cena;

    @Column(length = 1000)
    private String karakteristike;

    public Laptop() {
    }

    public Laptop(String naziv, BigDecimal cena, String karakteristike) {
        this.naziv = naziv;
        this.cena = cena;
        this.karakteristike = karakteristike;
    }

    public Long getIdLaptop() {
        return idLaptop;
    }

    public String getNaziv() {
        return naziv;
    }

    public BigDecimal getCena() {
        return cena;
    }

    public String getKarakteristike() {
        return karakteristike;
    }

    public void setIdLaptop(Long idLaptop) {
        this.idLaptop = idLaptop;
    }

    public void setNaziv(String naziv) {
        if (naziv == null) throw new NullPointerException("Naziv ne sme biti null");
        if (naziv.length() < 2) throw new IllegalArgumentException("Naziv mora imati bar 2 znaka");
        this.naziv = naziv;
    }

    public void setCena(BigDecimal cena) {
        if (cena == null) throw new NullPointerException("Cena ne sme biti null");
        if (cena.compareTo(BigDecimal.ZERO) <= 0) throw new IllegalArgumentException("Cena mora biti veca od 0");
        this.cena = cena;
    }

    public void setKarakteristike(String karakteristike) {
        this.karakteristike = karakteristike;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Laptop l = (Laptop) o;
        return Objects.equals(naziv, l.naziv) &&
               Objects.equals(cena, l.cena);
    }

    @Override
    public int hashCode() {
        return Objects.hash(naziv, cena);
    }

    @Override
    public String toString() {
        return "Laptop{naziv='" + naziv + "', cena=" + cena + "}";
    }
}
