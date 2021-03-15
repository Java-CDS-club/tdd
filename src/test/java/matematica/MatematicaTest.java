package matematica;

import static org.junit.Assert.assertEquals;

import java.util.concurrent.ThreadLocalRandom;

import br.com.caelum.matematica.Matematica;
import org.junit.Test;

public class MatematicaTest {

	@Test
	public void testaPrimeiraCondicao() {
		int numero = ThreadLocalRandom.current().nextInt(31, Integer.MAX_VALUE);
		int numeroAposCondicao = numero * 4;

		assertEquals(numeroAposCondicao, Matematica.contaMaluca(numero));
	}

	@Test
	public void testaSegundaCondicao() {
		int numero = ThreadLocalRandom.current().nextInt(11, 30);
		int numeroAposCondicao = numero * 3;

		assertEquals(numeroAposCondicao, Matematica.contaMaluca(numero));
	}

	@Test
	public void testaUltimaCondicao() {
		int numero = ThreadLocalRandom.current().nextInt(0, 10);
		int numeroAposCondicao = numero * 2;

		assertEquals(numeroAposCondicao, Matematica.contaMaluca(numero));
	}
}
