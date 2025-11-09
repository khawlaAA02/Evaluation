package ma.projet.util;

import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;

import java.io.IOException;
import java.util.Properties;

// Entités de l'exercice 2
import ma.projet.classes.Employe;
import ma.projet.classes.Projet;
import ma.projet.classes.Tache;
import ma.projet.classes.EmployeTache;
import ma.projet.classes.EmployeTacheId;

public class HibernateUtil {

    private static final SessionFactory sessionFactory = buildSessionFactory();

    private static SessionFactory buildSessionFactory() {
        try {
            // Charger application.properties (src/main/resources)
            Properties props = new Properties();
            props.load(
                    HibernateUtil.class.getClassLoader()
                            .getResourceAsStream("application.properties")
            );

            // Config Hibernate + enregistrement des entités
            Configuration cfg = new Configuration();
            cfg.setProperties(props);

            cfg.addAnnotatedClass(Employe.class);
            cfg.addAnnotatedClass(Projet.class);
            cfg.addAnnotatedClass(Tache.class);
            cfg.addAnnotatedClass(EmployeTache.class);
            cfg.addAnnotatedClass(EmployeTacheId.class);

            return cfg.buildSessionFactory(
                    new StandardServiceRegistryBuilder()
                            .applySettings(cfg.getProperties())
                            .build()
            );

        } catch (IOException e) {
            throw new RuntimeException("Impossible de charger application.properties", e);
        } catch (Exception e) {
            throw new RuntimeException("Erreur de construction de la SessionFactory", e);
        }
    }

    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }
}
