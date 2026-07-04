package rs.fon.laptopprodaja.service;

import rs.fon.laptopprodaja.entity.Laptop;

import java.math.BigDecimal;
import java.util.List;

/**
 * Interfejs koji definise poslovnu logiku za rad sa laptopovima.
 *
 * @author Luka Grcic
 * @version 1.0
 */
public interface LaptopServis {

    /**
     * Kreira novi laptop. Identifikator prosledjenog laptopa se zanemaruje,
     * odnosno laptop se uvek cuva kao novi zapis.
     *
     * @param laptop Laptop koji se kreira.
     * @return Novokreirani laptop sa dodeljenim identifikatorom.
     * @throws java.lang.NullPointerException Ako je laptop null.
     */
    Laptop kreirajLaptop(Laptop laptop);

    /**
     * Menja podatke postojeceg laptopa sa zadatim identifikatorom.
     *
     * @param id Identifikator laptopa koji se menja.
     * @param izmene Laptop koji sadrzi izmenjene podatke.
     * @return Izmenjeni laptop.
     * @throws java.lang.NullPointerException Ako je id null ili ako su
     * izmene null.
     * @throws org.springframework.web.server.ResponseStatusException Ako
     * laptop sa zadatim identifikatorom ne postoji (HTTP 404 NOT_FOUND).
     */
    Laptop izmeniLaptop(Long id, Laptop izmene);

    /**
     * Brise laptop sa zadatim identifikatorom.
     *
     * @param id Identifikator laptopa koji se brise.
     * @throws java.lang.NullPointerException Ako je id null.
     * @throws org.springframework.web.server.ResponseStatusException Ako
     * laptop sa zadatim identifikatorom ne postoji (HTTP 404 NOT_FOUND).
     */
    void obrisiLaptop(Long id);

    /**
     * Pretrazuje laptopove po nazivu. Ako je naziv null ili prazan (odnosno
     * sadrzi samo razmake), vracaju se svi laptopovi.
     *
     * @param naziv Naziv ili deo naziva laptopa koji se trazi.
     * @return Lista laptopova ciji naziv sadrzi zadati tekst (bez obzira na
     * velika i mala slova), odnosno svi laptopovi ako naziv nije zadat.
     */
    List<Laptop> pretraziLaptope(String naziv);

    /**
     * Vraca laptop sa zadatim identifikatorom.
     *
     * @param id Identifikator laptopa koji se trazi.
     * @return Laptop sa zadatim identifikatorom.
     * @throws java.lang.NullPointerException Ako je id null.
     * @throws org.springframework.web.server.ResponseStatusException Ako
     * laptop sa zadatim identifikatorom ne postoji (HTTP 404 NOT_FOUND).
     */
    Laptop vratiLaptop(Long id);

    /**
     * Vraca sve laptopove cija se cena nalazi u zadatom rasponu.
     *
     * @param min Minimalna cena laptopa.
     * @param max Maksimalna cena laptopa.
     * @return Lista laptopova cija je cena u zadatom rasponu.
     * @throws java.lang.NullPointerException Ako je min null ili ako je max
     * null.
     */
    List<Laptop> laptopPoRasponuCene(BigDecimal min, BigDecimal max);

}
