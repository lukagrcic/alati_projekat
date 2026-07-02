package rs.fon.laptopprodaja.service;

import rs.fon.laptopprodaja.entity.Laptop;

import java.math.BigDecimal;
import java.util.List;

public interface LaptopServis {
    Laptop kreirajLaptop(Laptop laptop);
    Laptop izmeniLaptop(Long id, Laptop izmene);
    void obrisiLaptop(Long id);
    List<Laptop> pretraziLaptope(String naziv);
    Laptop vratiLaptop(Long id);
    List<Laptop> laptopPoRasponuCene(BigDecimal min, BigDecimal max);
    List<Laptop> laptopDoMaksimalneCene(BigDecimal max);
}
