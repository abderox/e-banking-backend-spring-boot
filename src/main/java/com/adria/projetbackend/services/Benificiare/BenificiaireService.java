package com.adria.projetbackend.services.Benificiare;

import com.adria.projetbackend.exceptions.runTimeExpClasses.DomesticBenifOnlyExp;
import com.adria.projetbackend.exceptions.runTimeExpClasses.NoSuchBenificException;
import com.adria.projetbackend.models.Benificiaire;
import com.adria.projetbackend.repositories.BenificiaireRepository;
import com.adria.projetbackend.services.Compte.ICompteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class BenificiaireService implements IBenificiareService {


    @Autowired
    private BenificiaireRepository benificiaireRepository;

    @Autowired
    private ICompteService compteService;


    @Transactional
    public Benificiaire ajouterBenificiaire(Benificiaire benificiaire) {
        System.out.println("benificiaire = " + benificiaire.getRib( ));
        if ( !compteService.existsByRib(benificiaire.getRib( )) )
            throw new DomesticBenifOnlyExp("WE CURRENTLY ACCEPT ONLY DOMESTIC BENEFICIARIES WITH A VALID RIB.");
        return benificiaireRepository.save(benificiaire);
    }

    public boolean existsByRibAndClientId(String rib, Long id) {
        return benificiaireRepository.existsByRibAndClient_Id(rib, id);
    }

    @Override
    public Benificiaire consulterBenificiaireByRib(String rib, Long idClient) {
        Benificiaire benificiaire = benificiaireRepository.findByRibAndClient_Id(rib, idClient);
        if ( benificiaire == null )
            throw new NoSuchBenificException("NO SUCH BENEFICIARY EXISTS ! PLEASE CHECK THE RIB PROVIDED.");
        return benificiaire;
    }

    public List<Benificiaire> consulterTousLesBenificiaires(Long idClient) {
        return benificiaireRepository.findAllByClient_Id(idClient);
    }


    // ! To be reviewed
    public Benificiaire modifierBenificiaire(Long idBenificiaireAModifie, Benificiaire benificiaire) {
        Benificiaire benificiaire1 = benificiaireRepository.findById(idBenificiaireAModifie).get( );
        if ( benificiaire1.equals(null) ) throw new RuntimeException("benificiaire non existant");
        benificiaire1.setIntituleVirement(benificiaire.getIntituleVirement( ));
        benificiaire1.setNature(benificiaire.getNature( ));
        benificiaire1.setNom(benificiaire.getNom( ));
        benificiaire1.setRib(benificiaire.getRib( ));
        benificiaire1.setListVirements(benificiaire.getListVirements( ));

        return benificiaireRepository.save(benificiaire1);
    }

    public void supprimerBenificiaire(Long idBenificiaire) {
        benificiaireRepository.deleteById(idBenificiaire);
    }

    public Benificiaire consulterBenificiaire(Long idBenificiaire) {
        Benificiaire benificiaire = benificiaireRepository.findById(idBenificiaire).get( );
        if ( benificiaire.equals(null) ) throw new RuntimeException("benificiaire non existant");
        return benificiaire;
    }

    public List<Benificiaire> consulterBenificiaires() {
        return benificiaireRepository.findAll( );
    }

}
