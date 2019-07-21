package br.com.crcarvalho.forum.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.crcarvalho.forum.model.Curso;

public interface CursoRepository extends JpaRepository<Curso, Long> {
	
	Curso findByNome(String nomeCurso);

}
