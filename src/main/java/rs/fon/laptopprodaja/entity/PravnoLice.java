package rs.fon.laptopprodaja.entity;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

import java.util.Objects;

@Entity
@DiscriminatorValue("PRAVNO")
public class PravnoLice extends Kupac {

    private String naziv;
    private String pib;
    private String maticniBroj;

    public PravnoLice() {
    }

    public PravnoLice(String email, String telefon, Grad grad, String naziv, String pib, String maticniBroj) {
        super(email, telefon, grad);
        this.naziv = naziv;
        this.pib = pib;
        this.maticniBroj = maticniBroj;
    }

    @Override
    public String getTip() {
        return "pravno";
    }

    public String getNaziv() {
        return naziv;
    }

    public String getPib() {
        return pib;
    }

    public String getMaticniBroj() {
        return maticniBroj;
    }

    public void setNaziv(String naziv) {
        if (naziv == null) throw new NullPointerException("Naziv ne sme biti null");
        if (naziv.length() < 2) throw new IllegalArgumentException("Naziv mora imati bar 2 znaka");
        this.naziv = naziv;
    }

    public void setPib(String pib) {
        if (pib == null) throw new NullPointerException("PIB ne sme biti null");
        if (pib.length() != 9) throw new IllegalArgumentException("PIB mora imati tacno 9 cifara");
        this.pib = pib;
    }

    public void setMaticniBroj(String maticniBroj) {
        if (maticniBroj == null) throw new NullPointerException("Maticni broj ne sme biti null");
        if (maticniBroj.length() != 8) throw new IllegalArgumentException("Maticni broj mora imati tacno 8 cifara");
        this.maticniBroj = maticniBroj;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PravnoLice pl = (PravnoLice) o;
        return Objects.equals(pib, pl.pib) &&
               Objects.equals(naziv, pl.naziv) &&
               Objects.equals(getEmail(), pl.getEmail()) &&
               Objects.equals(getTelefon(), pl.getTelefon());
    }

    @Override
    public int hashCode() {
        return Objects.hash(pib, naziv, getEmail(), getTelefon());
    }

    @Override
    public String toString() {
        return "PravnoLice{pib='" + pib + "', naziv='" + naziv +
               "', email='" + getEmail() + "', telefon='" + getTelefon() + "'}";
    }
}
