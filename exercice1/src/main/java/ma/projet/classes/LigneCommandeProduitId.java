package ma.projet.classes;

import jakarta.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class LigneCommandeProduitId implements Serializable {
    @Column(name="commande_id")
    private Long commandeId;

    @Column(name="produit_id")
    private Long produitId;

    public LigneCommandeProduitId() {}
    public LigneCommandeProduitId(Long commandeId, Long produitId){
        this.commandeId = commandeId;
        this.produitId = produitId;
    }

    @Override public boolean equals(Object o){
        if(this==o) return true;
        if(!(o instanceof LigneCommandeProduitId)) return false;
        LigneCommandeProduitId that = (LigneCommandeProduitId) o;
        return Objects.equals(commandeId, that.commandeId) &&
                Objects.equals(produitId, that.produitId);
    }
    @Override public int hashCode(){ return Objects.hash(commandeId, produitId); }

    // getters/setters
    public Long getCommandeId(){ return commandeId; }
    public void setCommandeId(Long id){ this.commandeId = id; }
    public Long getProduitId(){ return produitId; }
    public void setProduitId(Long id){ this.produitId = id; }
}
