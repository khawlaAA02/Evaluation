package ma.projet.test;

import ma.projet.classes.*;
import ma.projet.service.*;

import java.time.LocalDate;
import java.util.List;

public class AppTest2 {
    public static void main(String[] args) {

        EmployeService empSrv = new EmployeService();
        ProjetService  prjSrv = new ProjetService();
        TacheService   tSrv   = new TacheService();

        // Données
        Employe chef = empSrv.create(new Employe("ELM", "Ayoub", "0600"));
        Employe e1   = empSrv.create(new Employe("ATTNI", "Khawla", "0601"));
        Employe e2   = empSrv.create(new Employe("EDA", "Mouna", "0602"));

        Projet p = prjSrv.create(new Projet(
                "Gestion de stock",
                LocalDate.of(2013,1,14),
                LocalDate.of(2013,5,1),
                chef));

        Tache t1 = tSrv.create(new Tache("Analyse",       LocalDate.of(2013,2,10), LocalDate.of(2013,2,20), 1500, p));
        Tache t2 = tSrv.create(new Tache("Conception",    LocalDate.of(2013,3,10), LocalDate.of(2013,3,15), 2000, p));
        Tache t3 = tSrv.create(new Tache("Développement", LocalDate.of(2013,4,10), LocalDate.of(2013,4,25), 5000, p));

        // Réalisations (EmployeTache)
        new EmployeTacheService().create(new EmployeTache(e1, t1, LocalDate.of(2013,2,10), LocalDate.of(2013,2,20)));
        new EmployeTacheService().create(new EmployeTache(e2, t2, LocalDate.of(2013,3,10), LocalDate.of(2013,3,15)));
        new EmployeTacheService().create(new EmployeTache(e2, t3, LocalDate.of(2013,4,10), LocalDate.of(2013,4,25)));

        // ----- Affichage demandé -----
        System.out.println("Projet : " + p.getId() + "     Nom : " + p.getNom() +
                "     Date début : " + p.getDateDebut());
        System.out.println("Liste des tâches:");
        System.out.println("Num  Nom            Date Début Réelle   Date Fin Réelle");

        List<Object[]> rows = prjSrv.tachesRealiseesAvecDates(p.getId());
        for(Object[] r: rows){
            System.out.printf("%-4s %-14s %-18s %-14s%n", r[0], r[1], r[2], r[3]);
        }

        // Exemples des méthodes demandées :
        System.out.println("\nTâches planifiées du projet:");
        prjSrv.tachesPlanifiees(p.getId()).forEach(t -> System.out.println(t.getId()+" "+t.getNom()));

        System.out.println("\nTâches réalisées par " + e2.getPrenom() + " :");
        empSrv.tachesRealiseesParEmploye(e2.getId()).forEach(r ->
                System.out.println(r[0]+" "+r[1]+" "+r[2]+" "+r[3]));

        System.out.println("\nProjets gérés par le chef " + chef.getPrenom() + " :");
        empSrv.projetsGeres(chef.getId()).forEach(pp -> System.out.println(pp.getId()+" "+pp.getNom()));

        System.out.println("\nTâches prix > 1000 (NamedQuery):");
        tSrv.prixSup1000().forEach(t -> System.out.println(t.getId()+" "+t.getNom()+" "+t.getPrix()));

        System.out.println("\nTâches réalisées entre 2013-02-01 et 2013-03-31 :");
        tSrv.tachesRealiseesEntre(LocalDate.of(2013,2,1), LocalDate.of(2013,3,31))
                .forEach(r -> System.out.println(r[0]+" "+r[1]+" "+r[2]+" "+r[3]+" prix="+r[4]));
    }
}
