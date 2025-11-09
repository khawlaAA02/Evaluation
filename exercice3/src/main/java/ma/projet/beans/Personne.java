package ma.projet.beans;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name="personnes")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name="dtype")
public abstract class Personne {
    @Id @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;

    @Column(nullable=false,length=60)  private String nom;
    @Column(nullable=false,length=60)  private String prenom;
    @Column(length=30)                 private String telephone;
    @Column(length=150)                private String adresse;
    private LocalDate dateNaissance;

    // getters/setters
    public Long getId(){ return id; }
    public String getNom(){ return nom; }
    public void setNom(String nom){ this.nom=nom; }
    public String getPrenom(){ return prenom; }
    public void setPrenom(String prenom){ this.prenom=prenom; }
    public String getTelephone(){ return telephone; }
    public void setTelephone(String telephone){ this.telephone=telephone; }
    public String getAdresse(){ return adresse; }
    public void setAdresse(String adresse){ this.adresse=adresse; }
    public LocalDate getDateNaissance(){ return dateNaissance; }
    public void setDateNaissance(LocalDate d){ this.dateNaissance=d; }
}
