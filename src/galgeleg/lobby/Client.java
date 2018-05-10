package galgeleg.lobby;

import galgeleg.logik.ClientInterface;
import galgeleg.logik.GalgeInterface;
import galgeleg.logik.LobbyInterface;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Scanner;

public class Client extends UnicastRemoteObject implements Runnable, Remote, ClientInterface {

    private final LobbyInterface chatServer;
    private String name;

    public Client(String name, LobbyInterface chatServer) throws RemoteException {
        this.name = name;
        this.chatServer = chatServer;
        this.chatServer.registrer(name, this);
        this.chatServer.sendToAll("Just Connected", this.name);

        System.out.println("\tWelcome " + name + "." +
                "\nHere you can write to fellow players or " +
                "\ntype \".Queue\" in order to play against then!");
    }

    public void run() {
        Scanner scanner = new Scanner(System.in);

        for ( ; ; ) {
            String message = scanner.nextLine();

            switch (message) {
                case ".Queue":
                    try {
                        chatServer.joinGameQueue(name);
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                    break;
                case "ExitConsole":
                    try {
                        chatServer.disconnectClient(name);
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                    System.exit(1);
                    break;

                default:
                    try {
                        chatServer.sendToAll(message, name);
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
            }
        }
    }

    public void getMessage(String message) {
        System.out.println(message);
    }

    public void startGame(GalgeInterface galgeInterface) {
        System.out.println("Game starting!");
        Game game = new Game(galgeInterface);
        game.mainGame();
    }
}