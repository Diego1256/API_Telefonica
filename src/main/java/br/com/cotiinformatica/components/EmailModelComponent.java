package br.com.cotiinformatica.components;

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
public class EmailModelComponent {
	
	private String emailDest;
	private String assunto;
	private String texto;
	private boolean isHtml;

}
