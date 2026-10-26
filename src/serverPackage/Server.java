package serverPackage;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    public static void main(String[] args) throws IOException {

        ServerSocket serverSocket = new ServerSocket(1234);
        System.out.println("Je suis un serveur en attente la connexion d'un client ");

        try {Socket socket = serverSocket.accept();
            System.out.println("un client est connecté");

            // In
            InputStream is = socket.getInputStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(is));

            // Out
            OutputStream os = socket.getOutputStream();
            PrintWriter pw = new PrintWriter(os,true);

            // lire op1
            String op1 = br.readLine();
            while ( !op1.equalsIgnoreCase("quit" )){
                // lire op
                String op = br.readLine();
                // lire op2
                String op2 = br.readLine();

                System.out.println("Operation reçue : " + op1 + " " + op + " " + op2);

                // traitement
                String res = calculer(
                                    Double.parseDouble(op1),
                                    op,
                                    Double.parseDouble(op2)
                                );

                // envoi
                pw.println(res);
                System.out.println("resultat envoyé : " + res);

                // relire op1
                op1 = br.readLine();
            }

            socket.close();
            serverSocket.close();
        }catch (IOException e){
            System.out.println("Erreur: " + e.getMessage());
        }
    }



    private static String calculer (double op1,String op, double op2){
        double res;

        switch (op){
            case "+":
                res = op1 + op2; break;
            case "-":
                res = op1 - op2; break;
            case "*":
                res = op1 * op2; break;
            case "/":
                res = op1 / op2; break;
            default :
                System.out.println("Choix Invalide ! ("+op+")");
                return "Choix d'operateur Invalide !";
        }

        return Double.toString(res);
    }
}