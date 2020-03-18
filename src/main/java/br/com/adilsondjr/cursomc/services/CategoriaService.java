package br.com.adilsondjr.cursomc.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import br.com.adilsondjr.cursomc.domain.Categoria;
import br.com.adilsondjr.cursomc.repositories.CategoriaRepository;
import br.com.adilsondjr.cursomc.services.exceptions.DataIntegrityException;
import br.com.adilsondjr.cursomc.services.exceptions.ObjectNotFoundException;

@Service
public class CategoriaService{
	
	@Autowired
	private CategoriaRepository repo;
	
	public Categoria find(Integer id) {

		Optional<Categoria> obj = repo.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto não encontrado! Id: " + id + ", Tipo: " + Categoria.class.getName()));
		
	}
	
	public Categoria insert(Categoria cat) {
		return repo.save(cat);
	}
	
	public Categoria update(Categoria cat) {
		find(cat.getId());
		return repo.save(cat);
	}
	
	public void delete(Integer id) {
		find(id);
		try {
			repo.deleteById(id);
		}catch (DataIntegrityViolationException e) {
			throw new DataIntegrityException("Não é possível excluir uma categoria que possui produtos");
		}
	}

}
