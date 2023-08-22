package com.chat_xmpp;

import org.jivesoftware.smack.AbstractXMPPConnection;
import org.jivesoftware.smack.ConnectionConfiguration;
import org.jivesoftware.smack.MessageListener;
import org.jivesoftware.smack.SmackException;
import org.jivesoftware.smack.SmackException.NoResponseException;
import org.jivesoftware.smack.SmackException.NotConnectedException;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.XMPPException.XMPPErrorException;
import org.jivesoftware.smack.chat2.IncomingChatMessageListener;
import org.jivesoftware.smack.packet.Message;
import org.jivesoftware.smack.packet.Presence;
import org.jivesoftware.smack.roster.Roster;
import org.jivesoftware.smack.roster.RosterEntry;
import org.jivesoftware.smack.tcp.XMPPTCPConnection;
import org.jivesoftware.smack.tcp.XMPPTCPConnectionConfiguration;
import org.jivesoftware.smackx.disco.ServiceDiscoveryManager;
import org.jivesoftware.smackx.filetransfer.FileTransferManager;
import org.jivesoftware.smackx.filetransfer.IncomingFileTransfer;
import org.jivesoftware.smackx.filetransfer.OutgoingFileTransfer;
import org.jivesoftware.smackx.muc.MultiUserChat;
import org.jivesoftware.smackx.muc.MultiUserChatManager;
import org.jivesoftware.smackx.muc.Occupant;
import org.jivesoftware.smackx.xdata.FormField;
import org.jivesoftware.smackx.xdata.form.Form;
import org.jivesoftware.smackx.muc.MultiUserChatException.MissingMucCreationAcknowledgeException;
import org.jivesoftware.smackx.muc.MultiUserChatException.MucAlreadyJoinedException;
import org.jivesoftware.smackx.muc.MultiUserChatException.NotAMucServiceException;
import org.jxmpp.jid.BareJid;
import org.jxmpp.jid.EntityBareJid;
import org.jxmpp.jid.EntityFullJid;
import org.jxmpp.jid.impl.JidCreate;
import org.jxmpp.jid.parts.Resourcepart;
import org.jxmpp.stringprep.XmppStringprepException;
import org.jivesoftware.smack.chat2.Chat;
import org.jivesoftware.smack.chat2.ChatManager;
import org.jivesoftware.smackx.xdata.packet.DataForm;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;

public class Comunicacion {

    private List<String> chatHistory = new ArrayList<>();
    private boolean showMessagePrompt = true;

    public void showContactList(AbstractXMPPConnection connection) {
        // Obtener la lista de contactos (roster)
        Roster roster = Roster.getInstanceFor(connection);
    
        // Obtener las entradas del roster
        Collection<RosterEntry> entries = roster.getEntries();
    
        // Imprimir las entradas del roster
        for (RosterEntry entry : entries) {
            // Obtener la presencia del contacto
            Presence presence = roster.getPresence(entry.getJid());
    
            // Imprimir el nombre del contacto
            System.out.println("\nContacto: " + entry.getName());
    
            // Comprobar si el contacto está disponible
            if (presence.isAvailable()) {
                System.out.println("Estado: En línea");
            } else {
                String status = presence.getStatus();
                if (status != null) {
                    System.out.println("Estado: " + status + "\n");
                } else {
                    System.out.println("Estado: Desconectado\n");
                }
            }
        }
    }
    
    public void addContact(AbstractXMPPConnection connection, String username) {
        // Obtener la lista de contactos (roster)
        Roster roster = Roster.getInstanceFor(connection);
        try {
            // Formar el JID completo a partir del nombre de usuario y el dominio XMPP
            String userJid = username + "@alumchat.xyz";
    
            // Convertir la cadena JID en un objeto BareJid
            BareJid bareJid = JidCreate.bareFrom(userJid);
    
            // Agregar el usuario al roster
            roster.createEntry(bareJid, userJid, null);
            System.out.println("\nUsuario agregado exitosamente a los contactos.\n");
        } catch (Exception e) {
            System.out.println("\nError al agregar el usuario a los contactos: " + e.getMessage());
        }
    }
     
