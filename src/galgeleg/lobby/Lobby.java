package galgeleg.lobby;

import galgeleg.logik.LobbyInterface;

import java.rmi.Naming;
import java.util.Scanner;

public class Lobby {

    private LobbyInterface lobby;

    private Scanner scanner = new Scanner(System.in);

    public void Lobby () {
        try {
            lobby = (LobbyInterface) Naming.lookup("rmi://130.225.170.244:1076/galgeleg");
//            lobby = (LobbyInterface) Naming.lookup("rmi://localhost:1076/galgeleg");

            String tempUsername, tempPassword;

            do {
                System.out.println("[System] Enter username: ");
                tempUsername = scanner.nextLine();
                System.out.println("[System] Enter password: ");
                tempPassword = scanner.nextLine();

            } while(!login(tempUsername, tempPassword));

            System.out.println("\n[System] Client messenger is running");

        }catch (Exception e) {
            System.out.println("client exception: " + e);
            e.printStackTrace();
        }

    }

    private Boolean login(String username, String password) throws java.rmi.RemoteException {
        if (lobby.login(username, password)) {
            try {
                new Thread(new Client(username, lobby)).start();
            } catch (Exception e) {
                System.out.println(e);
            }
            return true;
        } else {
            System.err.println("Login failed. Try again. ");
        }
        return false;
    }
}
