<%@ include file="header.jsp"%>
<h1>Confirmacion de la publicacion</h1>
	<main>
		<p>	El ID de la publicacion es: ${idpublicacion}. </p>
		<p>	El Mensaje de la publicacion es: ${publicacion.getMensaje()}</p>
		<p>	La categoria de la publicacion es: ${categoria}</p>
		<a href="registrarPublicacion">Hacer otra publicacion</a>
	</main>
<%@ include file="footer.jsp"%>