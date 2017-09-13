package br.com.caelum.leilao.teste;

import br.com.caelum.leilao.dominio.Lance;
import br.com.caelum.leilao.dominio.Leilao;
import br.com.caelum.leilao.dominio.Usuario;
import br.com.caelum.leilao.servico.Avaliador;

public class TesteDoAvaliador {
  public static void main(String[] args) {
    Usuario joao = new Usuario("João");
    Usuario jose = new Usuario("José");
    Usuario maria = new Usuario("Maria");

    Leilao leilao = new Leilao("Playstation 3 Novo");

    leilao.propoe(new Lance(joao, 250)); //Menor lance dado.
    leilao.propoe(new Lance(jose, 300)); 
    leilao.propoe(new Lance(maria, 400)); //Maior lance dado.

    Avaliador leiloeiro = new Avaliador();
    leiloeiro.avalia(leilao);

    System.out.println(leiloeiro.getMaiorLance());
    System.out.println(leiloeiro.getMenorLance());
  }
}
