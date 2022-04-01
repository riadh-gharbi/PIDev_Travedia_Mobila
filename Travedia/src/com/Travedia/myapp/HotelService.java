/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.Travedia.myapp;
import com.mycompany.services.utils.Statics;
import com.codename1.io.CharArrayReader;
import com.codename1.io.ConnectionRequest;
import com.codename1.io.JSONParser;
import com.codename1.io.NetworkEvent;
import com.codename1.io.NetworkManager;
import com.codename1.ui.events.ActionListener;
import entities.Hotel;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Razer
 */
public class HotelService {
    private boolean resultat;
   public static HotelService instance=null;
   public ArrayList<Hotel> Hotels; 
    private ConnectionRequest req;
    public static HotelService getInstance (){
        if (instance == null)
            instance = new HotelService();
        return instance;
    }
    
    public HotelService(){
        req=new ConnectionRequest ();
    }
    
    //ajout
    public boolean ajouterHotel(Hotel h){
      String url =Statics.BASE_URL+"/ajouterHotel?&Nom="+h.getNom()+"&adresse="+h.getAdresse()+"&Email="+h.getEmail()+"&NumTel="+h.getNumTel();
      req.setUrl(url);
      req.setFailSilently(true);
      req.addResponseListener(new ActionListener<NetworkEvent>() {
             @Override
             public void actionPerformed(NetworkEvent evt) {
               
                resultat = req.getResponseCode() == 200;
               
                 
                req.removeResponseListener(this);
                
             }
         });    
      NetworkManager.getInstance().addToQueueAndWait(req);
      return resultat;
      
    }
    
     //var
    public ArrayList<Hotel> parse(String jsonTxt) {
        try{
        Hotels =new ArrayList<>();
        
        JSONParser parser = new JSONParser();
        
           // System.out.println(jsonTxt);
        Map<String,Object> HotelsJSON= parser.parseJSON(new CharArrayReader(jsonTxt.toCharArray()));
        List<Map<String,Object>> listeHo;
            listeHo = (List<Map<String,Object>>)HotelsJSON.get("root");
            //System.out.println("listHoMap: "+listeHo.toString());
        
        for(Map<String,Object> item: listeHo) {
            //System.out.println("hello Hotel ");
              //          System.out.println("hello Hotel id " +item.get("id").toString() );

            Hotel ho = new Hotel();
            
            
            float id= Float.parseFloat(item.get("id").toString());
            
            ho.setId((int)id);
            ho.setNom(item.get("Nom").toString());
            ho.setAdresse(item.get("adresse").toString());
            ho.setEmail(item.get("Email").toString());
            System.out.println("EMAILLLLLLL???????");
            float numTel = Float.parseFloat(item.get("NumTel").toString());
            ho.setNumTel((int)numTel);
           
            System.out.println("hotellll"+ho.toString());
            
            Hotels.add(ho);
        }
        }
        catch(IOException | NumberFormatException ex)
        {
            ex.printStackTrace();
            System.out.println("aaa");
        }
        return Hotels;
    
    }
    
    //affichage
    public ArrayList<Hotel>affichageHotel(){
        ArrayList<Hotel> result = new ArrayList<> ();
        String url = Statics.BASE_URL+"/afficherHotel";
        req.setUrl(url);
        req.setPost(false);
        req.addResponseListener(new ActionListener<NetworkEvent>(){
            @Override
            public void actionPerformed(NetworkEvent evt) {
                try{
                    Hotels = parse(new String(req.getResponseData(),"utf-8"));
                    req.removeResponseListener(this);
                }catch (Exception ex){
                
                }
            }
        
        });
        
    NetworkManager.getInstance().addToQueueAndWait(req);
    return Hotels;
    }
    
    public boolean modifierHotel(Hotel ho)
     {
        String url =Statics.BASE_URL+"/modifierHotel?id="+ho.getId()+"&nom="+ho.getNom()+"&adresse="+ho.getAdresse()+"&email="+ho.getEmail()+"&numTel="+ho.getNumTel();
        req.setUrl(url);
        req.addResponseListener(new ActionListener<NetworkEvent>() {
             @Override
             public void actionPerformed(NetworkEvent evt) {
                 resultat = req.getResponseCode() == 200;
                 req.removeResponseListener(this);
             }
         });
         NetworkManager.getInstance().addToQueueAndWait(req);
         return resultat;
     }
    
    public boolean  SupprimerHotel(int idHo){
       String url = "http://127.0.0.1:8000" + "/deleteHotel?id=" +idHo;

        req.setUrl(url);
        req.setPost(false);
        req.setFailSilently(true);
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                 resultat = req.getResponseCode() == 200;
                req.removeResponseListener(this);
            }

        });
        NetworkManager.getInstance().addToQueueAndWait(req);
        return resultat;
      
      
      }
    
}
