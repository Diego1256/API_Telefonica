package br.com.cotiinformatica.controllers;

import java.util.Date;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.github.javafaker.Faker;

import br.com.cotiinformatica.components.EmailComponent;
import br.com.cotiinformatica.components.EmailModelComponent;
import br.com.cotiinformatica.components.MD5Component;
import br.com.cotiinformatica.dtos.RecuperarSenhaPostDto;

import br.com.cotiinformatica.entities.Usuario;

import br.com.cotiinformatica.repositories.UsuarioRepository;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(tags = "Recuperar senha")
@RestController
public class RecuperarSenhaController {

	@Autowired
	UsuarioRepository usuarioRepository;


	@Autowired
	MD5Component md5Component;

	@Autowired
	EmailComponent emailComponent;

	@ApiOperation("Serviço de recuperação e enviada um email através do FAKER")
	@PostMapping("/api/recuperar-senha")
	public ResponseEntity<String> post(@RequestBody RecuperarSenhaPostDto dto) {
		try {
			Usuario usuario = usuarioRepository.findByEmail(dto.getEmail());

			if (usuario == null) {
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Email não encontrado");
			}

			Faker faker = new Faker(new Locale("pt-BR"));
			String novaSenha = faker.internet().password(8, 10);

			usuario = new Usuario();
			usuario.setSenha(md5Component.encrypt(novaSenha));
			enviarEmailDeRecuperacaoDeSenha(usuario, novaSenha);
			

			return ResponseEntity.status(HttpStatus.OK).body("Nova senha enviada para a sua caixa de correio");
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body("Erro ao recuperar a senha " + e.getMessage());
		}
	}
	
	private void enviarEmailDeRecuperacaoDeSenha(Usuario usuario, String novaSenha) throws Exception{
		
		EmailModelComponent model = new EmailModelComponent();
		
		model.setEmailDest(usuario.getEmail());
		model.setAssunto("Recuperação de senha");
		model.setTexto("<p>Olá"+usuario.getNome()+", a sua nova senha é"+ novaSenha+"</p>");
		model.setHtml(true);
		
		emailComponent.enviaMensagem(model);
	}
}
