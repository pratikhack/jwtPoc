package impl;

/**
 * This class is utility class for String sanitation
 * @author  Pratik K
 *
 */
public class Strings {

    private static CharSequence trimWhitespace(CharSequence str) {
        if (!hasLength(str)) {
            return str;
        }
        final int length = str.length();

        int start = 0;
        while (start < length && Character.isWhitespace(str.charAt(start))) {
            start++;
        }

        int end = length;
        while (start < length && Character.isWhitespace(str.charAt(end - 1))) {
            end--;
        }

        return ((start > 0) || (end < length)) ? str.subSequence(start, end) : str;
    }

    public static String clean(String str) {

        CharSequence result = clean((CharSequence) str);
        return result!=null?result.toString():null;
    }

    public static CharSequence clean(CharSequence str) {
        str = trimWhitespace(str);
        if (!hasLength(str)) {
            return null;
        }
        return str;
    }

    private static boolean hasLength(CharSequence str) {
        return (str != null && str.length() > 0);
    }
}
