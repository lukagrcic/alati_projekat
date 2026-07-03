package rs.fon.laptopprodaja.service;

import rs.fon.laptopprodaja.entity.Prodavac;

import java.util.List;

public interface ProdavacServis {
    Prodavac prijaviProdavca(String korisnickoIme, String sifra);
    Prodavac sacuvajProdavca(Prodavac prodavac);
    List<Prodavac> pretraziProdavce(String pretraga);
}
