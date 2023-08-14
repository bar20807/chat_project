package com.chat_xmpp;
import java.io.IOException;
import java.util.Scanner;

public class App {
    public static void main(String[] args) throws IOException, InterruptedException {
        Scanner scanner = new Scanner(System.in);
        Cuentas cuentas = new Cuentas();

        while (true) {
            System.out.println("Menú:");
            System.out.println("1) Registrar una nueva cuenta en el servidor");
            System.out.println("2) Iniciar sesión con una cuenta");
            System.out.println("3) Cerrar sesión con una cuenta");
            System.out.println("4) Eliminar la cuenta del servidor");
            System.out.println("5) Salir");
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
                    System.out.print("Ingrese el nombre de usuario: ");
                    String username = scanner.nextLine();
                    System.out.print("Ingrese la contraseña: ");
                    String password = scanner.nextLine();
                    cuentas.Login(username, password);
                    break;

                case 3:
                    System.out.print("Ingrese el nombre de usuario: ");
                    String logoutUser = scanner.nextLine();
                    System.out.print("Ingrese la contraseña: ");
                    String logoutPassword = scanner.nextLine();
                    cuentas.Logout(logoutUser, logoutPassword);
                    break;

                case 4:
                    System.out.print("Ingrese el nombre de usuario: ");
                    String deleteUser = scanner.nextLine();
                    System.out.print("Ingrese la contraseña: ");
                    String deletePassword = scanner.nextLine();
                    cuentas.DeleteAccount(deleteUser, deletePassword);
                    break;

                case 5:
                    System.out.println("Saliendo...");
                    scanner.close();
                    System.exit(0);
                    break;

                default:
                    System.out.println("Opción inválida, intente nuevamente.");
            }
        }
    }
}