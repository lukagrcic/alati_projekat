package rs.fon.laptopprodaja.service.impl;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import rs.fon.laptopprodaja.entity.PrSS;
import rs.fon.laptopprodaja.entity.Prodavac;
import rs.fon.laptopprodaja.entity.StrSprema;
import rs.fon.laptopprodaja.repository.ProdavacRepository;
import rs.fon.laptopprodaja.repository.StrSpremaRepository;
import rs.fon.laptopprodaja.service.ProdavacServis;

import java.time.LocalDate;
import java.util.List;

@Service
public class ProdavacServisImpl implements ProdavacServis {

    private final ProdavacRepository prodavacRepository;
    private final StrSpremaRepository strSpremaRepository;

    public ProdavacServisImpl(ProdavacRepository prodavacRepository, StrSpremaRepository strSpremaRepository) {
        this.prodavacRepository = prodavacRepository;
        this.strSpremaRepository = strSpremaRepository;
    }

    @Override
    public Prodavac prijaviProdavca(String korisnickoIme, String sifra) {
        if (korisnickoIme == null) throw new NullPointerException("Korisnicko ime ne sme biti null");
        if (sifra == null) throw new NullPointerException("Sifra ne sme biti null");
        Prodavac prodavac = prodavacRepository.findByKorisnickoIme(korisnickoIme)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Pogresno korisnicko ime ili sifra"));
        if (!prodavac.getSifra().equals(sifra)) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Pogresno korisnicko ime ili sifra");
        }
        return prodavac;
    }

    @Override
    public Prodavac dodajStrucnuSpremuProdavcu(Long idProdavac, Long idStrucnaSprema, LocalDate datum) {
        if (idProdavac == null) throw new NullPointerException("ID prodavca ne sme biti null");
        if (idStrucnaSprema == null) throw new NullPointerException("ID strucne spreme ne sme biti null");
        Prodavac prodavac = prodavacRepository.findById(idProdavac)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Prodavac ne postoji: " + idProdavac));
        StrSprema strSprema = strSpremaRepository.findById(idStrucnaSprema)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Strucna sprema ne postoji: " + idStrucnaSprema));

        PrSS veza = new PrSS(prodavac, strSprema, datum != null ? datum : LocalDate.now());
        prodavac.getStrucneSpreme().add(veza);
        return prodavacRepository.save(prodavac);
    }

    @Override
    public Prodavac sacuvajProdavca(Prodavac prodavac) {
        if (prodavac == null) throw new NullPointerException("Prodavac ne sme biti null");
        return prodavacRepository.save(prodavac);
    }

    @Override
    public List<Prodavac> pretraziProdavce(String pretraga) {
        if (pretraga == null || pretraga.isBlank()) {
            return prodavacRepository.findAll();
        }
        return prodavacRepository.findByImeContainingIgnoreCaseOrPrezimeContainingIgnoreCase(pretraga, pretraga);
    }
}
