package br.well.thiagos.suites;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import br.well.thiagos.servicos.CalculadoraTest;
import br.well.thiagos.servicos.CalculoValorLocacaoTest;
import br.well.thiagos.servicos.LocacaoServiceTest;

@Ignore
@RunWith(Suite.class)
@SuiteClasses({
	CalculadoraTest.class,
	CalculoValorLocacaoTest.class,
	LocacaoServiceTest.class
})

public class SuiteExecucao {

	@BeforeClass
	public static void before() {
	}
	
	@AfterClass
	public static void after() {
	}
}
