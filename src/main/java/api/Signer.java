package api;

import excpetions.SignatureException;

public interface Signer {

    byte[] sign(byte[] data) throws SignatureException;
}
