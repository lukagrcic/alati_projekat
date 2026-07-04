package rs.fon.laptopprodaja.service.impl;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import rs.fon.laptopprodaja.entity.Prodavac;
import rs.fon.laptopprodaja.entity.StrSprema;
import rs.fon.laptopprodaja.repository.ProdavacRepository;
import rs.fon.laptopprodaja.repository.StrSpremaRepository;
import rs.fon.laptopprodaja.service.ProdavacServis;

import java.time.LocalDate;
import java.util.List;

/**
 * Implementira ProdavacServis koristeci ProdavacRepository za pristup
 * podacima o prodavcima i StrSpremaRepository za pristup podacima o
 * strucnim spremama.
 *
 * @author Luka Grcic
 * @version 1.0
 */
@Service
public class ProdavacServisImpl implements ProdavacServis {

    /** Repozitorijum za pristup podacima o prodavcima. */
    private final ProdavacRepository prodavacRepository;
    /** Repozitorijum za pristup podacima o strucnim spremama. */
    private final StrSpremaRepository strSpremaRepository;

    /**
     * Kreira novu instancu servisa sa zadatim repozitorijumima.
     *
     * @param prodavacRepository Repozitorijum za pristup podacima o prodavcima.
     * @param strSpremaRepository Repozitorijum za pristup podacima o strucnim spremama.
     */
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
