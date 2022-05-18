/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.Travedia.myapp;

import entities.Reclamation;
import entities.ReclamationReponse;
import com.codename1.io.CharArrayReader;
import com.codename1.io.ConnectionRequest;
import com.codename1.io.JSONParser;
import com.codename1.io.NetworkEvent;
import com.codename1.io.NetworkManager;
import com.codename1.processing.Result;
import com.codename1.ui.Button;
import com.codename1.ui.Form;
import com.codename1.ui.Label;
import com.codename1.ui.TextField;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.layouts.BoxLayout;
import java.io.IOException;
import java.io.StringReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


import java.util.Map;




/**
 *
 * @author riadh
 */
public class ReclamationService {
    ConnectionRequest con;
    Label titleLabel;
    TextField recContenu;
    Button envoyerBtn;
    Form afficherRecListe;
    private boolean resultat;
    //Make this into a map of reclamation and the responses of each one
    public ArrayList<Reclamation> Reclamations;
    public HashMap<Reclamation,ReclamationReponse> ReclamationMap;
    
    public static ReclamationService instance = null;
    
    
    //Initialiser la ConnectionRequest dans le constructeur
    public ReclamationService(){
        con = new ConnectionRequest();
    }
    
    
    //Effectuer le design pattern SINGLETON 
    public static ReclamationService getInstance()
    {
        if (instance ==null)
        {
            instance= new ReclamationService();
        }
        
        return instance;
    }
    
   
    //public ArrayList<Reclamation> parse(String jsonTxt) {
    public HashMap<Reclamation,ReclamationReponse> parse(String jsonTxt) {
        try{
        Reclamations =new ArrayList<>();
        ReclamationMap = new HashMap<>();
        JSONParser parser = new JSONParser();
        
        Map<String,Object> ReclamationsJSON;
            ReclamationsJSON = parser.parseJSON(new CharArrayReader(jsonTxt.toCharArray()));
        List<Map<String,Object>> listeRec;
            listeRec = (List<Map<String,Object>>)ReclamationsJSON.get("root");
        
        
        for(Map<String, Object> item: listeRec) {
            Reclamation r = new Reclamation();
            ReclamationReponse rep = null;
            Result result = Result.fromContent(item);
            //Get id from json response and set it in local device
            float id= Float.parseFloat(item.get("id").toString());
            r.setId((int)id);
            r.setContenu(item.get("contenu").toString());
            r.setSujet(item.get("sujet").toString());
            r.setEtat_reclamation(item.get("etatReclamation").toString());
            //if (item.get("utilisateur_id")==  null  ){!
            r.setUser_id(result.getAsInteger("utilisateur/id"));
            if(item.get("reclamationRep") == null)
            {
                ReclamationMap.put(r, rep);
            }else
            {
               Map<String,Object> reponseMap= (Map<String,Object>)item.get("reclamationRep");
               rep=new ReclamationReponse();
               rep.setContenu(reponseMap.get("contenu").toString());
               rep.setReclamationId(r.getId());
               ReclamationMap.put(r, rep);
            }
            
            Reclamations.add(r);
        }
        }
        catch(IOException | NumberFormatException ex)
        {
            ex.printStackTrace();
            System.out.println("aaa");
        }
        //return Reclamations;
        return ReclamationMap;
    }
    
     public HashMap<Reclamation,ReclamationReponse> getAllReclamation(){   
     //public ArrayList<Reclamation> getAllReclamation(){
         String url = "http://127.0.0.1:8000/afficherReclamations";
        con.setUrl(url);
        con.setPost(false);
        con.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                try {
                    ReclamationMap = parse(new String(con.getResponseData(),"utf-8"));
                    con.removeResponseListener(this);
                } catch (IOException ex) {
                    
                }
            }
        });
            NetworkManager.getInstance().addToQueueAndWait(con);
        return ReclamationMap;
    }
     
    
     
     public boolean ajouterReclamation(Reclamation r)
     {
         String url = "http://127.0.0.1:8000" + "/ajouterReclamation?&sujet="+r.getSujet()+"&contenu="+r.getContenu()+"&utilisateurID="+r.getUser_id()+"&etatReclamation="+r.getEtat_reclamation();
         //String url = "http://127.0.0.1:8000" + "/ajouterReclamation";
         
         con.setUrl(url); //Insert url in connection request
         con.setPost(false);
         con.setFailSilently(true);
         //con.addArgument("contenu", r.getContenu());
         //con.addArgument("utilisateur", r.getUser_id()+"");
         //con.addArgument("etatReclamation", r.getEtat_reclamation());
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
     
     public boolean ajouterReclamationReponse(ReclamationReponse rep)
     {
         String url = "http://127.0.0.1:8000" + "/ajouterReclamationReponse?&contenu="+rep.getContenu()+"&reclamationId="+rep.getReclamationId();
      
         
         con.setUrl(url); //Insert url in connection request
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
       public boolean modifierReclamation(Reclamation r)
     {
         String url = "http://127.0.0.1:8000" + "/modifierReclamation?id="+r.getId()+"&contenu="+r.getContenu()+"&utilisateurID="+r.getUser_id()+"&etatReclamation="+r.getEtat_reclamation();
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
        public boolean modifierReclamationResponse(ReclamationReponse rep)
     {
         String url = "http://127.0.0.1:8000" + "/modifierReclamationReponse?id="+rep.getId()+"&contenu="+rep.getContenu();
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
     public boolean  Delete(int idReclamation){
       String url = "http://127.0.0.1:8000" + "/deleteReclamation?id=" +idReclamation;

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
      public boolean  DeleteReponse(int idReclamationRep){
       String url = "http://127.0.0.1:8000" + "/deleteReclamationRep?id=" +idReclamationRep;

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
