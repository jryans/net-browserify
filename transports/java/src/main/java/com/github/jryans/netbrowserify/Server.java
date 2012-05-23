package com.github.jryans.netbrowserify;

import java.applet.Applet;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.security.AccessControlContext;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 *
 */
public class Server extends Applet {

    private ServerSocket serverSocket;
    private Socket socket;

    private ExecutorService exec;

    private Runnable runMe;

    private boolean doStartClient = false;

    private AccessControlContext context;

    public String getValue() {
        return "Bob";
    }

    public boolean startServer() {
        try {
            serverSocket.bind(new InetSocketAddress(9000));
        } catch (Exception e) {
            return false;
        }

        return true;
    }

    public void startClientTimer() {
        doStartClient = true;
    }

    public void startClientPriv() {
        AccessController.doPrivileged(new PrivilegedAction<Object>() {
            public Object run() {
                System.out.println("startClientPriv");
                try {
                    socket.connect(new InetSocketAddress("www.google.com", 80), 10000);
                    System.out.println("Client yay!");
                } catch (Exception ex) {
                    ex.printStackTrace();
                }

                return true;
            }
        });
    }

    public void startClientPrivContext() {
        AccessController.doPrivileged(new PrivilegedAction<Object>() {
            public Object run() {
                System.out.println("startClientPriv");
                try {
                    socket.connect(new InetSocketAddress("www.google.com", 80), 10000);
                    System.out.println("Client yay!");
                } catch (Exception ex) {
                    ex.printStackTrace();
                }

                return true;
            }
        }, context);
    }

    public boolean isServerBound() {
        return serverSocket.isBound();
    }

    public boolean isClientConnected() {
        return socket.isConnected();
    }

    @Override
    public void init() {
        try {
            serverSocket = new ServerSocket();
            socket = new Socket();
            exec = Executors.newSingleThreadExecutor();
        } catch (Exception e) {

        }

        new Timer().scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {

                if (!doStartClient) {
                    return;
                }

                System.out.println("startClientTimer");
                try {
                    socket.connect(new InetSocketAddress("www.google.com", 80), 10000);
                    System.out.println("Client yay!");
                } catch (Exception ex) {
                    ex.printStackTrace();
                }

                doStartClient = false;

            }
        }, 1000, 1000);

        runMe = new Runnable() {
            public void run() {
                System.out.println("startClientRunnable");
                try {
                    socket.connect(new InetSocketAddress("www.google.com", 80), 10000);
                    System.out.println("Client yay!");
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        };

        context = AccessController.getContext();

//        System.out.println("init");
//
//        try {
//            socket.connect(new InetSocketAddress("www.google.com", 80), 10000);
//            System.out.println("Client yay!");
//        } catch (Exception ex) {
//            ex.printStackTrace();
//        }
    }
}
