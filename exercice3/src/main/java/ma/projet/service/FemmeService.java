package ma.projet.service;

import ma.projet.dao.IDao;
import ma.projet.util.HibernateUtil;
import ma.projet.beans.Femme;
import ma.projet.beans.Mariage;
import ma.projet.beans.Homme;

import org.hibernate.Session;
import org.hibernate.Transaction;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Path;
import jakarta.persistence.criteria.Root;

import java.time.LocalDate;
import java.util.List;

public class FemmeService implements IDao<Femme> {

    // ===================== CRUD =====================

    @Override
    public Femme create(Femme f) {
        Session s = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = s.beginTransaction();
        try {
            s.persist(f);
            tx.commit();
            return f;
        } finally {
            s.close();
        }
    }

    @Override
    public Femme update(Femme f) {
        Session s = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = s.beginTransaction();
        try {
            s.merge(f);
            tx.commit();
            return f;
        } finally {
            s.close();
        }
    }

    @Override
    public void delete(Femme f) {
        Session s = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = s.beginTransaction();
        try {
            s.remove(s.merge(f));
            tx.commit();
        } finally {
            s.close();
        }
    }

    @Override
    public Femme findById(Long id) {
        Session s = HibernateUtil.getSessionFactory().openSession();
        try {
            return s.get(Femme.class, id);
        } finally {
            s.close();
        }
    }

    @Override
    public List<Femme> findAll() {
        Session s = HibernateUtil.getSessionFactory().openSession();
        try {
            return s.createQuery("from Femme", Femme.class).getResultList();
        } finally {
            s.close();
        }
    }

    // ===================== Méthodes demandées =====================

    /** Requête native nommée (NamedNativeQuery dans Mariage) : nombre d’enfants d’une femme entre deux dates. */
    public int nbEnfantsEntreDates(Long femmeId, LocalDate d1, LocalDate d2) {
        Session s = HibernateUtil.getSessionFactory().openSession();
        try {
            // utilise la version typée pour éviter la dépréciation
            Integer total = s.createNamedQuery("Mariage.nbEnfantsFemmeBetween", Integer.class)
                    .setParameter("fid", femmeId)
                    .setParameter("d1", d1)
                    .setParameter("d2", d2)
                    .getSingleResult();
            return total == null ? 0 : total;
        } finally {
            s.close();
        }
    }

    /** Requête nommée (définie dans l’entité Femme) : femmes mariées au moins deux fois. */
    public List<Femme> marieesAuMoinsDeuxFois() {
        Session s = HibernateUtil.getSessionFactory().openSession();
        try {
            return s.createNamedQuery("Femme.marieesAuMoinsDeuxFois", Femme.class).getResultList();
        } finally {
            s.close();
        }
    }

    /**
     * Criteria API : nombre d’hommes mariés à au moins 4 femmes entre deux dates.
     * Filtrage : dateDebut >= d1 et (dateFin IS NULL ou dateFin <= d2)
     */
    public long nbHommesMarieAQuatreFemmes(LocalDate d1, LocalDate d2) {
        Session s = HibernateUtil.getSessionFactory().openSession();
        try {
            CriteriaBuilder cb = s.getCriteriaBuilder();

            CriteriaQuery<Long> cq = cb.createQuery(Long.class);
            Root<Mariage> root   = cq.from(Mariage.class);

            // paths typés pour éviter les erreurs « JpaPath<Object> »
            Path<Homme>     hommePath    = root.get("homme");
            Path<Femme>     femmePath    = root.get("femme");
            Path<LocalDate> dateDebutPat = root.get("dateDebut");
            Path<LocalDate> dateFinPat   = root.get("dateFin");

            cq.select(cb.countDistinct(hommePath))
                    .where(
                            cb.and(
                                    cb.greaterThanOrEqualTo(dateDebutPat, d1),
                                    cb.or(cb.isNull(dateFinPat), cb.lessThanOrEqualTo(dateFinPat, d2))
                            )
                    )
                    .groupBy(hommePath)
                    .having(cb.ge(cb.countDistinct(femmePath), 4L));

            Long res = s.createQuery(cq).getSingleResult();
            return res == null ? 0L : res;
        } finally {
            s.close();
        }
    }
}
