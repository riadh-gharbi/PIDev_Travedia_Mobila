/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.Travedia.myapp;
import com.codename1.io.CharArrayReader;
import com.codename1.io.ConnectionRequest;
import com.codename1.io.JSONParser;
import com.codename1.io.NetworkEvent;
import com.codename1.io.NetworkManager;
import com.codename1.ui.events.ActionListener;
import entities.Region;
import java.io.IOException;
import com.codename1.io.MultipartRequest;

import com.codename1.io.CharArrayReader;
import com.codename1.io.ConnectionRequest;
import com.codename1.io.JSONParser;
import com.codename1.io.MultipartRequest;
import com.codename1.io.NetworkEvent;
import com.codename1.io.NetworkManager;
import com.codename1.ui.events.ActionListener;
import com.mycompany.services.utils.Statics;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
/**
 *
 * @author user
 */
public class serviceRegion {
    private boolean resultat;
   public static serviceRegion instance=null;
   public ArrayList<Region> Regions; 
   private ConnectionRequest con;
    private MultipartRequest req;
    public static serviceRegion getInstance (){
        if (instance == null)
            instance = new serviceRegion();
        return instance;
    }
    
    public serviceRegion(){
        con=new ConnectionRequest ();
         req=new MultipartRequest ();
    }
    //ajout
    public boolean ajoutRegion(Region r){
         String url = "http://127.0.0.1:8000" + "/ajouterRegionn?&nom="+r.getNom();
      con.setUrl(url);
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
    //ajout ghada
     public boolean ajoutR(Region r){
      String url ="http://127.0.0.1:8000" + "/ajouterRegionn?&nom="+r.getNom()+"&image="+r.getImage();
      
      req.setPost(true);
      
      req.setUrl(url);
      req.setFailSilently(true);
      if(!r.getImage().isEmpty())
        {
            try {
                        System.out.println("img field data " + r.getImage());
                        req.addData("image", r.getImage(), "image/jpeg");
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
                                                     System.out.println("again");

             }
         });    
      NetworkManager.getInstance().addToQueueAndWait(req);
                                                 System.out.println("annnd finish");

      return resultat;

    }
    
    
        //var
    public ArrayList<Region> parse(String jsonTxt) {
        try{
        Regions =new ArrayList<>();
        
        JSONParser parser = new JSONParser();
        
        Map<String,Object> RegionsJSON;
            RegionsJSON = parser.parseJSON(new CharArrayReader(jsonTxt.toCharArray()));
        List<Map<String,Object>> listeRec;
            listeRec = (List<Map<String,Object>>)RegionsJSON.get("root");
        
        
        for(Map<String, Object> item: listeRec) {
            Region reg = new Region();
            
            //Get id from json response and set it in local device
            float id= Float.parseFloat(item.get("id").toString());
            reg.setId((int)id);
            reg.setNom(item.get("nom").toString());
            reg.setImage(item.get("image").toString());
            //if (item.get("utilisateur_id")==  null  ){!
            //r.setUser_id((int)(item.get("utilistaeur_id")));}
            Regions.add(reg);
        }
        }
        catch(IOException | NumberFormatException ex)
        {
            ex.printStackTrace();
            System.out.println("aaa");
        }
        return Regions;
    
    }
    
    //affichage
    public ArrayList<Region>affichageRegion(){
        ArrayList<Region> result = new ArrayList<> ();
         String url = "http://127.0.0.1:8000" + "/afficherRegion";
        con.setUrl(url);
        con.addResponseListener(new ActionListener<NetworkEvent>(){
            @Override
            public void actionPerformed(NetworkEvent evt) {
                try{
                    Regions = parse(new String(con.getResponseData(),"utf-8"));
                    con.removeResponseListener(this);
                }catch (Exception ex){
                
                }
            }
        
        });
        
    NetworkManager.getInstance().addToQueueAndWait(con);
    return Regions;
    }
     public ArrayList<Region> affichregionid(String id) {
        String url = "http://127.0.0.1:8000" + "/regionid/"+id;
        con.setUrl(url);
        con.setPost(false);
        System.out.println(url);
        con.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                Regions = parse(new String(con.getResponseData()));
                System.out.println(con.getResponseData());
                System.out.println(Regions);
                con.removeResponseListener(this);
                System.out.println(Regions);
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(con);
        System.out.println(Regions);
        return Regions;
    }
    
    
    //affichage
//    public ArrayList<Categorie>affichageCategorie(){
//        ArrayList<Categorie> result = new ArrayList<> ();
//        String url = Statics.BASE_URL+"/afficherCategories";
//        req.setUrl(url);
//        req.addResponseListener(new ActionListener<NetworkEvent>(){
//            @Override
//            public void actionPerformed(NetworkEvent evt) {
//                JSONParser jsonp;
//                jsonp = new JSONParser();
//                
//                try{
//                    Map<String,Object>mapCategorie = jsonp.parseJSON(new CharArrayReader (new String (req.getResponseData(),"utf-8").toCharArray()));
//                    List<Map<String,Object>> listOfMaps = (List<Map<String,Object>>) mapCategorie.get("root");
//                
//                    for(Map<String,Object> obj : listOfMaps){
//                        Categorie cat = new Categorie();
//                        float id = Float.parseFloat(obj.get("id").toString());
//                        String nom = obj.get("nom").toString();
//                        String image = obj.get("image").toString();
//                        
//                        result.add(cat);
//                    }
//                
//                }catch (Exception ex){
//                ex.printStackTrace();
//                }
//            }
//        
//        });
//        
//    NetworkManager.getInstance().addToQueueAndWait(req);
//    return result;
//    }
    
    public boolean modifierRegion(Region reg)
     {
        String url ="http://127.0.0.1:8000" +"/modifierregionn?id="+reg.getId()+"&nom="+reg.getNom();
        con.setUrl(url);
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
    
    public boolean  SupprimerRegion(int id){
       String url = "http://127.0.0.1:8000" + "/deleteRegions?id=" +id;

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
