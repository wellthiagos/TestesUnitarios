package br.well.thiagos.servicos;

import br.well.thiagos.exceptions.NaoPodeDivirPorZeroException;

public class Calculadora {

	public int somar(int a, int b) {
		return a + b;
	}

	public int subtrair(int a, int b) {
		return a - b;
	}

	public int divide(int a, int b) throws NaoPodeDivirPorZeroException {
		if(b == 0 )
			throw new NaoPodeDivirPorZeroException();
		
		return a / b;
	}
}
