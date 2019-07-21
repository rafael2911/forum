package br.com.crcarvalho.forum.controller.form;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import br.com.crcarvalho.forum.model.Curso;
import br.com.crcarvalho.forum.model.Topico;
import br.com.crcarvalho.forum.repository.CursoRepository;

public class TopicoForm {
	
	@NotNull @NotEmpty @Size(min = 3)
	private String titulo;
	@NotNull @NotEmpty @Size(min = 5)
	private String mensagem;
	@NotNull @NotEmpty @Size(min = 3)
	private String nomeCurso;

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public String getMensagem() {
		return mensagem;
	}

	public void setMensagem(String mensagem) {
		this.mensagem = mensagem;
	}

	public String getNomeCurso() {
		return nomeCurso;
	}

	public void setNomeCurso(String nomeCurso) {
		this.nomeCurso = nomeCurso;
	}

	public Topico converter(CursoRepository cursoRepository) {
		
		Curso curso = cursoRepository.findByNome(nomeCurso);
		
		return new Topico(this.titulo, this.mensagem, curso);
	}

}
