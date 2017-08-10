package br.com.alura.loja.servico;

import java.net.URI;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.thoughtworks.xstream.XStream;

import br.com.alura.loja.dao.CarrinhoDAO;
import br.com.alura.loja.modelo.Carrinho;
import br.com.alura.loja.modelo.Produto;

@Path("carrinhos")
public class CarrinhoResource {


	/**
	 * 
	 * 
	 * Uso recomendado, pois o cache será correto a uri
	 * @param id
	 * @return
	 */
	@Path("{id}")
	@GET //O GET é um recurso usado para consulta informações
	@Produces(MediaType.APPLICATION_XML)
	public String busca(@PathParam("id") long id) {
		Carrinho carrinho = new CarrinhoDAO().busca(id);
		return carrinho.toXML();
	}
	
	@POST //O POST é um recurso usado para inclusão de informações
	@Consumes(MediaType.APPLICATION_XML)
	public Response adiciona(String conteudo) {
		Carrinho carrinho = (Carrinho) new XStream().fromXML(conteudo);
		new CarrinhoDAO().adiciona(carrinho);
		URI uri = URI.create("carrinhos/"+carrinho.getId());
		return Response.created(uri).build();
	}
	
	
	@Path("{id}/produto/{produtoId}")
	@DELETE //O DELETE é um recurso usado para deletar
	public Response deletaProdutocarrinho(@PathParam("id") long id, @PathParam("produtoId") long produtoId) {
		Carrinho carrinho = new CarrinhoDAO().busca(id);
		carrinho.remove(produtoId);
		return Response.ok().build();
	}
	
	
	@Path("{id}/produtos/{produtoId}/quantidade")
	@PUT
	@Consumes(MediaType.APPLICATION_XML)
	public Response alterarProduto(String conteudo, @PathParam("id") long id, @PathParam("produtoId") long produtoId) {
		Carrinho carrinho = new CarrinhoDAO().busca(id);
		Produto produto = (Produto) new XStream().fromXML(conteudo);
		carrinho.trocaQuantidade(produto);
		return Response.ok().build();
	}


	/**
	 * Usando produces Json
	 * @param id
	 * @return
	 */
/*	@Path("{id}")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public String busca(@PathParam("id") long id) {
		Carrinho carrinho = new CarrinhoDAO().busca(id);
		return carrinho.toJson();
	}	*/
	
	
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
