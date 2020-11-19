package ar.edu.unlam.tallerweb1.controladores;

import java.util.Date;
import java.util.List;

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
import ar.edu.unlam.tallerweb1.modelo.Usuario;
import ar.edu.unlam.tallerweb1.servicios.ServicioCategoria;
import ar.edu.unlam.tallerweb1.servicios.ServicioComentar;
import ar.edu.unlam.tallerweb1.servicios.ServicioPublicacion;

@Controller
public class ControladorPublicacion {

	@Inject
	private ServicioPublicacion servicioPublicacion;
	@Inject
	private ServicioCategoria servicioCategoria;
	@Inject
	private ServicioComentar servicioComentario;

	@RequestMapping(path = "home")
	public ModelAndView irAlHome(@RequestParam(value = "errorMensaje", required = false) String errorMensaje,
			@RequestParam(value = "errorCategoria", required = false) String errorCategoria,
			@RequestParam(value = "categoriaAMostrar", required = false) Long categoriaAMostrar,
			HttpServletRequest request) {
		ModelMap modelo = new ModelMap();
		Publicacion publicacion = new Publicacion();
		List<Publicacion> publicaciones = servicioPublicacion.buscarPublicaciones();

		if (!(categoriaAMostrar == null)) {
			Categoria categoria = servicioCategoria.mostrarCategoriaPorId(categoriaAMostrar);
			publicaciones = servicioPublicacion.buscarPublicacionesPorCategoria(categoria);
		}


		Usuario usuarioLogeado = request.getSession().getAttribute("USUARIO") != null
								? (Usuario) request.getSession().getAttribute("USUARIO")
								: null;
		
		List<Categoria> categorias = servicioCategoria.mostrarCategorias();
		List<Comentario> comentarios = servicioComentario.mostrarTodosLosComentarios();

		modelo.put("title", "RageQuit | Inicio");
		modelo.put("publicaciones", publicaciones);
		modelo.put("publicacion", publicacion);
		modelo.put("categorias", categorias);
		modelo.put("errorMensaje", errorMensaje);
		modelo.put("errorCategoria", errorCategoria);
		modelo.put("comentario", new Comentario());
		modelo.put("comentarios", comentarios);
		modelo.put("usuarioLogeado", usuarioLogeado);

		return new ModelAndView("home", modelo);
	}

	@RequestMapping(path = "/guardarPublicacion", method = RequestMethod.POST)
	public ModelAndView guardarPublicacion(@ModelAttribute("publicacion") Publicacion publicacion, HttpServletRequest request) {
		Date fecha = new Date();
		
		Usuario usuario = request.getSession().getAttribute("USUARIO") != null
				? (Usuario) request.getSession().getAttribute("USUARIO")
				: null;
				
		publicacion.setUsuario(usuario);		
		publicacion.setFechaHora(fecha);
		publicacion.setCantidadLikes(0);
	

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


		if (errorCategoria == null && errorMensaje == null) {
			servicioPublicacion.guardarPublicacion(publicacion);
		}

		return new ModelAndView("redirect:/home?errorMensaje=" + errorMensaje + "&errorCategoria=" + errorCategoria);
	}

	@RequestMapping(path = "/borrarPublicacion", method = RequestMethod.GET)
	public ModelAndView borrarPublicacion(@RequestParam(value = "botonBorrar", required = false) Long id) {
		servicioPublicacion.borrarPublicacion(id);

		return new ModelAndView("redirect:/home");
	}

	@RequestMapping(path = "/filtrarCategoria", method = RequestMethod.GET)
	public ModelAndView filtrarPublicacion(
			@RequestParam(value = "filtarPublicacionCategoria", required = false) Long idCategoria) {

		if (!(idCategoria == -1)) {
			Long categoriaAMostrar = null;
			categoriaAMostrar = idCategoria;
			return new ModelAndView("redirect:/home?categoriaAMostrar=" + categoriaAMostrar);
		}

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
