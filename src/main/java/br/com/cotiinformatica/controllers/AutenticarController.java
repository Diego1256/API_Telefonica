package br.com.cotiinformatica.controllers;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import br.com.cotiinformatica.components.JwtComponent;
import br.com.cotiinformatica.components.MD5Component;
import br.com.cotiinformatica.dtos.AutenticarPostDto;

import br.com.cotiinformatica.entities.Usuario;

import br.com.cotiinformatica.repositories.UsuarioRepository;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
@Api(tags="Autenticação de usuários")
@RestController
public class AutenticarController {
	
	@Autowired
	UsuarioRepository usuarioRepository;
	
	
	@Autowired
	MD5Component md5Component;
	
	@Autowired
	JwtComponent jwtComponent;

	@ApiOperation("Serviço de autenticação e geração de token JWT")
	@PostMapping("/api/autenticar")
	public ResponseEntity<String> post(@RequestBody AutenticarPostDto dto){
		try {
			Usuario usuario = usuarioRepository.findByTelefoneAndSenha(dto.getTelefone(), md5Component.encrypt(dto.getSenha()));
			
			if(usuario != null) {
				
				String acessToken = jwtComponent.generateToken(usuario);
				
				
				return ResponseEntity.status(HttpStatus.OK).body(acessToken);
			}else {
				return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Usuario e senha não identificados");
			}
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao autenticar " + e.getMessage());
		}
	}
}
