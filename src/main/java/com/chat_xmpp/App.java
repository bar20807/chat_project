package com.chat_xmpp;

import org.jivesoftware.smack.AbstractXMPPConnection;
import org.jivesoftware.smack.ConnectionConfiguration;
import org.jivesoftware.smack.SmackException;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.tcp.XMPPTCPConnection;
import org.jivesoftware.smack.tcp.XMPPTCPConnectionConfiguration;
import org.jxmpp.stringprep.XmppStringprepException;
import java.io.IOException;
import java.util.Scanner;

public class App {
    public static void main(String[] args) throws InterruptedException, IOException {
        
        //Creamos un objeto scanner
        Scanner sc = new Scanner(System.in);
        //Pedimmos el usuario
        System.out.println("Introduce el usuario: ");
        String username = sc.nextLine();
        //Pedimos la contraseña
        System.out.println("Introduce la contraseña: ");
        String password = sc.nextLine();

        //Creamos el objeto de la cuenta
        Cuentas cuenta = new Cuentas();
        
        //Llamamos al método Login
        cuenta.Login(username, password);
    }
}