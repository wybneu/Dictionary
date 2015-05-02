
// SimpleClient.java: a simple client program
import java.net.*;
import java.io.*;

/**
 * Dictclient provide a socket client for connect to the server
 * 
 * @author Yingbo Wang
 * @author Master of Information Technology of Melbourne univerisity
 * @author 349149
 * @version 1.0
 * 
 */

public class DictClient {
    public static void main(String args[]) {
        // open socket
        OutputStream s1out; // outputStream which for transfer message
        InetAddress aHost = null; // IP
        Integer socket_no = 0; // port
        String word = null; // word need to search
        try {

            switch (args.length) { // parameters
            case 1: {
                aHost = InetAddress.getByName("192.168.0.1");
                System.out.println("Auto Use default address");
                socket_no = 1234;
                System.out.println("Auto Use default prot 1234");
                word = args[0];
                break;
            }
            case 2: {
                aHost = InetAddress.getByName("192.168.0.1");
                System.out.println("Auto Use default address");
                socket_no = Integer.valueOf(args[0]).intValue();
                word = args[1];
                break;
            }
            case 3: {
                aHost = InetAddress.getByName(args[0]);
                socket_no = Integer.valueOf(args[1]).intValue();
                word = args[2];
                break;
            }
            default: {
                System.out
                        .println("Error!!! Please Usage: java DictServer [IP address] [Port Number] <word you want>");
                System.exit(1);
                break;
            }
            }

            Socket s1 = new Socket(aHost, socket_no);
            // Get an input file handle from the socket and read the input

            s1out = s1.getOutputStream();
            DataOutputStream dos = new DataOutputStream(s1out);
            // Send a string!
            dos.writeUTF(word);

            InputStream s1In = s1.getInputStream();
            DataInputStream dis = new DataInputStream(s1In);
            String st = new String(dis.readUTF());
            System.out.println(st);
            // When done, just close the connection and exit
            dis.close();
            s1In.close();
            s1.close();
        } catch (UnknownHostException uhe) {
            System.out.println("Unknown host: ");
            uhe.printStackTrace();
        } catch (IOException ioe) {
            System.out.println("IOException: this may cause by disconnect\n"
                    + ioe);
            ioe.printStackTrace();
        }

    }
}
