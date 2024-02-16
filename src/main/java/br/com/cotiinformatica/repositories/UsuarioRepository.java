package br.com.cotiinformatica.repositories;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.com.cotiinformatica.entities.Usuario;

@Repository
public interface UsuarioRepository extends CrudRepository<Usuario, Integer> {
	
	@Query("from Usuario u where u.email=:pEmail")
	Usuario findByEmail(@Param("pEmail") String email) throws Exception;	
	
	@Query("from Usuario u where u.telefone=:pTelefone")
	Usuario findByTelefone(@Param("pTelefone") String telefone) throws Exception;
	
	@Query("from Usuario u where u.telefone=:pTelefone and u.senha=:pSenha")
	Usuario findByTelefoneAndSenha(@Param("pTelefone") String telefone, @Param("pSenha") String senha ) throws Exception;

}
