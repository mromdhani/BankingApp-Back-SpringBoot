package tn.biat.rest;

import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import tn.biat.model.Compte;
import tn.biat.repository.IComptesRepository;

@RestController
@CrossOrigin(origins="*")
public class ComptesController {

	private IComptesRepository repo;

	public ComptesController(IComptesRepository repo) { // @Autowired n'est pas nécessaire depuis v4.3
		this.repo = repo;
	}

	// @RequestMapping(path="/comptes", method=RequestMethod.GET)
	@GetMapping(path = "/comptes")
	@PreAuthorize("hasRole('ADMIN')")
	public List<Compte> getAllComptes() {
		return repo.findAll();
	}

	@GetMapping(path = "/comptes/{id}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
	public ResponseEntity<Compte> getCompteById(@PathVariable(value = "id") String myId) {
         Compte res = repo.findOne(myId);
         return (res != null)? new ResponseEntity<Compte>(res, HttpStatus.OK)
        		           : new ResponseEntity<Compte>(HttpStatus.NOT_FOUND);
        		           
//		Optional<Compte> compte = repo.findById(myId);
//		if (!compte.isPresent()) {
//			return ResponseEntity.notFound().build();
//		}
//		return ResponseEntity.ok().body(compte.get());
	}

	@PostMapping(path = "/comptes")
	  @PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<Compte> addCompte(@RequestBody Compte c) {
		repo.save(c);
		return new ResponseEntity<Compte>(c, HttpStatus.CREATED);
	}

	@PutMapping(path = "/comptes")
	 @PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<Compte> updateCompte(@RequestBody Compte c) {
		repo.save(c);
		return new ResponseEntity<Compte>(c, HttpStatus.ACCEPTED);
	}
	@DeleteMapping(path = "/comptes/{id}")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<Compte> deleteCompteById(@PathVariable(value = "id") String myId) {		

		 Compte res = repo.findOne(myId);
         if (res != null) {
        	 repo.delete(myId);
        	 return new ResponseEntity<Compte>(HttpStatus.ACCEPTED);
         }  else 
        	 return new ResponseEntity<Compte>(HttpStatus.NOT_FOUND);
//		Optional<Compte> compte = repo.findById(myId);
//		if (!compte.isPresent()) {
//			return ResponseEntity.notFound().build();
//		} else
//		{
//			repo.deleteById(myId);
//			return  new ResponseEntity<Compte>(HttpStatus.ACCEPTED);
//		}
		
	}

}
