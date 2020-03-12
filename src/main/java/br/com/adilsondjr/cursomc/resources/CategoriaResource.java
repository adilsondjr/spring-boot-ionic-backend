package br.com.adilsondjr.cursomc.resources;

import java.util.ArrayList;
import java.util.List;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import br.com.adilsondjr.cursomc.domain.Categoria;

@RestController
@RequestMapping(path = "/categoria")
public class CategoriaResource {
	
	@RequestMapping(method = RequestMethod.GET)
	public List<Categoria> listar() {
		
		Categoria c1 = new Categoria(1, "Adilson");
		Categoria c2 = new Categoria(2, "Xicao");
		
		List<Categoria> l = new ArrayList<>();
		
		l.add(c1);
		l.add(c2);
		
		return l;
	}

}
