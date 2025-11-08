package ma.projet.classes;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.*;

@Entity
@Table(name = "commandes")
public class Commande {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable=false)
    private LocalDate date;

    @OneToMany(mappedBy = "commande", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<LigneCommandeProduit> lignes = new ArrayList<>();

    public Commande() {}
    public Commande(LocalDate date){ this.date = date; }

    // helper pour ajouter un produit
    public void addProduit(Produit p, int quantite){
        LigneCommandeProduit l = new LigneCommandeProduit(this, p, quantite);
        this.lignes.add(l);
        p.getLignes().add(l);
    }

    // getters/setters
    public Long getId(){ return id; }
    public LocalDate getDate(){ return date; }
    public void setDate(LocalDate date){ this.date = date; }
    public List<LigneCommandeProduit> getLignes(){ return lignes; }
}
