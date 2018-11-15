package impl.codecForJava7;

import javax.xml.bind.DatatypeConverter;

public class Base64URLDecoder {

    public static byte[] decode(String encoded) {
        char[] chars = encoded.toCharArray(); //always ASCII - one char == 1 byte

        //Base64 requires padding to be in place before decoding, so add it if necessary:
        chars = ensurePadding(chars);

        //Replace url-friendly chars back to normal Base64 chars:
        for (int i = 0; i < chars.length; i++) {
            if (chars[i] == '-') {
                chars[i] = '+';
            } else if (chars[i] == '_') {
                chars[i] = '/';
            }
        }

        String base64Text = new String(chars);

        return DatatypeConverter.parseBase64Binary(base64Text);
    }

    public static char[] ensurePadding(char[] chars) {

        char[] result = chars; //assume argument in case no padding is necessary

        int paddingCount = 0;

        int remainder = chars.length % 4;
        if (remainder == 2 || remainder == 3) {
            paddingCount = 4 - remainder;
        }

        if (paddingCount > 0) {
            result = new char[chars.length + paddingCount];
            System.arraycopy(chars, 0, result, 0, chars.length);
            for (int i = 0; i < paddingCount; i++) {
                result[chars.length + i] = '=';
            }
        }
        return result;
    }

}
