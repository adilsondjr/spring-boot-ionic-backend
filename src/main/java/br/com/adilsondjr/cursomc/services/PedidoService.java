package br.com.adilsondjr.cursomc.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import br.com.adilsondjr.cursomc.domain.Pedido;
import br.com.adilsondjr.cursomc.repositories.PedidoRepository;
import br.com.adilsondjr.cursomc.services.exceptions.ObjectNotFoundException;

@Controller
public class PedidoService {
	
	@Autowired
	private PedidoRepository repo;
	
	public Pedido find(Integer id) {

		Optional<Pedido> obj = repo.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto não encontrado! Id: " + id + ", Tipo: " + Pedido.class.getName()));
		
	}

}
