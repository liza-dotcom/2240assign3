package classes;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class LibraryManagementTest {
	
	 private static Transaction transaction;
	    private Book book;
	    private Member member;

	    @BeforeEach
	    public void setUp() {
	        // Initialize the single Transaction instance before each test
	        transaction = Transaction.getTransaction();
	        try {
				book = new Book(101, "Test Book");
			} catch (Exception e) {
				
				System.out.println("Error"+ e.getMessage());
			}
	        member = new Member(1, "Test Member");
	    }

	// Test case for validating book IDs
    @Test
    public void testBookId() {
        // Test valid cases
        try {
            Book validBook1 = new Book(100, "Valid Book 1");
            assertNotNull(validBook1);
            assertEquals(100, validBook1.getId());
            
            Book validBook2 = new Book(999, "Valid Book 2");
            assertNotNull(validBook2);
            assertEquals(999, validBook2.getId());
        } catch (Exception e) {
            fail("Exception should not be thrown for valid IDs.");
        }

        // Test invalid cases
        try {
            new Book(99, "Invalid Book 1");
            fail("Exception was expected for ID less than 100, but it was not thrown.");
        } catch (Exception e) {
            assertEquals("Invalid book ID. Book ID must be between 100 and 999.", e.getMessage());
        }

        try {
            new Book(1000, "Invalid Book 2");
            fail("Exception was expected for ID greater than 999, but it was not thrown.");
        } catch (Exception e) {
            assertEquals("Invalid book ID. Book ID must be between 100 and 999.", e.getMessage());
        }
    }
    
    // Test case for borrowing and returning books
    @Test
    public void testBorrowReturn() {
        // Assert the book is available
        assertTrue(book.isAvailable(), "The book should initially be available.");

        // Borrow the book
        boolean borrowSuccess = transaction.borrowBook(book, member);
        assertTrue(borrowSuccess, "Borrowing should succeed for an available book.");
        assertFalse(book.isAvailable(), "The book should now be unavailable.");

        // Attempt to borrow the same book again
        boolean borrowFail = transaction.borrowBook(book, member);
        assertFalse(borrowFail, "Borrowing should fail for an unavailable book.");

        // Return the book
        boolean returnSuccess = transaction.returnBook(book, member);
        assertTrue(returnSuccess, "Returning the book should succeed.");
        assertTrue(book.isAvailable(), "The book should now be available again.");

        // Attempt to return the same book again
        boolean returnFail = transaction.returnBook(book, member);
        assertFalse(returnFail, "Returning should fail for a book that is already available.");
    }
}
    