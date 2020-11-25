<!-- Modal -->
<div class="modal fade" id="seguidores" data-backdrop="static"
	data-keyboard="false" tabindex="-1"
	aria-labelledby="staticBackdropLabel" aria-hidden="true">
	<div class="modal-dialog modal-dialog-scrollable">
		<div class="modal-content">
			<div class="modal-header">
				<h5 class="modal-title text-secondary" id="staticBackdropLabel">Seguidores</h5>
				<button type="button" class="close" data-dismiss="modal"
					aria-label="Close">
					<span aria-hidden="true">&times;</span>
				</button>
			</div>
			<div class="modal-body">
				<c:if test="${not empty listaSeguidores }">
					<c:forEach items="${listaSeguidores}" var="seguidores">
						<div class="d-flex flex-start p-1">

							<img src="${seguidores.getUrl_imagen()}"
								class=" rounded-circle img-seg">

							<p class="text-secondary mt-auto ml-2">${seguidores.getNombreUsuario() }</p>

						</div>
						<hr class="text-secondary">
					</c:forEach>
				</c:if>
			</div>
		</div>
	</div>
</div>