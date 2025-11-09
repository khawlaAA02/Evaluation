package ma.projet.service;

import ma.projet.dao.IDao;
import ma.projet.util.HibernateUtil;
import ma.projet.classes.EmployeTache;
import org.hibernate.Session;
import org.hibernate.Transaction;
import java.util.List;

public class EmployeTacheService implements IDao<EmployeTache> {

    @Override public EmployeTache create(EmployeTache et){
        Session s=HibernateUtil.getSessionFactory().openSession();
        Transaction tx=s.beginTransaction(); s.persist(et); tx.commit(); s.close(); return et;
    }
    @Override public EmployeTache update(EmployeTache et){
        Session s=HibernateUtil.getSessionFactory().openSession();
        Transaction tx=s.beginTransaction(); s.merge(et); tx.commit(); s.close(); return et;
    }
    @Override public void delete(EmployeTache et){
        Session s=HibernateUtil.getSessionFactory().openSession();
        Transaction tx=s.beginTransaction(); s.remove(s.merge(et)); tx.commit(); s.close();
    }
    @Override public EmployeTache findById(Long id){
        throw new UnsupportedOperationException("clé composite – requête HQL requise");
    }
    @Override public List<EmployeTache> findAll(){
        Session s=HibernateUtil.getSessionFactory().openSession();
        List<EmployeTache> l=s.createQuery("from EmployeTache", EmployeTache.class).getResultList();
        s.close(); return l;
    }
}
