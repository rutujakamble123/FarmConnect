

package util;

public class CustomException {//exceptions
    public static class OutOfStockException extends Exception {
        public OutOfStockException(String msg) { super(msg); }
    }

    public static class InvalidInputException extends Exception {
        public InvalidInputException(String msg) { super(msg); }
    }
}
