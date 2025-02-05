import org.nocrala.tools.texttablefmt.BorderStyle;
import org.nocrala.tools.texttablefmt.CellStyle;
import org.nocrala.tools.texttablefmt.ShownBorders;
import org.nocrala.tools.texttablefmt.Table;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;
import java.util.regex.Pattern;

public class LibraryManagementSystem {
    private static Book[] books = new Book[100]; // Array books
    private static int bookCount = 0; // Counter books
    private static Scanner scanner = new Scanner(System.in);
    static String libraryName;
    static String libraryAddress;
    static boolean IsInvalidInput = false;
    static int rowsPerPage = 100;

    public static final String ANSI_RED  = "\u001B[31m";
    public static final String ANSI_GRENN = "\u001B[32m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_BLUE = "\u001B[34m";
    public static final String ANSI_RESET = "\u001B[0m";


    public static void main(String[] args) {
        setupLibrary();
        int choice;
        while (true) {
            displayMenu();
            while (true) {
                String input;
                input = scanner.nextLine();
                if (input.matches("\\d+")) {
                    choice = Integer.parseInt(input);
                    break;
                }else{
                    System.out.println(ANSI_RED + "Invalid input. Please enter a valid number." + ANSI_RESET);
                    displayMenu();
                }
            }
            switch (choice) {
                case 1:
                    addBook();
                    break;
                case 2:
                    showAllBooks();
                    break;
                case 3:
                    showAvailableBooks();
                    break;
                case 4:
                    borrowBook();
                    break;
                case 5:
                    returnBook();
                    break;
                case 6:
                    System.out.print("Enter number of rows per page to show: ");
                    rowsPerPage = scanner.nextInt();
                    scanner.nextLine();
                    break;
                case 7:
                    deleteBook();
                    break;
                case 8:
                    System.out.println("(Good Bye! 👋🙏🙏🙏");
                    return;

                default:
                    System.out.println("Invalid choice. Please try again.");
                    break;
            }
        }
    }


    private static void setupLibrary() {
        System.out.println("=================== SET UP LIBRARY ===================");
        while (true) {
            System.out.print("=> Enter Library's Name: ");
            libraryName = scanner.nextLine().toUpperCase();
            if (Pattern.matches("[A-Z ]{2,30}", libraryName)) {
                break;
            } else {
                System.out.println(ANSI_RED + "Invalid name. Please enter only letters and spaces (2 to 30 characters)." + ANSI_RESET);
            }
        }

        while (true) {
            System.out.print("=> Enter Library's Address: ");
            libraryAddress = scanner.nextLine();
            if (Pattern.matches("[A-Za-z0-9, ]{5,50}", libraryAddress)) {
                break;
            } else {
                System.out.println(ANSI_RED + "Invalid address. Please enter only letters, numbers, commas, and spaces (5 to 50 characters)." + ANSI_RESET);
            }
        }


        String currentTime = new SimpleDateFormat("E MMM dd HH:mm:ss z yyyy").format(new Date());
        System.out.println(ANSI_GRENN +
                "\n\"" + libraryName.toUpperCase() + "\" Library is already created in \"" + libraryAddress.toUpperCase() +
                "\" address successfully on " + currentTime + "\n" + ANSI_RESET);

        addSampleBooks();
    }

    private static void displayMenu() {
        System.out.println("============== " + ANSI_BLUE + libraryName.toUpperCase() + " LIBRARY " +  libraryAddress.toUpperCase()+ ANSI_RESET +" ================");
        System.out.println("1- Add Book");
        System.out.println("2- Show All Books");
        System.out.println("3- Show Available Books");
        System.out.println("4- Borrow Book");
        System.out.println("5- Return Book");
        System.out.println("6- Set Row show record");
        System.out.println("7- Delete Book");
        System.out.println("8- Exit");
        System.out.println("-----------------------------------------");
        System.out.print("=> Choose option (1-8): ");
    }

    private static void addSampleBooks() {
        String[] titles = {"Titanic", "Casablanca", "Citizen Kane", "Gone with the Wind", "The Godfather"};
        String[] authorNames = {"James Cameron", "Michael Curtiz", "Orson Welles", "Victor Fleming", "Francis Ford Coppola"};
        int[] birthYears = {1954, 1886, 1915, 1889, 1939};
        int[] deathYears = {2022, 1962, 1985, 1949, 2018};
        int[] publishedYears = {1997, 1942, 1941, 1939, 1972};

        for (int i = 0; i < titles.length; i++) {
            Author author = new Author(authorNames[i], birthYears[i], deathYears[i]);
            Book book = new Book(titles[i], author, publishedYears[i]);
            books[bookCount++] = book;
        }
    }

    private static void addBook() {
        String title;
        String authorName;
        int bookId = bookCount + 1;
        System.out.println("Book Id: " + bookId);
        while (true){
            System.out.print("Enter Book Title: ");
            title= scanner.nextLine();
            if(title.matches("^[^0-9].*")){
                break;
            }else {
                System.out.println(ANSI_RED + """
                         _______________________________________
                        | You can not input start with a number |
                        |_______________________________________|
                        """ + ANSI_RESET);
            }
        }

        while (true){
            System.out.print("Enter Author Name: ");
            authorName = scanner.nextLine();
            if(authorName.matches("^[^0-9].*")){
                break;
            }else {
                System.out.println(ANSI_RED + """
                         _______________________________________
                        | You can not input start with a number |
                        |_______________________________________|
                        """ + ANSI_RESET);
            }
        }
        String activeYears;
        int deathYear = 0;
        int birthYear = 0;
        while (true){
            System.out.print("Enter Author Active Years (birth-death, e.g., 1990-2005 or 1990-): ");
            activeYears = scanner.nextLine().trim();
            if(activeYears.matches("\\d{4}-\\d{0,4}")){
                String[] years = activeYears.split("-");
                birthYear = Integer.parseInt(years[0]);
                deathYear = years[1].isEmpty() ? -1 : Integer.parseInt(years[1]);
                if(birthYear<deathYear){
                    break;
                }else System.out.println(ANSI_RED + """
                         ________________________________________________________________________
                        |   The birth year must be smaller than death year!. Please try again!   |
                        |________________________________________________________________________|
                        """ + ANSI_RESET);
            }
            else System.out.println(ANSI_RED + """
                         _______________________________________
                        |    Wrong format!. Ex: 1990-2020 🙏.   |
                        |_______________________________________|
                        """ + ANSI_RESET);
        }


        int publishedYear = 0;
        while(true){
            System.out.print("Enter Published Year: ");
            publishedYear = scanner.nextInt();
            if(publishedYear>birthYear){
                break;
            }
        }
        Author author = new Author(authorName, birthYear, deathYear);
        Book book = new Book(title, author, publishedYear);
        books[bookCount++] = book;
        System.out.println(ANSI_GRENN + " | ------------------------- |");
        System.out.println(" |  Book added successfully! | ");
        System.out.println(" | ------------------------- |" + ANSI_RESET);
    }

    private static void showAllBooks() {
        int currentPage = 0;
        int totalPages = (int) Math.ceil((double) bookCount / rowsPerPage);

        while (true) {
            // Display the current page of books
            System.out.println("\n============== ALL BOOKS INFO (Page " + (currentPage + 1) + " of " + totalPages + ") ==============");
            CellStyle cellStyle = new CellStyle(CellStyle.HorizontalAlign.center);
            Table t = new Table(5, BorderStyle.UNICODE_BOX_DOUBLE_BORDER, ShownBorders.ALL);

            t.addCell(ANSI_BLUE + "Id", cellStyle);
            t.addCell(ANSI_BLUE + "Title", cellStyle);
            t.addCell(ANSI_BLUE + "Author Birth Death", cellStyle);
            t.addCell(ANSI_BLUE + "Published Year", cellStyle);
            t.addCell(ANSI_BLUE + "Status", cellStyle);

            int startIndex = currentPage * rowsPerPage;
            int endIndex = Math.min(startIndex + rowsPerPage, bookCount);

            for (int i = startIndex; i < endIndex; i++) {
                String bookId = books[i].getStatus().equals("REMOVED") ? ANSI_RED + "REMOVED" + ANSI_RESET : String.valueOf(books[i].getId());
                t.addCell(bookId, cellStyle);

                String bookTitle = books[i].getTitle().equals("REMOVED") ? ANSI_RED + books[i].getTitle() + ANSI_RESET : books[i].getTitle();
                t.addCell(bookTitle, cellStyle);

                String authorInfo = books[i].getAuthor().getName().equals("REMOVED") ? ANSI_RED + "REMOVED (REMOVED - REMOVED)" + ANSI_RESET : books[i].getAuthor().getName() + " (" +
                        (books[i].getAuthor().getBirthYear() == 0 ? "REMOVED" : books[i].getAuthor().getBirthYear()) +
                        " - " +
                        (books[i].getAuthor().getDeathYear() == 0 ? "Present" : books[i].getAuthor().getDeathYear()) + ")";
                t.addCell(authorInfo, cellStyle);

                String publicYear = books[i].getStatus().equals("REMOVED") ? ANSI_RED + "REMOVED" + ANSI_RESET : String.valueOf(books[i].getPublishedYear());
                t.addCell(publicYear, cellStyle);

                String status = books[i].getStatus();
                String statusBook = status.equals("Available") ? ANSI_GRENN + books[i].getStatus() + ANSI_RESET : (status.equals("REMOVED") ? ANSI_RED + "REMOVED" + ANSI_RESET : ANSI_YELLOW + "Unavailable" + ANSI_RESET);
                t.addCell(statusBook, cellStyle);
            }

            System.out.println(t.render());

            // Pagination menu
            System.out.println("\nChoose an option:");
            System.out.println("1. First Page\t\t2. Next Page\t\t3. Previous Page\t\t4. Last Page\t\t5. Exit");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    currentPage = 0;
                    break;
                case 2:
                    if (currentPage < totalPages - 1) {
                        currentPage++;
                    } else {
                        System.out.println("You're already on the last page.");
                    }
                    break;
                case 3:
                    if (currentPage > 0) {
                        currentPage--;
                    } else {
                        System.out.println("You're already on the first page.");
                    }
                    break;
                case 4:
                    currentPage = totalPages - 1;
                    break;
                case 5:
                    System.out.println("Exiting pagination...");
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
                    break;
            }
        }
    }


