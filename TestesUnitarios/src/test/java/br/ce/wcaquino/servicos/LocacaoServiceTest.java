package br.ce.wcaquino.servicos;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.junit.Assert;
import org.junit.Assume;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ErrorCollector;
import org.junit.rules.ExpectedException;

import br.ce.wcaquino.builders.FilmeBuilder;
import br.ce.wcaquino.builders.UsuarioBuilder;
import br.ce.wcaquino.matchers.MatchersProprios;
import br.well.thiagos.entidades.Filme;
import br.well.thiagos.entidades.Locacao;
import br.well.thiagos.entidades.Usuario;
import br.well.thiagos.exceptions.FilmeSemEstoqueException;
import br.well.thiagos.exceptions.LocadoraException;
import br.well.thiagos.servicos.LocacaoService;
import br.well.thiagos.utils.DataUtils;
import buildermaster.BuilderMaster;

public class LocacaoServiceTest {

	private LocacaoService service;

	@Rule
	public ErrorCollector error = new ErrorCollector();

	@Rule
	public ExpectedException exception = ExpectedException.none();

	@Before
	public void setup() {
		service = new LocacaoService();
	}

	@Test
	public void deveAlugarFilme() throws Exception {
		Assume.assumeFalse(DataUtils.verificarDiaSemana(new Date(), Calendar.SATURDAY));
		
		// cenário
		Usuario usuario = UsuarioBuilder.umUsuario().agora();
		List<Filme> filmes = Arrays.asList(FilmeBuilder.umFilme().comValor(5.0).agora());

		// ação
		Locacao locacao = service.alugarFilme(usuario, filmes);

		// Verificacao
		error.checkThat(locacao.getValor(), is(equalTo(5.0)));
		error.checkThat(DataUtils.isMesmaData(locacao.getDataLocacao(), new Date()), is(true));
		error.checkThat(locacao.getDataLocacao(), MatchersProprios.ehHoje());
		error.checkThat(DataUtils.isMesmaData(locacao.getDataRetorno(), DataUtils.obterDataComDiferencaDias(1)), is(true));
		error.checkThat(locacao.getDataRetorno(), MatchersProprios.ehHojeComDiferencaDia(1));

		// verificação
		/*
		 * assertEquals(5.0, locacao.getValor(), 0.01); assertThat(locacao.getValor(),
		 * is(equalTo(5.0))); assertThat(locacao.getValor(), is(not(6.0)));
		 * assertTrue(DataUtils.isMesmaData(locacao.getDataLocacao(), new Date()));
		 * assertTrue(DataUtils.isMesmaData(locacao.getDataRetorno(),
		 * DataUtils.obterDataComDiferencaDias(1)));
		 * 
		 * assertThat(DataUtils.isMesmaData(locacao.getDataLocacao(), new Date()),
		 * is(true)); assertThat(DataUtils.isMesmaData(locacao.getDataRetorno(),
		 * DataUtils.obterDataComDiferencaDias(1)), is(true));
		 */
	}

	// elegante
	@Test(expected = FilmeSemEstoqueException.class)
	public void naoDeveAlugarFilmeSemEstoque() throws Exception {
		// cenário
		Usuario usuario = UsuarioBuilder.umUsuario().agora();
		List<Filme> filmes = Arrays.asList(FilmeBuilder.umFilmeSemEstoque().agora());

		// ação
		service.alugarFilme(usuario, filmes);
	}

	// robusta
	@Test
	public void naoDeveAlugarFilmeSemUsuario() throws FilmeSemEstoqueException {
		// cenário
		List<Filme> filmes = Arrays.asList(FilmeBuilder.umFilme().agora());

		// acao
		try {
			service.alugarFilme(null, filmes);
			Assert.fail();
		} catch (LocadoraException e) {
			Assert.assertThat(e.getMessage(), is("Usuário vazio!"));
		}
	}

	// nova
	@Test
	public void naoDeveAlugarFilmeSemFilme() throws FilmeSemEstoqueException, LocadoraException {
		// cenário
		Usuario usuario = UsuarioBuilder.umUsuario().agora();

		exception.expect(LocadoraException.class);
		exception.expectMessage("Filme vazio!");

		// acao
		service.alugarFilme(usuario, null);
	}

	@Test
	public void devePagar75PctNoFilme3() throws FilmeSemEstoqueException, LocadoraException {
		// cenário
		Usuario usuario = UsuarioBuilder.umUsuario().agora();
		List<Filme> filmes = Arrays.asList(new Filme("Filme 1 ", 2, 4.0), new Filme("Filme 2", 2, 4.0),
				new Filme("Filme 3", 2, 4.0));
		
		//acao
		Locacao resultado = service.alugarFilme(usuario, filmes);
		
		//verificacao
		//4+4+3 = 11
		
		Assert.assertThat(resultado.getValor(), is(11.0));
	}
	
