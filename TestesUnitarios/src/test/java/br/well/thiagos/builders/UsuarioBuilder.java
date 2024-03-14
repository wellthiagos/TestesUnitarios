package br.well.thiagos.builders;

import br.well.thiagos.entidades.Usuario;

public class UsuarioBuilder {
	
	private Usuario usuario;
	
	private UsuarioBuilder() {}
	
	public static UsuarioBuilder umUsuario() {
		UsuarioBuilder builder = new UsuarioBuilder();
		builder.usuario = new Usuario();
		builder.usuario.setNome("Well");
		return builder;
	}
	
	public Usuario agora() {
		return usuario;
	}
}
