package ma.projet.classes;

import jakarta.persistence.*;
import java.util.*;

@Entity
@Table(name = "categories")
public class Categorie {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable=false, unique=true, length = 50)
    private String code;

    @Column(nullable=false, length = 100)
    private String libelle;

    @OneToMany(mappedBy = "categorie", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Produit> produits = new ArrayList<>();

    public Categorie() {}
    public Categorie(String code, String libelle){ this.code=code; this.libelle=libelle; }

    // getters/setters
    public Long getId(){ return id; }
    public String getCode(){ return code; }
    public void setCode(String code){ this.code = code; }
    public String getLibelle(){ return libelle; }
    public void setLibelle(String libelle){ this.libelle = libelle; }
    public List<Produit> getProduits(){ return produits; }
}
