package ma.projet.service;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import ma.projet.classes.Commande;
import ma.projet.dao.IDao;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@Transactional
public class CommandeService implements IDao<Commande> {
    @PersistenceContext private EntityManager em;

    @Override public Commande create(Commande o) { em.persist(o); return o; }
    @Override public Commande update(Commande o) { return em.merge(o); }
    @Override public boolean delete(Long id) {
        Commande c = em.find(Commande.class, id);
        if (c != null) { em.remove(c); return true; }
        return false;
    }
    @Override public Commande findById(Long id) { return em.find(Commande.class, id); }
    @Override public List<Commande> findAll() { return em.createQuery("from Commande", Commande.class).getResultList(); }
}
