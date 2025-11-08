package ma.projet.service;


import ma.projet.dao.IDao;
import ma.projet.util.HibernateUtil;
import ma.projet.classes.Categorie;
import org.hibernate.Session;
import org.hibernate.Transaction;
import java.util.List;

public class CategorieService implements IDao<Categorie> {

    @Override
    public Categorie create(Categorie c) {
        Session s = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = s.beginTransaction();
        s.persist(c);
        tx.commit();
        s.close();
        return c;
    }

    @Override
    public Categorie update(Categorie c) {
        Session s = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = s.beginTransaction();
        s.merge(c);
        tx.commit();
        s.close();
        return c;
    }
    public Categorie findByCode(String code){
        var s = HibernateUtil.getSessionFactory().openSession();
        var q = s.createQuery(
                "from Categorie c where c.code = :code", Categorie.class);
        q.setParameter("code", code);
        var list = q.getResultList();
        s.close();
        return list.isEmpty() ? null : list.get(0);
    }

    public Categorie createIfAbsent(String code, String libelle){
        Categorie existing = findByCode(code);
        return existing != null ? existing : create(new Categorie(code, libelle));
    }

    @Override
    public void delete(Categorie c) {
        Session s = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = s.beginTransaction();
        s.remove(s.merge(c));
        tx.commit();
        s.close();
    }

    @Override
    public Categorie findById(Long id) {
        Session s = HibernateUtil.getSessionFactory().openSession();
        Categorie c = s.get(Categorie.class, id);
        s.close();
        return c;
    }

    @Override
    public List<Categorie> findAll() {
        Session s = HibernateUtil.getSessionFactory().openSession();
        List<Categorie> list = s.createQuery("from Categorie", Categorie.class).getResultList();
        s.close();
        return list;
    }
}
