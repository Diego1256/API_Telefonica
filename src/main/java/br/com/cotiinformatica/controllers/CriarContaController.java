package br.com.cotiinformatica.controllers;



import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import br.com.cotiinformatica.components.EmailComponent;
import br.com.cotiinformatica.components.EmailModelComponent;
import br.com.cotiinformatica.components.MD5Component;
import br.com.cotiinformatica.dtos.CriarContaPostDto;
import br.com.cotiinformatica.entities.Plano;
import br.com.cotiinformatica.entities.Usuario;
import br.com.cotiinformatica.repositories.PlanoRepository;
import br.com.cotiinformatica.repositories.UsuarioRepository;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(tags = "Criar Conta")
@RestController
public class CriarContaController {

	@Autowired
	UsuarioRepository usuarioRepository;

	@Autowired
	PlanoRepository planoRepository;
	
	@Autowired
	MD5Component md5Component;

	@Autowired
	EmailComponent emailComponent;

	@ApiOperation("Serviço para criação de contas para usuarios")
	@PostMapping("/api/criar-conta")
	public ResponseEntity<String> post(@RequestBody CriarContaPostDto dto) {

		try {

			Usuario usuario = usuarioRepository.findByEmail(dto.getEmail());

			if (usuario != null) {
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Já existe esse email em nossa base");
			}

			usuario = new Usuario();

			
			usuario.setNome(dto.getNome());
			usuario.setEmail(dto.getEmail());
			usuario.setTelefone(dto.getTelefone());
			
			Optional<Plano> optionalPlano = planoRepository.findById(dto.getPlano());
            if (optionalPlano.isPresent()) {
                usuario.setPlano(optionalPlano.get());
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Plano não encontrado");
            }
			
			usuario.setSenha(md5Component.encrypt(dto.getSenha()));

			usuarioRepository.save(usuario);
			
			EnviarMensagemDeBoasVindas(usuario);


			return ResponseEntity.status(HttpStatus.CREATED).body("Usuario criado com sucesso");

		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body("Erro ao criar a conta " + e.getMessage());
		}
	}
	private void EnviarMensagemDeBoasVindas(Usuario usuario) throws Exception{
		EmailModelComponent model = new EmailModelComponent();
		model.setEmailDest(usuario.getEmail());
		model.setAssunto("Nova conta criada");
		model.setTexto("<p>Conta criada com sucesso</p>");
		model.setHtml(true);
		
		emailComponent.enviaMensagem(model);
	}
}
