package br.com.caelum.leilao.teste;

import org.junit.Assert;
import org.junit.Test;

import br.com.caelum.leilao.dominio.Lance;
import br.com.caelum.leilao.dominio.Leilao;
import br.com.caelum.leilao.dominio.Usuario;
import br.com.caelum.leilao.servico.Avaliador;

public class TesteDoAvaliador {

  @Test
  public void deveEntenderLancesEmOrdemCrescente() { // Deve ser "public void", não pode ser estático nem receber argumentos.
    // Parte 1: Montar cenário.
    Usuario joao = new Usuario("João");
    Usuario jose = new Usuario("José");
    Usuario maria = new Usuario("Maria");

    Leilao leilao = new Leilao("Playstation 3 Novo");

    leilao.propoe(new Lance(joao, 250)); // Menor lance dado.
    leilao.propoe(new Lance(jose, 300));
    leilao.propoe(new Lance(maria, 400)); // Maior lance dado.

    //Parte 2: Executar ação.
    Avaliador leiloeiro = new Avaliador();
    leiloeiro.avalia(leilao);

    // Parte 3: Validar do resultado.
    double maiorEsperado = 400;
    double menorEsperado = 250;

    Assert.assertEquals(maiorEsperado, leiloeiro.getMaiorLance(), 0.00001);
    Assert.assertEquals(menorEsperado, leiloeiro.getMenorLance(), 0.00001);
  }
}
