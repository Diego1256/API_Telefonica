package br.com.cotiinformatica.controllers;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mysql.cj.protocol.x.Ok;

import br.com.cotiinformatica.components.JwtComponent;
import br.com.cotiinformatica.dtos.BuscaPlanoDto;
import br.com.cotiinformatica.dtos.BuscaUsuariosGetDto;
import br.com.cotiinformatica.entities.Plano;
import br.com.cotiinformatica.entities.Usuario;
import br.com.cotiinformatica.repositories.UsuarioRepository;

@RestController
public class TempoMaximoDoPlanoUsuario {
	
	@Autowired
	UsuarioRepository usuarioRepository;
	
	@Autowired
	JwtComponent jwtComponent;
	
	@GetMapping("/api/tempoDeAplicacao")
	public ResponseEntity<BuscaPlanoDto> get(HttpServletRequest request){
		try {
			String accessToken = request.getHeader("Authorization").replace("Bearer", "");
			String telefoneUsuario = jwtComponent.getUserFromToken(accessToken);
			
			Usuario usuario = usuarioRepository.findByTelefone(telefoneUsuario);
			
			BuscaPlanoDto dto = new BuscaPlanoDto();
			if(usuario != null) {
				dto.setTempoDeAplicacao(usuario.getPlano().getTempoDeAplicacao());
			}
			
			
			return ResponseEntity.ok().body(dto);
			
		} catch (Exception e) {
			return ResponseEntity.internalServerError().body(null);
		}
		
	}

}
