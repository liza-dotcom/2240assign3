package classes;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Transaction {

	// making the private constructor to convert to singleton
	private Transaction () {}
	// creating an instance variable to access all the methods of the singleton class
	private static Transaction instance;
    // Perform the borrowing of a book
    public  boolean borrowBook(Book book, Member member) {
    	String transactionDetails="";
        if (book.isAvailable()) {
            book.borrowBook();
            member.borrowBook(book); 
            transactionDetails = getCurrentDateTime() + " - Borrowing: " + member.getName() + " borrowed " + book.getTitle();
            System.out.println(transactionDetails);
            Transaction.getTransaction().saveTransaction(  transactionDetails);
            return true;
        } else {
            System.out.println("The book is not available.");
            Transaction.getTransaction().saveTransaction(  transactionDetails);
            return false;
        }
        
    }

    // Perform the returning of a book
    public  void returnBook(Book book, Member member) {
    	String transactionDetails="";
        if (member.getBorrowedBooks().contains(book)) {
            member.returnBook(book);
            book.returnBook();
             transactionDetails = getCurrentDateTime() + " - Returning: " + member.getName() + " returned " + book.getTitle();
            System.out.println(transactionDetails);
        } else {
            System.out.println("This book was not borrowed by the member.");
        }
        Transaction.getTransaction().saveTransaction(  transactionDetails);
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
    public void saveTransaction(String transactionDetails)
    {
    	try
    	{
    	FileWriter write = new FileWriter("transactions.txt",true);
    	BufferedWriter bufferedWriter=new BufferedWriter(write);
    	bufferedWriter.write(transactionDetails);
    	bufferedWriter.newLine();
    	bufferedWriter.close();
    	}
    	catch (IOException e)
    	{
    		System.out.println("There was an error in writing the file:" + e.getMessage());
    	}
    }
  //Method to read history/details from .txt file and then displaying them.
    public void displayTransactionHistory()
    {
    	try
    	{
    		//Accessing the file to be read
        	FileReader read = new FileReader("transactions.txt");
        	BufferedReader bufferedReader= new BufferedReader (read);
        	//Read and show/display each line of file
        	System.out.println("Here is the history of the transactions:");
        	String history;
        	while ((history=bufferedReader.readLine())!= null)// display history till the .txt file is not empty/null.
        	{
        		System.out.println(history);
        	}
    	}
    	catch (FileNotFoundException e)// handle the exception if file is not found
    	{
    		System.out.println("The transaction history file was not found!");
    	}
    	catch (IOException e)// handle any other exception encountered while reading the file.
    	{
    		System.out.println("There was an error in reading the files:" + e.getMessage());
    	}
    	
    }
    
}