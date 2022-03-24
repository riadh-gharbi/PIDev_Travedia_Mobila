/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.Travedia.myapp;

import entities.Paiement;
import com.codename1.io.CharArrayReader;
import com.codename1.io.ConnectionRequest;
import com.codename1.io.JSONParser;
import com.codename1.io.NetworkEvent;
import com.codename1.io.NetworkManager;
import com.codename1.l10n.ParseException;
import com.codename1.l10n.SimpleDateFormat;
import com.codename1.ui.Button;
import com.codename1.ui.Form;
import com.codename1.ui.Label;
import com.codename1.ui.TextField;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.layouts.BoxLayout;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import com.codename1.processing.Result;

/**
 *
 * @author riadh
 */
public class PaiementService {
     ConnectionRequest con;
    
    private boolean resultat;
    
    public ArrayList<Paiement> Paiements;
    public static final String SKU_WORLD = "com.codename1.world";
    public static PaiementService instance = null;
    
    
    //Initialiser la ConnectionRequest dans le constructeur
    public PaiementService(){
        con = new ConnectionRequest();
    }
    
    
    //Effectuer le design pattern SINGLETON 
    public static PaiementService getInstance()
    {
        if (instance ==null)
        {
            instance= new PaiementService();
        }
        
        return instance;
    }
    
  
    public ArrayList<Paiement> parse(String jsonTxt) throws ParseException {
        try{
        Paiements =new ArrayList<>();
        
        JSONParser parser = new JSONParser();
        
        Map<String,Object> PaiementsJSON;
            PaiementsJSON = parser.parseJSON(new CharArrayReader(jsonTxt.toCharArray()));
        List<Map<String,Object>> listePay;
            listePay = (List<Map<String,Object>>)PaiementsJSON.get("root");
        
        
        for(Map<String, Object> item: listePay) {
            Paiement p = new Paiement();
            Result result = Result.fromContent(item);
            //Get id from json response and set it in local device
            float id= Float.parseFloat(item.get("id").toString());
            p.setId((int)id);
            //System.out.println(item.get("client").toString());
            float clientID= Float.parseFloat(result.getAsString("client/id"));
            //System.out.println("ID DU CLIENT" + clientID);
            p.setClientId((int)clientID);
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            String dateCreation = item.get("dateCreation").toString().substring(0, 10);
            p.setDate_creation(sdf.parse(dateCreation));
            
          
                 //System.out.println(p.getDate_creation());
                try{
                String datePaiement= item.get("datePaiement").toString().substring(0, 10);
                p.setDate_paiement(sdf.parse(datePaiement));}
                catch(NullPointerException ex)
                {
                  p.setDate_paiement(null);
                }
          
            float ownerID = Float.parseFloat(result.getAsString("owner/id"));
            p.setOwnerId((int)ownerID);
            float planningID = Float.parseFloat(result.getAsString("planning/id"));
            p.setPlanningId((int)planningID);
            float prix = Float.parseFloat(item.get("prix").toString());
            p.setPrix(prix);
        
            p.setSessionID(item.get("sessionID").toString());
            p.setStatut(item.get("statut").toString());
            p.setType_paiement(item.get("typePaiement").toString());
            
            //if (item.get("utilisateur_id")==  null  ){!
            //r.setUser_id((int)(item.get("utilistaeur_id")));}
            Paiements.add(p);
        //}
        }
        }
        catch(IOException | NumberFormatException  | NullPointerException nEx)
        {
            
            nEx.printStackTrace();
            System.out.println("Exception Parse Function Paiement");
        }
        return Paiements;
    
    }
    
    
     public ArrayList<Paiement> getAllPaiement(){
         String url = "http://127.0.0.1:8000/afficherPaiements";
        con.setUrl(url);
        con.setPost(false);
        con.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                try {
                    Paiements = parse(new String(con.getResponseData(),"utf-8"));
                    con.removeResponseListener(this);
                } catch (IOException | NullPointerException | ParseException ex) {
                    System.out.println("Exception Get All Paiement");
                }
            }
        });
            NetworkManager.getInstance().addToQueueAndWait(con);
        return Paiements;
    }
     
     public boolean ajouterPaiement(Paiement p)
     {
        // String url = "http://127.0.0.1:8000" + "/ajouterPaiement?&contenu="+r.getContenu()+"&utilisateurID="+r.getUser_id()+"&etatReclamation="+r.getEtat_reclamation();
         String url = "http://127.0.0.1:8000" + "/ajouterPaiement";
         
         con.setUrl(url); //Insert url in connection request
         con.setPost(false);
         con.setFailSilently(true);
          SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
          
         con.addArgument("clientID", p.getClientId()+"");
         con.addArgument("ownerID", p.getOwnerId()+"");
         con.addArgument("dateCreation",sdf.format(p.getDate_creation()));
         con.addArgument("datePaiement", sdf.format(p.getDate_paiement()));
         con.addArgument("planningID", p.getPlanningId()+"");
         con.addArgument("prix",p.getPrix()+"");
         con.addArgument("sessionID", p.getSessionID());
         con.addArgument("statut", p.getStatut());
         con.addArgument("typePaiement", p.getType_paiement());
         
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
       public boolean modifierPaiement(Paiement p)
     {
         //String url = "http://127.0.0.1:8000" + "/modifierReclamation?id="+r.getId()+"&contenu="+r.getContenu()+"&utilisateurID="+r.getUser_id()+"&etatReclamation="+r.getEtat_reclamation();
         String url = "http://127.0.0.1:8000" + "/modifierPaiement";
         
         con.setUrl(url); //Insert url in connection request
         con.setPost(false);
         SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
         con.addArgument("id", p.getId()+"");
         con.addArgument("clientID", p.getClientId()+"");
         con.addArgument("ownerID", p.getOwnerId()+"");
         con.addArgument("dateCreation",sdf.format(p.getDate_creation()));
         con.addArgument("datePaiement", sdf.format(p.getDate_paiement()));
         con.addArgument("planningID", p.getPlanningId()+"");
         con.addArgument("prix",p.getPrix()+"");
         con.addArgument("sessionID", p.getSessionID());
         con.addArgument("statut", p.getStatut());
         con.addArgument("typePaiement", p.getType_paiement());
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
     public boolean  Delete(int idPaiement){
       String url = "http://127.0.0.1:8000" + "/deletePaiement?id=" +idPaiement;

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
