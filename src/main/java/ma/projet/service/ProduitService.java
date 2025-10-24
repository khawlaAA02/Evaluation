package ma.projet.service;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import ma.projet.classes.Produit;
import ma.projet.dao.IDao;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.List;

@Service
@Transactional
public class ProduitService implements IDao<Produit> {
    @PersistenceContext private EntityManager em;

    @Override public Produit create(Produit o) { em.persist(o); return o; }
    @Override public Produit update(Produit o) { return em.merge(o); }
    @Override public boolean delete(Long id) {
        Produit p = em.find(Produit.class, id);
        if (p != null) { em.remove(p); return true; }
        return false;
    }
    @Override public Produit findById(Long id) { return em.find(Produit.class, id); }
    @Override public List<Produit> findAll() { return em.createQuery("from Produit", Produit.class).getResultList(); }

    public List<Produit> findByCategorie(Long catId) {
        return em.createQuery("from Produit where categorie.id=:x", Produit.class)
                .setParameter("x", catId).getResultList();
    }

    public List<Object[]> findProduitsByCommande(Long cmdId) {
        return em.createQuery(
                        "select p.reference, p.prix, l.quantite " +
                                "from LigneCommandeProduit l join l.produit p where l.commande.id=:x", Object[].class)
                .setParameter("x", cmdId).getResultList();
    }

    public List<Produit> findCommandesBetween(LocalDate d1, LocalDate d2) {
        return em.createQuery(
                        "select distinct p from LigneCommandeProduit l " +
                                "join l.commande c join l.produit p where c.date between :d1 and :d2", Produit.class)
                .setParameter("d1", d1).setParameter("d2", d2).getResultList();
    }

    public List<Produit> findPrixSuperieur(float prix) {
        return em.createQuery("from Produit where prix > :p", Produit.class)
                .setParameter("p", prix).getResultList();
    }
}
