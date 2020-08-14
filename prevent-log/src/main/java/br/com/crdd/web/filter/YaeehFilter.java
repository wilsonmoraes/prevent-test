package br.com.crdd.web.filter;

import br.com.crdd.service.UsuarioService;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

public class YaeehFilter extends GenericFilterBean {

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
            throws IOException, ServletException {

        final HttpServletRequest request = (HttpServletRequest) req;

        validateToken(request);
        chain.doFilter(req, res);
    }

    /**
     * Método criado pra validar se a requisição atual possui um token e se este
     * token é válido.
     *
     * @param request
     */
    protected void validateToken(final HttpServletRequest request) {
        UsuarioService.parseToken(request.getHeader("Authorization"));
    }
}