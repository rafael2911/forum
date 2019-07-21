package br.com.crcarvalho.forum.controller;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import br.com.crcarvalho.forum.controller.dto.DetalhesDoTopicoDto;
import br.com.crcarvalho.forum.controller.dto.TopicoDto;
import br.com.crcarvalho.forum.controller.form.AtualizacaoTopicoForm;
import br.com.crcarvalho.forum.controller.form.TopicoForm;
import br.com.crcarvalho.forum.model.Topico;
import br.com.crcarvalho.forum.repository.CursoRepository;
import br.com.crcarvalho.forum.repository.TopicoRepository;

@RestController
@RequestMapping("topicos")
public class TopicoController {
	
	@Autowired
	private TopicoRepository topicoRepository;
	
	@Autowired
	private CursoRepository cursoRepository;
	
	@GetMapping
	public List<TopicoDto> listar(String nomeCurso){
		
		if(nomeCurso == null)
			return TopicoDto.converter(topicoRepository.findAll());
		
		return TopicoDto.converter(topicoRepository.findByCursoNome(nomeCurso));
		
	}
	
	@PostMapping
	@Transactional
	public ResponseEntity<TopicoDto> cadastrar(@RequestBody @Valid TopicoForm form, UriComponentsBuilder uriBuilder) {
		Topico topico = form.converter(cursoRepository);
		topicoRepository.save(topico);
		
		URI location = uriBuilder.path("/topicos/{id}")
				.buildAndExpand(topico.getId())
				.toUri();
		
		return ResponseEntity.created(location)
					.body(new TopicoDto(topico));
		
	}
	
	@GetMapping("{id}")
	public ResponseEntity<DetalhesDoTopicoDto> detalhar(@PathVariable Long id) {
		
		Optional<Topico> optional = topicoRepository.findById(id);
		
		if(optional.isPresent()) {
			return ResponseEntity.ok().body(new DetalhesDoTopicoDto(optional.get()));
		}
		
		return ResponseEntity.notFound().build();
	}
	
	@PutMapping("{id}")
	@Transactional
	public ResponseEntity<TopicoDto> atualizar(@PathVariable Long id, @RequestBody @Valid AtualizacaoTopicoForm form) {
		
		Optional<Topico> optional = topicoRepository.findById(id);
		
		if(optional.isPresent()) {
			Topico topico = form.atualizar(id, topicoRepository);		
			return ResponseEntity.ok().body(new TopicoDto(topico));
		}
		
		return ResponseEntity.notFound().build();
		
	}
	
	@DeleteMapping("{id}")
	@Transactional
	public ResponseEntity<Void> apagar(@PathVariable Long id){
		
		Optional<Topico> optional = topicoRepository.findById(id);
		
		if(optional.isPresent()) {
			topicoRepository.deleteById(id);
			return ResponseEntity.ok().build();
		}
		
		return ResponseEntity.notFound().build();
		
	}
	
}
