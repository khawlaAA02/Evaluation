package ma.projet.classes;

import jakarta.persistence.*;

@Entity
public class LigneCommandeProduit {
    @EmbeddedId
    private LigneCommandeProduitId id = new LigneCommandeProduitId();

    @ManyToOne(optional = false) @MapsId("commandeId")
    private Commande commande;

    @ManyToOne(optional = false) @MapsId("produitId")
    private Produit produit;

    @Column(nullable = false)
    private int quantite;

    public LigneCommandeProduit() {}
    public LigneCommandeProduit(Commande commande, Produit produit, int quantite) {
        this.commande = commande;
        this.produit = produit;
        this.quantite = quantite;
    }

    public LigneCommandeProduitId getId() { return id; }
    public Commande getCommande() { return commande; }
    public void setCommande(Commande commande) { this.commande = commande; }
    public Produit getProduit() { return produit; }
    public void setProduit(Produit produit) { this.produit = produit; }
    public int getQuantite() { return quantite; }
    public void setQuantite(int quantite) { this.quantite = quantite; }
}
