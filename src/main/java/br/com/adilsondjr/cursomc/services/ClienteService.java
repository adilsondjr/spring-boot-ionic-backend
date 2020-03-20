package br.com.adilsondjr.cursomc.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import br.com.adilsondjr.cursomc.domain.Cliente;
import br.com.adilsondjr.cursomc.dto.ClienteDTO;
import br.com.adilsondjr.cursomc.repositories.ClienteRepository;
import br.com.adilsondjr.cursomc.services.exceptions.DataIntegrityException;
import br.com.adilsondjr.cursomc.services.exceptions.ObjectNotFoundException;

@Service
public class ClienteService {
	
	@Autowired
	private ClienteRepository repo;
	
	public Cliente find(Integer id) {

		Optional<Cliente> obj = repo.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto não encontrado! Id: " + id + ", Tipo: " + Cliente.class.getName()));
		
	}
	
	public List<Cliente> findAll(){
		return repo.findAll();
	}
	
	public Cliente update(Cliente cli) {
		Cliente cliente = find(cli.getId());
		updateData(cliente, cli);
		return repo.save(cliente);
	}
	
	public void delete(Integer id) {
		find(id);
		try {
			repo.deleteById(id);
		}catch (DataIntegrityViolationException e) {
			throw new DataIntegrityException("Não é possível excluir porque há entidades relacionadas");
		}
	}
	
	public Page<Cliente> findPage(Integer page, Integer linesPerPage, String orderBy, String direction){
		
		PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy);
		return repo.findAll(pageRequest);
		
	}
	
	public Cliente fromDTO(ClienteDTO cliDTO) {
		return new Cliente(cliDTO.getId(), cliDTO.getNome(), cliDTO.getEmail(), null, null);
	}
	
	private void updateData(Cliente cliente, Cliente cli) {
		cliente.setNome(cli.getNome());
		cliente.setEmail(cli.getEmail());
	}

}
