package utfpr.ct.dainf.if62c.avaliacao;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Timer;

/**
 * IF62C Fundamentos de Programação 2 Avaliação parcial.
 *
 * @author
 */
public class Agenda {

    private final String descricao;
    private final List<Compromisso> comps = new ArrayList<>();
    private final Timer timer;

    public Agenda(String descricao) {
        this.descricao = descricao;
        timer = new Timer(descricao);
    }

    public String getDescricao() {
        return descricao;
    }

    public List<Compromisso> getCompromissos() {
        return comps;
    }

    public void novoCompromisso(Compromisso comp ){
        comps.add(comp);
        Aviso aviso = new AvisoFinal(comp);
        comp.registraAviso(aviso);
        timer.schedule(aviso, comp.getData());
    }

    public void novoAviso(Compromisso comp, int ant) {
        Date date = comp.getData();
        Aviso av = new Aviso(comp);
        AvisoFinal af = new AvisoFinal(comp);
        comp.registraAviso(av);
        Timer timer = new Timer(comp.getDescricao());
        timer.schedule(av, new Date(date.getTime() - ant * 1000));
        timer.schedule(af, date);
    }

    public void novoAviso(Compromisso comp, int antecedencia, int intervalo) {
        Date date = comp.getData();
        Aviso av = new Aviso(comp);
        AvisoFinal af = new AvisoFinal(comp);
        comp.registraAviso(av);
        Timer timer = new Timer(comp.getDescricao());
        timer.schedule(av, new Date(date.getTime() - antecedencia * 1000), intervalo * 1000);
        timer.schedule(af, date);
    }

    public void cancela(Compromisso comp) {
        List<Aviso> av = comp.getAvisos();
        for (Aviso a : av) {
            cancela(a);
        }
        comps.remove(comp);
    }

    public void cancela(Aviso aviso) {
        aviso.cancel();
        aviso.getCompromisso().removeAviso(aviso);
    }

    public void destroi() {
        for (Compromisso c : comps) {
            cancela(c);
        }
    }
}
