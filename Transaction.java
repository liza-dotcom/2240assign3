package classes;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Transaction {

	// making the private constructor to convert to singleton
	private Transaction () {}
	// creating an instance variable to access all the methods of the singleton class
	private static Transaction instance;
    // Perform the borrowing of a book
    public  boolean borrowBook(Book book, Member member) {
        if (book.isAvailable()) {
            book.borrowBook();
            member.borrowBook(book); 
            String transactionDetails = getCurrentDateTime() + " - Borrowing: " + member.getName() + " borrowed " + book.getTitle();
            System.out.println(transactionDetails);
            return true;
        } else {
            System.out.println("The book is not available.");
            return false;
        }
    }

    // Perform the returning of a book
    public  void returnBook(Book book, Member member) {
        if (member.getBorrowedBooks().contains(book)) {
            member.returnBook(book);
            book.returnBook();
            String transactionDetails = getCurrentDateTime() + " - Returning: " + member.getName() + " returned " + book.getTitle();
            System.out.println(transactionDetails);
        } else {
            System.out.println("This book was not borrowed by the member.");
        }
    }

    // Get the current date and time in a readable format
    private  String getCurrentDateTime() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sdf.format(new Date());
    }
    public static Transaction getTransaction()
    {
    	if (instance==null)
    	{
    		instance= new Transaction(); // Create a new instance if there was no single/only instance initially.
    	}
    	return instance;
    }
}