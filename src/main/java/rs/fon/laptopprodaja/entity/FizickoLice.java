package rs.fon.laptopprodaja.entity;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

import java.util.Objects;

/**
 * Predstavlja kupca koji je fizicko lice.
 *
 * Nasledjuje zajednicke podatke iz klase Kupac i dodaje podatke
 * specificne za fizicko lice (ime, prezime i JMBG).
 *
 * @author Luka Grcic
 * @version 1.0
 */
@Entity
@DiscriminatorValue("FIZICKO")
public class FizickoLice extends Kupac {

    /** Ime fizickog lica kao String. */
    private String ime;

    /** Prezime fizickog lica kao String. */
    private String prezime;

    /** JMBG fizickog lica kao String. */
    private String jmbg;

    /**
     * Prazan konstruktor potreban za JPA.
     */
    public FizickoLice() {
    }

    /**
     * Kreira novo fizicko lice sa zadatim podacima.
     *
     * @param email Email adresa kupca.
     * @param telefon Broj telefona kupca.
     * @param grad Grad u kome se kupac nalazi.
     * @param ime Ime fizickog lica.
     * @param prezime Prezime fizickog lica.
     * @param jmbg JMBG fizickog lica.
     */
    public FizickoLice(String email, String telefon, Grad grad, String ime, String prezime, String jmbg) {
        super(email, telefon, grad);
        this.ime = ime;
        this.prezime = prezime;
        this.jmbg = jmbg;
    }

    /**
     * Vraca oznaku tipa kupca za fizicko lice.
     *
     * @return Uvek vraca "fizicko".
     */
    @Override
    public String getTip() {
        return "fizicko";
    }

    /**
     * Vraca ime fizickog lica.
     *
     * @return Ime fizickog lica.
     */
    public String getIme() {
        return ime;
    }

    /**
     * Vraca prezime fizickog lica.
     *
     * @return Prezime fizickog lica.
     */
    public String getPrezime() {
        return prezime;
    }

    /**
     * Vraca JMBG fizickog lica.
     *
     * @return JMBG fizickog lica.
     */
    public String getJmbg() {
        return jmbg;
    }

    /**
     * Postavlja ime fizickog lica na unetu vrednost.
     *
     * @param ime Novo ime fizickog lica.
     * @throws java.lang.NullPointerException Ako je ime null.
     * @throws java.lang.IllegalArgumentException Ako ime ima manje od 2 znaka.
     */
    public void setIme(String ime) {
        if (ime == null) throw new NullPointerException("Ime ne sme biti null");
        if (ime.length() < 2) throw new IllegalArgumentException("Ime mora imati bar 2 znaka");
        this.ime = ime;
    }

    /**
     * Postavlja prezime fizickog lica na unetu vrednost.
     *
     * @param prezime Novo prezime fizickog lica.
     * @throws java.lang.NullPointerException Ako je prezime null.
     * @throws java.lang.IllegalArgumentException Ako prezime ima manje od 2 znaka.
     */
    public void setPrezime(String prezime) {
        if (prezime == null) throw new NullPointerException("Prezime ne sme biti null");
        if (prezime.length() < 2) throw new IllegalArgumentException("Prezime mora imati bar 2 znaka");
        this.prezime = prezime;
    }

    /**
     * Postavlja JMBG fizickog lica na unetu vrednost.
     *
     * @param jmbg Novi JMBG fizickog lica.
     * @throws java.lang.NullPointerException Ako je jmbg null.
     * @throws java.lang.IllegalArgumentException Ako jmbg nema tacno 13 cifara.
     */
    public void setJmbg(String jmbg) {
        if (jmbg == null) throw new NullPointerException("JMBG ne sme biti null");
        if (jmbg.length() != 13) throw new IllegalArgumentException("JMBG mora imati tacno 13 cifara");
        this.jmbg = jmbg;
    }

    /**
     * Poredi dva fizicka lica po JMBG-u, imenu, prezimenu, emailu i telefonu.
     *
     * @param o Drugi objekat sa kojim se poredi.
     * @return
     * <ul>
     * <li><b>true</b> - ako su oba objekta klase FizickoLice sa istim JMBG-om,
     * imenom, prezimenom, emailom i telefonom ili ako su na istoj adresi.</li>
     * <li><b>false</b> - ako je drugi objekat null, ako je druge klase ili
     * ako se neko od navedenih polja razlikuje.</li>
     * </ul>
     */
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

    /**
     * Racuna hash kod fizickog lica na osnovu JMBG-a, imena, prezimena,
     * emaila i telefona.
     *
     * @return Hash kod fizickog lica.
     */
    @Override
    public int hashCode() {
        return Objects.hash(jmbg, ime, prezime, getEmail(), getTelefon());
    }

    /**
     * Vraca tekstualnu reprezentaciju fizickog lica.
     *
     * @return Tekstualna reprezentacija fizickog lica.
     */
    @Override
    public String toString() {
        return "FizickoLice{jmbg='" + jmbg + "', ime='" + ime + "', prezime='" + prezime +
               "', email='" + getEmail() + "', telefon='" + getTelefon() + "'}";
    }
}
