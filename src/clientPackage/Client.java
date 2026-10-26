package clientPackage;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Client {
    // nombre, opérateur (+ - * /), nombre ; espaces facultatifs
    static final Pattern SYNTAXE = Pattern.compile(
            "^\\s*(-?\\d+(?:\\.\\d+)?)\\s*([+\\-*/])\\s*(-?\\d+(?:\\.\\d+)?)\\s*$");

    public static void main(String[] args) throws IOException {
        System.out.println("je suis un client pas encore connecté");

        try (Socket socket = new Socket("localhost", 1234)) {
            System.out.println("je suis un client connecté");

            // Out
            OutputStream os = socket.getOutputStream();
            PrintWriter pw = new PrintWriter(os, true);

            // In
            InputStream is = socket.getInputStream();
            BufferedReader br = new BufferedReader(
                    new InputStreamReader(is));

            Scanner sc = new Scanner(System.in);

            while (true) {
                // saisir une operation

                System.out.print("taper une operation (ex : 55 * 25) ou quit : ");
                String operation = sc.nextLine().trim();
                if (operation.equals("quit")) {
                    break;
                }
                // Validation de la syntaxe
                Matcher m = SYNTAXE.matcher(operation);
                if (!m.matches()) {
                    System.out.println("Syntaxe invalide !");
                    continue;
                }
                // envoi au serveur un par ligne
                pw.println(m.group(1)); // op1
                pw.println(m.group(2)); // op
                pw.println(m.group(3)); // op2

                // recevoir la resultat
                String res = br.readLine();
                System.out.println("Résultat reçu: " + res);
            }

            pw.println("quit"); // prévenir le serveur
            sc.close();

        } catch (IOException e) {
            System.out.println("Erreur: "+e.getMessage());
        }

    }
}
