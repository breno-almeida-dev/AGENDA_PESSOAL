package com.exemplo.agenda.model;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
* Classe responsável pela interação com o banco de dados usando JPA.
* Lida com operações de CRUD para a entidade Contato.
*/
public class ContatoModel {
	
	private EntityManagerFactory emf;
	private EntityManager em;
	
	/**
	* Construtor da classe ContatoModel.
	* Inicializa o EntityManagerFactory e o EntityManager.
	*/
	public ContatoModel() {
		emf = Persistence.createEntityManagerFactory("AgendaPU");
		em = emf.createEntityManager();
	}
		
	/**
	* Adiciona um novo contato ao banco de dados.
	* 
	* @param nome	O nome do contato.
	* @param telefone O telefone do contato.
	*/
	public void adicionarContato(String nome, String telefone) {
		Contato contato = new Contato();
		contato.setNome(nome);
		contato.setTelefone(telefone);
		
		try {
			em.getTransaction().begin();
			em.persist(contato);
			em.getTransaction().commit();
		}catch (Exception e) {
			em.getTransaction().rollback();
			e.printStackTrace();
		}
	}
	
	/**
	* Adiciona um novo contato ao banco de dados.
	* 
	* @param id O ID do contato a ser atualizado
	* @param nome O nome do contato.
	* @param telefone O telefone do contato. 
	*/
	public void atualizarContato(int id, String nome, String telefone) {
		Contato contato = em.find(Contato.class, id);
		if (contato != null) {
			try {
				em.getTransaction().begin();
				contato.setNome(nome);
				contato.setTelefone(telefone);
				em.getTransaction().commit();
			}catch (Exception e) {
				em.getTransaction().rollback();
				e.printStackTrace();
			}	
		}
	}
	
	/**
	* Deleta um contato do banco de dados
	* 
	* @param id O ID do contato a ser atualizado
	*/
	public void deletarContato(int id) {
		Contato contato = em.find(Contato.class, id);
		if (contato != null) {
			try {
				em.getTransaction().begin();
				em.remove(contato);
				em.getTransaction().commit();
			}catch (Exception e) {
				em.getTransaction().rollback();
				e.printStackTrace();
			}
		}
	}
		
	/**
	* Lista todos os contatos presentes do banco de dados
	* 
	* @return Uma lista de Contatos.
	*/
	public List <Contato> listarContato(){
		try {
			return em.createQuery("from Contato", Contato.class).getResultList();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}	
}