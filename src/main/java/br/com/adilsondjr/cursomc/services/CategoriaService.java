package br.com.adilsondjr.cursomc.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.adilsondjr.cursomc.domain.Categoria;
import br.com.adilsondjr.cursomc.repositories.CategoriaRepositories;

@Service
public class CategoriaService{
	
	@Autowired
	private CategoriaRepositories repo;
	
	public Categoria buscar(Integer id) {
		
		Optional<Categoria> obj = repo.findById(id);		
		return obj.orElse(null);
		
	}

}
