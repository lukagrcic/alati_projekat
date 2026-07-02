package rs.fon.laptopprodaja.service;

import rs.fon.laptopprodaja.entity.Prodavac;

import java.time.LocalDate;
import java.util.List;

public interface ProdavacServis {
    Prodavac prijaviProdavca(String korisnickoIme, String sifra);
    Prodavac dodajStrucnuSpremuProdavcu(Long idProdavac, Long idStrucnaSprema, LocalDate datum);
    Prodavac sacuvajProdavca(Prodavac prodavac);
    List<Prodavac> pretraziProdavce(String pretraga);
}
