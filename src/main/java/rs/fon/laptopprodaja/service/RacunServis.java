package rs.fon.laptopprodaja.service;

import rs.fon.laptopprodaja.dto.KreirajRacunZahtev;
import rs.fon.laptopprodaja.entity.Racun;

import java.util.List;

/**
 * Interfejs koji definise poslovnu logiku za rad sa racunima.
 *
 * @author Luka Grcic
 * @version 1.0
 */
public interface RacunServis {

    /**
     * Kreira novi racun na osnovu zadatog zahteva. Racun mora sadrzati bar
     * jednu stavku, a svaka stavka se odnosi na postojeci laptop. Ukoliko
     * prodajna cena stavke nije zadata, koristi se trenutna cena laptopa.
     *
     * @param zahtev Zahtev koji sadrzi podatke o prodavcu, kupcu i stavkama
     * racuna.
     * @return Novokreirani racun.
     * @throws java.lang.NullPointerException Ako je zahtev null.
     * @throws org.springframework.web.server.ResponseStatusException Ako
     * prodavac sa zadatim identifikatorom ne postoji, ako kupac sa zadatim
     * identifikatorom ne postoji, ili ako laptop sa identifikatorom
     * navedenim u nekoj od stavki ne postoji (HTTP 404 NOT_FOUND), odnosno
     * ako zahtev ne sadrzi nijednu stavku (HTTP 400 BAD_REQUEST).
     */
    Racun kreirajRacun(KreirajRacunZahtev zahtev);

    /**
     * Vraca sve racune.
     *
     * @return Lista svih racuna.
     */
    List<Racun> pretraziRacune();

    /**
     * Vraca racun sa zadatim identifikatorom.
     *
     * @param id Identifikator racuna koji se trazi.
     * @return Racun sa zadatim identifikatorom.
     * @throws java.lang.NullPointerException Ako je id null.
     * @throws org.springframework.web.server.ResponseStatusException Ako
     * racun sa zadatim identifikatorom ne postoji (HTTP 404 NOT_FOUND).
     */
    Racun vratiRacun(Long id);

    /**
     * Vraca sve racune koje je izdao prodavac sa zadatim identifikatorom.
     *
     * @param idProdavac Identifikator prodavca po kome se racuni filtriraju.
     * @return Lista racuna koje je izdao zadati prodavac.
     * @throws java.lang.NullPointerException Ako je idProdavac null.
     */
    List<Racun> racuniZaProdavca(Long idProdavac);

    /**
     * Vraca sve racune izdate kupcu sa zadatim identifikatorom.
     *
     * @param idKupac Identifikator kupca po kome se racuni filtriraju.
     * @return Lista racuna izdatih zadatom kupcu.
     * @throws java.lang.NullPointerException Ako je idKupac null.
     */
    List<Racun> racuniZaKupca(Long idKupac);
}
