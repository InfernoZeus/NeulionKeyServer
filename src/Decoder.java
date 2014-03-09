public final class Decoder
{
    private static String plainLower;
    private static String cipher;
    private static String plain;
    private static String cipherLower;
    
    static {
        plain = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789()?=&/. ;:_-";
        plainLower = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789()?=&/. ;:_-".toLowerCase();
        cipher = "Q9A8ZWS%XEDC6RFVT5GBY4HNU3:J2MI1K;O0LP^7$#@!<>}{";
        cipherLower = "Q9A8ZWS%XEDC6RFVT5GBY4HNU3:J2MI1K;O0LP^7$#@!<>}{".toLowerCase();
    }
    
    public static String decode(final String stringToEncode) {
        final char[] array = new char[stringToEncode.length()];
        for (int i = 0; i < stringToEncode.length(); ++i) {
            final char char1 = stringToEncode.charAt(i);
            int n;
            if ((n = Decoder.cipher.indexOf(char1)) == -1) {
                n = Decoder.cipherLower.indexOf(char1);
                array[i] = Decoder.plain.charAt(n);
            }
            else {
                array[i] = Decoder.plainLower.charAt(n);
            }
        }
        String retString = new String(array);
        System.out.println("\"" + stringToEncode + "\" decodes to \"" + retString + "\"");
        return retString;
    }
}
