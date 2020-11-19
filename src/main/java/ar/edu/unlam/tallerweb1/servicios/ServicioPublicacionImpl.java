package ar.edu.unlam.tallerweb1.servicios;

import java.util.List;
import java.util.TreeSet;

import javax.inject.Inject;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ar.edu.unlam.tallerweb1.modelo.Categoria;
import ar.edu.unlam.tallerweb1.modelo.Publicacion;
import ar.edu.unlam.tallerweb1.modelo.PublicacionOrdenPorFecha;
import ar.edu.unlam.tallerweb1.repositorios.RepositorioPublicacion;

@Service
@Transactional
public class ServicioPublicacionImpl implements ServicioPublicacion {

	@Inject
	private RepositorioPublicacion repositorioPublicacion;

	@Override
	public Long guardarPublicacion(Publicacion publicacion){
		
		return repositorioPublicacion.guardarPublicacion(publicacion);
	}

	@Override
	public List<Publicacion> buscarPublicacionesPorCategoria(Categoria categoria) {
		return repositorioPublicacion.buscarPublicacionesPorCategoria(categoria);
	}

	@Override
	public List<Publicacion> buscarPublicaciones() {
		return repositorioPublicacion.buscarPublicaciones();
	}
	
	@Override
	public Publicacion obtenerPublicacion(Long id) {
		
		return repositorioPublicacion.obtenerPublicacion(id);
	}

	@Override
	public void borrarPublicacion(Long id) {
		repositorioPublicacion.borrarPublicacion(id);
		
	}
	
	public TreeSet<Publicacion> devolverPublicacionesOdenadasPorFechaRecienteAAntigua() { 

		 List <Publicacion> publicaciones = this.buscarPublicaciones();

		PublicacionOrdenPorFecha ordenFechaRecienteAAntigua = new PublicacionOrdenPorFecha(); 

		TreeSet<Publicacion> ordenadoPorFechaRecienteAAntigua = new TreeSet<Publicacion>(ordenFechaRecienteAAntigua); 

		ordenadoPorFechaRecienteAAntigua.addAll(publicaciones); 

		return ordenadoPorFechaRecienteAAntigua; 

		} 

}
