import javax.swing.*;
import java.awt.*;
import java.awt.Label;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.concurrent.*;
import java.util.Scanner;


/**
 * Simulation of thread pipeline model using java BlockingQueue as buffer between two stages of pipeline.
 */
public class pipelineModelDemo {


    /**
     * Main method
     *
     * @param args
     * @throws InterruptedException
     * @throws FileNotFoundException
     * @throws UnsupportedEncodingException
     */
    public static void main(String[] args) throws InterruptedException, FileNotFoundException, UnsupportedEncodingException {
        initializeModel();
    }

    /**
     * initial model variables and method setup
     *
     * @throws InterruptedException
     */
    private static void initializeModel() throws InterruptedException, FileNotFoundException, UnsupportedEncodingException {
        final int BLOCKING_QUEUE_SIZE = 100;
        ArrayBlockingQueue<File> file_queue = new ArrayBlockingQueue<>(BLOCKING_QUEUE_SIZE);
        ArrayBlockingQueue<String> search_queue = new ArrayBlockingQueue<>(BLOCKING_QUEUE_SIZE);
        performStage1(file_queue, search_queue);
    }


    /**
     * Method to perform first task of listing all files from directories and sub-directories
     *
     * @param file_queue
     * @param search_queue
     * @throws InterruptedException
     * @throws FileNotFoundException
     * @throws UnsupportedEncodingException
     */
    private static void performStage1(ArrayBlockingQueue<File> file_queue, ArrayBlockingQueue<String> search_queue) throws InterruptedException, FileNotFoundException, UnsupportedEncodingException {
        final String folder_to_search = "./src/Visualization";
        readAndSearchTerm(file_queue, search_queue, folder_to_search);
    }

    static JButton submit;
    static JTextField field;

    /**
     * Method to read search term
     *
     * @return input search keyword by user
     */
    public static void readAndSearchTerm(final ArrayBlockingQueue<File> file_queue, final ArrayBlockingQueue<String> search_queue, final String folder_to_search) {
        Label l4 = new Label(" ");
        JFrame f4 = new JFrame("PipeLine Representation");
        f4.getContentPane().add(l4, BorderLayout.CENTER);
        f4.getContentPane().setBackground(Color.darkGray);
        f4.setBounds(10, 5, 50000, 800);
        f4.setVisible(true);
        f4.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        Label l1 = new Label("Enter a keyword to search through files:");
        JFrame f1 = new JFrame("Stage 1");
        field = new JTextField(10);
        submit = new JButton("Search");
        submit.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                IdentifyFilesFromFolder identifier;
                identifier = new IdentifyFilesFromFolder(file_queue, new File(folder_to_search)); //checks for directories and sub-directories within the path given and puts into blocking queue
                new Thread(identifier).start();
                try {
                    performStage2(file_queue, field.getText(), search_queue);
                } catch (InterruptedException e1) {
                    e1.printStackTrace();
                } catch (FileNotFoundException e1) {
                    e1.printStackTrace();
                } catch (UnsupportedEncodingException e1) {
                    e1.printStackTrace();
                }
            }
        });
        f1.getContentPane().add(l1, BorderLayout.NORTH);
        f1.getContentPane().add(field, BorderLayout.CENTER);
        f1.getContentPane().add(submit, BorderLayout.SOUTH);
        f1.setBounds(14, 50, 300, 180);
        f1.setVisible(true);
    }

    /**
     * @param file_queue
     * @param search_term
     * @param search_queue
     * @throws InterruptedException
     * @throws FileNotFoundException
     * @throws UnsupportedEncodingException
     */
    private static void performStage2(ArrayBlockingQueue<File> file_queue, String search_term, ArrayBlockingQueue<String> search_queue) throws InterruptedException, FileNotFoundException, UnsupportedEncodingException {
        final int NUM = 10;

        for (int j = 1; j <= NUM; j++) {
            new Thread(new PerformSearch(file_queue, search_term, search_queue)).start();
        }
        Label l2 = new Label("Search operation going on...Putting search results in search queue and proceeding to next stage...");
        JFrame f2 = new JFrame("Stage 2 started...");
        Thread.sleep(2000);
        f2.getContentPane().add(l2, BorderLayout.CENTER);
        f2.setBounds(320, 220, 330, 180);
        f2.setVisible(true);
        performStage3(search_queue);
    }


    /**
     * @param search_queue
     * @throws InterruptedException
     * @throws FileNotFoundException
     * @throws UnsupportedEncodingException
     */
    private static void performStage3(ArrayBlockingQueue<String> search_queue) throws InterruptedException, FileNotFoundException, UnsupportedEncodingException {
        String text_results=null, html_results;
        for(int i=0; i<search_queue.size(); i++){
        	text_results = text_results + search_queue.take() + "\n";
        }
        
        JTextArea textArea = new JTextArea();
        JScrollPane scrollPane = new JScrollPane(textArea);
        textArea.setText("Following results were found and output text file was created\n");
        textArea.append(text_results);
        JFrame f3 = new JFrame("Stage 3");
        JScrollPane myJScrollPane = new JScrollPane(textArea, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        myJScrollPane.setViewportView(textArea);
        Thread.sleep(2000);
        f3.getContentPane().add(myJScrollPane, BorderLayout.CENTER);
        f3.setBounds(450, 450, 900, 300);
        f3.setVisible(true);
        PrintWriter writer = new PrintWriter("search_output.txt", "UTF-8");
        writer.println(text_results);
        writer.close();
    }
}


