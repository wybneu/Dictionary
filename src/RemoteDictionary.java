 import java.net.*;
import java.io.*;

/**
 * RemoteDictionary A server Used for the check word from dictionary file
 * 
 * @author Yingbo Wang
 * @author Master of Information Technology of Melbourne univerisity
 * @author 349149
 * @version 1.0
 * 
 * @see Result
 */

public class RemoteDictionary extends Thread {
    Socket s; // client socket
    int i; // client number
    String[] dictionary; // the dictionary string array
    String[] meaning; // the meaning string array
    Result result; // the meaning and error code need to return to client

    // a method to search the word in the dictionary
    public void search(String[] dictionary, String[] meaning, String word) {
        this.result.setErrorCode(0);
        this.result.setMeaning(null);
        for (int j = 0; j < (dictionary.length); j++) {
            if (word.equalsIgnoreCase(this.dictionary[j])) {
                this.result.setErrorCode(1);
                if (this.meaning[j] != null) {
                    this.result.setErrorCode(2);
                }
                this.result.setMeaning(this.meaning[j]);
            }
        }

        return;
    }
    public void run() {

        OutputStream s1out;
        String errCodeDef = null;
        try {
            // get the word from the socket
            InputStream s1In = s.getInputStream();
            DataInputStream dis = new DataInputStream(s1In);
            String st = new String(dis.readUTF());
            System.out.println("client " + (this.i + 1)
                    + " is searching word: " + st);
            
            // search for the result
            this.search(this.dictionary, this.meaning, st);
            // send the result to the client via error code
            s1out = s.getOutputStream();
            DataOutputStream dos = new DataOutputStream(s1out);
            switch (this.getResult().getErrorCode()) {
            case 0: {
                errCodeDef = "Sorry no such word!!!";
                break;
            }
            case 1: {
                errCodeDef = "Sorry no meaning for this word!!!!";
                break;
            }
            default: {
                errCodeDef = "word search success!!";
                break;
            }
            }
            if (this.getResult().getErrorCode() == 2) {
                dos.writeUTF(errCodeDef + "\nword: " + st + " is meaning: \n"
                        + this.getResult().toString());
            } else {
                dos.writeUTF("Can't find word: " + st + " because\nerrCode: "
                        + this.getResult().getErrorCode() + "\n" + errCodeDef);
            }
            // When done, just close the connection and exit
            dis.close();
            s1In.close();
            s.close();
            // s.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public RemoteDictionary(Socket s1, int ci) {
        this.setS(s1);
        this.setI(ci);
    }

    public RemoteDictionary(Socket s1, int ci, String[] a, String[] b, int n) {
        this.setS(s1);
        this.setI(ci);
        dictionary = new String[n];
        meaning = new String[n];
        result = new Result();
        for (int j = 0; j < n; j++) {
            this.dictionary[j] = a[j];
            this.meaning[j] = b[j];
        }
        // TODO Auto-generated constructor stub
    }

    public Socket getS() {
        return s;
    }

    public void setS(Socket s) {
        this.s = s;
    }

    public int getI() {
        return i;
    }

    public void setI(int i) {
        this.i = i;
    }

    public Result getResult() {
        return result;
    }

    public void setResult(Result result) {
        this.result = result;
    }

}