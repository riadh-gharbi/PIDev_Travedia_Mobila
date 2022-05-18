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
import com.codename1.l10n.ParseException;
import com.codename1.l10n.SimpleDateFormat;
import com.codename1.processing.Result;
import com.codename1.ui.events.ActionListener;
import entities.Planning;
import com.mycompany.services.utils.Statics;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;



/**
 *
 * @author Razer
 */
public class PlanningService {
    private boolean resultat;
   public static PlanningService instance=null;
   public ArrayList<Planning> Plannings; 
    private ConnectionRequest req;
    public static PlanningService getInstance (){
        if (instance == null)
            instance = new PlanningService();
        return instance;
    }
    
    public PlanningService(){
        req=new ConnectionRequest ();
    }
    
    //ajout
    public boolean ajouterPlanning(Planning h){
      SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");  
      String url =Statics.BASE_URL+"/ajouterPlanning?&date_depart="+sdf.format(h.getDateDepart())+"&date_fin="+sdf.format(h.getDateFin())+"&utilisateur="+h.getVoyageurId()+"&description="+h.getDescription()+"&hotels="+h.getHotelId()+"&type_plan="+h.getTypePlan()+"&destinations="+h.getDestinationId()+"&evenements="+h.getEvenementId()+"&prix="+h.getPrix();
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
    public ArrayList<Planning> parse(String jsonTxt) {
        try{
        Plannings =new ArrayList<>();
        
        JSONParser parser = new JSONParser();
        
            //System.out.println(jsonTxt);
        Map<String,Object> HotelsJSON;
            HotelsJSON = parser.parseJSON(new CharArrayReader(jsonTxt.toCharArray()));
        List<Map<String,Object>> listePla;
            listePla = (List<Map<String,Object>>)HotelsJSON.get("root");
        
        
        for(Map<String, Object> item: listePla) {
            Result result = Result.fromContent(item);
            //System.out.println("hello Planning");
                        //System.out.println("hello Planning id " +item.get("id").toString() );

            Planning pla = new Planning();
            
            
            float id= Float.parseFloat(item.get("id").toString());
            pla.setId((int)id);
            System.out.println(id);
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            String dateDepart = result.getAsString("dateDepart/date").substring(0, 10);
            try {
                pla.setDateDepart(sdf.parse(dateDepart));
            } catch (ParseException ex) {
                System.out.println(ex.getMessage());
            }
            String dateFin = result.getAsString("dateFin/date").substring(0, 10);
            try {
                pla.setDateFin(sdf.parse(dateFin));
            } catch (ParseException ex) {
                System.out.println(ex.getMessage());
            }
            float prix= Float.parseFloat(item.get("prix").toString());
            pla.setPrix((int)prix);
            pla.setTypePlan(item.get("typePlan").toString());
            pla.setDescription(item.get("description").toString());  
            float voyageurId = Float.parseFloat(result.getAsString("voyageurId/id"));
            pla.setVoyageurId((int)voyageurId);           
            //float EvenementId = Float.parseFloat(result.getAsString("evenements/id"));
            //pla.setEvenementId((int)EvenementId);
            //float DestinationId = Float.parseFloat(result.getAsString("destinations/id"));
            //pla.setEvenementId((int)DestinationId);
           // float HotelId = Float.parseFloat(result.getAsString("hotels/id"));
           // pla.setHotelId((int)HotelId);
           
          
          
            Plannings.add(pla);
        }
        }
        catch(IOException | NumberFormatException  | NullPointerException ex)
        {
            
            System.out.println(ex.getMessage()+" " + ex.getCause());
        }
        return Plannings;
    
    }
    
    //affichage
    public ArrayList<Planning>affichagePlanning(){
        
        String url = Statics.BASE_URL+"/afficherPlanning";
        req.setUrl(url);
        req.setPost(false);
        req.addResponseListener(new ActionListener<NetworkEvent>(){
            @Override
            public void actionPerformed(NetworkEvent evt) {
                try{
                    Plannings = parse(new String(req.getResponseData(),"utf-8"));
                    req.removeResponseListener(this);
                }catch (Exception ex){
                    System.err.println(ex.getMessage());
                }
            }
        
        });
        
    NetworkManager.getInstance().addToQueueAndWait(req);
    return Plannings;
    }
    
    public boolean modifierHotel(Planning h)
     {  SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");  
      String url =Statics.BASE_URL+"/ajouterPlanning?id="+h.getId()+"&date_depart="+sdf.format(h.getDateDepart())+"&date_fin="+sdf.format(h.getDateFin())+"&utilisateur="+h.getVoyageurId()+"&description="+h.getDescription()+"&hotels="+h.getHotelId()+"&type_plan="+h.getTypePlan()+"&destinations="+h.getDestinationId()+"&evenements="+h.getEvenementId()+"&prix="+h.getPrix();
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
    
    public boolean  SupprimerPlanning(int idPla){
       String url = "http://127.0.0.1:8000" + "/deletePlanning?id=" +idPla;

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
