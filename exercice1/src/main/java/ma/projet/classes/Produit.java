package ma.projet.classes;

import jakarta.persistence.*;
import java.util.*;

@Entity
@Table(name = "produits")
@NamedQuery(
        name = "Produit.findPrixSup100",
        query = "SELECT p FROM Produit p WHERE p.prix > 100"
)
public class Produit {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable=false, unique=true, length = 50)
    private String reference;

    @Column(nullable=false)
    private float prix;

    @ManyToOne(optional=false)
    @JoinColumn(name="categorie_id", nullable=false)
    private Categorie categorie;

    @OneToMany(mappedBy = "produit", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<LigneCommandeProduit> lignes = new ArrayList<>();

    public Produit() {}
    public Produit(String reference, float prix, Categorie categorie){
        this.reference=reference; this.prix=prix; this.categorie=categorie;
    }

    // getters/setters
    public Long getId(){ return id; }
    public String getReference(){ return reference; }
    public void setReference(String reference){ this.reference = reference; }
    public float getPrix(){ return prix; }
    public void setPrix(float prix){ this.prix = prix; }
    public Categorie getCategorie(){ return categorie; }
    public void setCategorie(Categorie categorie){ this.categorie = categorie; }
    public List<LigneCommandeProduit> getLignes(){ return lignes; }
}