    private static void showAvailableBooks() {
        CellStyle cellStyle = new CellStyle(CellStyle.HorizontalAlign.center);
        Table t = new Table(5, BorderStyle.UNICODE_BOX_DOUBLE_BORDER, ShownBorders.ALL);

        t.addCell(ANSI_BLUE + "Id", cellStyle);
        t.addCell(ANSI_BLUE + "Title", cellStyle);
        t.addCell(ANSI_BLUE + "Author Birth Death ",cellStyle);
        t.addCell( ANSI_BLUE + "Published Year",cellStyle);
        t.addCell(ANSI_BLUE + "Status", cellStyle);


        System.out.println(ANSI_BLUE +  "=============== AVAILABLE BOOKS INFO ===============" + ANSI_RESET);
        for (int i = 0; i < bookCount; i++) {
            if (books[i].getStatus().equals("Available")) {
                t.addCell(String.valueOf(books[i].getId()), cellStyle);
                t.addCell(books[i].getTitle());
                t.addCell(String.valueOf(books[i].getAuthor().getName() + "("  + books[i].getAuthor().getBirthYear() + "-" +  books[i].getAuthor().getDeathYear()) + ")", cellStyle);
                t.addCell(String.valueOf(books[i].getPublishedYear()), cellStyle);
                t.addCell(ANSI_GRENN +  books[i].getStatus() + ANSI_RESET);
            }
        }
        System.out.println(t.render());
    }

