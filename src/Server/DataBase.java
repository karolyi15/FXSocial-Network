package Server;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;

public final class DataBase {

    //********************************************************************************************************//
    //********************************************* CLASS FIELDS *********************************************//

    private static DataBase dataBase;
    private JSONObject data;

    //********************************************************************************************************//
    //******************************************** CLASS METHODS *********************************************//

    //Constructor
    private DataBase(){

        this.load();
    }

    public static DataBase getInstance(){

        if(dataBase == null){

            dataBase = new DataBase();
        }

        return dataBase;
    }

    //Requests
    public synchronized JSONObject query(String category, String object){

        try{

            JSONObject jsonCategory = (JSONObject) this.data.get(category);
            JSONObject jsonObject = (JSONObject) jsonCategory.get(object);

            return jsonObject;

        }catch (Exception e){

            return null;
        }
    }

    public synchronized boolean add(String category, String object, JSONObject jsonObject){

        try {

            JSONObject jsonCategory = (JSONObject) this.data.get(category);

            if(jsonCategory.containsKey(object)){

                return false;

            }else{

                jsonCategory.put(object,jsonObject);
                this.update();
                return true;
            }

        }catch (Exception e){

            e.printStackTrace();
            return false;
        }
    }

    public synchronized boolean delete(String category, String object){

        try{

            JSONObject jsonCategory = (JSONObject) this.data.get(category);

            if(jsonCategory.containsKey(object)){

                jsonCategory.remove(object);
                this.update();
                return true;

            }else{

                return  false;
            }

        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    //Management

    private void load(){

        JSONParser parser = new JSONParser();

        try(Reader reader = new FileReader("src/Server/DataBase.json")){

            this.data = (JSONObject) parser.parse(reader);

        }catch (ParseException e){

            e.printStackTrace();

        }catch (IOException e){

            e.printStackTrace();
        }
    }

    public synchronized void update(){

        try(FileWriter writer = new FileWriter("src/Server/DataBase.json")){

            writer.write(this.data.toJSONString());

        }catch (IOException e){

            e.printStackTrace();
        }
    }
}
