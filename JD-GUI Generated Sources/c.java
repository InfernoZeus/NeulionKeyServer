import java.io.DataInputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.io.Serializable;

public final class c
  implements Serializable
{
  private String jdField_a_of_type_JavaLangString = "";
  private String b = "";
  private boolean jdField_a_of_type_Boolean = true;
  byte[] jdField_a_of_type_ArrayOfByte = new byte[100000000];
  
  public c(DataInputStream paramDataInputStream)
  {
    int i = -1;
    int j = 0;
    Object localObject;
    try
    {
      String str = paramDataInputStream.readLine();
      do
      {
        if (j == 0)
        {
          this.jdField_a_of_type_JavaLangString = str;
          j = 1;
        }
        else
        {
          this.b = (this.b + str + "\r\n");
        }
        if ((str.startsWith("Content-Length")) || (str.startsWith("Content-length")))
        {
          localObject = str.split(" ");
          try
          {
            i = Integer.parseInt(localObject[1]);
          }
          catch (NumberFormatException localNumberFormatException)
          {
            i = -1;
          }
        }
        if ((str.startsWith(d.a("gZB{aFFDXZ"))) && (this.jdField_a_of_type_Boolean))
        {
          f.b = (f.b = str.substring(str.indexOf(" "), str.length()).trim()).substring(0, f.b.indexOf(";")).trim();
          this.jdField_a_of_type_Boolean = false;
          f.jdField_a_of_type_Boolean = true;
        }
        else if ((!this.jdField_a_of_type_Boolean) && (str.startsWith(d.a("gZB{aFFDXZ"))))
        {
          f.c = (f.c = str.substring(str.indexOf(" "), str.length()).trim()).substring(0, f.c.indexOf(";") + 1).trim();
          this.jdField_a_of_type_Boolean = true;
        }
        str = paramDataInputStream.readLine();
        if (str == null) {
          break;
        }
      } while (str.length() != 0);
    }
    catch (IOException localIOException2)
    {
      System.out.println("Error!!: ");
      return;
    }
    try
    {
      int k = 0;
      localObject = new byte[15000000];
      while ((j = paramDataInputStream.read((byte[])localObject)) != -1)
      {
        for (int m = 0; (m < j) && (m + k < 100000000); m++) {
          this.jdField_a_of_type_ArrayOfByte[(k++)] = localObject[m];
        }
        if (k >= i) {}
      }
    }
    catch (IOException localIOException1)
    {
      System.out.println("Error reading response body: " + localIOException1);
      return;
    }
  }
  
  public final String toString()
  {
    String str = this.jdField_a_of_type_JavaLangString + "\r\n";
    str = str + this.b;
    return str += "\r\n";
  }
}


/* Location:           C:\Users\Ben\Development\NeulionHack\
 * Qualified Name:     c
 * JD-Core Version:    0.7.0.1
 */