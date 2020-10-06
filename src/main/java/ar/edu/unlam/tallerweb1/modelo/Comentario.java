package ar.edu.unlam.tallerweb1.modelo;


import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public /*abstrat*/  class Comentario {  /* asbtract para clases que por si solas no existen en la vida real */
	/* ES UNARIA  DUDA CON EL IMPORT DE DATE*/
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private Date fechaHora;
	private Integer cantidadLikes;
	private String mensaje; /* no deberia de ir, pero no se hacer herencia foraneas */
	
	public Long getId() {
		return id;
	}
	public String getMensaje() {
		return mensaje;
	}
	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Date getFechaHora() {
		return fechaHora;
	}
	public void setFechaHora(Date fechaHora) {
		this.fechaHora = fechaHora;
	}
	public Integer getCantidadLikes() {
		return cantidadLikes;
	}
	public void setCantidadLikes(Integer cantidadLikes) {
		this.cantidadLikes = cantidadLikes;
	}
	
}
