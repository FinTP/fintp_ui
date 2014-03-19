package ro.allevo.fintpui.utils;

import java.io.IOException;
import java.net.HttpURLConnection;

import org.apache.commons.codec.binary.Base64;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.http.converter.json.MappingJacksonHttpMessageConverter;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.client.RestTemplate;

public class RestClient extends RestTemplate {

	public RestClient() {
		UserDetails principal = (UserDetails) SecurityContextHolder
				.getContext().getAuthentication().getPrincipal();
		final String username = principal.getUsername();
		final String password = principal.getPassword();
		SimpleClientHttpRequestFactory s = new SimpleClientHttpRequestFactory() {
			@Override
			protected void prepareConnection(HttpURLConnection connection,
					String httpMethod) throws IOException {
				String auth = username + ":" + password;
				byte[] encodedAuthorisation = Base64.encodeBase64(auth
						.getBytes());

				connection.setRequestProperty("Authorization", "Basic "
						+ new String(encodedAuthorisation));
			}
		};
		setRequestFactory(s);
		getMessageConverters().add(new MappingJacksonHttpMessageConverter());
	}

//	public RestClient(final String username, final String password) {
//
//		SimpleClientHttpRequestFactory s = new SimpleClientHttpRequestFactory() {
//			@Override
//			protected void prepareConnection(HttpURLConnection connection,
//					String httpMethod) throws IOException {
//				String auth = username + ":" + password;
//				byte[] encodedAuthorisation = Base64.encodeBase64(auth
//						.getBytes());
//
//				connection.setRequestProperty("Authorization", "Basic "
//						+ new String(encodedAuthorisation));
//			}
//		};
//		setRequestFactory(s);
//		getMessageConverters().add(new MappingJacksonHttpMessageConverter());
//
//	}
}
