package br.com.adilsondjr.cursomc.resources;

import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.adilsondjr.cursomc.domain.Cliente;
import br.com.adilsondjr.cursomc.dto.ClienteDTO;
import br.com.adilsondjr.cursomc.services.ClienteService;

@RestController
@RequestMapping(path = "/cliente")
public class ClienteResource {
	
	private static final Logger LOG = LoggerFactory.getLogger(ClienteResource.class);
	
	@Autowired
	private ClienteService service; 
		
	@RequestMapping(value="/{id}", method = RequestMethod.GET)
	public ResponseEntity<Cliente> find(@PathVariable Integer id) {
		
		Cliente c1 = service.find(id);		
		return ResponseEntity.ok().body(c1);
	}
	
	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<List<ClienteDTO>> findAll() {
		
		LOG.info("Buscando todas as Clientes: ");
		List<Cliente> cli = service.findAll();
		List<ClienteDTO> cliDTO = cli.stream().map(clis -> new ClienteDTO(clis)).collect(Collectors.toList());
		return ResponseEntity.ok().body(cliDTO);
	}
	
	@RequestMapping(value = "/page", method = RequestMethod.GET)
	public ResponseEntity<Page<ClienteDTO>> findPage(
			@RequestParam(value = "page", defaultValue = "0") Integer page, 
			@RequestParam(value = "linesPerPage", defaultValue = "24") Integer linesPerPage, 
			@RequestParam(value = "orderBy", defaultValue = "nome") String orderBy, 
			@RequestParam(value = "direction", defaultValue = "ASC") String direction) {
		
		LOG.info("Buscando todas as Clientes: ");
		Page<Cliente> cli = service.findPage(page, linesPerPage, orderBy, direction);
		Page<ClienteDTO> cliDTO = cli.map(clis -> new ClienteDTO(clis));
		return ResponseEntity.ok().body(cliDTO);
	}
	
	/*@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<Void> insert(@Valid @RequestBody ClienteDTO cliDTO){
		Cliente cli = service.fromDTO(cliDTO);
		cli = service.insert(cli);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
				.buildAndExpand(cli.getId()).toUri();
		
		return ResponseEntity.created(uri).build();
	}*/
	
	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	public ResponseEntity<Void> update(@Valid @RequestBody ClienteDTO cliDTO, @PathVariable Integer id){
		Cliente cli = service.fromDTO(cliDTO);
		cli.setId(id);
		cli = service.update(cli);
		
		return ResponseEntity.noContent().build();
	}
	
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<Void> delete(@PathVariable Integer id) {
		service.delete(id);
		return ResponseEntity.noContent().build();
	}

}
