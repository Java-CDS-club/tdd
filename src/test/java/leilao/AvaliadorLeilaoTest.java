package leilao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.logging.Level;

import br.com.caelum.leilao.dominio.Lance;
import br.com.caelum.leilao.exception.LanceIgualAZeroException;
import br.com.caelum.leilao.exception.LanceNegativoException;
import br.com.caelum.leilao.exception.LeilaoSemLancesException;
import org.junit.Test;

public class AvaliadorLeilaoTest extends LeilaoTestHelper {

	@Test
	public void validaMaioresLancamentos() throws Exception {
		BigDecimal primeiroMaiorLance = BigDecimal.valueOf(12222);
		BigDecimal segundoMaiorLance = BigDecimal.valueOf(2393);
		BigDecimal terceiroMaiorLance = BigDecimal.valueOf(900);
		List<Lance> listaMaioresLances;

		leilao.propoe(criaLance(maria, terceiroMaiorLance));
		leilao.propoe(criaLance(joao, BigDecimal.valueOf(250)));
		leilao.propoe(criaLance(jose, segundoMaiorLance));
		leilao.propoe(criaLance(roberto, primeiroMaiorLance));

		avaliador.avalia(leilao);

		listaMaioresLances = avaliador.getTresMaioresLances();

		assertEquals(3, listaMaioresLances.size());

		assertEquals(primeiroMaiorLance, listaMaioresLances.get(0).getValor());
		assertEquals(segundoMaiorLance, listaMaioresLances.get(1).getValor());
		assertEquals(terceiroMaiorLance, listaMaioresLances.get(2).getValor());
	}

	@Test
	public void validaLeilaoValoresRandomicos() throws LeilaoSemLancesException {
		BigDecimal maiorValor = BigDecimal.valueOf(Integer.MIN_VALUE);
		BigDecimal menorValor = BigDecimal.valueOf(Integer.MAX_VALUE);

		int indexUsuarioAleatorio;
		BigDecimal lanceRandomico;

		int numeroLances = ThreadLocalRandom.current().nextInt(10, 500);

		for (int i = 0; i < numeroLances; i++) {
			indexUsuarioAleatorio = ThreadLocalRandom.current().nextInt(0, listaUsuarios.size());
			lanceRandomico = getRandomBigDecimal(0, 200);

			try {
				leilao.propoe(criaLance(listaUsuarios.get(indexUsuarioAleatorio), lanceRandomico));
			} catch (Exception e) {
				LOG.log(Level.SEVERE, e.getMessage());
				assertTrue(validaExcecoesPossiveis(e));
				continue;
			}

			if (lanceRandomico.compareTo(maiorValor) > 0)
				maiorValor = lanceRandomico;
			if (lanceRandomico.compareTo(menorValor) < 0)
				menorValor = lanceRandomico;
		}

		avaliador.avalia(leilao);

		assertEquals(maiorValor, avaliador.getMaiorLance());
		assertEquals(menorValor, avaliador.getMenorLance());
	}

	@Test(expected = LeilaoSemLancesException.class)
	public void validaLeilaoComNenhumLance() throws LeilaoSemLancesException {
		avaliador.avalia(leilao);
	}

	@Test
	public void validaLeilaoComUmLance() throws Exception {
		BigDecimal valorLance = BigDecimal.valueOf(1400);

		leilao.propoe(criaLance(maria, valorLance));
		avaliador.avalia(leilao);

		assertEquals(valorLance, avaliador.getMaiorLance());
		assertEquals(valorLance, avaliador.getMenorLance());
		assertEquals(valorLance, avaliador.getMedia());
	}

	@Test
	public void validaLeilaoComDoisLances() throws Exception {
		BigDecimal valorLanceMaria = BigDecimal.valueOf(1400);
		BigDecimal valorLanceJoao = BigDecimal.valueOf(5000);
		Lance lanceMaria = criaLance(maria, valorLanceMaria);
		Lance lanceJoao = criaLance(joao, valorLanceJoao);
		List<Lance> listaLances;

		leilao.propoe(lanceMaria);
		leilao.propoe(lanceJoao);

		avaliador.avalia(leilao);

		listaLances = avaliador.getTresMaioresLances();

		assertEquals(2, listaLances.size());
		assertEquals(lanceMaria, listaLances.get(1));
		assertEquals(lanceJoao, listaLances.get(0));
		assertEquals(valorLanceJoao, avaliador.getMaiorLance());
		assertEquals(valorLanceMaria, avaliador.getMenorLance());
	}

	@Test
	public void validaLeilaoComCincoLances() throws LeilaoSemLancesException, LanceNegativoException, LanceIgualAZeroException {
		Lance lance1;
		Lance lance2;
		Lance lance3;
		List<Lance> listaLances = new ArrayList<>();
		List<Lance> listaLancesLeilao;
		BigDecimal lanceRandomico;
		int indexUsuarioAleatorio;

		for (int i = 0; i < 5; i++) {
			indexUsuarioAleatorio = ThreadLocalRandom.current().nextInt(1, listaUsuarios.size());
			lanceRandomico = getRandomBigDecimal(1, Integer.MAX_VALUE);
			Lance lance = criaLance(listaUsuarios.get(indexUsuarioAleatorio), lanceRandomico);

			try {
				leilao.propoe(lance);
			} catch (Exception e) {
				LOG.log(Level.SEVERE, e.getMessage());
				assertTrue(validaExcecoesPossiveis(e));
				i--; // Para voltar um índice e garantir que se adicione 5 lançamentos
				continue;
			}

			listaLances.add(lance);
		}

		listaLances.sort((l1, l2) -> {
			if (l1.getValor().compareTo(l2.getValor()) > 0)
				return -1;
			if (l1.getValor().compareTo(l2.getValor()) < 0)
				return 1;
			return 0;
		});

		lance1 = listaLances.get(0);
		lance2 = listaLances.get(1);
		lance3 = listaLances.get(2);

		avaliador.avalia(leilao);

		listaLancesLeilao = avaliador.getTresMaioresLances();

		assertEquals(lance1, listaLancesLeilao.get(0));
		assertEquals(lance2, listaLancesLeilao.get(1));
		assertEquals(lance3, listaLancesLeilao.get(2));
	}

	@Test
	public void validaAvaliacaoValores() throws Exception {
		BigDecimal maiorValorEsperado = BigDecimal.valueOf(800);
		BigDecimal menorValorEsperado = BigDecimal.valueOf(150);
		BigDecimal mediaEsperada = BigDecimal.valueOf(400);

		leilao.propoe(criaLance(maria, menorValorEsperado));
		leilao.propoe(criaLance(joao, BigDecimal.valueOf(250)));
		leilao.propoe(criaLance(jose, maiorValorEsperado));

		avaliador.avalia(leilao);

		assertEquals(maiorValorEsperado, avaliador.getMaiorLance());
		assertEquals(menorValorEsperado, avaliador.getMenorLance());
		assertEquals(mediaEsperada, avaliador.getMedia());
	}
}
