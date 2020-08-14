package br.com.crdd.web.open;

import br.com.crdd.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;


@RestController
@RequestMapping(value = "open/auth", produces = MediaType.APPLICATION_JSON_VALUE)
public class AuthController {
    @Autowired
    private UsuarioService usuarioService;

    @GetMapping(value = "/login")
    public ResponseEntity<Map<String, Object>> login(@RequestParam(value = "login") String login,
                                                     @RequestParam(value = "senha") String senha) {
        String token = usuarioService.login(login, senha);

        return ResponseEntity.status(HttpStatus.OK)
                .body(new HashMap<String, Object>() {{
                    put("token", token);
                }});
    }

}