    public void deleteContact(AbstractXMPPConnection connection, String username) {
        // Obtener la lista de contactos (roster)
        Roster roster = Roster.getInstanceFor(connection);
        try {
            // Formar el JID completo a partir del nombre de usuario y el dominio XMPP
            String userJid = username + "@alumchat.xyz";
    
            // Convertir la cadena JID en un objeto BareJid
            BareJid bareJid = JidCreate.bareFrom(userJid);
    
            // Eliminar el usuario del roster
            roster.removeEntry(roster.getEntry(bareJid));
            System.out.println("\nUsuario eliminado exitosamente de los contactos.\n");
        } catch (Exception e) {
            System.out.println("\nError al eliminar el usuario de los contactos: " + e.getMessage());
        }
    }

    public void showContactInfo(AbstractXMPPConnection connection, String userJid) {
        // Obtener la lista de contactos (roster)
        Roster roster = Roster.getInstanceFor(connection);
        // Formar el JID completo a partir del nombre de usuario y el dominio XMPP
        String userJid_complete = userJid + "@alumchat.xyz";
        try {
            // Obtener la entrada del roster correspondiente al usuario
            RosterEntry entry = roster.getEntry(JidCreate.bareFrom(userJid_complete));
    
            if (entry != null) {
                // Obtener los detalles de contacto
                String name = entry.getName();
                Presence presence = roster.getPresence(entry.getJid());
                String status = (presence.getStatus() == null) ? "Sin estado" : presence.getStatus();
    
                // Imprimir los detalles de contacto
                System.out.println("\nNombre: " + name);
                System.out.println("JID: " + entry.getJid());
                System.out.println("Disponible: " + presence.isAvailable());
                System.out.println("Estado: " + status);
            } else {
                System.out.println("El usuario no está en la lista de contactos.");
            }
        } catch (Exception e) {
            System.out.println("Error al obtener los detalles de contacto: " + e.getMessage());
        }
    }

    public void setPresence(AbstractXMPPConnection connection, String status) throws NotConnectedException, InterruptedException{
        try {
            // Crear un nuevo objeto Presence
            Presence presence = new Presence(Presence.Type.available);

            // Establecer el estado
            presence.setStatus(status);

            // Enviar el objeto Presence
            connection.sendStanza(presence);

            System.out.println("Mensaje de presencia definido exitosamente: " + status);
        } catch (Exception e) {
            System.out.println("Error al definir el mensaje de presencia: " + e.getMessage());
        }
    }

    public void sendMessage(AbstractXMPPConnection connection, String userJid) {
        ChatManager chatManager = ChatManager.getInstanceFor(connection);
        String userJidComplete = userJid + "@alumchat.xyz";
        
        // Registrar un listener para recibir mensajes entrantes
        chatManager.addIncomingListener(new IncomingChatMessageListener() {
            @Override
            public void newIncomingMessage(EntityBareJid from, Message message, Chat chat) {
                String incomingMessage = from.getLocalpart().toString() + ": " + message.getBody();
                chatHistory.add(incomingMessage);
                printChat();
            }
        });

        // Crear un chat con el usuario deseado
        EntityBareJid jid;
        try {
            jid = JidCreate.entityBareFrom(userJidComplete);
            Chat chat = chatManager.chatWith(jid);

            // Iniciar un bucle para enviar mensajes
            Scanner scanner = new Scanner(System.in);
            while (true) {
                // Leer el mensaje del usuario
                if (showMessagePrompt) {
                    System.out.print("Ingrese un mensaje (o 'salir' para terminar): ");
                }
                String mensaje = scanner.nextLine();

                // Verificar si el usuario quiere salir
                if (mensaje.equalsIgnoreCase("salir")) {
                    break;
                }

                // Enviar el mensaje al usuario
                chat.send(mensaje);
                String outgoingMessage = "Yo" + ": " + mensaje;
                chatHistory.add(outgoingMessage);
                showMessagePrompt = false;
                printChat();
            }
        } catch (Exception e) {
            System.out.println("Error al establecer la comunicación uno a uno: " + e.getMessage());
        }
    }

