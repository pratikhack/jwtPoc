package impl;

/**
 * This enum defines a standard cryptographic algorithms and
 * can be used for type safety while evaluating algorithm types.
 * @author Pratik Kumar
 */
public enum SignatureAlgorithm {

    /** JWA algorithm name for {@code HMAC using SHA-256} */
    HS256("HS256", "HMAC using SHA-256", "HMAC", "HmacSHA256", true);

    private final String  value;

    public String getValue() {
        return value;
    }

    public String getDescription() {
        return description;
    }

    public String getFamilyName() {
        return familyName;
    }

    public String getJcaName() {
        return jcaName;
    }

    public boolean isJdkStandard() {
        return jdkStandard;
    }

    public boolean isHmac() {
        return name().startsWith("HS");
    }

    private final String  description;
    private final String  familyName;
    private final String  jcaName;
    private final boolean jdkStandard;

    SignatureAlgorithm(String value, String description, String familyName, String jcaName, boolean jdkStandard) {
        this.value = value;
        this.description = description;
        this.familyName = familyName;
        this.jcaName = jcaName;
        this.jdkStandard = jdkStandard;
    }
}

