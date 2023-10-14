package lk.pubudu.app.util;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.security.SecureRandom;

@Component
public class HashGenerator {

	public String generate(String password) {
		if (password == null) return null;
		BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder(10, new SecureRandom());
		return bCryptPasswordEncoder.encode(password);
	}
	
	public Boolean isMatching(String password, String encodedPassword) {
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		return encoder.matches(password, encodedPassword);
	}
}
