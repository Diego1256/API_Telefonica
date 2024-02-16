package br.com.cotiinformatica.dtos;

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
public class BuscaUsuariosGetDto {
	private String nome;
	private String email;
	private String nomePlano;
	private String telefone;
	private Integer tempoDeAplicacao;
}
