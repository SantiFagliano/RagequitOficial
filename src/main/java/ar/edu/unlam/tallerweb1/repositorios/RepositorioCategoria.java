package ar.edu.unlam.tallerweb1.repositorios;

import ar.edu.unlam.tallerweb1.modelo.Categoria;

public interface RepositorioCategoria {
	
	void crearCategoria(Categoria categoria);
	
	Categoria mostrarCategoriaPorId(Long id);

}