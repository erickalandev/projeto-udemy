package io.github.erickalandev.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import io.github.erickalandev.domain.entity.Usuario;
import io.github.erickalandev.domain.repository.UsuarioRepository;
import io.github.erickalandev.exception.SenhaInvalidaException;

@Service
public class UsuarioServiceImpl implements UserDetailsService{
	
	@Autowired
	private PasswordEncoder encoder;
	
	@Autowired
	private UsuarioRepository repository;
	
	public UserDetails autenticar( Usuario usuario) {
		UserDetails user = loadUserByUsername(usuario.getLogin());
		boolean senhaBatem = encoder.matches(usuario.getSenha(), user.getPassword());
		if(senhaBatem) {
			return user;
		}
		throw new SenhaInvalidaException();
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Usuario usuario = repository.findByLogin(username)
				.orElseThrow( () -> new UsernameNotFoundException("Usuario nao encontrado no banco de dados"));
		
		String[] roles = usuario.isAdmin() ? new String[] {"USER", "ADMIN"} : new String[] {"USER"};  
		return User
				.builder()
				.username(usuario.getLogin())
				.password(usuario.getSenha())
				.roles(roles)
				.build();
	}

	public Usuario salvar( Usuario usuario) {
		return repository.save(usuario);
	}
	
}
