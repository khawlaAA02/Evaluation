package ma.projet.service;

import ma.projet.dao.IDao;
import ma.projet.util.HibernateUtil;
import ma.projet.classes.Commande;

import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class CommandeService implements IDao<Commande> {

    @Override
    public Commande create(Commande c) {
        Session s = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = s.beginTransaction();
        s.persist(c);
        tx.commit();
        s.close();
        return c;
    }

    @Override
    public Commande update(Commande c) {
        Session s = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = s.beginTransaction();
        s.merge(c);
        tx.commit();
        s.close();
        return c;
    }

    @Override
    public void delete(Commande c) {
        Session s = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = s.beginTransaction();
        s.remove(s.merge(c));
        tx.commit();
        s.close();
    }

    @Override
    public Commande findById(Long id) {
        Session s = HibernateUtil.getSessionFactory().openSession();
        Commande c = s.get(Commande.class, id);
        s.close();
        return c;
    }

    @Override
    public List<Commande> findAll() {
        Session s = HibernateUtil.getSessionFactory().openSession();
        List<Commande> list = s.createQuery("from Commande", Commande.class).getResultList();
        s.close();
        return list;
    }
}
