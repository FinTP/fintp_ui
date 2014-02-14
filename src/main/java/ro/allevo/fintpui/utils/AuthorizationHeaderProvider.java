package ro.allevo.fintpui.utils;

import org.apache.commons.codec.binary.Base64;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

public class AuthorizationHeaderProvider{

	public static String getAuthorizationHeader(){
		Authentication authentication = SecurityContextHolder.getContext()
				.getAuthentication();
		byte[] encodedAuthorisation = Base64.encodeBase64(new String(
				((UserDetails) authentication.getPrincipal()).getUsername()
						+ ":"
						+ ((UserDetails) authentication.getPrincipal())
								.getPassword()).getBytes());
		return "Basic " + new String(encodedAuthorisation);
	}
}
