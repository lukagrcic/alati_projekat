package rs.fon.laptopprodaja.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.Objects;

@Entity
@Table(name = "stavka_racuna")
public class StavkaRacuna {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private int rb;

    @Column(nullable = false)
    private BigDecimal prodajnaCena;

    @Column(nullable = false)
    private int kolicina;

    @Column(nullable = false)
    private BigDecimal iznos;

    @ManyToOne(optional = false)
    @JoinColumn(name = "id_laptop")
    private Laptop laptop;

    @ManyToOne(optional = false)
    @JoinColumn(name = "id_racun")
    @JsonBackReference("racun-stavke")
    private Racun racun;

    public StavkaRacuna() {
    }

    public StavkaRacuna(int rb, BigDecimal prodajnaCena, int kolicina, Laptop laptop) {
        this.rb = rb;
        this.prodajnaCena = prodajnaCena;
        this.kolicina = kolicina;
        this.laptop = laptop;
        this.iznos = prodajnaCena.multiply(BigDecimal.valueOf(kolicina));
    }

    public Long getId() {
        return id;
    }

    public int getRb() {
        return rb;
    }

    public BigDecimal getProdajnaCena() {
        return prodajnaCena;
    }

    public int getKolicina() {
        return kolicina;
    }

    public BigDecimal getIznos() {
        return iznos;
    }

    public Laptop getLaptop() {
        return laptop;
    }

    public Racun getRacun() {
        return racun;
    }

    public void preracunajIznos() {
        this.iznos = prodajnaCena.multiply(BigDecimal.valueOf(kolicina));
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setRb(int rb) {
        if (rb <= 0) throw new IllegalArgumentException("Rb mora biti veci od 0");
        this.rb = rb;
    }

    public void setProdajnaCena(BigDecimal prodajnaCena) {
        if (prodajnaCena == null) throw new NullPointerException("Prodajna cena ne sme biti null");
        if (prodajnaCena.compareTo(BigDecimal.ZERO) <= 0) throw new IllegalArgumentException("Prodajna cena mora biti veca od 0");
        this.prodajnaCena = prodajnaCena;
    }

    public void setKolicina(int kolicina) {
        if (kolicina <= 0) throw new IllegalArgumentException("Kolicina mora biti veca od 0");
        this.kolicina = kolicina;
    }

    public void setIznos(BigDecimal iznos) {
        if (iznos == null) throw new NullPointerException("Iznos ne sme biti null");
        if (iznos.compareTo(BigDecimal.ZERO) <= 0) throw new IllegalArgumentException("Iznos mora biti veci od 0");
        this.iznos = iznos;
    }

    public void setLaptop(Laptop laptop) {
        if (laptop == null) throw new NullPointerException("Laptop ne sme biti null");
        this.laptop = laptop;
    }

    public void setRacun(Racun racun) {
        if (racun == null) throw new NullPointerException("Racun ne sme biti null");
        this.racun = racun;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        StavkaRacuna s = (StavkaRacuna) o;
        return rb == s.rb &&
               kolicina == s.kolicina &&
               Objects.equals(laptop, s.laptop) &&
               Objects.equals(prodajnaCena, s.prodajnaCena) &&
               Objects.equals(iznos, s.iznos);
    }

    @Override
    public int hashCode() {
        return Objects.hash(rb, laptop, kolicina, prodajnaCena, iznos);
    }

    @Override
    public String toString() {
        return "StavkaRacuna{rb=" + rb + ", laptop=" + laptop + ", kolicina=" + kolicina +
               ", prodajnaCena=" + prodajnaCena + ", iznos=" + iznos + "}";
    }
}
