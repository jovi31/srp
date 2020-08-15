package edu.ifes.ci.si.les.srp.utils;

import java.util.Set;

import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;

public class CustomAuthorityUtils {
	
	public static Boolean isAdmin(UserDetails userDetails) {
		Set<String> authorities = AuthorityUtils.authorityListToSet(userDetails.getAuthorities());
		return authorities.contains("ROLE_ADMIN");
	}
	
}
