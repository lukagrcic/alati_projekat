package rs.fon.laptopprodaja.service;

import rs.fon.laptopprodaja.entity.Kupac;

import java.util.List;

public interface KupacServis {
    Kupac kreirajKupca(Kupac kupac);
    Kupac izmeniKupca(Long id, Kupac izmene);
    void obrisiKupca(Long id);
    List<Kupac> pretraziKupce();
    Kupac vratiKupca(Long id);
    List<Kupac> kupciPoGradu(Long idGrad);
    List<Kupac> kupciPoTipu(String tip);
}
