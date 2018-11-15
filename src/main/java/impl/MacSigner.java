package impl;

import api.Signer;
import excpetions.SignatureException;

import javax.crypto.Mac;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;

/**
 * A HMAC-SHA-256 based Signer which can actually sign the
 * data using the provided key.
 * @author Pratik Kumar
 */
public class MacSigner implements Signer {

    protected final impl.SignatureAlgorithm alg;
    protected final Key key;

    protected MacSigner(SignatureAlgorithm alg, Key key) {
        if (alg == null) {
            throw new IllegalArgumentException("SignatureAlgorithm cannot be null.");
        }

        if (key == null) {

            throw new IllegalArgumentException("Key cannot be null.");
        }
        this.alg = alg;
        this.key = key;
    }

    public byte[] sign(byte[] data) {
        Mac mac = getMacInstance();
        return mac.doFinal(data);
    }

    protected Mac getMacInstance() throws SignatureException {
        try {
            return doGetMacInstance();
        } catch (NoSuchAlgorithmException e) {
            String msg = "Unable to obtain JCA MAC algorithm '" + alg.getJcaName() + "': " + e.getMessage();
            throw new excpetions.SignatureException(msg, e);
        } catch (InvalidKeyException e) {
            String msg = "The specified signing key is not a valid " + alg.name() + " key: " + e.getMessage();
            throw new excpetions.SignatureException(msg, e);
        }
    }

    protected Mac doGetMacInstance() throws NoSuchAlgorithmException, InvalidKeyException {
        Mac mac = Mac.getInstance(alg.getJcaName());
        mac.init(key);
        return mac;
    }
}
