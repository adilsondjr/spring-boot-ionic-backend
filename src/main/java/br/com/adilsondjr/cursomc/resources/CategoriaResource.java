package br.com.adilsondjr.cursomc.resources;

import java.net.URI;
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
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import br.com.adilsondjr.cursomc.domain.Categoria;
import br.com.adilsondjr.cursomc.dto.CategoriaDTO;
import br.com.adilsondjr.cursomc.services.CategoriaService;

@RestController
@RequestMapping(path = "/categoria")
public class CategoriaResource {
	
	private static final Logger LOG = LoggerFactory.getLogger(CategoriaResource.class);

	@Autowired
	private CategoriaService service; 
		
	@RequestMapping(value="/{id}", method = RequestMethod.GET)
	public ResponseEntity<Categoria> find(@PathVariable Integer id) {
		
		LOG.info("Buscando a categoria: " + id);
		Categoria c1 = service.find(id);		
		return ResponseEntity.ok().body(c1);
		
	}
	
	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<List<CategoriaDTO>> findAll() {
		
		LOG.info("Buscando todas as categorias: ");
		List<Categoria> cat = service.findAll();
		List<CategoriaDTO> catDTO = cat.stream().map(cats -> new CategoriaDTO(cats)).collect(Collectors.toList());
		return ResponseEntity.ok().body(catDTO);
	}
	
	@RequestMapping(value = "/page", method = RequestMethod.GET)
	public ResponseEntity<Page<CategoriaDTO>> findPage(
			@RequestParam(value = "page", defaultValue = "0") Integer page, 
			@RequestParam(value = "linesPerPage", defaultValue = "24") Integer linesPerPage, 
			@RequestParam(value = "orderBy", defaultValue = "nome") String orderBy, 
			@RequestParam(value = "direction", defaultValue = "ASC") String direction) {
		
		LOG.info("Buscando todas as categorias: ");
		Page<Categoria> cat = service.findPage(page, linesPerPage, orderBy, direction);
		Page<CategoriaDTO> catDTO = cat.map(cats -> new CategoriaDTO(cats));
		return ResponseEntity.ok().body(catDTO);
	}
	
	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<Void> insert(@Valid @RequestBody CategoriaDTO catDTO){
		Categoria cat = service.fromDTO(catDTO);
		cat = service.insert(cat);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
				.buildAndExpand(cat.getId()).toUri();
		
		return ResponseEntity.created(uri).build();
	}
	
	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	public ResponseEntity<Void> update(@Valid @RequestBody CategoriaDTO catDTO, @PathVariable Integer id){
		Categoria cat = service.fromDTO(catDTO);
		cat.setId(id);
		cat = service.update(cat);
		
		return ResponseEntity.noContent().build();
	}
	
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<Void> delete(@PathVariable Integer id) {
		service.delete(id);
		return ResponseEntity.noContent().build();
	}

}
