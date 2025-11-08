package ma.projet.service;

import ma.projet.dao.IDao;
import ma.projet.util.HibernateUtil;
import ma.projet.classes.*;

import org.hibernate.Session;
import org.hibernate.Transaction;

import java.time.LocalDate;
import java.util.List;

public class ProduitService implements IDao<Produit> {

    @Override
    public Produit create(Produit p) {
        Session s = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = s.beginTransaction();
        s.persist(p);
        tx.commit();
        s.close();
        return p;
    }

    @Override
    public Produit update(Produit p) {
        Session s = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = s.beginTransaction();
        s.merge(p);
        tx.commit();
        s.close();
        return p;
    }

    @Override
    public void delete(Produit p) {
        Session s = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = s.beginTransaction();

        s.remove(s.merge(p));
        tx.commit();
        s.close();
    }

    @Override
    public Produit findById(Long id) {
        Session s = HibernateUtil.getSessionFactory().openSession();
        Produit p = s.get(Produit.class, id);
        s.close();
        return p;
    }

    @Override
    public List<Produit> findAll() {
        Session s = HibernateUtil.getSessionFactory().openSession();
        List<Produit> list = s.createQuery("from Produit", Produit.class).getResultList();
        s.close();
        return list;
    }

    // Produits par catégorie
    public List<Produit> findByCategorie(Long categorieId){
        Session s = HibernateUtil.getSessionFactory().openSession();
        List<Produit> list = s.createQuery(
                        "select p from Produit p where p.categorie.id = :cid", Produit.class)
                .setParameter("cid", categorieId)
                .getResultList();
        s.close();
        return list;
    }

    // Produits commandés entre deux dates
    public List<Object[]> produitsCommandesEntre(LocalDate d1, LocalDate d2){
        Session s = HibernateUtil.getSessionFactory().openSession();
        List<Object[]> rows = s.createQuery(
                        "select p.reference, p.prix, sum(l.quantite) " +
                                "from LigneCommandeProduit l join l.produit p join l.commande c " +
                                "where c.date between :d1 and :d2 " +
                                "group by p.reference, p.prix", Object[].class)
                .setParameter("d1", d1)
                .setParameter("d2", d2)
                .getResultList();
        s.close();
        return rows;
    }

    // Produits dans une commande
    public List<Object[]> produitsParCommande(Long cid){
        Session s = HibernateUtil.getSessionFactory().openSession();
        List<Object[]> rows = s.createQuery(
                        "select p.reference, p.prix, l.quantite, c.date " +
                                "from LigneCommandeProduit l join l.produit p join l.commande c " +
                                "where c.id = :cid", Object[].class)
                .setParameter("cid", cid)
                .getResultList();
        s.close();
        return rows;
    }

    // Price > 100 via NamedQuery
    public List<Produit> produitsPrixSup100(){
        Session s = HibernateUtil.getSessionFactory().openSession();
        List<Produit> list = s.createNamedQuery("Produit.findPrixSup100", Produit.class).getResultList();
        s.close();
        return list;
    }
}
