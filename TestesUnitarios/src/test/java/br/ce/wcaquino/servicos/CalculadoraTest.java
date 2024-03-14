package br.ce.wcaquino.servicos;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import br.well.thiagos.exceptions.NaoPodeDivirPorZeroException;
import br.well.thiagos.servicos.Calculadora;

public class CalculadoraTest {
	
	private Calculadora calc;
	
	@Before
	public void setup() {
		calc = new Calculadora();
	}

	@Test
	public void deveSomarDoisValores() {
		// cenario
		int a = 5;
		int b = 3;
		Calculadora calc = new Calculadora();

		// acao
		int resultado = calc.somar(a, b);

		// verificacao
		Assert.assertEquals(8, resultado);
	}

	@Test
	public void deveSubtrairDoisValores() {
		// cenario
		int a = 8;
		int b = 5;

		// acao
		int resultado = calc.subtrair(a, b);

		// verificacao
		Assert.assertEquals(3, resultado);
	}

	@Test
	public void deveDivirDoisValores() throws NaoPodeDivirPorZeroException {
		// cenario
		int a = 6;
		int b = 3;

		// acao
		int resultado = calc.divide(a, b);

		// verificacao
		Assert.assertEquals(2, resultado);
	}
	
	@Test(expected = NaoPodeDivirPorZeroException.class)
	public void deveLancarExcecaoAoDividirPorZero() throws NaoPodeDivirPorZeroException {
		// cenario
		int a = 10;
		int b = 0;

		// acao
		calc.divide(a, b);
	}
}
