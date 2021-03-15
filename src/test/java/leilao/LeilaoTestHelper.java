package leilao;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.logging.Logger;

import br.com.caelum.leilao.dominio.Lance;
import br.com.caelum.leilao.dominio.Leilao;
import br.com.caelum.leilao.dominio.Usuario;
import br.com.caelum.leilao.exception.LanceIgualAZeroException;
import br.com.caelum.leilao.exception.LanceNegativoException;
import br.com.caelum.leilao.exception.LanceSeguidoException;
import br.com.caelum.leilao.exception.LeilaoSemLancesException;
import br.com.caelum.leilao.exception.LimiteLanceException;
import br.com.caelum.leilao.exception.UltimoLanceIndisponivelException;
import br.com.caelum.leilao.servico.Avaliador;
import org.junit.After;
import org.junit.Before;

public class LeilaoTestHelper {

	final static Logger LOG = Logger.getLogger(AvaliadorLeilaoTest.class.getName());

	final Usuario maria = new Usuario("Maria");
	final Usuario jose = new Usuario("José");
	final Usuario joao = new Usuario("João");
	final Usuario roberto = new Usuario("Roberto");
	final Usuario lucas = new Usuario("Lucas");

	final List<Usuario> listaUsuarios = Arrays.asList(maria, jose, joao, roberto, lucas);
	final List<Class<? extends Exception>> listaExceptions = Arrays
			.asList(LanceIgualAZeroException.class, LanceNegativoException.class, LanceSeguidoException.class,
					LeilaoSemLancesException.class, LimiteLanceException.class, UltimoLanceIndisponivelException.class);

	Leilao leilao;
	Avaliador avaliador;

	@Before
	public void beforeTest() {
		avaliador = new Avaliador();
		leilao = new Leilao("Playstation 5");
	}

	@After
	public void afterTest() {
		avaliador.exibeLances(leilao);
	}

	// Utils
	protected BigDecimal getRandomBigDecimal(int min, int max) {
		return BigDecimal.valueOf(ThreadLocalRandom.current().nextInt(min, max));
	}

	protected Boolean validaExcecoesPossiveis(Exception thrownException) {
		for(Class<? extends Exception> e : listaExceptions){
			if(thrownException.getClass().equals(e)){
				return true;
			}
		}

		return false;
	}

	public Lance criaLance(Usuario usuario, BigDecimal valor) throws LanceNegativoException, LanceIgualAZeroException {
		return new Lance(usuario, valor);
	}
}
