package rs.fon.laptopprodaja.entity;

import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table(name = "str_sprema")
public class StrSprema {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idStrucnaSprema;

    @Column(nullable = false)
    private String naziv;

    public StrSprema() {
    }

    public StrSprema(String naziv) {
        this.naziv = naziv;
    }

    public Long getIdStrucnaSprema() {
        return idStrucnaSprema;
    }

    public String getNaziv() {
        return naziv;
    }

    public void setIdStrucnaSprema(Long idStrucnaSprema) {
        this.idStrucnaSprema = idStrucnaSprema;
    }

    public void setNaziv(String naziv) {
        if (naziv == null) throw new NullPointerException("Naziv ne sme biti null");
        if (naziv.length() < 2) throw new IllegalArgumentException("Naziv mora imati bar 2 znaka");
        this.naziv = naziv;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        StrSprema s = (StrSprema) o;
        return Objects.equals(naziv, s.naziv);
    }

    @Override
    public int hashCode() {
        return Objects.hash(naziv);
    }

    @Override
    public String toString() {
        return "StrSprema{naziv='" + naziv + "'}";
    }
}
