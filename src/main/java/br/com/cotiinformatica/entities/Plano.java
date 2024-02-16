package br.com.cotiinformatica.entities;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name="plano")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Plano {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="idplano", nullable=false)
	private Integer idPlano;
	@Column(name="nomePlano", length=40, nullable=false)
	private String nomePlano;
	@Column(name="tempodeaplicacao",length=60, nullable=false)
	private Integer tempoDeAplicacao;
	@Column(name="tempoRestante",length=60, nullable=false)
	private Integer tempoRestante;
	
	@OneToMany(mappedBy ="plano")
	private List<Usuario> usuario;
	
	
	

}
