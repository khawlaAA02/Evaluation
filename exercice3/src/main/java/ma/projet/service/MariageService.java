package ma.projet.service;

import ma.projet.dao.IDao;
import ma.projet.util.HibernateUtil;
import ma.projet.beans.Mariage;
import org.hibernate.Session; import org.hibernate.Transaction;
import java.util.List;

public class MariageService implements IDao<Mariage> {
    @Override public Mariage create(Mariage m){ var s=HibernateUtil.getSessionFactory().openSession();
        var tx=s.beginTransaction(); s.persist(m); tx.commit(); s.close(); return m; }
    @Override public Mariage update(Mariage m){ var s=HibernateUtil.getSessionFactory().openSession();
        var tx=s.beginTransaction(); s.merge(m); tx.commit(); s.close(); return m; }
    @Override public void delete(Mariage m){ var s=HibernateUtil.getSessionFactory().openSession();
        var tx=s.beginTransaction(); s.remove(s.merge(m)); tx.commit(); s.close(); }
    @Override public Mariage findById(Long id){ var s=HibernateUtil.getSessionFactory().openSession();
        var o=s.get(Mariage.class,id); s.close(); return o; }
    @Override public List<Mariage> findAll(){ var s=HibernateUtil.getSessionFactory().openSession();
        var l=s.createQuery("from Mariage", Mariage.class).getResultList(); s.close(); return l; }
}