    private static void borrowBook() {
        String id_String;
        int id = 0;
        while (true){
            System.out.print("Enter Book ID to Borrow: ");
            id_String = scanner.nextLine();
            if(id_String.matches("^\\d$")){
                id = Integer.parseInt(id_String);
                break;
            }else System.out.println(ANSI_RED + """
                         _______________________________________
                        |    You can input only number!🙏.      |
                        |_______________________________________|
                        """ + ANSI_RESET);
        }

        for (int i = 0; i < bookCount; i++) {
            if (books[i].getId() == id && books[i].getStatus().equals("Available")) {
                books[i].setStatus("Unavailable"); // Keep status simple for logic
                System.out.println(ANSI_GRENN + "| --------------------------- |");
                System.out.println("| Book borrowed successfully! |");
                System.out.println("| --------------------------- |" + ANSI_RESET);

                return;
            }
        }
        System.out.println(ANSI_GRENN + "| ----------------------------------- |");
        System.out.println("| Book not found or already borrowed. |");
        System.out.println("| ----------------------------------- |" + ANSI_RESET);
    }

//    Set row to show record

//    ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++=

    private static void displayBooksPage(int page, int rowsPerPage) {
        int start = (page - 1) * rowsPerPage;
        int end = Math.min(start + rowsPerPage, bookCount);

        CellStyle cellStyle = new CellStyle(CellStyle.HorizontalAlign.center);
        Table t = new Table(5, BorderStyle.UNICODE_BOX_DOUBLE_BORDER, ShownBorders.ALL);

        t.addCell(ANSI_BLUE + "Id", cellStyle);
        t.addCell(ANSI_BLUE + "Title", cellStyle);
        t.addCell(ANSI_BLUE + "Author Birth Death ", cellStyle);
        t.addCell(ANSI_BLUE + "Published Year", cellStyle);
        t.addCell(ANSI_BLUE + "Status", cellStyle);

        for (int i = start; i < end; i++) {
            t.addCell(String.valueOf(books[i].getId()), cellStyle);
            t.addCell(books[i].getTitle());
            t.addCell(String.valueOf(books[i].getAuthor().getName() + "(" + books[i].getAuthor().getBirthYear() + "-" + books[i].getAuthor().getDeathYear() + ")"), cellStyle);
            t.addCell(String.valueOf(books[i].getPublishedYear()), cellStyle);
            t.addCell(books[i].getStatus());
        }
        System.out.println(t.render());
    }

//    +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++



