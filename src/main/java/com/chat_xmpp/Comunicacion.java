package com.chat_xmpp;

import org.jivesoftware.smack.AbstractXMPPConnection;
import org.jivesoftware.smack.packet.Presence;
import org.jivesoftware.smack.roster.Roster;
import org.jivesoftware.smack.roster.RosterEntry;
import org.jxmpp.jid.BareJid;
import org.jxmpp.jid.impl.JidCreate;

import java.util.Collection;

public class Comunicacion {

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
    
                // Imprimir los detalles de contacto
                System.out.println("\nNombre: " + name);
                System.out.println("JID: " + entry.getJid());
                System.out.println("Disponible: " + presence.isAvailable());
                System.out.println("Estado: " + presence.getStatus());
            } else {
                System.out.println("El usuario no está en la lista de contactos.");
            }
        } catch (Exception e) {
            System.out.println("Error al obtener los detalles de contacto: " + e.getMessage());
        }
    }
    
    
}
