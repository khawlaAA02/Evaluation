package ma.projet.service;

import ma.projet.dao.IDao;
import ma.projet.util.HibernateUtil;
import ma.projet.beans.*;
import org.hibernate.Session; import org.hibernate.Transaction;
import java.time.LocalDate; import java.util.List;

public class HommeService implements IDao<Homme> {

    @Override public Homme create(Homme h){ var s=HibernateUtil.getSessionFactory().openSession();
        var tx=s.beginTransaction(); s.persist(h); tx.commit(); s.close(); return h; }
    @Override public Homme update(Homme h){ var s=HibernateUtil.getSessionFactory().openSession();
        var tx=s.beginTransaction(); s.merge(h); tx.commit(); s.close(); return h; }
    @Override public void delete(Homme h){ var s=HibernateUtil.getSessionFactory().openSession();
        var tx=s.beginTransaction(); s.remove(s.merge(h)); tx.commit(); s.close(); }
    @Override public Homme findById(Long id){ var s=HibernateUtil.getSessionFactory().openSession();
        var o=s.get(Homme.class,id); s.close(); return o; }
    @Override public List<Homme> findAll(){ var s=HibernateUtil.getSessionFactory().openSession();
        var l=s.createQuery("from Homme", Homme.class).getResultList(); s.close(); return l; }

    /** Épouses d’un homme entre deux dates (nom/prénom + dates + enfants) */
    public List<Object[]> epousesEntreDates(Long hommeId, LocalDate d1, LocalDate d2){
        var s=HibernateUtil.getSessionFactory().openSession();
        var rows = s.createQuery(
                        "select f.nom, f.prenom, m.dateDebut, m.dateFin, m.nbrEnfant " +
                                "from Mariage m join m.femme f " +
                                "where m.homme.id=:hid and m.dateDebut>=:d1 and (m.dateFin is null or m.dateFin<=:d2) " +
                                "order by m.dateDebut", Object[].class)
                .setParameter("hid", hommeId).setParameter("d1", d1).setParameter("d2", d2)
                .getResultList();
        s.close(); return rows;
    }

    /** Afficher les mariages d’un homme avec détails (exemple demandé) */
    public List<Object[]> mariagesDetail(Long hommeId){
        var s=HibernateUtil.getSessionFactory().openSession();
        var rows = s.createQuery(
                        "select f.nom, f.prenom, m.dateDebut, m.dateFin, m.nbrEnfant " +
                                "from Mariage m join m.femme f where m.homme.id=:hid order by m.dateDebut", Object[].class)
                .setParameter("hid", hommeId).getResultList();
        s.close(); return rows;
    }
}
