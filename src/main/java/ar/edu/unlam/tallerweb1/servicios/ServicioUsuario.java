package ar.edu.unlam.tallerweb1.servicios;

import java.util.List;

import ar.edu.unlam.tallerweb1.modelo.Categoria;
import ar.edu.unlam.tallerweb1.modelo.Usuario;
import ar.edu.unlam.tallerweb1.repositorios.RepositorioUsuario;

public interface ServicioUsuario {

	List<Usuario> listarUsuarios();

	void cambiarRol(Long id, String rol);

	void cambiarNombre(Long id, String nombre);

	void cambiarApellido(Long id, String apellido);

	void cambiarEmail(Long id, String email);

	void cambiarNombreUsuario(Long id, String nombreUsuario);

	void cambiarContrasenia(Long id, String contrasenia);

	void setRepositorioUsuario(RepositorioUsuario repositorioUsuario);

	Usuario obtenerUsuarioPorId(Long id);

	Usuario obtenerUsuarioPorNombreUsuario(String nombreUsuario);

	void aumentarSeguidores(Usuario seguidoAumentarSeguidores);

	void aumentarSeguidos(Usuario seguidorAumentarSeguidos);

	void disminuirSeguidores(Usuario seguidoDisminuirSeguidores);

	void disminuirSeguidos(Usuario seguidorDisminuirSeguidos);

	RepositorioUsuario getRepositorioUsuario();

	void aumentarCategoriasSeguidas(Usuario seguidor);

	void disminuirCategoriasSeguidas(Usuario seguidor);
}
