package rs.fon.laptopprodaja.service;

import rs.fon.laptopprodaja.entity.Prodavac;

import java.util.List;

/**
 * Interfejs koji definise poslovnu logiku za rad sa prodavcima.
 *
 * @author Luka Grcic
 * @version 1.0
 */
public interface ProdavacServis {

    /**
     * Prijavljuje prodavca na osnovu korisnickog imena i sifre.
     *
     * @param korisnickoIme Korisnicko ime prodavca koji se prijavljuje.
     * @param sifra Sifra prodavca koji se prijavljuje.
     * @return Prodavac ciji su korisnicko ime i sifra zadati.
     * @throws java.lang.NullPointerException Ako je korisnickoIme null ili
     * ako je sifra null.
     * @throws org.springframework.web.server.ResponseStatusException Ako ne
     * postoji prodavac sa zadatim korisnickim imenom, ili ako zadata sifra
     * ne odgovara sifri prodavca (HTTP 401 UNAUTHORIZED).
     */
    Prodavac prijaviProdavca(String korisnickoIme, String sifra);

    /**
     * Cuva prodavca. Koristi se i za kreiranje novog i za izmenu postojeceg
     * prodavca, u zavisnosti od toga da li prosledjeni prodavac ima
     * dodeljen identifikator.
     *
     * @param prodavac Prodavac koji se cuva.
     * @return Sacuvani prodavac.
     * @throws java.lang.NullPointerException Ako je prodavac null.
     */
    Prodavac sacuvajProdavca(Prodavac prodavac);

    /**
     * Pretrazuje prodavce po imenu ili prezimenu. Ako je pretraga null ili
     * prazna (odnosno sadrzi samo razmake), vracaju se svi prodavci.
     *
     * @param pretraga Tekst po kome se pretrazuju ime i prezime prodavca.
     * @return Lista prodavaca ciji ime ili prezime sadrzi zadati tekst (bez
     * obzira na velika i mala slova), odnosno svi prodavci ako pretraga
     * nije zadata.
     */
    List<Prodavac> pretraziProdavce(String pretraga);
}
