package rs.fon.laptopprodaja.entity;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import jakarta.persistence.*;

/**
 * Apstraktna klasa koja predstavlja kupca laptopa.
 *
 * Konkretni tipovi kupca su FizickoLice i PravnoLice, koji nasledjuju
 * zajednicke podatke definisane u ovoj klasi.
 *
 * @author Luka Grcic
 * @version 1.0
 */
@Entity
@Table(name = "kupac")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "tip_kupca")
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.EXISTING_PROPERTY, property = "tip", visible = true)
@JsonSubTypes({
        @JsonSubTypes.Type(value = FizickoLice.class, name = "fizicko"),
        @JsonSubTypes.Type(value = PravnoLice.class, name = "pravno")
})
public abstract class Kupac {

    /** Identifikator kupca u bazi podataka. */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idKupac;

    /** Email adresa kupca kao String. */
    @Column(nullable = false)
    private String email;

    /** Broj telefona kupca kao String. */
    private String telefon;

    /** Grad u kome se kupac nalazi. */
    @ManyToOne
    @JoinColumn(name = "id_grad")
    private Grad grad;

    /**
     * Prazan konstruktor potreban za JPA.
     */
    public Kupac() {
    }

    /**
     * Kreira novog kupca sa zadatim emailom, telefonom i gradom.
     *
     * @param email Email adresa kupca.
     * @param telefon Broj telefona kupca.
     * @param grad Grad u kome se kupac nalazi.
     */
    public Kupac(String email, String telefon, Grad grad) {
        this.email = email;
        this.telefon = telefon;
        this.grad = grad;
    }

    /**
     * Vraca identifikator kupca.
     *
     * @return Identifikator kupca.
     */
    public Long getIdKupac() {
        return idKupac;
    }

    /**
     * Vraca email adresu kupca.
     *
     * @return Email adresa kupca.
     */
    public String getEmail() {
        return email;
    }

    /**
     * Vraca broj telefona kupca.
     *
     * @return Broj telefona kupca.
     */
    public String getTelefon() {
        return telefon;
    }

    /**
     * Vraca grad u kome se kupac nalazi.
     *
     * @return Grad kupca.
     */
    public Grad getGrad() {
        return grad;
    }

    /**
     * Postavlja identifikator kupca na unetu vrednost.
     *
     * @param idKupac Novi identifikator kupca.
     */
    public void setIdKupac(Long idKupac) {
        this.idKupac = idKupac;
    }

    /**
     * Postavlja email adresu kupca na unetu vrednost.
     *
     * @param email Nova email adresa kupca.
     * @throws java.lang.NullPointerException Ako je email null.
     * @throws java.lang.IllegalArgumentException Ako email ne sadrzi karakter '@'.
     */
    public void setEmail(String email) {
        if (email == null) throw new NullPointerException("Email ne sme biti null");
        if (!email.contains("@")) throw new IllegalArgumentException("Email mora sadrzati @");
        this.email = email;
    }

    /**
     * Postavlja broj telefona kupca na unetu vrednost.
     *
     * @param telefon Novi broj telefona kupca.
     */
    public void setTelefon(String telefon) {
        this.telefon = telefon;
    }

    /**
     * Postavlja grad kupca na unetu vrednost.
     *
     * @param grad Novi grad kupca.
     * @throws java.lang.NullPointerException Ako je grad null.
     */
    public void setGrad(Grad grad) {
        if (grad == null) throw new NullPointerException("Grad ne sme biti null");
        this.grad = grad;
    }

    /**
     * Vraca oznaku tipa kupca, u zavisnosti od konkretne podklase.
     *
     * @return Tip kupca ("fizicko" ili "pravno").
     */
    @Transient
    public abstract String getTip();
}
