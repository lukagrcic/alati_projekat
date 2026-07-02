package rs.fon.laptopprodaja.service.impl;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import rs.fon.laptopprodaja.entity.FizickoLice;
import rs.fon.laptopprodaja.entity.Kupac;
import rs.fon.laptopprodaja.entity.PravnoLice;
import rs.fon.laptopprodaja.repository.KupacRepository;
import rs.fon.laptopprodaja.service.KupacServis;

import java.util.List;

@Service
public class KupacServisImpl implements KupacServis {

    private final KupacRepository kupacRepository;

    public KupacServisImpl(KupacRepository kupacRepository) {
        this.kupacRepository = kupacRepository;
    }

    @Override
    public Kupac kreirajKupca(Kupac kupac) {
        if (kupac == null) throw new NullPointerException("Kupac ne sme biti null");
        kupac.setIdKupac(null);
        return kupacRepository.save(kupac);
    }

    @Override
    public Kupac izmeniKupca(Long id, Kupac izmene) {
        if (id == null) throw new NullPointerException("ID ne sme biti null");
        if (izmene == null) throw new NullPointerException("Izmene ne smeju biti null");
        Kupac postojeci = vratiKupca(id);
        if (!postojeci.getClass().equals(izmene.getClass())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Ne moze se promeniti tip kupca");
        }
        postojeci.setEmail(izmene.getEmail());
        postojeci.setTelefon(izmene.getTelefon());
        postojeci.setGrad(izmene.getGrad());

        if (postojeci instanceof FizickoLice fl && izmene instanceof FizickoLice izmFl) {
            fl.setIme(izmFl.getIme());
            fl.setPrezime(izmFl.getPrezime());
            fl.setJmbg(izmFl.getJmbg());
        } else if (postojeci instanceof PravnoLice pl && izmene instanceof PravnoLice izmPl) {
            pl.setNaziv(izmPl.getNaziv());
            pl.setPib(izmPl.getPib());
            pl.setMaticniBroj(izmPl.getMaticniBroj());
        }
        return kupacRepository.save(postojeci);
    }

    @Override
    public void obrisiKupca(Long id) {
        if (id == null) throw new NullPointerException("ID ne sme biti null");
        if (!kupacRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Kupac ne postoji: " + id);
        }
        kupacRepository.deleteById(id);
    }

    @Override
    public List<Kupac> pretraziKupce() {
        return kupacRepository.findAll();
    }

    @Override
    public Kupac vratiKupca(Long id) {
        if (id == null) throw new NullPointerException("ID ne sme biti null");
        return kupacRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Kupac ne postoji: " + id));
    }

    @Override
    public List<Kupac> kupciPoGradu(Long idGrad) {
        if (idGrad == null) throw new NullPointerException("ID grada ne sme biti null");
        return kupacRepository.findByGrad_IdGrad(idGrad);
    }

    @Override
    public List<Kupac> kupciPoTipu(String tip) {
        if (tip == null) throw new NullPointerException("Tip kupca ne sme biti null");
        return kupacRepository.findByTipKupca(tip);
    }
}
