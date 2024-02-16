package br.com.cotiinformatica.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class BuscaPlanoDto {
	
	private String nomePlano;
	private Integer idPlano;
	private Integer tempoDeAplicacao;

}
