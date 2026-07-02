package rs.fon.laptopprodaja;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import rs.fon.laptopprodaja.entity.*;
import rs.fon.laptopprodaja.repository.*;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * Ubacuje minimalne pocetne podatke pri prvom pokretanju (ako je baza prazna),
 * kako bi se aplikacija mogla odmah isprobati. Nije deo poslovne logike.
 */
@Component
public class PocetniPodaci implements CommandLineRunner {

    private final GradRepository gradRepo;
    private final StrSpremaRepository ssRepo;
    private final LaptopRepository laptopRepo;
    private final ProdavacRepository prodavacRepo;
    private final KupacRepository kupacRepo;

    public PocetniPodaci(GradRepository gradRepo, StrSpremaRepository ssRepo, LaptopRepository laptopRepo,
                         ProdavacRepository prodavacRepo, KupacRepository kupacRepo) {
        this.gradRepo = gradRepo;
        this.ssRepo = ssRepo;
        this.laptopRepo = laptopRepo;
        this.prodavacRepo = prodavacRepo;
        this.kupacRepo = kupacRepo;
    }

    @Override
    public void run(String... args) {
        if (gradRepo.count() > 0) {
            return;
        }
        Grad ns = gradRepo.save(new Grad("Novi Sad", "21000"));
        Grad bg = gradRepo.save(new Grad("Beograd", "11000"));

        ssRepo.save(new StrSprema("SSS"));
        ssRepo.save(new StrSprema("VSS"));

        laptopRepo.save(new Laptop("Lenovo ThinkPad X1", new BigDecimal("180000"), "i7, 16GB RAM, 512GB SSD"));
        laptopRepo.save(new Laptop("Dell XPS 13", new BigDecimal("210000"), "i7, 16GB RAM, 1TB SSD"));
        laptopRepo.save(new Laptop("HP Pavilion 15", new BigDecimal("95000"), "i5, 8GB RAM, 256GB SSD"));

        prodavacRepo.save(new Prodavac("Marko", "Markovic", "marko", "marko123"));

        kupacRepo.save(new FizickoLice("pera@mail.com", "0641234567", ns, "Pera", "Peric", "0101990800123"));
        kupacRepo.save(new PravnoLice("firma@mail.com", "0117654321", bg, "Tech doo", "123456789", "20987654"));
    }
}
