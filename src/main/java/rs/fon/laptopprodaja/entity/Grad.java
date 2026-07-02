package rs.fon.laptopprodaja.entity;

import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table(name = "grad")
public class Grad {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idGrad;

    @Column(nullable = false)
    private String naziv;

    @Column(nullable = false)
    private String postanskiBroj;

    public Grad() {
    }

    public Grad(String naziv, String postanskiBroj) {
        this.naziv = naziv;
        this.postanskiBroj = postanskiBroj;
    }

    public Long getIdGrad() {
        return idGrad;
    }

    public String getNaziv() {
        return naziv;
    }

    public String getPostanskiBroj() {
        return postanskiBroj;
    }

    public void setIdGrad(Long idGrad) {
        this.idGrad = idGrad;
    }

    public void setNaziv(String naziv) {
        if (naziv == null) throw new NullPointerException("Naziv ne sme biti null");
        if (naziv.length() < 2) throw new IllegalArgumentException("Naziv mora imati bar 2 znaka");
        this.naziv = naziv;
    }

    public void setPostanskiBroj(String postanskiBroj) {
        if (postanskiBroj == null) throw new NullPointerException("Postanski broj ne sme biti null");
        if (!postanskiBroj.matches("\\d{5}")) throw new IllegalArgumentException("Postanski broj mora imati tacno 5 cifara");
        this.postanskiBroj = postanskiBroj;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Grad g = (Grad) o;
        return Objects.equals(naziv, g.naziv);
    }

    @Override
    public int hashCode() {
        return Objects.hash(naziv);
    }

    @Override
    public String toString() {
        return "Grad{naziv='" + naziv + "'}";
    }
}
