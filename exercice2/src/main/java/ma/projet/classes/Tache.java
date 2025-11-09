package ma.projet.classes;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.*;

@Entity @Table(name="taches")
@NamedQuery(
        name = "Tache.findPrixSup1000",
        query = "SELECT t FROM Tache t WHERE t.prix > 1000"
)
public class Tache {
    @Id @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;

    @Column(nullable=false, length=120) private String nom;
    @Column(nullable=false) private LocalDate dateDebut;
    @Column private LocalDate dateFin;
    @Column(nullable=false) private double prix;

    @ManyToOne(optional=false) @JoinColumn(name="projet_id", nullable=false)
    private Projet projet;

    @OneToMany(mappedBy = "tache", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<EmployeTache> affectations = new ArrayList<>();

    public Tache() {}
    public Tache(String nom, LocalDate dd, LocalDate df, double prix, Projet projet){
        this.nom=nom; this.dateDebut=dd; this.dateFin=df; this.prix=prix; this.projet=projet;
    }

    // getters/setters
    public Long getId(){ return id; }
    public String getNom(){ return nom; }
    public void setNom(String nom){ this.nom = nom; }
    public LocalDate getDateDebut(){ return dateDebut; }
    public void setDateDebut(LocalDate d){ this.dateDebut = d; }
    public LocalDate getDateFin(){ return dateFin; }
    public void setDateFin(LocalDate d){ this.dateFin = d; }
    public double getPrix(){ return prix; }
    public void setPrix(double prix){ this.prix = prix; }
    public Projet getProjet(){ return projet; }
    public void setProjet(Projet p){ this.projet = p; }
    public List<EmployeTache> getAffectations(){ return affectations; }
}
