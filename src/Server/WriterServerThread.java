package Server;

import java.io.*;
import java.net.Socket;

public abstract class WriterServerThread extends ServerThread {

    //********************************************************************************************************//
    //********************************************* CLASS FIELDS *********************************************//

    //Client Communication
    protected PrintWriter writer;

    //Connection State
    protected boolean running;

    //********************************************************************************************************//
    //******************************************** CLASS METHODS *********************************************//

    //Constructor
    public WriterServerThread(iServer server, Socket socket){

        super(server, socket);

        this.running = false;

    }

    @Override
    public void run(){

        try {

            //Connection State
            this.running = true;

            //Read data from the client (read to byte array)
            InputStream input = this.socket.getInputStream();
            //Set byte array to string
            BufferedReader reader = new BufferedReader(new InputStreamReader(input));


            //Write data as a byte array
            OutputStream output = socket.getOutputStream();
            //Converts byte array to text format
            this.writer = new PrintWriter(output, true);

            String inputString;

            do {

                inputString = reader.readLine();
                this.parseInputString(inputString);

            }while (this.running);

            socket.close();

        }catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void terminate(){

        this.running = false;
    }

    //Client Communication
    protected abstract void parseInputString(String inputString);

    public void write(String output){

        this.writer.println(output);
    }

}
