package ar.edu.unlam.tallerweb1.controladores;

import java.util.Date;
import java.util.List;
import java.util.TreeSet;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import ar.edu.unlam.tallerweb1.modelo.Categoria;
import ar.edu.unlam.tallerweb1.modelo.Comentario;
import ar.edu.unlam.tallerweb1.modelo.Publicacion;
import ar.edu.unlam.tallerweb1.modelo.PublicacionEstado;
import ar.edu.unlam.tallerweb1.modelo.Seguir;
import ar.edu.unlam.tallerweb1.modelo.Usuario;
import ar.edu.unlam.tallerweb1.servicios.ServicioCategoria;
import ar.edu.unlam.tallerweb1.servicios.ServicioComentar;
import ar.edu.unlam.tallerweb1.servicios.ServicioLikePublicacion;
import ar.edu.unlam.tallerweb1.servicios.ServicioLikeComentario;
import ar.edu.unlam.tallerweb1.servicios.ServicioPublicacion;
import ar.edu.unlam.tallerweb1.servicios.ServicioSeguir;

@Controller
public class ControladorPublicacion {

	@Inject
	private ServicioPublicacion servicioPublicacion;
	@Inject
	private ServicioCategoria servicioCategoria;
	@Inject
	private ServicioComentar servicioComentario;
	@Inject
	private ServicioLikePublicacion servicioLike;
	@Inject
	private ServicioSeguir servicioSeguir;

	@RequestMapping(path = "home")
	public ModelAndView irAlHome(@RequestParam(value = "errorMensaje", required = false) String errorMensaje,
			@RequestParam(value = "errorCategoria", required = false) String errorCategoria,
			@RequestParam(value = "categoriaAMostrar", required = false) Long categoriaAMostrar,
			@RequestParam(value = "ordenPublicaciones", required = false) String ordenPublicaciones,
			@RequestParam(value = "errorComentarioVacio", required = false) String errorComentarioVacio,
			@RequestParam(value = "errorBorrarPublicacion", required = false) String errorBorrarPublicacion,
			HttpServletRequest request) {
		ModelMap modelo = new ModelMap();
		Publicacion publicacion = new Publicacion();
		TreeSet<Publicacion> publicaciones = new TreeSet<>();

		ordenPublicaciones = ordenPublicaciones == null ? "ordenFechaRecienteAAntigua" : ordenPublicaciones;

		Usuario usuarioLogeado = request.getSession().getAttribute("USUARIO") != null
				? (Usuario) request.getSession().getAttribute("USUARIO")
				: null;

		if (categoriaAMostrar == null) {
			publicaciones = servicioPublicacion.devolverPublicacionesOrdenadasPor(ordenPublicaciones, usuarioLogeado);
		} else if (!(categoriaAMostrar == null)) {
			Categoria categoria = servicioCategoria.mostrarCategoriaPorId(categoriaAMostrar);
			List publicacionesList = servicioPublicacion.buscarPublicacionesPorCategoria(categoria);

			publicaciones = servicioPublicacion.ordenarUnaListaDePublicacionesPor(ordenPublicaciones, publicacionesList,
					usuarioLogeado);
		}

		List<Seguir> seguimientos = servicioSeguir.devolverListaDeSeguimientos();
		List<Categoria> categorias = servicioCategoria.mostrarCategorias();
		List<Comentario> comentarios = servicioComentario.mostrarTodosLosComentarios();

		modelo.put("title", "RageQuit | Inicio");
		modelo.put("publicaciones", publicaciones);
		modelo.put("publicacion", publicacion);
		modelo.put("categorias", categorias);
		modelo.put("ordenPublicaciones", ordenPublicaciones);
		modelo.put("errorMensaje", errorMensaje);
		modelo.put("errorCategoria", errorCategoria);
		modelo.put("comentario", new Comentario());
		modelo.put("comentarios", comentarios);
		modelo.put("usuarioLogeado", usuarioLogeado);
		modelo.put("errorComentarioVacio", errorComentarioVacio);
		modelo.put("errorBorrarPublicacion", errorBorrarPublicacion);
		modelo.put("seguimientos", seguimientos);

		return new ModelAndView("home", modelo);
	}

