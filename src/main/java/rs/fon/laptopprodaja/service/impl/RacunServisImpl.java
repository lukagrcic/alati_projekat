package rs.fon.laptopprodaja.service.impl;

import jakarta.transaction.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import rs.fon.laptopprodaja.dto.KreirajRacunZahtev;
import rs.fon.laptopprodaja.dto.StavkaZahtev;
import rs.fon.laptopprodaja.entity.*;
import rs.fon.laptopprodaja.repository.KupacRepository;
import rs.fon.laptopprodaja.repository.LaptopRepository;
import rs.fon.laptopprodaja.repository.ProdavacRepository;
import rs.fon.laptopprodaja.repository.RacunRepository;
import rs.fon.laptopprodaja.service.RacunServis;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Service
public class RacunServisImpl implements RacunServis {

    private final RacunRepository racunRepository;
    private final ProdavacRepository prodavacRepository;
    private final KupacRepository kupacRepository;
    private final LaptopRepository laptopRepository;

    public RacunServisImpl(RacunRepository racunRepository, ProdavacRepository prodavacRepository,
                           KupacRepository kupacRepository, LaptopRepository laptopRepository) {
        this.racunRepository = racunRepository;
        this.prodavacRepository = prodavacRepository;
        this.kupacRepository = kupacRepository;
        this.laptopRepository = laptopRepository;
    }

    @Override
    @Transactional
    public Racun kreirajRacun(KreirajRacunZahtev zahtev) {
        if (zahtev == null) throw new NullPointerException("Zahtev ne sme biti null");
        Prodavac prodavac = prodavacRepository.findById(zahtev.idProdavac())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Prodavac ne postoji"));
        Kupac kupac = kupacRepository.findById(zahtev.idKupac())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Kupac ne postoji"));

        if (zahtev.stavke() == null || zahtev.stavke().isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Racun mora imati bar jednu stavku");
        }

        Racun racun = new Racun(LocalDate.now(), prodavac, kupac);

        int rb = 1;
        for (StavkaZahtev sz : zahtev.stavke()) {
            Laptop laptop = laptopRepository.findById(sz.idLaptop())
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Laptop ne postoji: " + sz.idLaptop()));
            BigDecimal cena = sz.prodajnaCena() != null ? sz.prodajnaCena() : laptop.getCena();
            StavkaRacuna stavka = new StavkaRacuna(rb++, cena, sz.kolicina(), laptop);
            racun.dodajStavku(stavka);
        }
        racun.preracunajUkupanIznos();
        return racunRepository.save(racun);
    }

    @Override
    public List<Racun> pretraziRacune() {
        return racunRepository.findAll();
    }

    @Override
    public Racun vratiRacun(Long id) {
        if (id == null) throw new NullPointerException("ID ne sme biti null");
        return racunRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Racun ne postoji: " + id));
    }

    @Override
    public List<Racun> racuniZaProdavca(Long idProdavac) {
        if (idProdavac == null) throw new NullPointerException("ID prodavca ne sme biti null");
        return racunRepository.findByProdavac_IdProdavac(idProdavac);
    }

    @Override
    public List<Racun> racuniZaKupca(Long idKupac) {
        if (idKupac == null) throw new NullPointerException("ID kupca ne sme biti null");
        return racunRepository.findByKupac_IdKupac(idKupac);
    }


}
