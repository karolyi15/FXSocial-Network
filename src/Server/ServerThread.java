package Server;

import java.net.Socket;

public abstract class ServerThread extends Thread{

    //********************************************************************************************************//
    //********************************************* CLASS FIELDS *********************************************//

    //Server Connection
    protected iServer server;
    protected Socket socket;

    //********************************************************************************************************//
    //******************************************** CLASS METHODS *********************************************//

    //Constructor
    public  ServerThread(iServer server, Socket socket){

        this.server = server;
        this.socket = socket;

    }

    public abstract void run();
    public abstract void terminate();
}
