package io.github.erickalandev.security.jwt;

import java.util.Date;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Service;

import io.github.erickalandev.VendasApplication;
import io.github.erickalandev.domain.entity.Usuario;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Service
public class JwtService {
	
	@Value("${security.jwt.expiracao}")
	private String expiracao;
	
	@Value("${security.jwt.chave-assinatura}")
	private String chaveAssinatura; //chave do servidor
	
	public String gerarToken( Usuario usuario ) {
		long expString = Long.valueOf(expiracao);
		/*Ao logar, o localDateTime pega o exato momento que entrou(now)
		 * e soma (plusMinutes) os valor em minutos que foi colocado no parenteses
		 * no caso 30 minutos(expString) 
		 */
		LocalDateTime dataHoraExpiracao = LocalDateTime.now().plusMinutes(expString);
		/*Esse instante criado seria a zona, ou seja, a hora vai estar sendo feita de 
		 * acordo com a zona do Brasil que eh onde estamos(systemDefault)
		*/
		Instant instant = dataHoraExpiracao.atZone(ZoneId.systemDefault()).toInstant();
		/*Na api que estamos utilizando a expiracao do jwt tem que ser do tipo Date, por isso a conversao*/
		Date data = Date.from( instant );
		return Jwts
				 		.builder()
				 		/*o setSubject seria o payload do token, onde colocamos as informacoes do usuario 
				 		 * por exemplo para ele poder fazer as requisicoes no sistema.*/
				 		.setSubject(usuario.getLogin())
				 		.setExpiration(data)
				 		/*signWith eh a assinatura do token*/
				 		.signWith( SignatureAlgorithm.HS512, chaveAssinatura )
				 		/*o compact vai fazer todos esses dados virarem uma string e passar como token*/
				 		.compact();
				 		
	}

	private Claims obterClaims( String token ) throws ExpiredJwtException {
		return Jwts
				.parser()
				.setSigningKey(chaveAssinatura)
				.parseClaimsJws(token)
				.getBody();
	}
	
	public boolean tokenValido( String token) {
		try {
			Claims claims = obterClaims(token);
			Date dataExpiracao = claims.getExpiration();
			LocalDateTime data = dataExpiracao.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
			return !LocalDateTime.now().isAfter(data);
		} catch (Exception e) {
			return false;
		}		
	}
	
	public String obterLoginUsuario(String token) throws ExpiredJwtException {
		return (String) obterClaims(token).getSubject();
	}
	
	/*metodo de rodar uma aplicacao standarlone*/
	 public static void main(String[] args) {
		 ConfigurableApplicationContext context = SpringApplication.run(VendasApplication.class);
		 JwtService service = context.getBean(JwtService.class);
		 Usuario usuario = Usuario.builder().login("fulano").build();
		 String token = service.gerarToken(usuario);
		 System.out.println(token);
		 
		 boolean isTokenValido = service.tokenValido(token);
		 System.out.println("token valido? "+ isTokenValido);
		 
		 System.out.println(service.obterLoginUsuario(token));
	 }
}
