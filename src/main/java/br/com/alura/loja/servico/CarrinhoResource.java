package br.com.alura.loja.servico;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import br.com.alura.loja.dao.CarrinhoDAO;
import br.com.alura.loja.modelo.Carrinho;

@Path("carrinhos")
public class CarrinhoResource {


	/**
	 * Uso recomendado, pois o cache será correto a uri
	 * @param id
	 * @return
	 */
	@Path("{id}")
	@GET
	@Produces(MediaType.APPLICATION_XML)
	public String busca(@PathParam("id") long id) {
		Carrinho carrinho = new CarrinhoDAO().busca(id);
		return carrinho.toXML();
	}

	
	/**
	 * Uso do @QueryParam - não é indicado passar o parâmetro diretamente na URL, 
	 * pois iremos perder a capacidade de usar o cache, já que o navegador não 
	 * utiliza cache em urls com parâmetro.
	 * 
	 * @return
	 */
	/*	@GET
	@Produces(MediaType.APPLICATION_XML)
	public String busca(@QueryParam("id") long id) {
		Carrinho carrinho = new CarrinhoDAO().busca(id);
		return carrinho.toXML();
	}
*/
}
