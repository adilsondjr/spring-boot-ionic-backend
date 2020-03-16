package br.com.adilsondjr.cursomc.domain.enums;

public enum EstadoPagamento {
	
	PENDENTE(1, "PENDENTE"),
	CANCELADO(2, "CANCELADO"),
	QUITADO(3, "QUITADO");
	
	private int cod;
	private String descricao;

	private EstadoPagamento(int cod, String descricao) {
		this.cod = cod;
		this.descricao = descricao;
	}
	
	public int getCod() {
		return cod;
	}
	
	public String getDescricao() {
		return descricao;
	}
	
	public static EstadoPagamento toEnum(Integer cod) {
		
		if(cod == null) {
			return null;
		}
		
		for (EstadoPagamento ep : EstadoPagamento.values()) {
			if (cod.equals(ep.getCod())) {
				return ep;
			}
		}
		
		throw new IllegalArgumentException("Id invalido: " + cod);
		
	}

}