	@RequestMapping(path = "/guardarPublicacion", method = RequestMethod.POST)
	public ModelAndView guardarPublicacion(@ModelAttribute("publicacion") Publicacion publicacion,
			HttpServletRequest request) {
		Date fecha = new Date();

		Usuario usuario = request.getSession().getAttribute("USUARIO") != null
				? (Usuario) request.getSession().getAttribute("USUARIO")
				: null;

		String errorCategoria = null;
		String errorMensaje = null;
		if (publicacion.getCategoriaId() == -1) {
			errorCategoria = "Falta elegir categoria";
		} else {
			Long idCategoria = publicacion.getCategoriaId();
			Categoria categoria = servicioCategoria.mostrarCategoriaPorId(idCategoria);
			publicacion.setCategoria(categoria);
		}

		if (publicacion.getMensaje().isEmpty()) {
			errorMensaje = "La publicacion no puede tener un mensaje vacio";
		}

		publicacion.setUsuario(usuario);
		publicacion.setFechaHora(fecha);
		publicacion.setCantidadLikes(0);
		publicacion.setCantidadComentarios(0);
		publicacion.setEstado(PublicacionEstado.ACTIVO);

		if (errorCategoria == null && errorMensaje == null) {
			servicioPublicacion.guardarPublicacion(publicacion);
		}

		return new ModelAndView("redirect:/home?errorMensaje=" + errorMensaje + "&errorCategoria=" + errorCategoria);
	}

	@RequestMapping(path = "/borrarPublicacion", method = RequestMethod.POST)
	public ModelAndView borrarPublicacion(@RequestParam(value = "botonBorrar", required = false) Long id,
			HttpServletRequest request) {
		Long idUsuarioQuePidioBorrarPublicacion = (Long) request.getSession().getAttribute("ID");
		Publicacion publicacionABorrar = servicioPublicacion.obtenerPublicacionPorId(id);
		Long idUsuarioQueCreoLaPublicacion = publicacionABorrar.getUsuario().getId();

		if (!idUsuarioQuePidioBorrarPublicacion.equals(idUsuarioQueCreoLaPublicacion)) {
			return new ModelAndView("redirect:/home?errorBorrarPublicacion=true");
		}

		servicioPublicacion.borrarPublicacion(id);

		return new ModelAndView("redirect:/home?errorBorrarPublicacion=false");
	}

	@RequestMapping(path = "/filtrarCategoria", method = RequestMethod.GET)
	public ModelAndView filtrarPublicacion(
			@RequestParam(value = "ordenPublicaciones", required = false) String ordenPublicaciones,
			@RequestParam(value = "filtarPublicacionCategoria", required = false) Long idCategoria) {

		if (!(idCategoria == -1)) {
			Long categoriaAMostrar = null;
			categoriaAMostrar = idCategoria;
			return new ModelAndView("redirect:/home?categoriaAMostrar=" + categoriaAMostrar + "&ordenPublicaciones="
					+ ordenPublicaciones);
		}

		return new ModelAndView("redirect:/home?ordenPublicaciones=" + ordenPublicaciones);
	}

	@RequestMapping(path = "/darLikePublicacion", method = RequestMethod.POST)
	public ModelAndView darLikePublicacion(@RequestParam(value = "idPublicacionADarLike", required = false) Long id,
			HttpServletRequest request) {
		Publicacion publicacion = servicioPublicacion.obtenerPublicacionPorId(id);
		Usuario usuario = request.getSession().getAttribute("USUARIO") != null
				? (Usuario) request.getSession().getAttribute("USUARIO")
				: null;
		servicioLike.darLikeAPublicacion(publicacion, usuario);

		return new ModelAndView("redirect:/home");
	}

	public ServicioCategoria getServicioCategoria() {
		return servicioCategoria;
	}

	public void setServicioCategoria(ServicioCategoria servicioCategoria) {
		this.servicioCategoria = servicioCategoria;
	}

	public ServicioPublicacion getServicioPublicacion() {
		return servicioPublicacion;
	}

	public void setServicioPublicacion(ServicioPublicacion servicioPublicacion) {
		this.servicioPublicacion = servicioPublicacion;
	}

}
