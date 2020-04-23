package br.com.adilsondjr.cursomc.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.adilsondjr.cursomc.domain.Cidade;
import br.com.adilsondjr.cursomc.domain.Cliente;
import br.com.adilsondjr.cursomc.domain.Endereco;
import br.com.adilsondjr.cursomc.domain.enums.TipoCliente;
import br.com.adilsondjr.cursomc.dto.ClienteDTO;
import br.com.adilsondjr.cursomc.dto.ClienteNewDTO;
import br.com.adilsondjr.cursomc.repositories.ClienteRepository;
import br.com.adilsondjr.cursomc.repositories.EnderecoRepository;
import br.com.adilsondjr.cursomc.services.exceptions.DataIntegrityException;
import br.com.adilsondjr.cursomc.services.exceptions.ObjectNotFoundException;

@Service
public class ClienteService {
	
	@Autowired
	private ClienteRepository repo;
	
	@Autowired
	private EnderecoRepository enderecoRepository;
	
	@Transactional
	public Cliente insert(Cliente cli) {
		cli.setId(null);		
		cli = repo.save(cli);
		enderecoRepository.saveAll(cli.getEnderecos());
		return cli;
	}
	
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
	
	public Cliente fromDTO(ClienteNewDTO cliDTO) {
		Cliente cli = new Cliente(null, cliDTO.getNome(), cliDTO.getEmail(), cliDTO.getCpfCnpj(), TipoCliente.toEnum(cliDTO.getTipoCliente()));
		Cidade cid = new Cidade(cliDTO.getCidadeId(), null, null);
		Endereco end = new Endereco(null, cliDTO.getLogradouro(), cliDTO.getNumero(), cliDTO.getComplemento(), cliDTO.getBairro(), cliDTO.getCep(), cli, cid);
		cli.getEnderecos().add(end);
		cli.getTelefones().add(cliDTO.getTelefone1());
		if(cliDTO.getTelefone2() != null) {
			cli.getTelefones().add(cliDTO.getTelefone2());
		}
		if(cliDTO.getTelefone3() != null) {
			cli.getTelefones().add(cliDTO.getTelefone3());
		}
		
		return cli;
	}
	
	private void updateData(Cliente cliente, Cliente cli) {
		cliente.setNome(cli.getNome());
		cliente.setEmail(cli.getEmail());
	}

}
