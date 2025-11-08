package ma.projet.test;

import ma.projet.classes.*;
import ma.projet.service.*;

import java.time.LocalDate;
import java.util.List;

public class AppTest {
    public static void main(String[] args) {

        CategorieService catSrv = new CategorieService();
        ProduitService prodSrv = new ProduitService();
        CommandeService cmdSrv = new CommandeService();

        Categorie c1 = catSrv.createIfAbsent("SCR", "Écrans");
        Categorie c2 = catSrv.createIfAbsent("LAP", "Laptops");


        Produit p1 = prodSrv.create(new Produit("ES12", 120, c1));
        Produit p2 = prodSrv.create(new Produit("ZR85", 100, c1));
        Produit p3 = prodSrv.create(new Produit("EE85", 200, c2));

        Commande cmd = cmdSrv.create(new Commande(LocalDate.of(2013, 3, 14)));
        cmd.addProduit(p1, 7);
        cmd.addProduit(p2, 14);
        cmd.addProduit(p3, 5);
        cmdSrv.update(cmd);

        System.out.println("Commande : " + cmd.getId() + "   Date : " + cmd.getDate());
        System.out.println("Réf\tPrix\tQté");

        List<Object[]> rows = prodSrv.produitsParCommande(cmd.getId());
        for(Object[] r : rows){
            System.out.println(r[0] + "\t" + r[1] + "\t" + r[2]);
        }
    }
}
