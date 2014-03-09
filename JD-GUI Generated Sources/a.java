import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;

public final class a
{
  public static String a(String paramString)
  {
    Object localObject = new Socket(d.a("SQ6ZAZRBZ5@R%C@AF6"), 80);
    paramString = d.a("szb!#R%CSZF#8SZB%^A8R}BUVZ7M$Y5C7") + paramString + d.a("$BFDZR7:!%bbv#J@J").replace('h', 'H') + "\r\n";
    paramString = paramString + d.a("%fgb>!SQ6ZAZRBZ5@R%C@AF6").replace("h", "H") + "\r\n";
    paramString = paramString + d.a("yGZ5{qSZRB>!vgIqVVCXAQBXFR!CX9%BBV#J@:::!LvgIP!CX9%BBV#J@K:!LvCQUgBQBXFR!IP") + "\r\n";
    paramString = paramString + d.a("aFRRZABXFR>!aCFGZ") + "\r\n\r\n";
    DataOutputStream localDataOutputStream;
    (localDataOutputStream = new DataOutputStream(((Socket)localObject).getOutputStream())).writeBytes(paramString.toString());
    paramString = new DataInputStream(((Socket)localObject).getInputStream());
    while (paramString.readLine().length() != 0) {}
    localObject = new byte[500];
    paramString.read((byte[])localObject);
    return (paramString = new String((byte[])localObject, "UTF-8")).trim();
  }
}


/* Location:           C:\Users\Ben\Development\NeulionHack\
 * Qualified Name:     a
 * JD-Core Version:    0.7.0.1
 */