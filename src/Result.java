/**
 * Result this class is used to keep the error code for the result and the
 * meaning of the word which can be found in dictionary
 * 
 * @author Yingbo Wang
 * @author Master of Information Technology of Melbourne univerisity
 * @author 349149
 * 
 * @version 1.0
 * 
 */

public class Result {
    String meaning; // the meaning need to transfer to the client
    int errorCode = 0; // 0 for no that word
                       // 1 for null meaning
                       // 2 for have meaning

    public Result() {
        meaning = "";
        errorCode = 0;
    }

    public Result(String a) {
        meaning = a;
        errorCode = 2;
    }

    public Result(String a, int b) {
        meaning = a;
        errorCode = b;
    }

    public String getMeaning() {
        return meaning;
    }

    public void setMeaning(String meaning) {
        this.meaning = meaning;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    public void setEmpty() {
        this.errorCode = 0;
        this.meaning = "";
    }

    public String toString() {
        return meaning;

    }

}
