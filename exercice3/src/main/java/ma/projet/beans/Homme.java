package ma.projet.beans;
import jakarta.persistence.*;
import java.util.*;

@Entity
@DiscriminatorValue("HOMME")
public class Homme extends Personne {
    @OneToMany(mappedBy="homme", cascade=CascadeType.ALL, orphanRemoval=true)
    private List<Mariage> mariages = new ArrayList<>();
    public List<Mariage> getMariages(){ return mariages; }
}
