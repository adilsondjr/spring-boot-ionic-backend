package br.com.adilsondjr.cursomc.resources;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import br.com.adilsondjr.cursomc.domain.Cliente;
import br.com.adilsondjr.cursomc.services.ClienteService;

@RestController
@RequestMapping(path = "/cliente")
public class ClienteResource {
	
	@Autowired
	private ClienteService service; 
		
	@RequestMapping(value="/{id}", method = RequestMethod.GET)
	public ResponseEntity<?> find(@PathVariable Integer id) {
		
		Cliente c1 = service.buscar(id);		
		return ResponseEntity.ok().body(c1);
	}

}