    private void printChat() {
        System.out.println("\n---- Historial del chat ----");
        for (String message : chatHistory) {
            System.out.println(message);
        }
        System.out.println("----------------------------");
    }

    //Chat grupal método
    public void joinGroupChat(AbstractXMPPConnection connection) throws XmppStringprepException, NotAMucServiceException, NoResponseException, XMPPErrorException, NotConnectedException, InterruptedException, MucAlreadyJoinedException, MissingMucCreationAcknowledgeException {
        // Creación de un manager de multiples personas, o de grupo
        MultiUserChatManager mucManager = MultiUserChatManager.getInstanceFor(connection);
        // Pide el nombre del grupo a cual unirse.
        Scanner input = new Scanner(System.in);
        System.out.println("Ingrese un apodo para el grupo: ");
        String apodo = input.nextLine();
        Resourcepart nickname = Resourcepart.from(apodo);
        System.out.println("Ingrese nombre del grupo: ");
        String room = input.nextLine();
        // Crea el Jid del grupo. Se utiliza @conference para esto.
        EntityBareJid roomJid = JidCreate.entityBareFrom(room + "@conference.alumchat.xyz");
        ServiceDiscoveryManager discoManager = ServiceDiscoveryManager.getInstanceFor(connection);
        MultiUserChat muc = mucManager.getMultiUserChat(roomJid);
        // Get MultiUser chat para crear un grupo, o para también tener el grupo
        try {
            discoManager.discoverInfo(roomJid);
            System.out.println("Se encontro grupo: " + roomJid);
        } catch (SmackException.NoResponseException | XMPPException.XMPPErrorException
                | SmackException.NotConnectedException e) {
            System.out.println("No se encontro grupo: " + roomJid);

            Resourcepart name = Resourcepart.from(room);
            muc.create(name);
            System.out.println("Chat grupal creado: " + muc.getRoom());

        }
        Occupant ownOccupant = muc.getOccupant(connection.getUser()); // Get your own occupant
        System.out.println(ownOccupant);
        if (ownOccupant != null) {
            System.out.println(ownOccupant.getRole());
        } else {
            System.out.println("No formas parte de este grupo");
        }
        // respecto.

        muc.join(nickname);
        // Recurso para poder mandar al grupo. Después de une.
        muc.addMessageListener(new MessageListener() {
            @Override
            public void processMessage(Message message) {
                EntityFullJid roomJid;
                try {
                    roomJid = JidCreate
                            .entityFullFrom(room + "@conference.alumchat.xyz/" + apodo);
                    if (!roomJid.equals(message.getFrom()))
                        System.out.println(
                                "Mensaje recibido por " + message.getFrom() + ": "
                                        + message.getBody());
                } catch (XmppStringprepException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }

            }
        });
        System.out.println("Se ha unido al group!");
    }

    public void sendMessageToGroupChat(AbstractXMPPConnection connection) throws SmackException.NotConnectedException, InterruptedException, XmppStringprepException {
        // Manager para recibir mensajes del grupo.
        int group = 0;
        // Menu de que hacer en el grupo.
        while (group != 2) {
            MultiUserChatManager mucManager = MultiUserChatManager.getInstanceFor(connection);
            Scanner input = new Scanner(System.in);
            System.out.println("Que quiere hacer?\n1. Mandar un mensaje.\n2. Salir del grupo. ");
            group = input.nextInt();
            if (group == 1) {
                input.nextLine();

                System.out.print("Ingrese el nombre del grupo: ");
                String room = input.nextLine();
                EntityBareJid roomJid = JidCreate.entityBareFrom(room + "@conference.alumchat.xyz");
                ServiceDiscoveryManager discoManager = ServiceDiscoveryManager
                        .getInstanceFor(connection);
                MultiUserChat muc = mucManager.getMultiUserChat(roomJid);
                // Get MultiUser chat para crear un grupo, o para también tener el grupo
                try {
                    discoManager.discoverInfo(roomJid);
                    System.out.println("Se encontro grupo: " + roomJid);
                    System.out.println("Escriba el mensaje que desea mandar: ");
                    String message = input.nextLine();

                    muc.sendMessage(message);
                } catch (SmackException.NoResponseException | XMPPException.XMPPErrorException
                        | SmackException.NotConnectedException e) {
                    System.out.println("No se encontro grupo: " + roomJid);

                }

            } else if (group == 2) {
                System.out.println("Volviendo al menu.");
            }
    }
}

