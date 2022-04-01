/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.Travedia.myapp;

import com.codename1.io.CharArrayReader;
import com.codename1.io.ConnectionRequest;
import com.codename1.io.JSONParser;
import com.codename1.io.MultipartRequest;
import com.codename1.io.NetworkEvent;
import com.codename1.io.NetworkManager;
import com.codename1.l10n.SimpleDateFormat;
import com.codename1.ui.events.ActionListener;
import entities.Evenement;
import com.mycompany.services.utils.Statics;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 *
 * @author user
 */
public class EvenementServices {
    private boolean resultat;
   public static EvenementServices instance=null;
   public ArrayList<Evenement> Evenements; 
    private MultipartRequest req;
    public static EvenementServices getInstance (){
        if (instance == null)
            instance = new EvenementServices();
        return instance;
    }
    
    public EvenementServices(){
        req=new MultipartRequest ();
    }
    
    //ajout
    public boolean ajoutEvenement(Evenement evenement){
      String url =Statics.BASE_URL+"/addev?&nom="+evenement.getNom()+"&description="+evenement.getDescription()+"&datedeb="+evenement.getDatedeb()+"&datefin="+evenement.getDatefin()+"&categorie="+evenement.getCategorie();
       req.setPost(true);
      
      req.setUrl(url);
      req.setFailSilently(true);
      if(!evenement.getImage().isEmpty())
        {
            try {
                        System.out.println("img field data " + evenement.getImage());
                        req.addData("image", evenement.getImage(), "image/jpeg");
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
      NetworkManager.getInstance().addToQueueAndWait(req);
      return resultat;
      
    
    }
    
     //var
    public ArrayList<Evenement> parse(String jsonTxt) {
        try{
        Evenements =new ArrayList<>();
        
        JSONParser parser = new JSONParser();
        
            System.out.println(jsonTxt);
        Map<String,Object> EvenementsJSON;
            EvenementsJSON = parser.parseJSON(new CharArrayReader(jsonTxt.toCharArray()));
        List<Map<String,Object>> listeRec;
            listeRec = (List<Map<String,Object>>)EvenementsJSON.get("root");
        
        
        for(Map<String, Object> item: listeRec) {
            System.out.println("hello evenement ");
                        System.out.println("hello evenement id " +item.get("id").toString() );

            Evenement ev = new Evenement();
            
            //Get id from json response and set it in local device
            float id= Float.parseFloat(item.get("id").toString());
            ev.setId((int)id);
            ev.setNom(item.get("nom").toString());
            ev.setImage(item.get("image").toString());
            ev.setDescription(item.get("description").toString());
            
            //date1
            ev.setDatedeb(item.get("datedeb").toString());
            //date2
                        ev.setDatefin(item.get("datefin").toString());

            /*String DateConverter2 = item.get("datefin").toString().substring(item.get("datefin").toString().indexOf("timestamp")+ 10, item.get("date").toString().lastIndexOf("}"));
            Date currentTime2 = new Date (Double.valueOf(DateConverter2).longValue() * 1000);
            SimpleDateFormat formatter2 = new SimpleDateFormat("yyyy-MM-dd");
            String dateString2 = formatter2.format(currentTime2);
            ev.setDatefin(dateString2);*/
            //ev.setDatedeb((Date) item.get("datedeb"));
            //ev.setDatefin((Date) item.get("datefin"));
            ev.setCategorie(item.get("categorie").toString());
            //if (item.get("utilisateur_id")==  null  ){!
            //r.setUser_id((int)(item.get("utilistaeur_id")));}
            Evenements.add(ev);
        }
        }
        catch(IOException | NumberFormatException ex)
        {
            ex.printStackTrace();
            System.out.println("aaa");
        }
        return Evenements;
    
    }
    
    //affichage
    public ArrayList<Evenement>affichageEvenement(){
        req = new MultipartRequest();
        ArrayList<Evenement> result = new ArrayList<> ();
        String url = Statics.BASE_URL+"/afficherEvenements";
        req.setUrl(url);
        req.addResponseListener(new ActionListener<NetworkEvent>(){
            @Override
            public void actionPerformed(NetworkEvent evt) {
                try{
                    Evenements = parse(new String(req.getResponseData(),"utf-8"));
                    req.removeResponseListener(this);
                }catch (Exception ex){
                
                }
            }
        
        });
        
    NetworkManager.getInstance().addToQueueAndWait(req);
    return Evenements;
    }
    
    public boolean modifierEvenement(Evenement evenement)
     {
        String url =Statics.BASE_URL+"/modifierEvenements?id="+evenement.getId()+"&nom="+evenement.getNom()+"&image="+evenement.getImage()+"&description="+evenement.getDescription()+"&datedeb="+evenement.getDatedeb()+"&datefin="+evenement.getDatefin()+"&categorie="+evenement.getCategorie();
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
    
    public boolean  SupprimerEvenement(int idEv){
       String url = "http://127.0.0.1:8000" + "/deleteEvenements?id=" +idEv;

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
