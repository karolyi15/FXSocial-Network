package Server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public abstract class MultiThreadedServer implements iServer{

    //********************************************************************************************************//
    //********************************************* CLASS FIELDS *********************************************//

    //Sever Socket
    private int port=9090;
    private ServerSocket serverSocket;

    //Server State
    private boolean running;

    //********************************************************************************************************//
    //******************************************** CLASS METHODS *********************************************//

    //Constructor
    public  MultiThreadedServer(){

        this.running = false;
    }

    public void start(){

        try {

            this.serverSocket = new ServerSocket(port);

            this.running = true;
            System.out.print("Server is listening in port: " + port+"\n");


            while (running) {

                //Starts listening for incoming client requests
                Socket socket = serverSocket.accept();

                //Update Active Connections
                System.out.println("*** New User Connected ***");

                //Create Connection Thread
                this.createServerThread(socket);

            }

            this.serverSocket.close();

        }catch(IOException e) {

            System.out.println("Error while attempting to connect user");
        }

    }

    protected abstract void createServerThread(Socket socket);

    public void terminate(){

        this.running = false;
    }
}
