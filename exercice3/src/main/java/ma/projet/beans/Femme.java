package ma.projet.beans;
import jakarta.persistence.*;
import java.util.*;

@Entity
@DiscriminatorValue("FEMME")
@NamedQuery( // femmes mariées au moins 2 fois
        name="Femme.marieesAuMoinsDeuxFois",
        query="select f from Femme f where " +
                "(select count(m) from Mariage m where m.femme = f) >= 2"
)
public class Femme extends Personne {
    @OneToMany(mappedBy="femme", cascade=CascadeType.ALL, orphanRemoval=true)
    private List<Mariage> mariages = new ArrayList<>();
    public List<Mariage> getMariages(){ return mariages; }
}
