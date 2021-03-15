package br.com.caelum.leilao.servico;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;

import br.com.caelum.leilao.dominio.Lance;
import br.com.caelum.leilao.dominio.Leilao;
import br.com.caelum.leilao.exception.LeilaoSemLancesException;

public class Avaliador {

	private BigDecimal maiorDeTodos = BigDecimal.valueOf(Double.MIN_VALUE);
	private BigDecimal menorDeTodos = BigDecimal.valueOf(Double.MAX_VALUE);
	private BigDecimal media = BigDecimal.ZERO;
	private final List<Lance> listaMaioresLances = new ArrayList<>();

	public void avalia(Leilao leilao) throws LeilaoSemLancesException {
		if(leilao.getLances().isEmpty())
			throw new LeilaoSemLancesException("O leilão não pode ser avaliado sem lances.");

		for (Lance lance : leilao.getLances()) {
			if (lance.getValor().compareTo(maiorDeTodos) > 0)
				maiorDeTodos = lance.getValor();
			if (lance.getValor().compareTo(menorDeTodos) < 0)
				menorDeTodos = lance.getValor();

			media = media.add(lance.getValor());
		}

		if (maiorDeTodos.equals(BigDecimal.valueOf(Double.MIN_VALUE)))
			maiorDeTodos = BigDecimal.ZERO;

		if (menorDeTodos.equals(BigDecimal.valueOf(Double.MAX_VALUE)))
			menorDeTodos = BigDecimal.ZERO;

		media = media.compareTo(BigDecimal.ZERO) > 0 ?
				media.divide(BigDecimal.valueOf(leilao.getLances().size()), RoundingMode.HALF_UP) :
				BigDecimal.ZERO;

		listaMaioresLances.addAll(leilao.getLances());
		listaMaioresLances.sort((l1, l2) -> {
			if (l1.getValor().compareTo(l2.getValor()) < 0)
				return 1;
			if (l1.getValor().compareTo(l2.getValor()) > 0)
				return -1;
			return 0;
		});
	}

	public void exibeLances(Leilao leilao) {
		leilao.getLances().forEach(lance -> {
			int numeroLance = leilao.getLances().indexOf(lance);
			String usuarioLance = lance.getUsuario().getNome();
			BigDecimal valorLance = lance.getValor();

			System.out.println(String.format("Lance nº %s, dado por %s, de valor %s", numeroLance, usuarioLance, valorLance));
		});
	}

	public List<Lance> getTresMaioresLances() {
		return listaMaioresLances.subList(0, Math.min(listaMaioresLances.size(), 3));
	}

	public BigDecimal getMaiorLance() {
		return maiorDeTodos;
	}

	public BigDecimal getMenorLance() {
		return menorDeTodos;
	}

	public BigDecimal getMedia() {
		return media;
	}
}
