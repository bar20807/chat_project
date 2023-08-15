package com.chat_xmpp;
import org.jivesoftware.smack.AbstractXMPPConnection;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.packet.Presence;
import org.jivesoftware.smack.SmackException;
import java.util.Scanner;

public class Administracion{

    public void AdminMenu(AbstractXMPPConnection connection) throws XMPPException, SmackException, InterruptedException {
        Cuentas cuenta = new Cuentas();
        Comunicacion comunicacion = new Comunicacion();
        while (true) {
            Scanner scanner = new Scanner(System.in);
            System.out.println("Menu de administración: ");
            System.out.println("1. Ver perfil");
            System.out.println("2. Actualizar estado");
            System.out.println("3. Administar mensajes");
            System.out.println("4. Administrar contactos");
            System.out.println("5. Borrar cuenta");
            System.out.println("6. Cerrar sesión");
            System.out.print("Ingrese la opción deseada: ");
            int option = scanner.nextInt();

            switch (option) {
                case 1:
                    cuenta.showProfile(connection);
                    break;
                case 2:
                    System.out.println("Actualiza tu estado: ");
                    System.out.println("1. Disponible");
                    System.out.println("2. Ausente");
                    System.out.println("3. No molestar");
                    System.out.print("Ingresa el estado en el que te encuentras: ");
                    int option_status = scanner.nextInt();
                    Presence updatedPresence = cuenta.getPresenceByOption(option_status);
                    connection.sendStanza(updatedPresence);
                    System.out.println("\n¡Estado actualizado!\n");
                    break;
                case 3:
                    break;
                case 4:
                    
                    break;
                case 5:
                    cuenta.DeleteAccount(connection);
                    break;
                case 6:
                    cuenta.Logout(connection);
                    System.exit(0);
                    break;
                default:
                    System.out.println("Opción inválida, intente nuevamente.");
            }
        }
    }
}