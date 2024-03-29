package br.com.cotiinformatica.entities;



import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "usuario")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Usuario {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "idusuario")
	private Integer idUsuario;
	@Column(name = "nome", length = 150, nullable = false)
	private String nome;
	@Column(name = "email", length = 100, nullable = false, unique = true)
	private String email;
	
	@Column(name = "telefone", length = 12, nullable = false, unique=true)
	private String telefone;
	
	@Column(name = "senha", length = 40, nullable = false)
	private String senha;
	
	@ManyToOne
	@JoinColumn(name="idplano", nullable = false)
	private Plano plano;

	

}
