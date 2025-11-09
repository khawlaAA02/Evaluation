package ma.projet.classes;

import jakarta.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class EmployeTacheId implements Serializable {
    @Column(name="employe_id") private Long employeId;
    @Column(name="tache_id")   private Long tacheId;

    public EmployeTacheId(){}
    public EmployeTacheId(Long employeId, Long tacheId){
        this.employeId=employeId; this.tacheId=tacheId;
    }

    @Override public boolean equals(Object o){
        if(this==o) return true;
        if(!(o instanceof EmployeTacheId)) return false;
        EmployeTacheId that=(EmployeTacheId)o;
        return Objects.equals(employeId, that.employeId) &&
                Objects.equals(tacheId, that.tacheId);
    }
    @Override public int hashCode(){ return Objects.hash(employeId, tacheId); }

    // getters/setters
    public Long getEmployeId(){ return employeId; }
    public void setEmployeId(Long id){ this.employeId=id; }
    public Long getTacheId(){ return tacheId; }
    public void setTacheId(Long id){ this.tacheId=id; }
}
