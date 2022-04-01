/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycopany.services;
import com.codename1.io.CharArrayReader;
import com.codename1.io.ConnectionRequest;
import com.codename1.io.JSONParser;
import com.codename1.io.NetworkEvent;
import com.codename1.io.NetworkManager;
import com.codename1.ui.Button;
import com.codename1.ui.Form;
import com.codename1.ui.Label;
import com.codename1.ui.TextField;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.layouts.BoxLayout;
import com.mycompany.entites.Destination;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import com.codename1.io.CharArrayReader;
import com.codename1.io.ConnectionRequest;
import com.codename1.io.JSONParser;
import com.codename1.io.MultipartRequest;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;



import java.util.Map;
/**
 *
 * @author Ameni
 */
public class serviceDestination {
     ConnectionRequest con;
    Label titleLabel;
    TextField recContenu;
    Button envoyerBtn;
    Form afficherRecListe;
    private boolean resultat;
    private MultipartRequest req;
    public ArrayList<Destination> Destinations;
    
    public static serviceDestination instance = null;
    
    
    //Initialiser la ConnectionRequest dans le constructeur
    public serviceDestination(){
        con = new ConnectionRequest();
        req =new MultipartRequest ();
    }
    
    
    //Effectuer le design pattern SINGLETON 
    public static serviceDestination getInstance()
    {
        if (instance ==null)
        {
            instance= new serviceDestination();
        }
        
        return instance;
    }
 
    public ArrayList<Destination> parse(String jsonTxt) {
        try{
        Destinations =new ArrayList<>();
        
        JSONParser parser = new JSONParser();
        
        Map<String,Object> DestinationsJSON;
            DestinationsJSON = parser.parseJSON(new CharArrayReader(jsonTxt.toCharArray()));
        List<Map<String,Object>> listeRec;
            listeRec = (List<Map<String,Object>>)DestinationsJSON.get("root");
        
        
        for(Map<String, Object> item: listeRec) {
          Destination d = new Destination();
            
            //Get id from json response and set it in local device
            float id= Float.parseFloat(item.get("id").toString());
            d.setId((int)id);
            d.setNom(item.get("nom").toString());
            d.setDescription(item.get("description").toString());
            d.setImage(item.get("image").toString());

            //if (item.get("utilisateur_id")==  null  ){!
            //r.setUser_id((int)(item.get("utilistaeur_id")));}
            Destinations.add(d);
        }
        }
        catch(IOException | NumberFormatException ex)
        {
            ex.printStackTrace();
            System.out.println("aaa");
        }
        return Destinations;
    
    }
    
    
     public ArrayList<Destination> getAllDestination(){
         String url = "http://127.0.0.1:8000/afficherDestinations";
        con.setUrl(url);
        con.setPost(false);
        con.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                try {
                    Destinations = parse(new String(con.getResponseData(),"utf-8"));
                    con.removeResponseListener(this);
                } catch (IOException ex) {
                    
                }
            }
        });
            NetworkManager.getInstance().addToQueueAndWait(con);
        return Destinations;
    }
     
     public boolean ajouterDestination(Destination d)
     {
         String url = "http://127.0.0.1:8000" + "/ajouterDestination?&nom="+d.getNom()+"&description="+d.getDescription()+"&region=" + d.getRegion();
         
          req.setUrl(url);
      req.setFailSilently(true);
      if(!d.getImage().isEmpty())
        {
            try {
                        System.out.println("img field data " + d.getImage());
                        req.addData("image", d.getImage(), "");
            } catch (IOException e) {
                System.out.println("oogabooga");
                            e.printStackTrace();

                //file not found
            }
        }
                      System.out.println("added the img");

      req.addResponseListener(new ActionListener<NetworkEvent>() {
             @Override
             public void actionPerformed(NetworkEvent evt) {
                                     System.out.println("action performed");

                resultat = req.getResponseCode() == 200;
               
                 
                req.removeResponseListener(this);
                
             }
         });    
      NetworkManager.getInstance().addToQueueAndWait(con);
      return resultat;
      
    
    }
         
         
       public boolean modifierDestination(Destination d)
     {
         String url = "http://127.0.0.1:8000" + "/modifierDestinaationj?id="+d.getId()+"&nom="+d.getNom()+"&description="+d.getDescription()+"&idreg=" + d.getId_region();
         con.setUrl(url); //Insert url in connection request
         
         con.setFailSilently(true);
         con.addResponseListener(new ActionListener<NetworkEvent>() {
             @Override
             public void actionPerformed(NetworkEvent evt) {
                 resultat = con.getResponseCode() == 200;
                 con.removeResponseListener(this);
             }
         });
         NetworkManager.getInstance().addToQueueAndWait(con);
         return resultat;
     }
     public boolean  Delete(int id){
       String url = "http://127.0.0.1:8000" + "/deleteDestination?id=" +id;

        con.setUrl(url);
        con.setPost(false);
        con.setFailSilently(true);
        con.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                 resultat = con.getResponseCode() == 200;
                con.removeResponseListener(this);
            }

        });
        NetworkManager.getInstance().addToQueueAndWait(con);
        return resultat;
      
      
      }

    
}