	@Test
	public void devePagar50PctNoFilme4() throws FilmeSemEstoqueException, LocadoraException {
		// cenário
		Usuario usuario = UsuarioBuilder.umUsuario().agora();
		List<Filme> filmes = Arrays.asList(new Filme("Filme 1 ", 2, 4.0), new Filme("Filme 2", 2, 4.0),
				new Filme("Filme 3", 2, 4.0), new Filme("Filme 4", 2, 4.0));
		
		//acao
		Locacao resultado = service.alugarFilme(usuario, filmes);
		
		//verificacao
		//4+4+3+2 = 13
		
		Assert.assertThat(resultado.getValor(), is(13.0));
	}
	
	@Test
	public void devePagar25PctNoFilme5() throws FilmeSemEstoqueException, LocadoraException {
		// cenário
		Usuario usuario = UsuarioBuilder.umUsuario().agora();
		List<Filme> filmes = Arrays.asList(new Filme("Filme 1 ", 2, 4.0), new Filme("Filme 2", 2, 4.0),
				new Filme("Filme 3", 2, 4.0), new Filme("Filme 4", 2, 4.0), new Filme("Filme 5", 2, 4.0));
		
		//acao
		Locacao resultado = service.alugarFilme(usuario, filmes);
		
		//verificacao
		//4+4+3+2+1 = 14
		
		Assert.assertThat(resultado.getValor(), is(14.0));
	}
	
	@Test
	public void devePagar0PctNoFilme6() throws FilmeSemEstoqueException, LocadoraException {
		// cenário
		Usuario usuario = UsuarioBuilder.umUsuario().agora();
		List<Filme> filmes = Arrays.asList(
				new Filme("Filme 1", 2, 4.0), new Filme("Filme 2", 2, 4.0),
				new Filme("Filme 3", 2, 4.0), new Filme("Filme 4", 2, 4.0), 
				new Filme("Filme 5", 2, 4.0), new Filme("Filme 6", 2, 4.0));
		
		//acao
		Locacao resultado = service.alugarFilme(usuario, filmes);
		
		//verificacao
		//4+4+3+2+1 = 14
		
		Assert.assertThat(resultado.getValor(), is(14.0));
	}
	
	@Test
	public void deveDevolverNaSegundaAoAlugarNoSabado() throws FilmeSemEstoqueException, LocadoraException {
		Assume.assumeTrue(DataUtils.verificarDiaSemana(new Date(), Calendar.SATURDAY));
		
		// cenário
		Usuario usuario = UsuarioBuilder.umUsuario().agora();
		List<Filme> filmes = Arrays.asList(FilmeBuilder.umFilme().agora());
		
		//acao
		Locacao retorno = service.alugarFilme(usuario, filmes);
		
		//verificacao
		boolean ehSegunda = DataUtils.verificarDiaSemana(retorno.getDataLocacao(), Calendar.MONDAY);
		Assert.assertTrue(ehSegunda);
		//assertThat(retorno.getDataRetorno(), new DiaSemanaMatcher(Calendar.MONDAY));
		assertThat(retorno.getDataRetorno(), MatchersProprios.caiEm(Calendar.MONDAY));
		assertThat(retorno.getDataRetorno(), MatchersProprios.caiNumaSegunda());
	}

	/*
	 * @Test public void testLocacaoFilmeSemEstoqueRobusta() { // cenário
	 * LocacaoService service = new LocacaoService(); Usuario usuario = new
	 * Usuario("Well"); Filme filme = new Filme("Um sonho de liberdade", 0, 5.0);
	 * 
	 * // ação try { service.alugarFilme(usuario, filme);
	 * Assert.fail("Deveria ter lançado uma exceção!"); } catch (Exception e) {
	 * Assert.assertThat(e.getMessage(), is("Filme sem estoque!")); } }
	 * 
	 * @Test public void testLocacaoFilmeSemEstoqueMelhorForma() throws Exception {
	 * // cenário LocacaoService service = new LocacaoService(); Usuario usuario =
	 * new Usuario("Well"); Filme filme = new Filme("Um sonho de liberdade", 0,
	 * 5.0); exception.expect(Exception.class);
	 * exception.expectMessage("Filme sem estoque!");
	 * 
	 * // ação service.alugarFilme(usuario, filme); }
	 */
	
	public static void main(String[] args) {
		new BuilderMaster().gerarCodigoClasse(Locacao.class);
	}
}