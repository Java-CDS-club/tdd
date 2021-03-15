package br.com.caelum.leilao.dominio;

import java.math.BigDecimal;

import br.com.caelum.leilao.exception.LanceIgualAZeroException;
import br.com.caelum.leilao.exception.LanceNegativoException;

public class Lance {

	private Usuario usuario;
	private BigDecimal valor;
	
	public Lance(Usuario usuario, BigDecimal valor) throws LanceIgualAZeroException, LanceNegativoException {
		if(valor.compareTo(BigDecimal.ZERO) == 0)
			throw new LanceIgualAZeroException("Não se pode criar um lance com valor zero.");
		else if (valor.compareTo(BigDecimal.ZERO) < 0)
			throw new LanceNegativoException("Não se pode criar um lance com valor negativo.");

		this.usuario = usuario;
		this.valor = valor;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public BigDecimal getValor() {
		return valor;
	}
	
	
	
}
