package rs.fon.laptopprodaja.entity;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

import java.util.Objects;

@Entity
@DiscriminatorValue("FIZICKO")
public class FizickoLice extends Kupac {

    private String ime;
    private String prezime;
    private String jmbg;

    public FizickoLice() {
    }

    public FizickoLice(String email, String telefon, Grad grad, String ime, String prezime, String jmbg) {
        super(email, telefon, grad);
        this.ime = ime;
        this.prezime = prezime;
        this.jmbg = jmbg;
    }

    @Override
    public String getTip() {
        return "fizicko";
    }

    public String getIme() {
        return ime;
    }

    public String getPrezime() {
        return prezime;
    }

    public String getJmbg() {
        return jmbg;
    }

    public void setIme(String ime) {
        if (ime == null) throw new NullPointerException("Ime ne sme biti null");
        if (ime.length() < 2) throw new IllegalArgumentException("Ime mora imati bar 2 znaka");
        this.ime = ime;
    }

    public void setPrezime(String prezime) {
        if (prezime == null) throw new NullPointerException("Prezime ne sme biti null");
        if (prezime.length() < 2) throw new IllegalArgumentException("Prezime mora imati bar 2 znaka");
        this.prezime = prezime;
    }

    public void setJmbg(String jmbg) {
        if (jmbg == null) throw new NullPointerException("JMBG ne sme biti null");
        if (jmbg.length() != 13) throw new IllegalArgumentException("JMBG mora imati tacno 13 cifara");
        this.jmbg = jmbg;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FizickoLice fl = (FizickoLice) o;
        return Objects.equals(jmbg, fl.jmbg) &&
               Objects.equals(ime, fl.ime) &&
               Objects.equals(prezime, fl.prezime) &&
               Objects.equals(getEmail(), fl.getEmail()) &&
               Objects.equals(getTelefon(), fl.getTelefon());
    }

    @Override
    public int hashCode() {
        return Objects.hash(jmbg, ime, prezime, getEmail(), getTelefon());
    }

    @Override
    public String toString() {
        return "FizickoLice{jmbg='" + jmbg + "', ime='" + ime + "', prezime='" + prezime +
               "', email='" + getEmail() + "', telefon='" + getTelefon() + "'}";
    }
}
