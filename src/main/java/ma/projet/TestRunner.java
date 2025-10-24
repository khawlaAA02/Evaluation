package ma.projet;

import ma.projet.classes.*;
import ma.projet.service.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

@Component
public class TestRunner implements CommandLineRunner {

    private final CategorieService categorieService;
    private final ProduitService produitService;
    private final CommandeService commandeService;
    private final LigneCommandeService ligneService;

    public TestRunner(CategorieService cs, ProduitService ps, CommandeService cos, LigneCommandeService ls) {
        this.categorieService = cs;
        this.produitService = ps;
        this.commandeService = cos;
        this.ligneService = ls;
    }

    @Override
    public void run(String... args) {
        // 1) Catégorie : find-or-create pour éviter "Duplicate entry 'INF'"
        Categorie cat = categorieService
                .findByCode("INF")
                .orElseGet(() -> categorieService.create(new Categorie("INF", "Informatique")));

        // 2) Données produits/commande
        Produit p1 = produitService.create(new Produit("ES12", 120f, cat));
        Produit p2 = produitService.create(new Produit("ZR85", 100f, cat));
        Produit p3 = produitService.create(new Produit("EE85", 200f, cat));

        Commande cmd = commandeService.create(new Commande(LocalDate.of(2013, 3, 14)));

        ligneService.create(new LigneCommandeProduit(cmd, p1, 7));
        ligneService.create(new LigneCommandeProduit(cmd, p2, 14));
        ligneService.create(new LigneCommandeProduit(cmd, p3, 5));

        // 3) Affichage
        System.out.println("Commande : " + cmd.getId() + "     Date : " + cmd.getDate());
        System.out.println("Liste des produits :");
        System.out.printf("%-12s %-8s %s%n", "Référence", "Prix", "Quantité");
        List<Object[]> rows = produitService.findProduitsByCommande(cmd.getId());
        for (Object[] r : rows) {
            System.out.printf("%-12s %-8.0f %d%n", r[0], (Float) r[1], (Integer) r[2]);
        }
    }
}
