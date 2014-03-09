import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintStream;

public final class b
{
  private String b;
  private String c;
  private String d;
  private String e = "";
  private String f = d.a("vgI!CX9%BBV#J@K:!LvCQUgBQBXFR!IP");
  String a;
  
  public b(BufferedReader paramBufferedReader)
  {
    this.jdField_a_of_type_JavaLangString = "";
    Object localObject = "";
    try
    {
      localObject = paramBufferedReader.readLine();
    }
    catch (IOException localIOException2)
    {
      System.out.println("COULDN'T READ REQUEST FROM VLC SHITS BROKE ALSO FUCK NEULION. GET BETTER DEVELOPERS, LIKE HOLY SHIT THIS IS SO EASY");
      System.exit(0);
    }
    String[] arrayOfString = new String[3];
    try
    {
      arrayOfString = ((String)localObject).split(" ");
    }
    catch (NullPointerException localNullPointerException)
    {
      System.out.println("REQUEST FROM VLC WAS NULL SOMETHING IS WRONG LOL");
      System.exit(0);
    }
    this.b = arrayOfString[0];
    this.c = arrayOfString[1];
    this.d = arrayOfString[2];
    if ((this.c.contains(d.a("@DZU"))) && (f.b == null)) {
      try
      {
        f.b = a.a(f.jdField_a_of_type_JavaLangString);
      }
      catch (IOException localIOException3)
      {
        System.out.println("Couldn't Generate key!");
        System.exit(0);
      }
    }
    try
    {
      localObject = paramBufferedReader.readLine();
      while (((String)localObject).length() != 0)
      {
        if (!((String)localObject).startsWith(d.a("aFRRZABXFR>"))) {
          if (((String)localObject).startsWith(d.a("yGZ5{qSZRB")))
          {
            this.e = (this.e + d.a("yGZ5{qSZRB>!") + this.f + "\r\n");
          }
          else
          {
            if ((((String)localObject).startsWith(d.a("xAU"))) || (((String)localObject).contains(d.a("v5QS6Q"))) || (((String)localObject).contains(d.a("5QRSZ").replace("r", "R"))))
            {
              localObject = paramBufferedReader.readLine();
              continue;
            }
            this.e = (this.e + (String)localObject + "\r\n");
          }
        }
        if (((String)localObject).startsWith(d.a("%FGB>").replace('h', 'H')))
        {
          if ((arrayOfString = ((String)localObject).split(" "))[1].indexOf(':') > 0)
          {
            localObject = arrayOfString[1].split(":");
            this.jdField_a_of_type_JavaLangString = localObject[0];
            Integer.parseInt(localObject[1]);
          }
          else
          {
            this.jdField_a_of_type_JavaLangString = arrayOfString[1];
          }
          if (this.jdField_a_of_type_JavaLangString.contains(d.a("RCGD@RZYCXFR@AF6"))) {
            this.jdField_a_of_type_JavaLangString = this.jdField_a_of_type_JavaLangString.replace(d.a("RCGD@RZYCXFR@AF6"), d.a("2:0@O;@JIJ@1J"));
          }
        }
        localObject = paramBufferedReader.readLine();
      }
      if (f.b != null)
      {
        if (!f.jdField_a_of_type_Boolean)
        {
          this.e = (this.e + d.a("aFFDXZ>!RCTVBX87}}QYB%}}7") + f.b + "\r\n");
          return;
        }
        if (f.c != null)
        {
          this.e = (this.e + d.a("aFFDXZ>!") + f.c + " " + f.b + "\r\n");
          return;
        }
        this.e = (this.e + d.a("aFFDXZ>!") + f.b + "\r\n");
        return;
      }
    }
    catch (IOException localIOException1)
    {
      System.out.println("Error reading from socket: " + localIOException1);
      System.exit(0);
    }
  }
  
  public final String toString()
  {
    String str = this.b + " " + this.c + " " + this.d + "\r\n";
    str = str + this.e;
    str = str + "Connection: close\r\n";
    return str += "\r\n";
  }
}


/* Location:           C:\Users\Ben\Development\NeulionHack\
 * Qualified Name:     b
 * JD-Core Version:    0.7.0.1
 */