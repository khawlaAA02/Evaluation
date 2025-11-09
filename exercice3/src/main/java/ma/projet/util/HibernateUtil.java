package ma.projet.util;

import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import java.util.Properties;
import java.io.IOException;

import ma.projet.beans.*;

public class HibernateUtil {
    private static final SessionFactory sessionFactory = build();

    private static SessionFactory build() {
        try {
            Properties p = new Properties();
            p.load(HibernateUtil.class.getClassLoader().getResourceAsStream("application.properties"));

            Configuration cfg = new Configuration().setProperties(p);
            cfg.addAnnotatedClass(Personne.class);
            cfg.addAnnotatedClass(Homme.class);
            cfg.addAnnotatedClass(Femme.class);
            cfg.addAnnotatedClass(Mariage.class);
            return cfg.buildSessionFactory(new StandardServiceRegistryBuilder().applySettings(cfg.getProperties()).build());
        } catch (IOException e) {
            throw new RuntimeException("application.properties introuvable", e);
        }
    }
    public static SessionFactory getSessionFactory(){ return sessionFactory; }
}
