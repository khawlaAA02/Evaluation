package ma.projet.service;

import ma.projet.dao.IDao;
import ma.projet.util.HibernateUtil;
import ma.projet.classes.LigneCommandeProduit;

import org.hibernate.Session;
import org.hibernate.Transaction;
import java.util.List;

public class LigneCommandeService implements IDao<LigneCommandeProduit> {

    @Override
    public LigneCommandeProduit create(LigneCommandeProduit l) {
        Session s = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = s.beginTransaction();
        s.persist(l);
        tx.commit();
        s.close();
        return l;
    }

    @Override
    public LigneCommandeProduit update(LigneCommandeProduit l) {
        Session s = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = s.beginTransaction();
        s.merge(l);
        tx.commit();
        s.close();
        return l;
    }

    @Override
    public void delete(LigneCommandeProduit l) {
        Session s = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = s.beginTransaction();
        s.remove(s.merge(l));
        tx.commit();
        s.close();
    }

    @Override
    public LigneCommandeProduit findById(Long id) {
        throw new UnsupportedOperationException("Clé composite → utiliser requête HQL");
    }

    @Override
    public List<LigneCommandeProduit> findAll() {
        Session s = HibernateUtil.getSessionFactory().openSession();
        List<LigneCommandeProduit> list =
                s.createQuery("from LigneCommandeProduit", LigneCommandeProduit.class).getResultList();
        s.close();
        return list;
    }
}
