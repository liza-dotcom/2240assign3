package src.classes;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.Before;
import org.junit.jupiter.api.Test;

class LibraryManagementTest {
	
	

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
    
  
}
