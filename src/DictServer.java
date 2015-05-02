import java.net.*;
import java.io.*;
import java.util.Scanner;

/**
 * DictServer Use for the check word from dictionary file provide a socket
 * server and initialization of the command line and the dictionary file
 * 
 * @author Yingbo Wang
 * @author Master of Information Technology of Melbourne univerisity
 * @author 349149
 * @version 1.0
 * 
 * @see RemoteDictionary
 */

public class DictServer {

    public static void main(String args[]) {

        ServerSocket s = null; // ServerSocket
        int socket_no = 1234; // default port
        String dictFile = "C:\\dict.txt\\"; // default file path
        Socket s1 = null; // client Socket
        int ci = 0; // count for client thread
        File file = new File(dictFile); // default path
        int n = 20; // max piece of dictionary
        String dictionary[] = new String[n]; // keep the dictionary word
        String meaning[] = new String[n]; // keep the description

        // parameter for server

        if (args.length == 0) {
            System.out
                    .println("Auto Use default port 1234\nAuto Use default Dict File \'C:\\dict.txt\\'");
        } else if (args.length == 1) {
            System.out.println("Auto Use default Dict File \'C:\\dict.txt\\'");
            socket_no = Integer.valueOf(args[0]).intValue();
        } else if (args.length != 2) {
            System.out
                    .println("Error!!! Please Usage: java DictServer <Port Number> <Dict File Path>");
            System.exit(1);
        } else {
            socket_no = Integer.valueOf(args[0]).intValue();
            dictFile = args[1];
            file = new File(dictFile);

        }

        System.out.println("Server Starting....\nLoding file \'" + dictFile
                + "\'");

        // loading file

        try {
            Scanner scanner = new Scanner(file); // find in lines
            scanner.useDelimiter(System.getProperty("line.separator"));
            int i = 0;
            while ((scanner.hasNext()) && (i < 20)) {
                Scanner linescanner = new Scanner(scanner.next()); // find in
                                                                   // line
                linescanner.useDelimiter(",");
                dictionary[i] = linescanner.next();
                if (linescanner.hasNext() != true) {
                    meaning[i] = null;
                } else {
                    meaning[i] = linescanner.next();
                }
                System.out.println("word " + dictionary[i] + " £¬mean£º"
                        + meaning[i]);
                i++;
            }
            System.out
                    .println("dictionary has been opened sucessfully. there are "
                            + i + " word inside");
            scanner.close();
        } catch (FileNotFoundException e) {
            System.out.println(e + "\nno Such file!!!!!!!!");
            System.exit(1);
        }

        // start socket
        try {

            s = new ServerSocket(socket_no);

            // Socket s1= new Socket();

            // loop
            while (true) {

                // Get a communication stream associated with the socket
                // OutputStream s1out = s1.getOutputStream();
                // DataOutputStream dos = new DataOutputStream (s1out);
                // // Send a string!
                // dos.writeUTF("Hi there");

                s1 = s.accept(); // Wait and accept a connection
                s1.setSoTimeout(14000); // time to stop a server thread
                RemoteDictionary server = new RemoteDictionary(s1, ci,
                        dictionary, meaning, n); // new server
                new Thread(server).start(); // server works in a new thread
                ci++; // client count++

                // Close the connection, but not the server socket

                // dos.close();
                // s1out.close();

                /**
                 * BufferedReader reader = new BufferedReader( new
                 * InputStreamReader(s1.getInputStream())); // read the message
                 * from client and parse the execution word = reader.readLine();
                 * result = this.search(word); // write the result back to the
                 * client BufferedWriter writer = new BufferedWriter( new
                 * OutputStreamWriter(socket.getOutputStream()));
                 * writer.write(¡°¡±+result); writer.newLine(); writer.flush(); //
                 * close the stream reader.close(); writer.close()
                 */
            }
        } catch (SocketException e) {
            System.out
                    .println("Socket error, this may cauased by network communication (broken connection, address not reachable, bad data..)\n"
                            + e.getMessage());
        } catch (IOException ioe) {
            System.out
                    .println("IOException ,this maybe caused by wrong input data\n "
                            + ioe);
            ioe.printStackTrace();
        } catch (NumberFormatException ne) {
            System.out.println("A Correct Number is needed"
                    + "\nNumberFormatException" + ne);
            ne.printStackTrace();
        } finally {
            try {
                if (s != null)
                    s.close();
            } catch (IOException ioe) {
                System.out.println("IOException: " + ioe);
                ioe.printStackTrace();
            }
        }
    }

}