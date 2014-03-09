public final class d
{
  private static String a = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789()?=&/. ;:_-".toLowerCase();
  private static String b = "Q9A8ZWS%XEDC6RFVT5GBY4HNU3:J2MI1K;O0LP^7$#@!<>}{";
  private static String c = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789()?=&/. ;:_-";
  private static String d = "Q9A8ZWS%XEDC6RFVT5GBY4HNU3:J2MI1K;O0LP^7$#@!<>}{".toLowerCase();
  
  public static String a(String paramString)
  {
    char[] arrayOfChar = new char[paramString.length()];
    for (int i = 0; i < paramString.length(); i++)
    {
      int j = paramString.charAt(i);
      int k;
      if ((k = b.indexOf(j)) == -1)
      {
        k = d.indexOf(j);
        arrayOfChar[i] = c.charAt(k);
      }
      else
      {
        arrayOfChar[i] = a.charAt(k);
      }
    }
    return new String(arrayOfChar);
  }
}


/* Location:           C:\Users\Ben\Development\NeulionHack\
 * Qualified Name:     d
 * JD-Core Version:    0.7.0.1
 */