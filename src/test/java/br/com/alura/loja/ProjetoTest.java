package br.com.alura.loja;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.jersey.client.ClientConfig;
import org.glassfish.jersey.filter.LoggingFilter;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.thoughtworks.xstream.XStream;

import br.com.alura.loja.modelo.Projeto;

public class ProjetoTest {

	HttpServer server;
	WebTarget target;

	@Before
	public void antes() {
		server = Servidor.inicializaServidor();
		ClientConfig config = new ClientConfig();
		config.register(new LoggingFilter());
		Client client = ClientBuilder.newClient(config);
		target = client.target("http://localhost:8080");
	}

	@Test
	public void testaQueAConexaoComOServidorFuncionaNoPathDeProjetos() {
		Projeto projeto = target.path("/projetos/1").request().get(Projeto.class);
		Assert.assertEquals(projeto.getNome(), "Minha loja");
	}

	@Test
	public void deveInserirUmProjeto() {
		Projeto projeto = new Projeto(3l, "Meu conhecimento", 2017);
		Entity<Projeto> entity = Entity.entity(projeto, MediaType.APPLICATION_XML);
		Response response = target.path("/projetos").request().post(entity);
		Assert.assertEquals(201, response.getStatus());
	}

	@After
	public void depois() {
		server.stop();
	}
}
