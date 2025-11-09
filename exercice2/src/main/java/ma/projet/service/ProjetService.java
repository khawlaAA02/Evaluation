package ma.projet.service;

import ma.projet.dao.IDao;
import ma.projet.util.HibernateUtil;
import ma.projet.classes.*;
import org.hibernate.Session;
import org.hibernate.Transaction;
import java.util.List;

public class ProjetService implements IDao<Projet> {

    @Override public Projet create(Projet p){
        Session s=HibernateUtil.getSessionFactory().openSession();
        Transaction tx=s.beginTransaction(); s.persist(p); tx.commit(); s.close(); return p;
    }
    @Override public Projet update(Projet p){
        Session s=HibernateUtil.getSessionFactory().openSession();
        Transaction tx=s.beginTransaction(); s.merge(p); tx.commit(); s.close(); return p;
    }
    @Override public void delete(Projet p){
        Session s=HibernateUtil.getSessionFactory().openSession();
        Transaction tx=s.beginTransaction(); s.remove(s.merge(p)); tx.commit(); s.close();
    }
    @Override public Projet findById(Long id){
        Session s=HibernateUtil.getSessionFactory().openSession();
        Projet p=s.get(Projet.class, id); s.close(); return p;
    }
    @Override public List<Projet> findAll(){
        Session s=HibernateUtil.getSessionFactory().openSession();
        List<Projet> l=s.createQuery("from Projet", Projet.class).getResultList(); s.close(); return l;
    }

    /** Tâches planifiées d’un projet */
    public List<Tache> tachesPlanifiees(Long projetId){
        Session s=HibernateUtil.getSessionFactory().openSession();
        List<Tache> list = s.createQuery(
                        "from Tache t where t.projet.id = :pid order by t.dateDebut", Tache.class)
                .setParameter("pid", projetId).getResultList();
        s.close(); return list;
    }

    /** Tâches réalisées (avec dates réelles) d’un projet */
    public List<Object[]> tachesRealiseesAvecDates(Long projetId){
        Session s=HibernateUtil.getSessionFactory().openSession();
        List<Object[]> rows = s.createQuery(
                        "select t.id, t.nom, et.dateDebutReelle, et.dateFinReelle " +
                                "from EmployeTache et join et.tache t " +
                                "where t.projet.id = :pid " +
                                "order by et.dateDebutReelle nulls last, t.nom", Object[].class)
                .setParameter("pid", projetId).getResultList();
        s.close(); return rows;
    }
}
