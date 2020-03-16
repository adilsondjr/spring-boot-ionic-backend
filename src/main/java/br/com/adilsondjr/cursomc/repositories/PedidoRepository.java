package br.com.adilsondjr.cursomc.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.adilsondjr.cursomc.domain.Pedido;

public interface PedidoRepository extends JpaRepository<Pedido, Integer> {

}
