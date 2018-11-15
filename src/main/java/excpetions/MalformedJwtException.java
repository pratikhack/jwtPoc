package excpetions;

public class MalformedJwtException extends JwtException {

    public MalformedJwtException(String message) {
        super(message);
    }

    public MalformedJwtException(String message, Throwable cause) {
        super(message, cause);
    }
}
