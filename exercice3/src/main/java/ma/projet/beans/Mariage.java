package ma.projet.beans;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name="mariages")
@NamedNativeQuery( // nombre d'enfants d'une femme entre dates
        name = "Mariage.nbEnfantsFemmeBetween",
        query = """
            SELECT COALESCE(SUM(nbr_enfant),0) FROM mariages
            WHERE femme_id = :fid
              AND (date_debut >= :d1 AND (date_fin IS NULL OR date_fin <= :d2))
            """,
        resultClass = Integer.class
)
public class Mariage {
    @Id @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional=false) @JoinColumn(name="homme_id", nullable=false)
    private Homme homme;

    @ManyToOne(optional=false) @JoinColumn(name="femme_id", nullable=false)
    private Femme femme;

    @Column(name="date_debut", nullable=false)
    private LocalDate dateDebut;

    @Column(name="date_fin")
    private LocalDate dateFin;

    @Column(name="nbr_enfant", nullable=false)
    private int nbrEnfant;

    public Mariage(){}
    public Mariage(Homme h, Femme f, LocalDate dd, LocalDate df, int n){
        this.homme=h; this.femme=f; this.dateDebut=dd; this.dateFin=df; this.nbrEnfant=n;
    }

    // getters/setters
    public Long getId(){ return id; }
    public Homme getHomme(){ return homme; }
    public void setHomme(Homme h){ this.homme=h; }
    public Femme getFemme(){ return femme; }
    public void setFemme(Femme f){ this.femme=f; }
    public LocalDate getDateDebut(){ return dateDebut; }
    public void setDateDebut(LocalDate d){ this.dateDebut=d; }
    public LocalDate getDateFin(){ return dateFin; }
    public void setDateFin(LocalDate d){ this.dateFin=d; }
    public int getNbrEnfant(){ return nbrEnfant; }
    public void setNbrEnfant(int n){ this.nbrEnfant=n; }
}
