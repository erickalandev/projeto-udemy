package io.github.erickalandev.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.filter.OncePerRequestFilter;

import io.github.erickalandev.security.jwt.JwtAuthFilter;
import io.github.erickalandev.security.jwt.JwtService;
import io.github.erickalandev.service.impl.UsuarioServiceImpl;

@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	
	@Autowired
	private UsuarioServiceImpl usuarioService;
	
	@Autowired
	private JwtService jwtService;
 
	@Bean
	public PasswordEncoder passwordEncoder() {
		return	 new BCryptPasswordEncoder();
	}
	
	@Bean
	public OncePerRequestFilter jwtFilter() {
		return new JwtAuthFilter(jwtService, usuarioService);
	}
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth
			.userDetailsService(usuarioService)
			.passwordEncoder(passwordEncoder());
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
			 .csrf().disable()
			 .authorizeRequests()
			 	.antMatchers("/api/clientes/**")
			 		.hasAnyRole("USER", "ADMIN")
			 	.antMatchers("/api/produtos/**")
			 		.hasRole("ADMIN")
			 	.antMatchers("/api/pedidos/**")
			 		.hasAnyRole("USER", "ADMIN")
			 	.antMatchers(HttpMethod.POST, "/api/usuarios/**")
			 		.permitAll()
			 	.anyRequest()
			 		.authenticated()
			 //volta pra raiz do httpSecurity
			 .and()
			 		/*definicao para nao criar mais sessoes. cada requisicao vai conter todos os elementos necessarios
			 		 para acontecer, no caso antes com o httpBasic, a gente tinha um usuarios que ja estava logado na sessao
			 		 e toda requisicao ja tinha um usuario logado. Mas agora nao tem mais.*/
			 		.sessionManagement()
			 		/* O acesso sera stateless agora, fazendo a aplicacao nao ter mais sessao de usuario.
			 		 * Agora toda requisicao tem que passar o token
			 		 */
			 		.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
			.and()
					/*esse metodo pode adicionar um filtro(jwtFilter) antes de fazer o contexto de toda aplicacao 
					  feito pelo filtro do proprio spring security(UsernamePasswordAuthenticationFilter)
					  o metodo jwtFilter e onde vai criar nosso usuario ai jogamos esse usuario dentro do contexto 
					  do spring security para ele verificar as autorizacoes de requisicoes la em cima(authorizeRequests()*/
					.addFilterBefore( jwtFilter(), UsernamePasswordAuthenticationFilter.class );
	}
	
	@Override
	public void configure(WebSecurity web) throws Exception {
		web.ignoring().antMatchers(
				"/v2/api-docs",
                "/configuration/ui",
                "/swagger-resources/**",
                "/configuration/security",
                "/swagger-ui.html",
                "/webjars/**");
	}
}
