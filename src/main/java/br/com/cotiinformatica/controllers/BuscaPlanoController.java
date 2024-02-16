package br.com.cotiinformatica.controllers;

import java.util.ArrayList;
import java.util.List;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.cotiinformatica.dtos.BuscaPlanoDto;
import br.com.cotiinformatica.entities.Plano;
import br.com.cotiinformatica.repositories.PlanoRepository;
import io.swagger.annotations.Api;
@Api(tags="Busca Plano")
@RestController
public class BuscaPlanoController {

	@Autowired
	PlanoRepository planoRepository;

	@GetMapping("/api/busca-plano")
	public ResponseEntity<List<BuscaPlanoDto>>get() {
		
		try {
			List<BuscaPlanoDto> lista = new ArrayList<BuscaPlanoDto>();
			for (Plano plano : planoRepository.findAll()) {
				BuscaPlanoDto dto = new BuscaPlanoDto();
				dto.setNomePlano(plano.getNomePlano());
				dto.setIdPlano(plano.getIdPlano());
				lista.add(dto);
			}
			return ResponseEntity.ok().body(lista);
		} catch (Exception e) {
			return ResponseEntity.internalServerError().body(null);
		}
		
	}
}
