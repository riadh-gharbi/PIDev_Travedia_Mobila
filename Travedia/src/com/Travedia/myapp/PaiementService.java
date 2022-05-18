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

;
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
            String clientName = result.getAsString("client/nom") + " " + result.getAsString("client/prenom");
            String ownerName = result.getAsString("owner/nom") + " " + result.getAsString("owner/prenom");
            p.setClientName(clientName);
            p.setOwnerName(ownerName);
            //System.out.println("ID DU CLIENT" + clientID);
            p.setClientId((int)clientID);
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            String dateCreation = result.getAsString("dateCreation/date").substring(0, 10);
            //System.out.println(dateCreation);
            p.setDate_creation(sdf.parse(dateCreation));
            
          
                 //System.out.println(p.getDate_creation());
                 if ("null".equals(result.getAsString("datePaiement")))
                 {
                     System.out.println("parsing datepaiement null");
                 p.setDate_paiement(null);
                 }else{
                try{
                
                String datePaiement= result.getAsString("datePaiement/date").substring(0, 10);
                p.setDate_paiement(sdf.parse(datePaiement));}
                catch(NullPointerException | ParseException ex)
                {
                  p.setDate_paiement(null);
                }
                 }
            float ownerID = Float.parseFloat(result.getAsString("owner/id"));
            p.setOwnerId((int)ownerID);
            try{
            float planningID = Float.parseFloat(result.getAsString("planning/id"));
                System.out.println(planningID);
            p.setPlanningId((int)planningID);
            }catch(IllegalArgumentException  ex)
            {
            ex.printStackTrace();
            }
            float prix = Float.parseFloat(item.get("prix").toString());
            p.setPrix(prix);
            try{
            p.setSessionID(item.get("sessionID").toString());}catch(NullPointerException ex)
            {
                p.setSessionID(null);
            }
            p.setStatut(item.get("statut").toString());
            p.setType_paiement(item.get("typePaiement").toString());
            
            //if (item.get("utilisateur_id")==  null  ){!
            //r.setUser_id((int)(item.get("utilistaeur_id")));}
            Paiements.add(p);
            
        //}
        }
        }
        catch(IOException | NumberFormatException  | NullPointerException | ParseException nEx)
        {
            
            nEx.printStackTrace();
            System.out.println("Exception Parse Function Paiement " +nEx.getMessage() + " "+ nEx.getCause());
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
                    System.err.println("Exception Get All Paiement "+ex.getMessage());
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
        // con.addArgument("datePaiement", sdf.format(p.getDate_paiement()));
         con.addArgument("planningID", p.getPlanningId()+"");
         con.addArgument("prix",p.getPrix()+"");
        // con.addArgument("sessionID", p.getSessionID());
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
//         if (resultat)
//         {
//             SendMail();
//         }
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
     
//     
//     /**
//	   Outgoing Mail (SMTP) Server
//	   requires TLS or SSL: smtp.gmail.com (use authentication)
//	   Use Authentication: Yes
//	   Port for TLS/STARTTLS: 587
//	 */
//	public void SendMail() throws MessagingException {
//		final String fromEmail = "travediacontact@gmail.com"; //requires valid gmail id
//		final String password = "123solo456friendly"; // correct password for gmail id
//		final String toEmail = "riadh.gharbi@esprit.tn"; // Test mail; later replaced by user mail
//		
//		System.out.println("TLSEmail Start");
//		Properties props = new Properties();
//                props.put("mail.transport.protocol","smtp"); //SMTP PROTOCOL
//		props.put("mail.smtp.host", "smtp.gmail.com"); //SMTP Host
//		props.put("mail.smtp.port", "587"); //TLS Port
//		props.put("mail.smtp.auth", "true"); //enable authentication
//		props.put("mail.smtp.starttls.enable", "true"); //enable STARTTLS
//		
//                //create Authenticator object to pass in Session.getInstance argument
//		Authenticator auth = new Authenticator() {
//			//override the getPasswordAuthentication method
//                        @Override
//			protected PasswordAuthentication getPasswordAuthentication() {
//				return new PasswordAuthentication(fromEmail, password);
//			}
//		};
//               
//		Session session = Session.getInstance(props, auth);
//		 MimeMessage message = new MimeMessage(session);
//                 message.setFrom(new InternetAddress("Confirmation de Paiement"));
//                 InternetAddress toMail = new InternetAddress(toEmail);
//                 message.setRecipient(Message.RecipientType.TO, toMail);
//                 message.setSubject("Paiement Enovyé");
//                 message.setSentDate(new Date(System.currentTimeMillis()));
//		          SMTPTransport st = (SMTPTransport)session.getTransport("smtps");
//                          st.connect("smtp.gmail",465,fromEmail,password);
//                          st.sendMessage(message, message.getAllRecipients());
//	}

}
