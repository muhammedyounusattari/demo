package deleteit;

import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.*;
import com.nimbusds.jwt.*;

import java.security.KeyStore;
import java.security.PrivateKey;
import java.security.cert.Certificate;
import java.util.Date;

public class JwtTokenGenerator {

    public static String generateJwtToken(String appId, String tenantId, PrivateKey privateKey, Certificate certificate) throws JOSEException {
        // Create JWT claims
        JWTClaimsSet claimsSet = new JWTClaimsSet.Builder()
                .audience("00000002-0000-0000-c000-000000000000")
                .issuer(appId)
                .notBeforeTime(new Date())
                .expirationTime(new Date(System.currentTimeMillis() + 600000)) // 10 minutes
                .build();

        // Create JWS header with the certificate
        JWSHeader header = new JWSHeader.Builder(JWSAlgorithm.RS256)
                .x509CertChain(Collections.singletonList(Base64.encode(certificate.getEncoded())))
                .build();

        // Create the signed JWT
        SignedJWT signedJWT = new SignedJWT(header, claimsSet);
        signedJWT.sign(new RSASSASigner(privateKey));

        return signedJWT.serialize();
    }

    public static void main(String[] args) throws Exception {
        // Load the certificate and private key from a keystore
        KeyStore keyStore = KeyStore.getInstance("PKCS12");
        keyStore.load(new FileInputStream("path/to/keystore.p12"), "keystorePassword".toCharArray());
        PrivateKey privateKey = (PrivateKey) keyStore.getKey("alias", "keyPassword".toCharArray());
        Certificate certificate = keyStore.getCertificate("alias");

        String jwtToken = generateJwtToken("YOUR_APP_ID", "YOUR_TENANT_ID", privateKey, certificate);
        System.out.println("Generated JWT Token: " + jwtToken);
    }
}
