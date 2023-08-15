package com.chat_xmpp;
import java.io.IOException;
import java.util.Scanner;

import org.jivesoftware.smack.AbstractXMPPConnection;

public class App {
    public static void main(String[] args) throws IOException, InterruptedException {
        Scanner scanner = new Scanner(System.in);
        Cuentas cuentas = new Cuentas();

        while (true) {
            System.out.println("Menú:");
            System.out.println("1) Registrar una nueva cuenta en el servidor");
            System.out.println("2) Iniciar sesión con una cuenta");
            System.out.println("3) Salir");
            System.out.print("Ingrese la opción deseada: ");
            
            int option = scanner.nextInt();
            scanner.nextLine(); // Consumir la nueva línea pendiente después de nextInt()

            switch (option) {
                case 1:
                    System.out.print("Ingrese el nuevo nombre de usuario: ");
                    String newUser = scanner.nextLine();
                    System.out.print("Ingrese la nueva contraseña: ");
                    String newPassword = scanner.nextLine();
                    cuentas.Register(newUser, newPassword);
                    break;

                case 2:
                    System.out.println("Iniciar Sesión: ");
                    System.out.print("Usuario: ");
                    String username = scanner.next();
                    System.out.print("Contraseña: ");
                    String password = scanner.next();

                    try { 
                        AbstractXMPPConnection connection = cuentas.Login(username,password);
                        if (connection.isAuthenticated()) {
                            Administracion menu = new Administracion();
                            menu.AdminMenu(connection);
                        } else {
                            System.out.println("\nError de autentificación\n");
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;
                
                case 3:
                    System.out.println("Saliendo...");
                    System.exit(0);
                    break;
                default:
                    System.out.println("Opción inválida, intente nuevamente.");
            }
        }
    }
}