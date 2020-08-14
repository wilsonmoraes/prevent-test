package br.com.crdd.dao;

import br.com.crdd.domain.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;


@Repository
@Transactional
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    Usuario findUnicByLoginAndSenha(String login, String senha);


}
