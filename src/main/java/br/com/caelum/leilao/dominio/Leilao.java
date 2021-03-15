package br.com.caelum.leilao.dominio;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import br.com.caelum.leilao.exception.LanceSeguidoException;
import br.com.caelum.leilao.exception.LimiteLanceException;
import br.com.caelum.leilao.exception.UltimoLanceIndisponivelException;

public class Leilao {

	private String descricao;
	private List<Lance> lances;

	public Leilao(String descricao) {
		this.descricao = descricao;
		this.lances = new ArrayList<Lance>();
	}

	// Operacoes
	public void propoe(List<Lance> lances) throws Exception {
		for (Lance lance : lances) {
			propoe(lance);
		}
	}

	public void propoe(Lance lance) throws Exception {
		validaLance(lance);

		lances.add(lance);
	}

	public void dobra(Usuario usuario) throws Exception {
		Lance ultimoLance = getUltimoLanceByUsuario(usuario);

		propoe(new Lance(ultimoLance.getUsuario(), ultimoLance.getValor().multiply(BigDecimal.valueOf(2))));
	}

	// Checagens
	private void validaLance(Lance lance) throws Exception {
		checaLimiteLancesSeguidos(lance);
		checaLimiteLances(lance);
	}

	private void checaLimiteLances(Lance lance) throws LimiteLanceException {
		int vezes = (int) lances.stream().filter(l -> l.getUsuario().equals(lance.getUsuario())).count();

		// Não pode passar de 5
		if (vezes == 4)
			throw new LimiteLanceException("O máximo de lançamentos de um usuário por leilão é 5.");
	}

	private void checaLimiteLancesSeguidos(Lance lance) throws LanceSeguidoException {
		Lance ultimoLance = getUltimoLance();

		if (ultimoLance != null) {
			boolean ultimoLanceMesmoUsuario = ultimoLance.getUsuario().equals(lance.getUsuario());

			if (ultimoLanceMesmoUsuario) {
				throw new LanceSeguidoException("Não se pode realizar dois lances para o mesmo usuário consecutivamente.");
			}
		}
	}

	// Getters
	private Lance getUltimoLanceByUsuario(Usuario usuario) throws UltimoLanceIndisponivelException {
		Lance ultimoLance = null;

		for (Lance lance : lances) {
			if (lance.getUsuario().equals(usuario)) {
				ultimoLance = lance;
			}
		}

		if(ultimoLance == null)
			throw new UltimoLanceIndisponivelException("Não houve lances para esse usuário.");

		return ultimoLance;
	}

	public Lance getUltimoLance() {
		return lances.isEmpty() ? null : lances.get(lances.size() - 1);
	}

	public String getDescricao() {
		return descricao;
	}

	public List<Lance> getLances() {
		return Collections.unmodifiableList(lances);
	}
}
