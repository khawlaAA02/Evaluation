package ma.projet.classes;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity @Table(name="employe_tache")
public class EmployeTache {

    @EmbeddedId
    private EmployeTacheId id = new EmployeTacheId();

    @ManyToOne(optional=false) @MapsId("employeId")
    @JoinColumn(name="employe_id", nullable=false)
    private Employe employe;

    @ManyToOne(optional=false) @MapsId("tacheId")
    @JoinColumn(name="tache_id", nullable=false)
    private Tache tache;

    @Column private LocalDate dateDebutReelle;
    @Column private LocalDate dateFinReelle;

    public EmployeTache(){}
    public EmployeTache(Employe e, Tache t, LocalDate ddr, LocalDate dfr){
        this.employe=e; this.tache=t; this.dateDebutReelle=ddr; this.dateFinReelle=dfr;
    }

    @PrePersist @PreUpdate
    private void syncKey(){
        if(employe!=null && tache!=null){
            this.id = new EmployeTacheId(employe.getId(), tache.getId());
        }
    }

    // getters/setters
    public Employe getEmploye(){ return employe; }
    public void setEmploye(Employe e){ this.employe = e; }
    public Tache getTache(){ return tache; }
    public void setTache(Tache t){ this.tache = t; }
    public LocalDate getDateDebutReelle(){ return dateDebutReelle; }
    public void setDateDebutReelle(LocalDate d){ this.dateDebutReelle = d; }
    public LocalDate getDateFinReelle(){ return dateFinReelle; }
    public void setDateFinReelle(LocalDate d){ this.dateFinReelle = d; }
}
