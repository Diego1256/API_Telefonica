package br.com.cotiinformatica.controllers;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import br.com.cotiinformatica.components.JwtComponent;
import br.com.cotiinformatica.components.MD5Component;
import br.com.cotiinformatica.dtos.AlterarSenhaPutDto;
import br.com.cotiinformatica.entities.Usuario;
import br.com.cotiinformatica.repositories.UsuarioRepository;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(tags = "Alteração de senha de usuario")
@RestController
public class AlterarSenhaController {

	@Autowired
	UsuarioRepository usuarioRepository;

	@Autowired
	MD5Component md5Component;

	@Autowired
	JwtComponent jwtComponent;

	@ApiOperation("Serviço de alteração de senha que pede autenticação por token")
	@PutMapping("/api/alterar-senha")
	public ResponseEntity<String> put(@RequestBody AlterarSenhaPutDto dto, HttpServletRequest request) {
		try {
			String acessToken = request.getHeader("Authorization").replace("Bearer", "").trim();
			String telefone = jwtComponent.getUserFromToken(acessToken);

			String senhaAtual = md5Component.encrypt(dto.getSenhaAtual());
			String novaSenha = md5Component.encrypt(dto.getNovaSenha());

			Usuario usuario = usuarioRepository.findByTelefoneAndSenha(telefone, senhaAtual);

			if (usuario == null) {
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Email ou senha errados");
			}

			usuario = new Usuario();
			usuario.setSenha(md5Component.encrypt(novaSenha));
			usuarioRepository.save(usuario);

			

			return ResponseEntity.status(HttpStatus.OK).body("Email alterado com sucesso");
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body("Erro ao alterar a senha " + e.getMessage());
		}

	}

}
