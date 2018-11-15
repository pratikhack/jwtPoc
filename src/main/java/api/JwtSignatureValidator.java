package api;

public interface JwtSignatureValidator {

    boolean isValid(String jwtWithoutSignature, String base64UrlEncodedSignature);

}

