package ma.projet.service;

import ma.projet.dao.IDao;
import ma.projet.util.HibernateUtil;
import ma.projet.classes.*;
import org.hibernate.Session;
import org.hibernate.Transaction;
import java.time.LocalDate;
import java.util.List;

public class TacheService implements IDao<Tache> {

    @Override public Tache create(Tache t){
        Session s=HibernateUtil.getSessionFactory().openSession();
        Transaction tx=s.beginTransaction(); s.persist(t); tx.commit(); s.close(); return t;
    }
    @Override public Tache update(Tache t){
        Session s=HibernateUtil.getSessionFactory().openSession();
        Transaction tx=s.beginTransaction(); s.merge(t); tx.commit(); s.close(); return t;
    }
    @Override public void delete(Tache t){
        Session s=HibernateUtil.getSessionFactory().openSession();
        Transaction tx=s.beginTransaction(); s.remove(s.merge(t)); tx.commit(); s.close();
    }
    @Override public Tache findById(Long id){
        Session s=HibernateUtil.getSessionFactory().openSession();
        Tache t=s.get(Tache.class, id); s.close(); return t;
    }
    @Override public List<Tache> findAll(){
        Session s=HibernateUtil.getSessionFactory().openSession();
        List<Tache> l=s.createQuery("from Tache", Tache.class).getResultList(); s.close(); return l;
    }

    /** NamedQuery : prix > 1000 DH */
    public List<Tache> prixSup1000(){
        Session s=HibernateUtil.getSessionFactory().openSession();
        List<Tache> list = s.createNamedQuery("Tache.findPrixSup1000", Tache.class).getResultList();
        s.close(); return list;
    }

    /** Tâches réalisées entre deux dates réelles (bornes incluses) */
    public List<Object[]> tachesRealiseesEntre(LocalDate d1, LocalDate d2){
        Session s=HibernateUtil.getSessionFactory().openSession();
        List<Object[]> rows = s.createQuery(
                        "select t.id, t.nom, et.dateDebutReelle, et.dateFinReelle, t.prix " +
                                "from EmployeTache et join et.tache t " +
                                "where et.dateDebutReelle >= :d1 and et.dateFinReelle <= :d2 " +
                                "order by et.dateDebutReelle", Object[].class)
                .setParameter("d1", d1).setParameter("d2", d2).getResultList();
        s.close(); return rows;
    }
}
