import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.StringTokenizer;

/**
 * Created by 47989768s on 19/04/17.
 */
public class Server extends Thread{

    static final int port = 9090;
    ServerSocket serverSocket;
    private boolean working = true;
    Socket socket;
//    private File log;

    public Server() {

        socket = null;
//        log = new File("LOG.txt");

    }

    @Override
    public void run() {

        startServer();

    }

    private void startServer() {


        try {
            serverSocket = new ServerSocket(port);
            while(working){
                socket = serverSocket.accept();
                System.out.println("socket init!");
                processPetition();

            }
        }catch(IOException e){
            e.printStackTrace();
        }
    }

    private void processPetition() {

        BufferedReader br;
        PrintWriter pw;

        try {
            br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            pw = new PrintWriter(socket.getOutputStream(), true);

            String request = br.readLine();
            StringTokenizer tokenizer = new StringTokenizer(request);
            String petition = tokenizer.nextToken();

            String response = "";
            switch (petition) {
                case "GET":
                    response =
                            "<html>" +
                            "<head>" +
                            "<meta charset=\"UTF-8\">" +
                            "<title>Hola Dionís</title>" +
                            "<b>Esto es un servidor WEB que funciona de 10.</b>" +
                            "</head>" +
                            "<body>" +
                            "<h2>IP = " + socket.getInetAddress().getHostAddress() + "</h2>" +
                            "<p>Me llamo Gerard!</p>" +
                            "</body>" +
                            "</html>";
                    break;

                default:
                    response =
                            "<html>" +
                            "<head>" +
                            "<meta charset=\"UTF-8\">" +
                            "<title>ERROR</title>" +
                            "</head>" +
                            "<body>" +
                            "<p>Petition not recognized</p>" +
                            "</body>" +
                            "</html>";
                    break;
            }



            System.out.println(response);

            pw.println("HTTP/1.0 200 OK");
            pw.println("Content-Type: text/html");
            pw.println("Server: Bot");
            pw.println("");
            pw.print(response);
            pw.flush();

            socket.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
