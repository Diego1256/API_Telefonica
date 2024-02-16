package br.com.cotiinformatica.controllers;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.cotiinformatica.components.JwtComponent;
import br.com.cotiinformatica.dtos.BuscaUsuariosGetDto;
import br.com.cotiinformatica.entities.Usuario;
import br.com.cotiinformatica.repositories.UsuarioRepository;
import io.swagger.annotations.Api;

@Api(tags = "Busca usuarios")
@RestController
public class BuscaUsuario {
	@Autowired
	UsuarioRepository usuarioRepository;
	
	@Autowired
	JwtComponent jwtComponent;

	@GetMapping("/api/busca-usuario")
	ResponseEntity<BuscaUsuariosGetDto> get(HttpServletRequest request) {

		try {
			String accessToken = request.getHeader("Authorization").replace("Bearer", "");
			String telefoneUsuario = jwtComponent.getUserFromToken(accessToken);
			
			Usuario usuario = usuarioRepository.findByTelefone(telefoneUsuario);
			
			BuscaUsuariosGetDto dto = new BuscaUsuariosGetDto();
			
			if(usuario != null) {
				dto.setEmail(usuario.getEmail());
				dto.setNome(usuario.getNome());
				dto.setTelefone(usuario.getTelefone());
				dto.setNomePlano(usuario.getPlano().getNomePlano());
				dto.setTempoDeAplicacao(usuario.getPlano().getTempoDeAplicacao());
			}
			
			return ResponseEntity.ok().body(dto);
		} catch (Exception e) {
			return ResponseEntity.internalServerError().body(null);
		}

	}
}
