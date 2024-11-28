import java.util.ArrayList;
import java.util.List;

public class Library {
    private List<Member> members = new ArrayList<Member>();
    private List<Book> books = new ArrayList<Book>();

 // Add a new member to the library
    public boolean addMember(Member member) {
       if(findMemberById(member.getId())!=null)// check if the member with same ID already exists.
       
       {  //Display a message if another member has a matching Id.
    	  System.out.println("Cannot add the member as the provided ID already exists in the system");
    	  return false;
       }
       else 
       {   //add member if no member has a matching id.
    	   members.add(member);
    	  return true;
       }
      
    }
    
    // Add a new book to the library
    public boolean addBook(Book book) {
        if(findBookById(book.getId())!=null)
        {   //Display an appropriate message
        	System.out.println("Cannot add the book as there is already a book with same ID!");
        	return false;
        }
        else
        {   //add book if there is no matching id in the system.
        	books.add(book);
        	return true;
        }
    	
    }

    // Find a member by ID
    public Member findMemberById(int id) {
        for (Member member : members) {
            if (member.getId() == id) {
                return member;
            }
        }
        return null;
    }

    // Find a book by ID
    public Book findBookById(int id) {
        for (Book book : books) {
            if (book.getId() == id) {
                return book;
            }
        }
        return null;
    }

    // Get the list of members
    public List<Member> getMembers() {
        return members;
    }
    
    // Get the list of books
    public List<Book> getBooks() {
        return books;
    }
}
