package galgeleg.lobby;

import galgeleg.logik.LobbyInterface;

import java.rmi.Naming;
import java.util.Scanner;

public class Lobby {

    private LobbyInterface lobby;

    private Scanner scanner = new Scanner(System.in);

    public void Lobby () {
        try {
            lobby = (LobbyInterface) Naming.lookup("rmi://localhost:1076/galgeleg");

            System.out.println("[System] Enter username: ");
            String tempUsername = scanner.nextLine();
            System.out.println("[System] Enter password: ");
            String TempPassword = scanner.nextLine();

            while(!login(tempUsername, TempPassword));

            System.out.println("\n[System] Client messenger is running");

        }catch (Exception e) {
            System.out.println("client exception: " + e);
            e.printStackTrace();
        }
    }

    private Boolean login(String username, String password) throws java.rmi.RemoteException {
        if (lobby.login(username, password)) {
            new Thread(new Client(username, lobby)).start();
            return true;
        } else {
            System.err.println("Login failed. Try again. ");
        }
        return false;
    }
}
