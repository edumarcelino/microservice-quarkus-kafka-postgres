package br.com.common.util;

import java.io.InputStream;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.time.Duration;
import java.util.Base64;
import java.util.Set;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import org.eclipse.microprofile.jwt.JsonWebToken;

import br.com.domain.model.enums.Role;
import io.smallrye.jwt.auth.principal.JWTParser;
import io.smallrye.jwt.build.Jwt;
import io.smallrye.jwt.build.JwtClaimsBuilder;

@ApplicationScoped
public class TokenUtils {

	@Inject
	JWTParser jwtParser;



	public static String generateToken(String username, Role role, Duration duration, String issuer) throws Exception {
		String privateKeyLocation = "/privateKey.pem";

		PrivateKey privateKey = readPrivateKey(privateKeyLocation);

		long currentTimeInSecs = currentTimeInSecs();

		JwtClaimsBuilder claimsBuilder = Jwt.claims()
				.issuer(issuer)
				.subject(username)
				.issuedAt(currentTimeInSecs)
				.expiresAt(currentTimeInSecs + duration.getSeconds())
				.groups(Set.of(role.name())); // Apenas um role

		return claimsBuilder.jws().keyId(privateKeyLocation).sign(privateKey);
	}

	public static PrivateKey readPrivateKey(final String pemResName) throws Exception {
		System.out.println("🔍 Tentando carregar a chave privada do arquivo: " + pemResName);

		InputStream contentIS = TokenUtils.class.getClassLoader().getResourceAsStream(pemResName);

		if (contentIS == null) {
			System.out.println("❌ Arquivo não encontrado no classpath: " + pemResName);
			throw new IllegalArgumentException("Arquivo não encontrado no classpath: " + pemResName);
		}

		try (contentIS) {
			byte[] tmp = new byte[4096];
			int length = contentIS.read(tmp);
			String keyContent = new String(tmp, 0, length, "UTF-8");

			System.out.println("✅ Arquivo carregado com sucesso. Tamanho: " + length + " bytes");
			System.out.println("🔐 Início do conteúdo da chave:\n"
					+ keyContent.substring(0, Math.min(100, keyContent.length())) + "...");

			return decodePrivateKey(keyContent);
		}
	}

	public static PrivateKey decodePrivateKey(final String pemEncoded) throws Exception {
		byte[] encodedBytes = toEncodedBytes(pemEncoded);

		PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(encodedBytes);
		KeyFactory kf = KeyFactory.getInstance("RSA");
		return kf.generatePrivate(keySpec);
	}

	public static byte[] toEncodedBytes(final String pemEncoded) {
		final String normalizedPem = removeBeginEnd(pemEncoded);
		return Base64.getDecoder().decode(normalizedPem);
	}

	public static String removeBeginEnd(String pem) {
		pem = pem.replaceAll("-----BEGIN (.*)-----", "");
		pem = pem.replaceAll("-----END (.*)----", "");
		pem = pem.replaceAll("\r\n", "");
		pem = pem.replaceAll("\n", "");
		return pem.trim();
	}

	public static int currentTimeInSecs() {
		long currentTimeMS = System.currentTimeMillis();
		return (int) (currentTimeMS / 1000);
	}

	public JsonWebToken parseToken(String token) throws Exception {
		return jwtParser.parse(token);
	}

	public String getUsernameFromToken(String token) throws Exception {
		JsonWebToken jwt = parseToken(token);
		return jwt.getName(); // ou jwt.getClaim(Claims.upn.name());
	}

	public Set<String> getRolesFromToken(String token) throws Exception {
		JsonWebToken jwt = parseToken(token);
		return jwt.getGroups();
	}
}
