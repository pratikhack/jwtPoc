package impl;

import api.JwtSignatureValidator;
import excpetions.MalformedJwtException;

import java.security.Key;

/**
 * This class parses the JWT string and validates the signature of the JWT using
 * HS256 Algorithm.
 * @author Pratik Kumar
 */
public class HMACSignatureParser {

    private Key key;
    public static final char SEPARATOR_CHAR = '.';

    /**
     * @param key key used for signing the JWT token
     */
    public HMACSignatureParser(Key key) {
        if(key == null) {
            throw new IllegalArgumentException("HMAC algos need a valid secret key");
        }
        this.key = key;
    }


    /**
     * This method checks the validity of the JWT token
     * @param jwt A base64encoded JWT token
     * @return true if the jwt token is authentic , else return false
     */

    public boolean isValidJWT(String jwt) throws MalformedJwtException {

        if(jwt == null )
        {
            throw new MalformedJwtException("JWT String argument cannot be null or empty.");
        }

        String base64UrlEncodedHeader = null;
        String base64UrlEncodedPayload = null;
        String base64UrlEncodedDigest = null;

        int delimiterCount = 0;

        StringBuilder sb = new StringBuilder(128);

        for (char c : jwt.toCharArray()) {

            if (c == SEPARATOR_CHAR) {

                CharSequence tokenSeq = Strings.clean(sb);
                String token = tokenSeq!=null?tokenSeq.toString():null;

                if (delimiterCount == 0) {
                    base64UrlEncodedHeader = token;
                } else if (delimiterCount == 1) {
                    base64UrlEncodedPayload = token;
                }

                delimiterCount++;
                sb.setLength(0);
            } else {
                sb.append(c);
            }
        }

        if (delimiterCount != 2) {
            String msg = "JWT strings must contain exactly 2 period characters. Found: " + delimiterCount;
            throw new MalformedJwtException(msg);
        }
        if (sb.length() > 0) {
            base64UrlEncodedDigest = sb.toString();
        }

        if (base64UrlEncodedPayload == null) {
            throw new MalformedJwtException("JWT string '" + jwt + "' is missing a body/payload.");
        }

        String jwtWithoutSignature = base64UrlEncodedHeader + SEPARATOR_CHAR + base64UrlEncodedPayload;

        JwtSignatureValidator macValidator = new MacValidator(SignatureAlgorithm.HS256,this.key);

        return macValidator.isValid(jwtWithoutSignature,base64UrlEncodedDigest);
    }
}
