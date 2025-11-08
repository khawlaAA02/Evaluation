package ma.projet.classes;

import jakarta.persistence.*;

@Entity
@Table(name = "lignes_commande")
public class LigneCommandeProduit {

    @EmbeddedId
    private LigneCommandeProduitId id = new LigneCommandeProduitId();

    @ManyToOne(optional=false) @MapsId("commandeId")
    @JoinColumn(name="commande_id", nullable=false)
    private Commande commande;

    @ManyToOne(optional=false) @MapsId("produitId")
    @JoinColumn(name="produit_id", nullable=false)
    private Produit produit;

    @Column(nullable=false)
    private int quantite;

    public LigneCommandeProduit(){}

    public LigneCommandeProduit(Commande commande, Produit produit, int quantite){
        this.commande = commande;
        this.produit = produit;
        this.quantite = quantite;
    }

    @PrePersist @PreUpdate
    private void syncKey(){
        if(commande!=null && produit!=null){
            this.id = new LigneCommandeProduitId(commande.getId(), produit.getId());
        }
    }

    // getters/setters
    public LigneCommandeProduitId getId(){ return id; }
    public Commande getCommande(){ return commande; }
    public void setCommande(Commande c){ this.commande = c; }
    public Produit getProduit(){ return produit; }
    public void setProduit(Produit p){ this.produit = p; }
    public int getQuantite(){ return quantite; }
    public void setQuantite(int q){ this.quantite = q; }
}
