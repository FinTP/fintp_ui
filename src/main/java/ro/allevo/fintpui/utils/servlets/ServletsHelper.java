package ro.allevo.fintpui.utils.servlets;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;
import com.sun.jersey.api.client.filter.HTTPBasicAuthFilter;

public class ServletsHelper {
	
	private String url;
	
	public Client getAPIClient(){
		final ClientConfig cc = new DefaultClientConfig();
		final Client client = Client.create(cc);
		Authentication authentication = SecurityContextHolder.getContext()
				.getAuthentication();
		client.addFilter(new HTTPBasicAuthFilter(new String(
				((UserDetails) authentication.getPrincipal()).getUsername()),
				new String(((UserDetails) authentication.getPrincipal())
						.getPassword())));
		return client;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
}
