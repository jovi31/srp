package edu.ifes.ci.si.les.srp.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import edu.ifes.ci.si.les.srp.config.CustomUser;
import edu.ifes.ci.si.les.srp.model.Usuario;
import edu.ifes.ci.si.les.srp.repositories.UsuarioRepository;

@Component
public class CustomUserDetailService implements UserDetailsService {
	
	private final UsuarioRepository repository;
	
	@Autowired
	public CustomUserDetailService(UsuarioRepository repository) {
		this.repository = repository;
	}

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		Usuario usuario = Optional.ofNullable(repository
				.findByEmail(email)).orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado."));
		
		List<GrantedAuthority> authorityListAdmin = AuthorityUtils.createAuthorityList("ROLE_USER", "ROLE_ADMIN");
		List<GrantedAuthority> authorityListUser = AuthorityUtils.createAuthorityList("ROLE_USER");
		
		Boolean isAdmin = usuario.getAdmin();
		
		return new CustomUser(usuario.getEmail(), usuario.getSenha(), isAdmin ? authorityListAdmin : authorityListUser, usuario.getId());
	}
	
}
