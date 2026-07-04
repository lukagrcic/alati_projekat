package rs.fon.laptopprodaja.entity;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

import java.util.Objects;

/**
 * Predstavlja kupca koji je pravno lice.
 *
 * Nasledjuje zajednicke podatke iz klase Kupac i dodaje podatke
 * specificne za pravno lice (naziv, PIB i maticni broj).
 *
 * @author Luka Grcic
 * @version 1.0
 */
@Entity
@DiscriminatorValue("PRAVNO")
public class PravnoLice extends Kupac {

    /** Naziv pravnog lica kao String. */
    private String naziv;

    /** PIB pravnog lica kao String. */
    private String pib;

    /** Maticni broj pravnog lica kao String. */
    private String maticniBroj;

    /**
     * Prazan konstruktor potreban za JPA.
     */
    public PravnoLice() {
    }

    /**
     * Kreira novo pravno lice sa zadatim podacima.
     *
     * @param email Email adresa kupca.
     * @param telefon Broj telefona kupca.
     * @param grad Grad u kome se kupac nalazi.
     * @param naziv Naziv pravnog lica.
     * @param pib PIB pravnog lica.
     * @param maticniBroj Maticni broj pravnog lica.
     */
    public PravnoLice(String email, String telefon, Grad grad, String naziv, String pib, String maticniBroj) {
        super(email, telefon, grad);
        this.naziv = naziv;
        this.pib = pib;
        this.maticniBroj = maticniBroj;
    }

    /**
     * Vraca oznaku tipa kupca za pravno lice.
     *
     * @return Uvek vraca "pravno".
     */
    @Override
    public String getTip() {
        return "pravno";
    }

    /**
     * Vraca naziv pravnog lica.
     *
     * @return Naziv pravnog lica.
     */
    public String getNaziv() {
        return naziv;
    }

    /**
     * Vraca PIB pravnog lica.
     *
     * @return PIB pravnog lica.
     */
    public String getPib() {
        return pib;
    }

    /**
     * Vraca maticni broj pravnog lica.
     *
     * @return Maticni broj pravnog lica.
     */
    public String getMaticniBroj() {
        return maticniBroj;
    }

    /**
     * Postavlja naziv pravnog lica na unetu vrednost.
     *
     * @param naziv Novi naziv pravnog lica.
     * @throws java.lang.NullPointerException Ako je naziv null.
     * @throws java.lang.IllegalArgumentException Ako naziv ima manje od 2 znaka.
     */
    public void setNaziv(String naziv) {
        if (naziv == null) throw new NullPointerException("Naziv ne sme biti null");
        if (naziv.length() < 2) throw new IllegalArgumentException("Naziv mora imati bar 2 znaka");
        this.naziv = naziv;
    }

    /**
     * Postavlja PIB pravnog lica na unetu vrednost.
     *
     * @param pib Novi PIB pravnog lica.
     * @throws java.lang.NullPointerException Ako je pib null.
     * @throws java.lang.IllegalArgumentException Ako pib nema tacno 9 cifara.
     */
    public void setPib(String pib) {
        if (pib == null) throw new NullPointerException("PIB ne sme biti null");
        if (pib.length() != 9) throw new IllegalArgumentException("PIB mora imati tacno 9 cifara");
        this.pib = pib;
    }

    /**
     * Postavlja maticni broj pravnog lica na unetu vrednost.
     *
     * @param maticniBroj Novi maticni broj pravnog lica.
     * @throws java.lang.NullPointerException Ako je maticni broj null.
     * @throws java.lang.IllegalArgumentException Ako maticni broj nema tacno 8 cifara.
     */
    public void setMaticniBroj(String maticniBroj) {
        if (maticniBroj == null) throw new NullPointerException("Maticni broj ne sme biti null");
        if (maticniBroj.length() != 8) throw new IllegalArgumentException("Maticni broj mora imati tacno 8 cifara");
        this.maticniBroj = maticniBroj;
    }

    /**
     * Poredi dva pravna lica po PIB-u, nazivu, emailu i telefonu.
     *
     * @param o Drugi objekat sa kojim se poredi.
     * @return
     * <ul>
     * <li><b>true</b> - ako su oba objekta klase PravnoLice sa istim PIB-om,
     * nazivom, emailom i telefonom ili ako su na istoj adresi.</li>
     * <li><b>false</b> - ako je drugi objekat null, ako je druge klase ili
     * ako se neko od navedenih polja razlikuje.</li>
     * </ul>
     */
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

    /**
     * Racuna hash kod pravnog lica na osnovu PIB-a, naziva, emaila i telefona.
     *
     * @return Hash kod pravnog lica.
     */
    @Override
    public int hashCode() {
        return Objects.hash(pib, naziv, getEmail(), getTelefon());
    }

    /**
     * Vraca tekstualnu reprezentaciju pravnog lica.
     *
     * @return Tekstualna reprezentacija pravnog lica.
     */
    @Override
    public String toString() {
        return "PravnoLice{pib='" + pib + "', naziv='" + naziv +
               "', email='" + getEmail() + "', telefon='" + getTelefon() + "'}";
    }
}