    //Métodos para enviar archivos
    public void sendFileMessage(AbstractXMPPConnection connection) {
        ChatManager chatManager = ChatManager.getInstanceFor(connection);
        chatManager.addIncomingListener(new IncomingChatMessageListener() {
            @Override
            public void newIncomingMessage(EntityBareJid from, Message message, Chat chat) {
                // En el caso que se recibe un mensaje, recibe la información de dicho mensaje.
                if (message.getBody().startsWith("file://")) {
                    // Contenido del archivo, y también formato del archivo final. También quien
                    // mando el archivo y su contenido
                    String base64File = message.getBody().substring(7);
                    String fileType = message.getBody().substring(7, 7 + base64File.indexOf("://"));
                    base64File = base64File.substring(base64File.indexOf("://") + 3);

                    // Decodifica el archivo en bytes.
                    byte[] file = java.util.Base64.getDecoder().decode(base64File);

                    System.out.println("\nReceived file from " + from + ": " + fileType);

                    File fileToSave = new File("C:\\Users\\barre\\chat_project\\recieved_file." + fileType);

                    try {
                        // Guarda los bytes del archivo en el disco.
                        FileOutputStream fileOutputStream = new FileOutputStream(fileToSave);
                        fileOutputStream.write(file);
                        fileOutputStream.close();
                    } catch (Exception e) {
                        System.out.println("Error: " + e.getMessage());
                    }

                } else {
                    // Si solo es un mensaje cualquiera, muestra la información de este mensaje.
                    System.out.println("New message from " + from + ": " + message.getBody());
                }
            }
        });

        Cuentas cuentaDomain = new Cuentas();
        Scanner input = new Scanner(System.in);
        System.out.println("Ingrese el usuario al que desea enviarle el archivo: ");
        String username = input.nextLine();
        System.out.println("Ingrese dirección del archivo: ");
        String filePath = input.nextLine();
        // Pide el usuario y la dirección del archivo a donde enviar.
        File file = new File(filePath);

        byte[] fileBytes = new byte[(int) file.length()];
        // En un array de bytes, se escribe qué tan grande es el archivo.
        try {
            // Try catch en caso que no pueda leer el archivo.
            java.io.FileInputStream fileInputStream = new java.io.FileInputStream(file);
            fileInputStream.read(fileBytes);
            fileInputStream.close();
        } catch (Exception e) {
            System.out.println("Error al enviar el archivo" + e.getMessage());
        }
        // Encoding para leerlo en base64 File
        String base64File = java.util.Base64.getEncoder().encodeToString(fileBytes);
        String fileType = filePath.substring(filePath.lastIndexOf(".") + 1);
        // Escribe el substring y al mandarlo, lo devuelve en un file.
        String message = "file://" + fileType + "://" + base64File;

        try {
            EntityBareJid userID = JidCreate
                    .entityBareFrom(username + "@" + cuentaDomain.getConfig().getXMPPServiceDomain());
            Chat chat = chatManager.chatWith(userID);
            chat.send(message);
        } catch (Exception e) {
            System.out.println("Error al enviar el archivo" + e.getMessage());
            return;
        }
        // Try catch para cuando manda el archivo.

        System.out.println("Archivo enviado correctamente");
    }


}
