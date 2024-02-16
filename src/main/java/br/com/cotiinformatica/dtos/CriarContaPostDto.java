package br.com.cotiinformatica.dtos;

import br.com.cotiinformatica.entities.Plano;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString

public class CriarContaPostDto {
	
	private String nome;
	private String email;
	private String telefone;
	private String senha;
	private Integer plano;

}
