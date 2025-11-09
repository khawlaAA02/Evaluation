package ma.projet.classes;

import jakarta.persistence.*;
import java.util.*;

@Entity @Table(name="employes")
public class Employe {
    @Id @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;

    @Column(nullable=false, length=60) private String nom;
    @Column(nullable=false, length=60) private String prenom;
    @Column(length=30) private String telephone;

    // 1 employé peut être chef de plusieurs projets
    @OneToMany(mappedBy = "chef")
    private List<Projet> projetsGeres = new ArrayList<>();

    @OneToMany(mappedBy = "employe", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<EmployeTache> affectations = new ArrayList<>();

    public Employe() {}
    public Employe(String nom, String prenom, String telephone){
        this.nom=nom; this.prenom=prenom; this.telephone=telephone;
    }

    // getters/setters
    public Long getId(){ return id; }
    public String getNom(){ return nom; }
    public void setNom(String nom){ this.nom = nom; }
    public String getPrenom(){ return prenom; }
    public void setPrenom(String prenom){ this.prenom = prenom; }
    public String getTelephone(){ return telephone; }
    public void setTelephone(String telephone){ this.telephone = telephone; }
    public List<Projet> getProjetsGeres(){ return projetsGeres; }
    public List<EmployeTache> getAffectations(){ return affectations; }
}
