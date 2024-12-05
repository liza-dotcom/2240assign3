package src.classes;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class LibraryGUI extends Application {
    private Library library = new Library();
    private Transaction transaction = Transaction.getTransaction();

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Library Management System");

        // Main layout
        VBox mainLayout = new VBox(10);
        mainLayout.setPadding(new Insets(15));

        // Buttons for operations
        Button addMemberButton = new Button("Add Member");
        Button addBookButton = new Button("Add Book");
        Button borrowBookButton = new Button("Borrow Book");
        Button returnBookButton = new Button("Return Book");
        Button viewBorrowedBooksButton = new Button("View Borrowed Books");
        Button viewTransactionHistoryButton = new Button("View Transaction History");

        mainLayout.getChildren().addAll(
                addMemberButton, addBookButton, borrowBookButton, returnBookButton,
                viewBorrowedBooksButton, viewTransactionHistoryButton
        );

        // Set actions for each button
        addMemberButton.setOnAction(e -> openAddMemberWindow());
        addBookButton.setOnAction(e -> openAddBookWindow());
        borrowBookButton.setOnAction(e -> openBorrowBookWindow());
        returnBookButton.setOnAction(e -> openReturnBookWindow());
        viewBorrowedBooksButton.setOnAction(e -> openViewBorrowedBooksWindow());
        viewTransactionHistoryButton.setOnAction(e -> openTransactionHistoryWindow());

        Scene scene = new Scene(mainLayout, 400, 300);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void openAddMemberWindow() {
        Stage addMemberStage = new Stage();
        addMemberStage.setTitle("Add Member");

        VBox layout = new VBox(10);
        layout.setPadding(new Insets(10));

        TextField idField = new TextField();
        idField.setPromptText("Member ID");
        TextField nameField = new TextField();
        nameField.setPromptText("Member Name");
        Button addButton = new Button("Add");

        addButton.setOnAction(e -> {
            try {
                int id = Integer.parseInt(idField.getText());
                String name = nameField.getText();
                Member newMember = new Member(id, name);
                library.addMember(newMember);
                showAlert("Success", "Member added successfully.");
                addMemberStage.close();
            } catch (Exception ex) {
                showAlert("Error", "Invalid input: " + ex.getMessage());
            }
        });

        layout.getChildren().addAll(new Label("Enter Member Details"), idField, nameField, addButton);
        addMemberStage.setScene(new Scene(layout, 300, 200));
        addMemberStage.show();
    }

    private void openAddBookWindow() {
        Stage addBookStage = new Stage();
        addBookStage.setTitle("Add Book");

        VBox layout = new VBox(10);
        layout.setPadding(new Insets(10));

        TextField idField = new TextField();
        idField.setPromptText("Book ID");
        TextField titleField = new TextField();
        titleField.setPromptText("Book Title");
        Button addButton = new Button("Add");

        addButton.setOnAction(e -> {
            try {
                int id = Integer.parseInt(idField.getText());
                String title = titleField.getText();
                Book newBook = new Book(id, title);
                library.addBook(newBook);
                showAlert("Success", "Book added successfully.");
                addBookStage.close();
            } catch (Exception ex) {
                showAlert("Error", "Invalid input: " + ex.getMessage());
            }
        });

        layout.getChildren().addAll(new Label("Enter Book Details"), idField, titleField, addButton);
        addBookStage.setScene(new Scene(layout, 300, 200));
        addBookStage.show();
    }

    private void openBorrowBookWindow() {
        Stage borrowStage = new Stage();
        borrowStage.setTitle("Borrow Book");

        VBox layout = new VBox(10);
        layout.setPadding(new Insets(10));

        ComboBox<Member> memberComboBox = new ComboBox<>();
        memberComboBox.getItems().addAll(library.getMembers());
        memberComboBox.setPromptText("Select Member");

        ComboBox<Book> bookComboBox = new ComboBox<>();
        bookComboBox.getItems().addAll(library.getBooks().stream().filter(Book::isAvailable).toList());
        bookComboBox.setPromptText("Select Book");

        Button borrowButton = new Button("Borrow");

        borrowButton.setOnAction(e -> {
            Member member = memberComboBox.getValue();
            Book book = bookComboBox.getValue();
            if (member != null && book != null) {
                transaction.borrowBook(book, member);
                showAlert("Success", "Book borrowed successfully.");
                borrowStage.close();
            } else {
                showAlert("Error", "Please select a valid member and book.");
            }
        });

        layout.getChildren().addAll(new Label("Borrow Book"), memberComboBox, bookComboBox, borrowButton);
        borrowStage.setScene(new Scene(layout, 300, 200));
        borrowStage.show();
    }

    private void openReturnBookWindow() {
        Stage returnStage = new Stage();
        returnStage.setTitle("Return Book");

        VBox layout = new VBox(10);
        layout.setPadding(new Insets(10));

        ComboBox<Member> memberComboBox = new ComboBox<>();
        memberComboBox.getItems().addAll(library.getMembers());
        memberComboBox.setPromptText("Select Member");

        ComboBox<Book> bookComboBox = new ComboBox<>();
        bookComboBox.setPromptText("Select Book");

        memberComboBox.setOnAction(e -> {
            Member selectedMember = memberComboBox.getValue();
            if (selectedMember != null) {
                bookComboBox.getItems().setAll(selectedMember.getBorrowedBooks());
            }
        });

        Button returnButton = new Button("Return");

        returnButton.setOnAction(e -> {
            Member member = memberComboBox.getValue();
            Book book = bookComboBox.getValue();
            if (member != null && book != null) {
                transaction.returnBook(book, member);
                showAlert("Success", "Book returned successfully.");
                returnStage.close();
            } else {
                showAlert("Error", "Please select a valid member and book.");
            }
        });

        layout.getChildren().addAll(new Label("Return Book"), memberComboBox, bookComboBox, returnButton);
        returnStage.setScene(new Scene(layout, 300, 200));
        returnStage.show();
    }

    private void openViewBorrowedBooksWindow() {
        Stage viewStage = new Stage();
        viewStage.setTitle("Borrowed Books");

        VBox layout = new VBox(10);
        layout.setPadding(new Insets(10));

        TextArea textArea = new TextArea();
        textArea.setEditable(false);

        library.getMembers().forEach(member -> {
            textArea.appendText(member.getName() + ":\n");
            member.getBorrowedBooks().forEach(book -> textArea.appendText(" - " + book.getTitle() + "\n"));
        });

        layout.getChildren().addAll(new Label("Borrowed Books"), textArea);
        viewStage.setScene(new Scene(layout, 400, 300));
        viewStage.show();
    }

    private void openTransactionHistoryWindow() {
        Stage historyStage = new Stage();
        historyStage.setTitle("Transaction History");

        VBox layout = new VBox(10);
        layout.setPadding(new Insets(10));

        TextArea textArea = new TextArea();
        textArea.setEditable(false);

        textArea.appendText(transaction.getTransactionHistory());

        layout.getChildren().addAll(new Label("Transaction History"), textArea);
        historyStage.setScene(new Scene(layout, 400, 300));
        historyStage.show();
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
