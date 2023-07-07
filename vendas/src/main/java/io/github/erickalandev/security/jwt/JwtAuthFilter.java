package io.github.erickalandev.security.jwt;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import io.github.erickalandev.service.impl.UsuarioServiceImpl;

public class JwtAuthFilter extends OncePerRequestFilter {
	
	private JwtService jwtService;
	private UsuarioServiceImpl usuarioService;
	
	public JwtAuthFilter( JwtService jwtService, UsuarioServiceImpl usuarioService) {
		this.jwtService = jwtService;
		this.usuarioService = usuarioService;
	}

	@Override
	protected void doFilterInternal(
			HttpServletRequest request, 
			HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
		/*esse metodo do request pega qualquer regra de requisicao que esta sendo processado
		o Authorization no caso e aquela requisicao do postman onde tempos que passar o token*/
		String authorization = request.getHeader("Authorization");
		if(authorization != null && authorization.startsWith("Bearer")) {
			String token = authorization.split(" ")[1]; //[] e a posicao da "palavra" que foi recortada
			boolean isValid = jwtService.tokenValido(token);
			if(isValid) {
				String loginUsuario = jwtService.obterLoginUsuario(token);
				UserDetails usuario = usuarioService.loadUserByUsername(loginUsuario);
				UsernamePasswordAuthenticationToken user = 
						new UsernamePasswordAuthenticationToken(usuario, null, usuario.getAuthorities());
				/*e necessario fazer essa authentificacao abaixo para dizer que esse objeto de authentificacao
				 (UsernamePasswordAuthenticationToken) e uma autentificacao web. Ele 
				 diz ao meu contexto do spring security que se trata de uma autentificacao de aplicacao web */
				user.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
				/*Esse aqui abaixo e o contexto do spring security, para dizer quem e o usuario autenticado
				 quanto pegar um usuario autenticado 
				 getAuthentication: obtem o usuario da sessao(que esta dentro do contexto)
				 setAuthentication: a gente passa uma autentificacao para o SecurityContextHolder
				 */
				SecurityContextHolder.getContext().setAuthentication(user);
			}
			
			filterChain.doFilter(request, response);
		}
		
	}

}
