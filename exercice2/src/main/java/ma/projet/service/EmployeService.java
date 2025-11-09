package ma.projet.service;

import ma.projet.dao.IDao;
import ma.projet.util.HibernateUtil;
import ma.projet.classes.Employe;
import ma.projet.classes.Projet;

import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class EmployeService implements IDao<Employe> {

    @Override
    public Employe create(Employe e) {
        Session s = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = s.beginTransaction();
        s.persist(e);
        tx.commit();
        s.close();
        return e;
    }

    @Override
    public Employe update(Employe e) {
        Session s = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = s.beginTransaction();
        s.merge(e);
        tx.commit();
        s.close();
        return e;
    }

    @Override
    public void delete(Employe e) {
        Session s = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = s.beginTransaction();
        s.remove(s.merge(e));
        tx.commit();
        s.close();
    }

    @Override
    public Employe findById(Long id) {
        Session s = HibernateUtil.getSessionFactory().openSession();
        Employe e = s.get(Employe.class, id);
        s.close();
        return e;
    }

    @Override
    public List<Employe> findAll() {
        Session s = HibernateUtil.getSessionFactory().openSession();
        List<Employe> list = s.createQuery("from Employe", Employe.class).getResultList();
        s.close();
        return list;
    }


    public List<Object[]> tachesRealiseesParEmploye(Long employeId){
        Session s = HibernateUtil.getSessionFactory().openSession();
        List<Object[]> rows = s.createQuery(
                        "select t.id, t.nom, et.dateDebutReelle, et.dateFinReelle " +
                                "from EmployeTache et join et.tache t " +
                                "where et.employe.id = :id " +
                                "order by et.dateDebutReelle nulls last, t.nom", Object[].class)
                .setParameter("id", employeId)
                .getResultList();
        s.close();
        return rows;
    }


    public List<Projet> projetsGeres(Long employeId){
        Session s = HibernateUtil.getSessionFactory().openSession();
        List<Projet> list = s.createQuery(
                        "from Projet p where p.chef.id = :id order by p.dateDebut", Projet.class)
                .setParameter("id", employeId)
                .getResultList();
        s.close();
        return list;
    }
}
