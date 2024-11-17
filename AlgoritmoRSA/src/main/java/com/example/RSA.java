package com.example;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.Scanner;
import java.io.*;

public class RSA {
    private BigInteger n;      // Modulo
    private BigInteger e;      // Esponente pubblico
    private BigInteger d;      // Esponente privato

    private int bitLength = 64; // Lunghezza delle chiavi
    private SecureRandom random = new SecureRandom();

    private static final Logger logger = LogManager.getLogger(RSA.class);

    public static void main(String[] args) {
        RSA rsa = new RSA();
        rsa.printKeys();

        Scanner scanner = new Scanner(System.in);

        logger.info("Scegli l'origine del testo da criptare:");
        logger.info("Inserisci 1 se vuoi inserire il testo da tastiera o 2 se lo vuoi prendere da un file");

        int scelta = scanner.nextInt();
        scanner.nextLine();

        String input = "";

        //scelta dell'input da tastiera o da file
        switch (scelta) {
            case 1 -> {
                logger.info("Inserisci il codice da criptare:");
                input = scanner.nextLine();
            } 
            case 2 -> {
                logger.info("Inserisci il percorso del file:");
                String path = scanner.nextLine();
                input = readFile(path);
                if (input == null || input.isEmpty()) {
                    logger.error("Il file non esiste o è vuoto");
                    scanner.close();
                    return;
                }
            }
            default -> {
                logger.warn("Scelta non valida");
                scanner.close();
                return;
            }
                
        }

        //Cripto il messaggio
        String encrypted = rsa.encrypt(input);
        logger.info("Testo criptato: " + encrypted);

        //Decripto il messaggio
        String decrypted = rsa.decrypt(encrypted);
        logger.info("Testo decriptato: " + decrypted);

        boolean success = writeFIle("output.txt", decrypted);
        if (success) {
            logger.info("Il testo decriptato è stato salvato con successo nel file output.txt");
        } else {
            logger.error("Errore durante il salvataggio del file.");
        }

        scanner.close();

    }

    private static String readFile(String path) {
        StringBuilder content = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new FileReader(path))) {
            String line;
            while ((line = reader.readLine()) != null) {
                content.append(line).append("\n");
            }
        } catch (IOException e) {
            logger.error("Errore durante la lettura del file: " + e.getMessage());
        }
        return content.toString().trim();
    }

    public RSA() {
        generateKeys();
    }

    // 1) Generare le chiavi pubblica e privata
    private void generateKeys() {
        // Genera due numeri primi grandi
        BigInteger p = BigInteger.probablePrime(bitLength, random);
        BigInteger q = BigInteger.probablePrime(bitLength, random);

        // Calcola n = p * q
        n = p.multiply(q);

        // Calcola phi = (p-1) * (q-1)
        BigInteger phi = (p.subtract(BigInteger.ONE)).multiply(q.subtract(BigInteger.ONE));

        // Scegli un esponente pubblico e (gcd(e, phi) == 1)
        e = BigInteger.valueOf(65537); // Un valore comune per e
        while (!phi.gcd(e).equals(BigInteger.ONE)) {
            e = e.add(BigInteger.TWO);
        }

        // Calcola l'esponente privato d (d = e^-1 mod phi)
        d = e.modInverse(phi);
    }

    // 2) Criptare un testo in chiaro
    public String encrypt(String plainText) {
        return new BigInteger(plainText.getBytes()).modPow(e, n).toString();
    }

    // 3) Decriptare un testo criptato
    public String decrypt(String cipherText) {
        BigInteger encrypted = new BigInteger(cipherText);
        return new String(encrypted.modPow(d, n).toByteArray());
    }

    // Visualizza le chiavi
    public void printKeys() {
        logger.info("Chiave Pubblica: (e=" + e + ", n=" + n + ")");
        logger.info("Chiave Privata: (d=" + d + ", n=" + n + ")");
    }

    private static boolean writeFIle(String path, String content) {
        try (FileWriter writer = new FileWriter(path)) {
            writer.write(content);
            return true;
        } catch (IOException e) {
            logger.error("Errore durante la scrittura del file: " + e.getMessage());
            return false;
        }
    }
    
}