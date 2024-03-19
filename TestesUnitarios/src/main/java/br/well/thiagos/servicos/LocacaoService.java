package br.well.thiagos.servicos;

import static br.well.thiagos.utils.DataUtils.adicionarDias;

import java.util.Calendar;
import java.util.Date;	
import java.util.List;

import br.well.thiagos.dao.LocacaoDAO;
import br.well.thiagos.entidades.Filme;
import br.well.thiagos.entidades.Locacao;
import br.well.thiagos.entidades.Usuario;
import br.well.thiagos.exceptions.FilmeSemEstoqueException;
import br.well.thiagos.exceptions.LocadoraException;
import br.well.thiagos.utils.DataUtils;

public class LocacaoService {
	
	private LocacaoDAO dao;
	private SPCService spcService;

	public Locacao alugarFilme(Usuario usuario, List<Filme> filmes) throws FilmeSemEstoqueException, LocadoraException {

		if (usuario == null)
			throw new LocadoraException("Usuário vazio!");

		if (filmes == null || filmes.isEmpty())
			throw new LocadoraException("Filme vazio!");

		for (Filme filme : filmes) {
			if (filme.getEstoque().equals(0))
				throw new FilmeSemEstoqueException();
		}
		
		if(spcService.possuiNegativacao(usuario)) {
			throw new LocadoraException("Usuário Negativado");
		}
			
		Locacao locacao = new Locacao();
		locacao.setFilmes(filmes);
		locacao.setUsuario(usuario);
		locacao.setDataLocacao(new Date());
		Double valorTotal = 0d;
		// valorTotal = filmes.stream().mapToDouble(Filme::getPrecoLocacao).sum();
		for (int i = 0; i < filmes.size(); i++) {
			Filme filme = filmes.get(i);
			Double valorFilme = filme.getPrecoLocacao();

			switch(i) {
				case 2: valorFilme = valorFilme * 0.75; break;
				case 3: valorFilme = valorFilme * 0.50; break;
				case 4: valorFilme = valorFilme * 0.25; break;
				case 5: valorFilme = 0d; break;
			}
			
			valorTotal += valorFilme;
		}

		locacao.setValor(valorTotal);

		// Entrega no dia seguinte
		Date dataEntrega = new Date();
		dataEntrega = adicionarDias(dataEntrega, 1);
		
		if(DataUtils.verificarDiaSemana(dataEntrega, Calendar.SUNDAY))
			dataEntrega = adicionarDias(dataEntrega, 1);
		
		locacao.setDataRetorno(dataEntrega);

		// Salvando a locacao...
		dao.salvar(locacao);

		return locacao;
	}
	
	public void setLocacaoDAO(LocacaoDAO dao) {
		this.dao = dao; 
	}
	
	public void setSPCService(SPCService spcService) {
		this.spcService = spcService;
	}
}