/**
 * Class to find and put files from given directory and sub-directories
 */
class IdentifyFilesFromFolder implements Runnable {
    private ArrayBlockingQueue<File> file_queue;
    private File folderToSearch;
    public static File EMPTY_FILE = new File("");


    /**
     * Constructor function
     *
     * @param file_queue     blocking queue to hold files within given folder/directory
     * @param folderToSearch given folder to search for files
     */
    public IdentifyFilesFromFolder(ArrayBlockingQueue<File> file_queue, File folderToSearch) {
        this.file_queue = file_queue;
        this.folderToSearch = folderToSearch;
    }


    /**
     * Method to get all files in directory and sub-directories and put in file_queue
     *
     * @param folderToSearch
     * @throws InterruptedException
     */
    public void listAndPut(File folderToSearch) throws InterruptedException {
        File[] filesInFolder;
        filesInFolder = folderToSearch.listFiles();
        for (File file : filesInFolder) {
            if (file.isDirectory()) {
                listAndPut(file);
            } else {
                file_queue.put(file);
            }
        }
    }

    public void run() {
        try {
            // do recursive listing of files in directory and sub-directories
            listAndPut(folderToSearch);
            //put empty file at the end of queue to signal end of files
            file_queue.put(EMPTY_FILE);
        } catch (InterruptedException e) {
            System.out.println("Something went wrong. Please try again later!");
        }
    }

}


/**
 * Class to perform search for a given input keyword string from list of files stored in queue
 */
class PerformSearch implements Runnable {
    private String search_term;
    private ArrayBlockingQueue<File> file_queue;
    private ArrayBlockingQueue<String> search_queue;

    /**
     * Constructor function
     *
     * @param file_queue  blocking queue holding files
     * @param search_term search term
     */
    public PerformSearch(ArrayBlockingQueue<File> file_queue, String search_term, ArrayBlockingQueue<String> search_queue) {
        this.file_queue = file_queue;
        this.search_term = search_term;
        this.search_queue = search_queue;
    }


    /**
     * Method to search given file for given search term and print matching lines
     *
     * @param fileToSearch file to perform search on
     * @throws IOException
     */
    public void doSearch(File fileToSearch) throws IOException, InterruptedException {
        Scanner fileScanner;
        fileScanner = new Scanner(new FileInputStream(fileToSearch));
        int fileLineNumber = 0;
        while (fileScanner.hasNextLine()) {
            fileLineNumber++;
            String fileLine = fileScanner.nextLine();
            if (fileLine.contains(search_term)) {
                search_queue.put("File Name: " + fileToSearch.getName() + "*** File Line Number:" + Integer.toString(fileLineNumber) + "*** Line data: " + fileLine);
                //System.out.printf("File Name: %s *** File Line Number: %d *** Line data: %s%n", fileToSearch.getName(), fileLineNumber, fileLine);
            }
        }
        fileScanner.close();
    }

    /**
     * Run method for thread start
     */
    public void run() {

        try {
            Boolean complete = false;
            while (!complete) {
                File fileToSearch;
                fileToSearch = file_queue.take();
                if (fileToSearch == IdentifyFilesFromFolder.EMPTY_FILE) {
                    file_queue.put(fileToSearch);
                    complete = true;
                } else {
                    doSearch(fileToSearch);
                }
            }
        } catch (InterruptedException e) {
            System.out.println("Something went wrong. Please try again later!");
        } catch (IOException e) {
            System.out.println("Something went wrong. Please try again later!");
        }
    }

}