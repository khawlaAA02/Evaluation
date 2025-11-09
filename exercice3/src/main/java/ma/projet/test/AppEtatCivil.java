package ma.projet.test;

import ma.projet.beans.*;
import ma.projet.service.*;
import java.time.LocalDate;
import java.util.List;

public class AppEtatCivil {
    public static void main(String[] args) {

        var hSrv = new HommeService();
        var fSrv = new FemmeService();
        var mSrv = new MariageService();

        // 5 hommes
        Homme h1 = hSrv.create(new Homme()); h1.setNom("SAFI"); h1.setPrenom("SAID"); hSrv.update(h1);
        Homme h2 = hSrv.create(new Homme()); h2.setNom("ELM");  h2.setPrenom("Youssef"); hSrv.update(h2);
        Homme h3 = hSrv.create(new Homme()); h3.setNom("NADA"); h3.setPrenom("Ali");     hSrv.update(h3);
        Homme h4 = hSrv.create(new Homme()); h4.setNom("KAMAL");h4.setPrenom("Reda");    hSrv.update(h4);
        Homme h5 = hSrv.create(new Homme()); h5.setNom("OMAR"); h5.setPrenom("Amine");   hSrv.update(h5);

        // 10 femmes
        Femme f1=fSrv.create(new Femme()); f1.setNom("SALIMA"); f1.setPrenom("RAMI"); fSrv.update(f1);
        Femme f2=fSrv.create(new Femme()); f2.setNom("AMAL");   f2.setPrenom("ALI");  fSrv.update(f2);
        Femme f3=fSrv.create(new Femme()); f3.setNom("WAFA");   f3.setPrenom("ALAOUI"); fSrv.update(f3);
        Femme f4=fSrv.create(new Femme()); f4.setNom("KARIMA"); f4.setPrenom("ALAMI");  fSrv.update(f4);
        Femme f5=fSrv.create(new Femme()); f5.setNom("LINA");   f5.setPrenom("SAAD");   fSrv.update(f5);
        Femme f6=fSrv.create(new Femme()); f6.setNom("NORA");   f6.setPrenom("OMARI");  fSrv.update(f6);
        Femme f7=fSrv.create(new Femme()); f7.setNom("HIBA");   f7.setPrenom("AZIZ");   fSrv.update(f7);
        Femme f8=fSrv.create(new Femme()); f8.setNom("DUAA");   f8.setPrenom("TAHA");   fSrv.update(f8);
        Femme f9=fSrv.create(new Femme()); f9.setNom("SABAH");  f9.setPrenom("MOURAD"); fSrv.update(f9);
        Femme f10=fSrv.create(new Femme());f10.setNom("ILHAM"); f10.setPrenom("SAMI");  fSrv.update(f10);

        // Mariages de h1 (exemple du sujet)
        mSrv.create(new Mariage(h1,f4, LocalDate.of(1989,9,3), LocalDate.of(1990,9,3), 0));  // échoué
        mSrv.create(new Mariage(h1,f1, LocalDate.of(1990,9,3), null, 4));
        mSrv.create(new Mariage(h1,f2, LocalDate.of(1995,9,3), null, 2));
        mSrv.create(new Mariage(h1,f3, LocalDate.of(2000,11,4), null, 3));

        // Quelques mariages pour autres tests
        mSrv.create(new Mariage(h2,f5, LocalDate.of(2010,1,1), null, 1));
        mSrv.create(new Mariage(h2,f6, LocalDate.of(2011,1,1), null, 2));
        mSrv.create(new Mariage(h2,f7, LocalDate.of(2012,1,1), null, 3));
        mSrv.create(new Mariage(h2,f8, LocalDate.of(2013,1,1), null, 0)); // h2 a 4 femmes

        // ---- Affichages demandés ----
        System.out.println("Liste des femmes :");
        for(Femme f : fSrv.findAll()){
            System.out.println(" - " + f.getNom() + " " + f.getPrenom());
        }

        // Femme la plus âgée (tri sur dateNaissance non saisie ici → à compléter si besoin)

        // Épouses d'un homme donné entre 1989 et 2001
        System.out.println("\nEpouses de " + h1.getNom()+" "+h1.getPrenom()+" entre 1989 et 2001:");
        var ep = hSrv.epousesEntreDates(h1.getId(), LocalDate.of(1989,1,1), LocalDate.of(2001,12,31));
        for(Object[] r: ep){
            System.out.println("Femme: " + r[0]+" "+r[1] + "  Début: "+r[2]+"  Fin: "+r[3]+"  Enfants: "+r[4]);
        }

        // Nb d'enfants d'une femme entre 1990 et 2000 (native named)
        int nb = fSrv.nbEnfantsEntreDates(f1.getId(), LocalDate.of(1990,1,1), LocalDate.of(2000,12,31));
        System.out.println("\nEnfants de " + f1.getNom()+" "+f1.getPrenom()+" entre 1990 et 2000 = " + nb);

        // Femmes mariées au moins deux fois
        System.out.println("\nFemmes mariées >= 2 fois :");
        for(Femme f : fSrv.marieesAuMoinsDeuxFois()){
            System.out.println(" - " + f.getNom()+" "+f.getPrenom());
        }

        // Nombre d'hommes mariés à 4 femmes entre 2010 et 2015 (Criteria)
        long nbH = fSrv.nbHommesMarieAQuatreFemmes(LocalDate.of(2010,1,1), LocalDate.of(2015,12,31));
        System.out.println("\nHommes mariés à 4 femmes (2010-2015) = " + nbH);

        // Affichage style énoncé (h1)
        System.out.println("\nNom : " + h1.getNom()+" "+h1.getPrenom());
        System.out.println("Mariages En Cours :");
        int i=1;
        for(Object[] r: hSrv.mariagesDetail(h1.getId())){
            if(r[3] == null) {
                System.out.println((i++) + ". Femme : " + r[0]+" "+r[1] + "   Date Début : " + r[2] + "    Nbr Enfants : " + r[4]);
            }
        }
        System.out.println("\nMariages échoués :");
        i=1;
        for(Object[] r: hSrv.mariagesDetail(h1.getId())){
            if(r[3] != null) {
                System.out.println((i++) + ". Femme : " + r[0]+" "+r[1] + "   Date Début : " + r[2] +
                        "    Date Fin : " + r[3] + "    Nbr Enfants : " + r[4]);
            }
        }
    }
}