    private static void returnBook() {
        int id;
        String input;
        while (true){
            System.out.print("Enter Book ID to Return Book: ");
            input = scanner.nextLine();
            if(input.matches("^\\d$")){
                break;
            }else System.out.println(ANSI_RED + """
                         _______________________________________
                        |    You can input only number!🙏.      |
                        |_______________________________________|
                        """ + ANSI_RESET);
        }
        id = Integer.parseInt(input);

        for (int i = 0; i < bookCount; i++) {
            if (books[i].getId() == id && books[i].getStatus().equals("Unavailable")) {
                books[i].setStatus("Available");
                System.out.println(ANSI_GRENN + "Book returned successfully!" + ANSI_RESET);
                return;
            }
        }
        System.out.println(ANSI_RED + "Book not found or already available." + ANSI_RESET);
    }


    private static void deleteBook() {
        int id;
        String input;
        while (true){
            System.out.print("Enter Book ID to Delete: ");
            input = scanner.nextLine();
            if(input.matches("^\\d$")){
                break;
            }else System.out.println(ANSI_RED + """
                         _______________________________________
                        |    You can input only number!🙏.      |
                        |_______________________________________|
                        """ + ANSI_RESET);
        }
        id = Integer.parseInt(input);
        for(int i =0; i< bookCount; i++){
            if(books[i].getId() == id) {
                books[i].getAuthor().setName("REMOVED");
                books[i].setTitle("REMOVED");
                books[i].setStatus("REMOVED");
                books[i].setPublishedYear(0);
                books[i].getAuthor().setBirthYear(0);
                books[i].getAuthor().setDeathYear(0);
                System.out.println(ANSI_GRENN + "You are book Deleted Successfully. thanks you🙏" + ANSI_RESET);
            }else System.out.println(ANSI_RED + "Book not found." + ANSI_RESET);
        }
    }
}

// ___________________________________________________________________
//|                         Nhanh: Kimson                             |
//|                         Class: PVH                                |
//|                         Thanks you🙏                              |
//____________________________________________________________________
