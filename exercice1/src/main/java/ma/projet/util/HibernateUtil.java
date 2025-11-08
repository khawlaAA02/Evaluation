package ma.projet.util;

import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;

import java.io.IOException;
import java.util.Properties;

import ma.projet.classes.*;

public class HibernateUtil {
    private static final SessionFactory sessionFactory = buildSessionFactory();

    private static SessionFactory buildSessionFactory() {
        try {
            Properties props = new Properties();
            props.load(HibernateUtil.class.getClassLoader()
                    .getResourceAsStream("application.properties"));

            Configuration cfg = new Configuration();
            cfg.setProperties(props);

            cfg.addAnnotatedClass(Categorie.class);
            cfg.addAnnotatedClass(Produit.class);
            cfg.addAnnotatedClass(Commande.class);
            cfg.addAnnotatedClass(LigneCommandeProduit.class);
            cfg.addAnnotatedClass(LigneCommandeProduitId.class);

            return cfg.buildSessionFactory(
                    new StandardServiceRegistryBuilder()
                            .applySettings(cfg.getProperties())
                            .build()
            );
        } catch (IOException e) {
            throw new RuntimeException("Erreur chargement application.properties", e);
        } catch (Exception e) {
            throw new RuntimeException("Erreur construction SessionFactory", e);
        }
    }

    // ⬇️ C’EST CETTE MÉTHODE QUI DOIT EXISTER
    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }
}
