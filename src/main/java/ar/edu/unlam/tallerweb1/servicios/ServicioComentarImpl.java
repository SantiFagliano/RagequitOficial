package ar.edu.unlam.tallerweb1.servicios;

import java.util.List;

import javax.inject.Inject;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import ar.edu.unlam.tallerweb1.modelo.Comentario;
import ar.edu.unlam.tallerweb1.repositorios.RepositorioComentario;

@Service
@Transactional
public class ServicioComentarImpl implements ServicioComentar {
	
	@Inject
	private RepositorioComentario repositorioComentar;


	@Override
	public Long enviarComentario(Comentario comentario) {
		return  repositorioComentar.enviarComentario(comentario);
	}

	@Override
	public Comentario mostrarComentario(Long id) {
		return repositorioComentar.mostrarComentario(id);
	}

	@Override
	public void borrarComentario(Long id) {
		repositorioComentar.borrarComentario(id);
	}

	@Override
	public void darLikeComentario(Long id) {
		repositorioComentar.darLikeComentario(id);
	}

	@Override
	public List<Comentario> listaDeComentarios() {
		return repositorioComentar.verListaComentarios();
	}

}