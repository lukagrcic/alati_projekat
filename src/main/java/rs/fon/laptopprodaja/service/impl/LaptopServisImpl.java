package rs.fon.laptopprodaja.service.impl;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import rs.fon.laptopprodaja.entity.Laptop;
import rs.fon.laptopprodaja.repository.LaptopRepository;
import rs.fon.laptopprodaja.service.LaptopServis;

import java.math.BigDecimal;
import java.util.List;

@Service
public class LaptopServisImpl implements LaptopServis {

    private final LaptopRepository laptopRepository;

    public LaptopServisImpl(LaptopRepository laptopRepository) {
        this.laptopRepository = laptopRepository;
    }

    @Override
    public Laptop kreirajLaptop(Laptop laptop) {
        if (laptop == null) throw new NullPointerException("Laptop ne sme biti null");
        laptop.setIdLaptop(null);
        return laptopRepository.save(laptop);
    }

    @Override
    public Laptop izmeniLaptop(Long id, Laptop izmene) {
        if (id == null) throw new NullPointerException("ID ne sme biti null");
        if (izmene == null) throw new NullPointerException("Izmene ne smeju biti null");
        Laptop laptop = vratiLaptop(id);
        laptop.setNaziv(izmene.getNaziv());
        laptop.setCena(izmene.getCena());
        laptop.setKarakteristike(izmene.getKarakteristike());
        return laptopRepository.save(laptop);
    }

    @Override
    public void obrisiLaptop(Long id) {
        if (id == null) throw new NullPointerException("ID ne sme biti null");
        if (!laptopRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Laptop ne postoji: " + id);
        }
        laptopRepository.deleteById(id);
    }

    @Override
    public List<Laptop> pretraziLaptope(String naziv) {
        if (naziv == null || naziv.isBlank()) {
            return laptopRepository.findAll();
        }
        return laptopRepository.findByNazivContainingIgnoreCase(naziv);
    }

    @Override
    public Laptop vratiLaptop(Long id) {
        if (id == null) throw new NullPointerException("ID ne sme biti null");
        return laptopRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Laptop ne postoji: " + id));
    }

    @Override
    public List<Laptop> laptopPoRasponuCene(BigDecimal min, BigDecimal max) {
        if (min == null) throw new NullPointerException("Minimalna cena ne sme biti null");
        if (max == null) throw new NullPointerException("Maksimalna cena ne sme biti null");
        return laptopRepository.findByCenaBetween(min, max);
    }

    @Override
    public List<Laptop> laptopDoMaksimalneCene(BigDecimal max) {
        if (max == null) throw new NullPointerException("Maksimalna cena ne sme biti null");
        return laptopRepository.findByCenaLessThanEqual(max);
    }
}
