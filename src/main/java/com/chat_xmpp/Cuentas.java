package com.chat_xmpp;

import java.io.IOException;
import java.util.Scanner;

import org.jivesoftware.smack.AbstractXMPPConnection;
import org.jivesoftware.smack.ConnectionConfiguration;
import org.jivesoftware.smack.ConnectionConfiguration.SecurityMode;
import org.jivesoftware.smack.SmackConfiguration;
import org.jivesoftware.smack.SmackException;
import org.jivesoftware.smack.SmackException.NotConnectedException;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.packet.Presence;
import org.jivesoftware.smack.roster.Roster;
import org.jivesoftware.smack.tcp.XMPPTCPConnection;
import org.jivesoftware.smack.tcp.XMPPTCPConnectionConfiguration;
import org.jivesoftware.smackx.iqregister.AccountManager;
import org.jxmpp.jid.DomainBareJid;
import org.jxmpp.jid.impl.JidCreate;
import org.jxmpp.jid.parts.Localpart;
import org.jxmpp.stringprep.XmppStringprepException;
public class Cuentas {
    private String userName;
    private String password;

    //Método que recibe el usuario
    public void setUser (String userName){
        this.userName = userName;
    }
    //Método que retorna dicho usuario 
    public String getUser (){
        return userName;
    }
    //Método que recibe la contraseña
    public void setPassword (String password){
        this.password = password;
    }
    //Método que retorna dicha contraseña
    public String getPassword (){
        return password;
    }

    //Método para retornar la configuración de la conexión
    public XMPPTCPConnectionConfiguration getConfig() throws XmppStringprepException {
        String xmppDomainString = "alumchat.xyz"; 

        DomainBareJid xmppDomain = JidCreate.domainBareFrom(xmppDomainString);

        XMPPTCPConnectionConfiguration config = XMPPTCPConnectionConfiguration.builder()
            .setUsernameAndPassword(getUser(), getPassword())
            .setXmppDomain(xmppDomain)
            .setHost("alumchat.xyz")
            .setSecurityMode(ConnectionConfiguration.SecurityMode.disabled)
            .build();

        return config;
    }

    //Método que recibe la contraseña
    public AbstractXMPPConnection Login(String username, String password) throws XmppStringprepException {

        System.out.println("alumchat.xyz");

        AbstractXMPPConnection connection = new XMPPTCPConnection(getConfig());
        try {
            connection.connect();
            if (connection.isConnected()) {
                System.out.println("\nConectando al server...\n");
            }
            connection.login(username, password);

            if (connection.isAuthenticated()) {
                System.out.println("\nAutenticación correcta\n");
            } else {
                System.out.println("\nOcurrió un error en la autenticación\n");
            }
        } catch (SmackException | IOException | XMPPException | InterruptedException e) {
            e.printStackTrace();
        }

        return connection;
        
    }

    public void Register(String new_username, String new_password) throws IOException {
        try {
            SmackConfiguration.DEBUG = true;
            AbstractXMPPConnection connection = new XMPPTCPConnection(getConfig());
            connection.connect();

            AccountManager accountManager = AccountManager.getInstance(connection);
            accountManager.sensitiveOperationOverInsecureConnection(true);
            accountManager.createAccount(Localpart.from(new_username), new_password);

            System.out.println("Cuenta creada exitosamente");
            connection.disconnect();
        } 
        catch (SmackException | XMPPException | InterruptedException | IOException e) {
            e.printStackTrace();
        }
    }

    public void DeleteAccount(AbstractXMPPConnection connection) throws InterruptedException {
        try {
            AccountManager accountManager = AccountManager.getInstance(connection);
            accountManager.deleteAccount();
            System.out.println("\nCuenta eliminada exitosamente\n");
        } catch (XMPPException | SmackException e) {
            System.out.println("\nSe obtuvo un error al intentar borrar la cuenta: " + e.getMessage());
        }
    }

    public void Logout(AbstractXMPPConnection connection) {
        connection.disconnect();
        System.out.println("\nSesión cerrada exitosamente\n");
    }
    
    
    public void showProfile(AbstractXMPPConnection connection) {
        String username = connection.getUser().toString();
        Roster roster = Roster.getInstanceFor(connection);
        Presence presence = roster.getPresence(connection.getUser().asEntityBareJidIfPossible());
    
        System.out.println("\nMi perfil: ");
        System.out.println("Usuario: " + username);
        System.out.println("Estado: " + presence.getStatus());
        System.out.println("Modo: " + presence.getMode());
    }

    public Presence getPresenceByOption(int option) {
        switch (option) {
            case 1:
                return new Presence(Presence.Type.available);
            case 2:
                return new Presence(Presence.Type.available, "Estoy ausente", 1, Presence.Mode.away);
            case 3:
                return new Presence(Presence.Type.available, "No molestar", 1, Presence.Mode.dnd);
            default:
                return new Presence(Presence.Type.available);
        }
    }

}
