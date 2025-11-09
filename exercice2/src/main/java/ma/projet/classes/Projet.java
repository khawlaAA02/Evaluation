package ma.projet.classes;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.*;

@Entity @Table(name="projets")
public class Projet {
    @Id @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;

    @Column(nullable=false, length=120) private String nom;
    @Column(nullable=false) private LocalDate dateDebut;
    @Column private LocalDate dateFin;

    // Chef de projet
    @ManyToOne(optional=false) @JoinColumn(name="chef_id", nullable=false)
    private Employe chef;

    @OneToMany(mappedBy = "projet", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Tache> taches = new ArrayList<>();

    public Projet() {}
    public Projet(String nom, LocalDate dateDebut, LocalDate dateFin, Employe chef){
        this.nom=nom; this.dateDebut=dateDebut; this.dateFin=dateFin; this.chef=chef;
    }

    // getters/setters
    public Long getId(){ return id; }
    public String getNom(){ return nom; }
    public void setNom(String nom){ this.nom = nom; }
    public LocalDate getDateDebut(){ return dateDebut; }
    public void setDateDebut(LocalDate d){ this.dateDebut = d; }
    public LocalDate getDateFin(){ return dateFin; }
    public void setDateFin(LocalDate d){ this.dateFin = d; }
    public Employe getChef(){ return chef; }
    public void setChef(Employe e){ this.chef = e; }
    public List<Tache> getTaches(){ return taches; }
}
