package rs.fon.laptopprodaja.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "prodavac")
public class Prodavac {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idProdavac;

    @Column(nullable = false)
    private String ime;

    @Column(nullable = false)
    private String prezime;

    @Column(nullable = false, unique = true)
    private String korisnickoIme;

    @Column(nullable = false)
    private String sifra;

    @OneToMany(mappedBy = "prodavac", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference("prodavac-prss")
    private List<PrSS> strucneSpreme = new ArrayList<>();

    public Prodavac() {
    }

    public Prodavac(String ime, String prezime, String korisnickoIme, String sifra) {
        this.ime = ime;
        this.prezime = prezime;
        this.korisnickoIme = korisnickoIme;
        this.sifra = sifra;
    }

    public Long getIdProdavac() {
        return idProdavac;
    }

    public String getIme() {
        return ime;
    }

    public String getPrezime() {
        return prezime;
    }

    public String getKorisnickoIme() {
        return korisnickoIme;
    }

    public String getSifra() {
        return sifra;
    }

    public List<PrSS> getStrucneSpreme() {
        return strucneSpreme;
    }

    public void setIdProdavac(Long idProdavac) {
        this.idProdavac = idProdavac;
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

    public void setKorisnickoIme(String korisnickoIme) {
        if (korisnickoIme == null) throw new NullPointerException("Korisnicko ime ne sme biti null");
        if (korisnickoIme.length() < 3) throw new IllegalArgumentException("Korisnicko ime mora imati bar 3 znaka");
        this.korisnickoIme = korisnickoIme;
    }

    public void setSifra(String sifra) {
        if (sifra == null) throw new NullPointerException("Sifra ne sme biti null");
        if (sifra.length() < 4) throw new IllegalArgumentException("Sifra mora imati bar 4 znaka");
        this.sifra = sifra;
    }

    public void setStrucneSpreme(List<PrSS> strucneSpreme) {
        this.strucneSpreme = strucneSpreme;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Prodavac p = (Prodavac) o;
        return Objects.equals(korisnickoIme, p.korisnickoIme) &&
               Objects.equals(ime, p.ime) &&
               Objects.equals(prezime, p.prezime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(korisnickoIme, ime, prezime);
    }

    @Override
    public String toString() {
        return "Prodavac{korisnickoIme='" + korisnickoIme + "', ime='" + ime + "', prezime='" + prezime + "'}";
    }
}
