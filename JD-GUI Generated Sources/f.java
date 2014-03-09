import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;

public class f
{
  private static int jdField_a_of_type_Int;
  private static ServerSocket jdField_a_of_type_JavaNetServerSocket;
  public static String a;
  public static String b;
  public static String c;
  public static boolean a;
  
  static
  {
    jdField_a_of_type_JavaLangString = null;
    b = null;
    c = null;
    jdField_a_of_type_Boolean = false;
  }
  
  public static void main(String[] paramArrayOfString)
  {
    if (paramArrayOfString.length <= 0)
    {
      System.out.println("Please enter URL");
      return;
    }
    jdField_a_of_type_JavaLangString = paramArrayOfString[0];
    int i = 80;
    jdField_a_of_type_Int = 80;
    try
    {
      jdField_a_of_type_JavaNetServerSocket = new ServerSocket(jdField_a_of_type_Int, 0, InetAddress.getByName("localhost"));
    }
    catch (IOException localIOException2)
    {
      System.out.println("Error creating socket: " + localIOException2);
      System.exit(-1);
    }
    paramArrayOfString = null;
    try
    {
      for (;;)
      {
        if ((!jdField_a_of_type_JavaLangString.contains(d.a("R9Q"))) || (!jdField_a_of_type_JavaLangString.contains(d.a("RWC")))) {
          paramArrayOfString = jdField_a_of_type_JavaNetServerSocket.accept();
        }
        if (paramArrayOfString.isConnected())
        {
          String[] arrayOfString = paramArrayOfString;
          Object localObject2;
          try
          {
            BufferedReader localBufferedReader = new BufferedReader(new InputStreamReader(arrayOfString.getInputStream()));
            localObject2 = new b(localBufferedReader);
          }
          catch (IOException localIOException3)
          {
            System.out.println("Error reading request from client: " + localIOException3);
          }
          continue;
          Object localObject1;
          try
          {
            localObject1 = new Socket((localObject1 = localObject2).jdField_a_of_type_JavaLangString, 80);
            DataOutputStream localDataOutputStream;
            (localDataOutputStream = new DataOutputStream(((Socket)localObject1).getOutputStream())).writeBytes(((b)localObject2).toString());
          }
          catch (UnknownHostException localUnknownHostException)
          {
            System.out.println("Unknown host: " + (localObject1 = localObject2).jdField_a_of_type_JavaLangString);
            System.out.println(localUnknownHostException);
            continue;
          }
          catch (IOException localIOException4)
          {
            System.out.println("Error writing request to server: " + localIOException4);
          }
          continue;
          try
          {
            Object localObject3 = new DataInputStream(((Socket)localObject1).getInputStream());
            ((Socket)localObject1).getInputStream();
            localObject2 = new c((DataInputStream)localObject3);
            (localObject3 = new DataOutputStream(arrayOfString.getOutputStream())).writeBytes(((c)localObject2).toString());
            ((DataOutputStream)localObject3).write(((c)localObject2).a);
            arrayOfString.close();
            ((Socket)localObject1).close();
          }
          catch (IOException localIOException5) {}
        }
        else
        {
          System.err.println("Socket is not connected");
        }
      }
    }
    catch (IOException localIOException1)
    {
      System.out.println("Error reading request from client: " + localIOException1);
    }
  }
}


/* Location:           C:\Users\Ben\Development\NeulionHack\
 * Qualified Name:     f
 * JD-Core Version:    0.7.0.1
 */