package impl;

import api.JwtSignatureValidator;
import impl.codecForJava7.Base64URLDecoder;

import java.security.Key;
import java.security.MessageDigest;
import java.util.Base64;

import static java.nio.charset.StandardCharsets.US_ASCII;

/**
 * This class implements a Jwt Signature validator and
 * provides methods to validate the computed hash and
 * actual hash.
 * @author Pratik Kumar
 */
public class MacValidator implements JwtSignatureValidator {

    private final MacSigner signer;

    public MacValidator(SignatureAlgorithm alg, Key key) {
        this.signer = new MacSigner(alg, key);
    }

    public boolean isValid(byte[] data, byte[] signature) {
        byte[] computed = this.signer.sign(data);
        return MessageDigest.isEqual(computed, signature);
    }

    public boolean isValid(String jwtWithoutSignature, String base64UrlEncodedSignature) {
        byte[] data = jwtWithoutSignature.getBytes(US_ASCII);

        // for java 7 , extra util class  Base64URLDecoder
        // used to ensure padding before decoding encoded url.

        byte[] signature = Base64URLDecoder.decode(base64UrlEncodedSignature);

        /*in built support in java 8 , noe need to take  care of padding.
        Un comment following line to work with java 8 */
      //   byte[] signature = Base64.getUrlDecoder().decode(base64UrlEncodedSignature.getBytes(US_ASCII));
        return isValid(data, signature);
    }


}

