package tn.biat.repository;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import tn.biat.model.Compte;

//@Repository
public interface IComptesRepository  extends JpaRepository<Compte, String>{
	
	List<Compte> findBySoldeIsLessThanEqual(BigDecimal valeur);
	List<Compte> findByProprietaireIsLike(String pattern);
	
	@Query("select  c from Compte c where c.solde <= ?1")
	List<Compte> findBySoldeInferieurA(BigDecimal valeur);
	
	@Query("select  c from Compte c where c.proprietaire Like %?1%")
	List<Compte> findByProprietaireComme(String pattern);
	
//	@Override
//	default Page<Compte> findAll(Pageable pageable) {
//
//		return findAll(PageRequest.of(1,2));
//	}
	
	

}
