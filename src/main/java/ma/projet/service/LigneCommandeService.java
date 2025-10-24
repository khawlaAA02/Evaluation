package ma.projet.service;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import ma.projet.classes.Commande;
import ma.projet.classes.Produit;
import ma.projet.classes.LigneCommandeProduit;
import ma.projet.classes.LigneCommandeProduitId;
import ma.projet.dao.IDao;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class LigneCommandeService implements IDao<LigneCommandeProduit> {

    @PersistenceContext
    private EntityManager em;

    @Override
    public LigneCommandeProduit create(LigneCommandeProduit o) {
        // Ré-attacher les entités liées pour éviter "detached entity passed to persist"
        if (o.getCommande() != null && o.getCommande().getId() != null) {
            o.setCommande(em.getReference(Commande.class, o.getCommande().getId()));
        }
        if (o.getProduit() != null && o.getProduit().getId() != null) {
            o.setProduit(em.getReference(Produit.class, o.getProduit().getId()));
        }
        em.persist(o);
        return o;
    }

    @Override
    public LigneCommandeProduit update(LigneCommandeProduit o) {
        if (o.getCommande() != null && o.getCommande().getId() != null) {
            o.setCommande(em.getReference(Commande.class, o.getCommande().getId()));
        }
        if (o.getProduit() != null && o.getProduit().getId() != null) {
            o.setProduit(em.getReference(Produit.class, o.getProduit().getId()));
        }
        return em.merge(o);
    }

    // L'ID est composite -> on ne l'utilise pas via Long
    @Override
    public boolean delete(Long id) { return false; }

    @Override
    public LigneCommandeProduit findById(Long id) { return null; }

    @Override
    public List<LigneCommandeProduit> findAll() {
        return em.createQuery("from LigneCommandeProduit", LigneCommandeProduit.class).getResultList();
    }

    // Méthode utilitaire adaptée à la clé composite
    public boolean deleteById(Long commandeId, Long produitId) {
        LigneCommandeProduitId key = new LigneCommandeProduitId(commandeId, produitId);
        LigneCommandeProduit lcp = em.find(LigneCommandeProduit.class, key);
        if (lcp == null) return false;
        em.remove(lcp);
        return true;
    }
}
