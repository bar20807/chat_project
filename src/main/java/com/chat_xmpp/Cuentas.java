package com.chat_xmpp;

import java.io.IOException;

import org.jivesoftware.smack.AbstractXMPPConnection;
import org.jivesoftware.smack.ConnectionConfiguration;
import org.jivesoftware.smack.ConnectionConfiguration.SecurityMode;
import org.jivesoftware.smack.SmackConfiguration;
import org.jivesoftware.smack.SmackException;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.tcp.XMPPTCPConnection;
import org.jivesoftware.smack.tcp.XMPPTCPConnectionConfiguration;
import org.jivesoftware.smackx.iqregister.AccountManager;
import org.jxmpp.jid.DomainBareJid;
import org.jxmpp.jid.EntityBareJid;
import org.jxmpp.jid.impl.JidCreate;
import org.jxmpp.jid.parts.Localpart;
import org.jxmpp.stringprep.XmppStringprepException;

import io.github.cdimascio.dotenv.Dotenv;

public class Cuentas {
    public void Login (String username, String password) throws XmppStringprepException, InterruptedException{
        // Configuración de la conexión
        XMPPTCPConnectionConfiguration config = XMPPTCPConnectionConfiguration.builder()
                .setUsernameAndPassword(username, password)
                .setXmppDomain("alumchat.xyz")
                .setHost("alumchat.xyz")
                .setPort(5222) // Puerto por defecto para XMPP
                .setSecurityMode(ConnectionConfiguration.SecurityMode.disabled) // Desactivar SSL/TLS para este ejemplo
                .setCompressionEnabled(false)
                .build();

        AbstractXMPPConnection connection = new XMPPTCPConnection(config);

        try {
            // Conexión al servidor
            connection.connect();
            connection.login();

            // Si todo va bien, se mostrará este mensaje de conexión exitosa
            System.out.println("Conexión exitosa al servidor XMPP");

            // Realizar las operaciones adicionales aquí después de la conexión exitosa.

            // Cerrar la conexión cuando hayas terminado de utilizarla
            connection.disconnect();
        } catch (SmackException | IOException | XMPPException e) {
            // Si hay algún error en la conexión o autenticación, se mostrará el mensaje de error
            System.out.println("Error al conectar o autenticar: " + e.getMessage());
        }
    }

    public void Register(String new_username, String new_password) throws IOException {
        DomainBareJid xmppDomain = JidCreate.domainBareFrom("alumchat.xyz");
        try {
            SmackConfiguration.DEBUG = true;

            XMPPTCPConnectionConfiguration config = XMPPTCPConnectionConfiguration.builder()
                    .setUsernameAndPassword(new_username, new_password)
                    .setXmppDomain(xmppDomain)
                    .setHost("alumchat.xyz")
                    .setPort(5222)
                    .setSecurityMode(SecurityMode.disabled)
                    .build();

            AbstractXMPPConnection connection = new XMPPTCPConnection(config);
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

    public void DeleteAccount(String username, String password) throws XmppStringprepException, InterruptedException {
        DomainBareJid xmppDomain = JidCreate.domainBareFrom("alumchat.xyz");
        
        try {
            SmackConfiguration.DEBUG = true;
    
            XMPPTCPConnectionConfiguration config = XMPPTCPConnectionConfiguration.builder()
                    .setUsernameAndPassword(username, password)
                    .setXmppDomain(xmppDomain)
                    .setHost("alumchat.xyz")
                    .setPort(5222)
                    .setSecurityMode(SecurityMode.disabled)
                    .build();
    
            AbstractXMPPConnection connection = new XMPPTCPConnection(config);
            connection.connect();
            connection.login();
    
            AccountManager accountManager = AccountManager.getInstance(connection);
            accountManager.sensitiveOperationOverInsecureConnection(true);
            accountManager.deleteAccount();
    
            System.out.println("Cuenta eliminada exitosamente");
            connection.disconnect();
        } 
        catch (SmackException | XMPPException | InterruptedException | IOException e) {
            e.printStackTrace();
        }
    }

}
