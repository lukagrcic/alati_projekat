package rs.fon.laptopprodaja.entity;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import jakarta.persistence.*;

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

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idKupac;

    @Column(nullable = false)
    private String email;

    private String telefon;

    @ManyToOne
    @JoinColumn(name = "id_grad")
    private Grad grad;

    public Kupac() {
    }

    public Kupac(String email, String telefon, Grad grad) {
        this.email = email;
        this.telefon = telefon;
        this.grad = grad;
    }

    public Long getIdKupac() {
        return idKupac;
    }

    public String getEmail() {
        return email;
    }

    public String getTelefon() {
        return telefon;
    }

    public Grad getGrad() {
        return grad;
    }

    public void setIdKupac(Long idKupac) {
        this.idKupac = idKupac;
    }

    public void setEmail(String email) {
        if (email == null) throw new NullPointerException("Email ne sme biti null");
        if (!email.contains("@")) throw new IllegalArgumentException("Email mora sadrzati @");
        this.email = email;
    }

    public void setTelefon(String telefon) {
        this.telefon = telefon;
    }

    public void setGrad(Grad grad) {
        if (grad == null) throw new NullPointerException("Grad ne sme biti null");
        this.grad = grad;
    }

    @Transient
    public abstract String getTip();
}
