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
            System.out.println("\nMenu de administración: ");
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
                    System.out.println("\nActualiza tu estado: ");
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
                    AdminMessageMenu(connection);
                    break;
                case 4:
                    AdminContactMenu(connection);
                    break;
                case 5:
                    cuenta.DeleteAccount(connection);
                    break;
                case 6:
                    cuenta.Logout(connection);
                    System.exit(0);
                    break;
                default:
                    System.out.println("Opción inválida, intente nuevamente.\n");
            }
        }
    }
    //Menú de gestión de contactos
    private void AdminContactMenu(AbstractXMPPConnection connection) throws XMPPException, SmackException, InterruptedException {
        Comunicacion comunicacion = new Comunicacion();
        boolean exit = false;
        String new_contact = " ";
        String delete_contact = " ";
        String contact_info = " ";
        while (!exit) {
            Scanner scanner = new Scanner(System.in);
            System.out.println("\nAdministrar contactos: ");
            System.out.println("1. Mostrar listado de contactos");
            System.out.println("2. Agregar nuevo contacto");
            System.out.println("3. Más detalles sobre un contacto");
            System.out.println("4. Eliminar un contacto");
            System.out.println("5. Salir");
            System.out.print("Ingrese la opción deseada: ");
            int option = scanner.nextInt();

            switch (option) {
                case 1:
                    comunicacion.showContactList(connection);
                    break;
                case 2:
                    scanner.nextLine();
                    System.out.print("\nIngrese el nombre de usuario del contacto: ");
                    new_contact = scanner.nextLine();
                    comunicacion.addContact(connection, new_contact);
                    break;
                case 3:
                    scanner.nextLine();
                    System.out.println("Ingrese el nombre de usuario del contacto para obtener más información: ");
                    contact_info = scanner.nextLine();
                    comunicacion.showContactInfo(connection, contact_info);
                    break;
                
                case 4:
                    scanner.nextLine();
                    System.out.println("\nIngrese el nombre de usuario del contacto que desea eliminar: ");
                    delete_contact = scanner.nextLine();
                    comunicacion.deleteContact(connection, delete_contact);
                    break;
                case 5:
                    exit = true;
                    break;
                default:
                    System.out.println("Opción inválida, intente nuevamente.\n");
            }
        }
    }
    //Menú de gestión de mensaje
    private void AdminMessageMenu(AbstractXMPPConnection connection) throws XMPPException, SmackException, InterruptedException {
        Comunicacion comunicacion = new Comunicacion();
        boolean exit = false;
        String direct_contact = " ";
        String delete_contact = " ";
        String contact_info = " ";
        while (!exit) {
            Scanner scanner = new Scanner(System.in);
            System.out.println("\nAdministrar mensajes: ");
            System.out.println("1. Enviar mensaje directo");
            System.out.println("2. Participar en chat grupal");
            System.out.println("3. Definir mensaje de presencia");
            System.out.println("4. Enviar/Recibir notificaciones");
            System.out.println("5. Salir");
            System.out.print("Ingrese la opción deseada: ");
            int option = scanner.nextInt();

            switch (option) {
                case 1:
                    scanner.nextLine();
                    System.out.print("\nIngrese el nombre de usuario de quien desea enviarle mensaje: ");
                    direct_contact = scanner.nextLine();
                    comunicacion.sendMessage(connection, direct_contact);
                    break;
                case 2:
               
                    break;
                case 3:
                    scanner.nextLine();
                    System.out.print("Ingrese el mensaje de presencia que desea definir: ");
                    String status = scanner.nextLine();
                    comunicacion.setPresence(connection, status);
                    break;
                
                case 4:
                    break;
                case 5:
                    exit = true;
                    break;
                default:
                    System.out.println("Opción inválida, intente nuevamente.\n");
            }
        }
    }
}