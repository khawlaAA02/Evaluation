package ma.projet.service;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import ma.projet.classes.Categorie;
import ma.projet.dao.IDao;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class CategorieService implements IDao<Categorie> {

    @PersistenceContext
    private EntityManager em;

    @Override
    public Categorie create(Categorie o) {
        // Ne pas recréer si le code existe déjà
        Optional<Categorie> existante = findByCode(o.getCode());
        if (existante.isPresent()) {
            return existante.get();
        }
        em.persist(o);
        return o;
    }

    public Optional<Categorie> findByCode(String code) {
        List<Categorie> list = em.createQuery(
                        "select c from Categorie c where c.code = :code", Categorie.class)
                .setParameter("code", code)
                .getResultList();
        return list.isEmpty() ? Optional.empty() : Optional.of(list.get(0));
    }

    @Override public Categorie update(Categorie o) { return em.merge(o); }
    @Override public boolean delete(Long id) {
        Categorie c = em.find(Categorie.class, id);
        if (c == null) return false;
        em.remove(c); return true;
    }
    @Override public Categorie findById(Long id) { return em.find(Categorie.class, id); }
    @Override public List<Categorie> findAll() {
        return em.createQuery("from Categorie", Categorie.class).getResultList();
    }
}
