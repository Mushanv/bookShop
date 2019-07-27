

package exception;



public class BooksNotFoundException extends Exception {
    public BooksNotFoundException() {
    }

    public BooksNotFoundException(String msg) {
        super(msg);
    }
}
