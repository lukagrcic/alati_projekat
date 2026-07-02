package rs.fon.laptopprodaja.service.impl;

import org.springframework.stereotype.Service;
import rs.fon.laptopprodaja.entity.Grad;
import rs.fon.laptopprodaja.entity.StrSprema;
import rs.fon.laptopprodaja.repository.GradRepository;
import rs.fon.laptopprodaja.repository.StrSpremaRepository;
import rs.fon.laptopprodaja.service.SifarnikServis;

import java.util.List;

@Service
public class SifarnikServisImpl implements SifarnikServis {

    private final GradRepository gradRepository;
    private final StrSpremaRepository strSpremaRepository;

    public SifarnikServisImpl(GradRepository gradRepository, StrSpremaRepository strSpremaRepository) {
        this.gradRepository = gradRepository;
        this.strSpremaRepository = strSpremaRepository;
    }

    @Override
    public List<Grad> ucitajGradove() {
        return gradRepository.findAll();
    }

    @Override
    public Grad kreirajGrad(Grad grad) {
        if (grad == null) throw new NullPointerException("Grad ne sme biti null");
        return gradRepository.save(grad);
    }

    @Override
    public List<StrSprema> ucitajStrucneSpreme() {
        return strSpremaRepository.findAll();
    }

    @Override
    public StrSprema kreirajStrucnuSpremu(StrSprema strSprema) {
        if (strSprema == null) throw new NullPointerException("Strucna sprema ne sme biti null");
        return strSpremaRepository.save(strSprema);
    }
}
