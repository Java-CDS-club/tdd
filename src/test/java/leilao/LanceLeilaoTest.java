package leilao;

import java.math.BigDecimal;

import br.com.caelum.leilao.exception.LanceIgualAZeroException;
import br.com.caelum.leilao.exception.LanceNegativoException;
import org.junit.Test;

public class LanceLeilaoTest extends LeilaoTestHelper {

	@Test(expected = LanceNegativoException.class)
	public void validaLanceNegativo() throws LanceIgualAZeroException, LanceNegativoException {
		criaLance(jose, getRandomBigDecimal(Integer.MIN_VALUE,-1));
	}

	@Test(expected = LanceIgualAZeroException.class)
	public void validaLanceIgualAZero() throws LanceIgualAZeroException, LanceNegativoException {
		criaLance(jose, BigDecimal.ZERO);
	}
}
