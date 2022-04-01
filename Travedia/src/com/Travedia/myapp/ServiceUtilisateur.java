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
import com.codename1.processing.Result;
import com.codename1.ui.ComboBox;
import com.codename1.ui.Dialog;
import com.codename1.ui.TextField;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.util.Resources;
import entities.Profile;
import gui.SessionManager;
import com.mycompany.services.utils.Statics;
import java.util.ArrayList;
import java.util.Map;
import java.util.Vector;

/**
 *
 * @author Ibtihel
 */
public class ServiceUtilisateur {

    //houni besh n3aytou lel crud eli zednehom fl symfony o yraj3ou response Json o nzidou alihom code ekher 
    public static ServiceUtilisateur instance = null;
    // public static boolean resultOK = true ;

    String json;
    private boolean resultat;

    private ConnectionRequest req;

    public static ServiceUtilisateur getInstance() {

        if (instance == null) {
            instance = new ServiceUtilisateur();
        }
        return instance;
    }

    public ServiceUtilisateur() {
        req = new ConnectionRequest();
    }

    //SignUp
    public boolean signup(TextField nom, TextField prenom, TextField email, TextField password, ComboBox role, ComboBox langue) {

        String url = Statics.BASE_URL + "/user/signUp?nom=" + nom.getText().toString() + "&prenom=" + prenom.getText().toString() + "&email=" + email.getText().trim() + "&password=" + password.getText().toString() + "&roles=" + role.getSelectedItem().toString() + "&langue=" + langue.getSelectedItem().toString();

        req.setUrl(url);

        //ctrl saisie
        if (nom.getText().equals(" ") && prenom.getText().equals(" ") && email.getText().equals(" ") && password.getText().equals(" ")) {

            Dialog.show("Erreur", "Veuillez remplir les champs", "OK", null);

        } else {
            resultat = req.getResponseCode() == 200;
        }

        //hethi ki tssir l execution mtaa url
        req.addResponseListener((e) -> {

            //data mel form
            byte[] data = (byte[]) e.getMetaData(); //lezm nhadhrou les données ka meta data yaani nekhdhou les id mtaa kol textField
            String responseData = new String(data); //nekhdhou l content
        });

        //baaed l execution mtaa requete eli heya l url nestanew reponse l server
        NetworkManager.getInstance().addToQueueAndWait(req);
        return resultat;

    }

    //Signin
    public boolean signin(TextField email, TextField password) {

        /*String url = Statics.BASE_URL + "/user/signin?email=" + email.getText().toString() + "&password=" + password.getText().toString();

        req.setUrl(url);

        req.addResponseListener((e) -> {
            JSONParser j = new JSONParser();
            String json = new String(req.getResponseData()) + "";
            try {
                if (json.equals("user not found")) {
                    Dialog.show("Erreur", "utilisateur introuvable", "OK", null);
                    System.out.print("error1");
                } else if (json.equals("password not found")) {
                    Dialog.show("Erreur", "utilisateur introuvable", "OK", null);
                    System.out.print("error2");
                } else {

                    Map<String, Object> user = j.parseJSON(new CharArrayReader(json.toCharArray()));

                    //session
                    float id = Float.parseFloat(user.get("id").toString());

                    SessionManager.setId((int) id);
                    SessionManager.setNom(user.get("nom").toString());
                    SessionManager.setPrenom(user.get("prenom").toString());
                    SessionManager.setEmail(user.get("email").toString());
                    SessionManager.setPassowrd(user.get("password").toString());
                    SessionManager.setLangue(user.get("langue").toString());
                    SessionManager.setRole(user.get("roles").toString());

                    System.out.print(SessionManager.getNom());

                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }

        });
        NetworkManager.getInstance().addToQueueAndWait(req);
        return resultat;*/
        String url = Statics.BASE_URL + "/user/signin?email="
                + email.getText().trim()
                + "&password=" + password.getText().toString();
        req.setUrl(url);
        req.addResponseListener((e) -> {
            JSONParser j = new JSONParser();
            String json = new String(req.getResponseData()) + "";
            try {

                Map<String, Object> user = j.parseJSON(new CharArrayReader(json.toCharArray()));

//session
                float id = Float.parseFloat(user.get("id").toString());
                
                //ObjectMapper objectMapper = new ObjectMapper();
                Object userProfile = user.get("profile");
                //Map<String, Object> profile = objectMapper.convertValue(userProfile, Map.class);;
                //float profileId = Float.parseFloat(user.get("profileId").toString());

                //System.out.print(profile);
                Result result = Result.fromContent(user);
                SessionManager.setId((int) id);
                //SessionManager.setEvaluation((int) evaluation);
                float profileId= Float.parseFloat(result.getAsString("profile/id"));
                SessionManager.setProfileId((int) profileId);
                SessionManager.setEmail(user.get("email").toString());
                SessionManager.setPassowrd(user.get("password").toString());
                SessionManager.setRole(user.get("roles").toString());
                SessionManager.setNom(user.get("nom").toString());
                SessionManager.setPrenom(user.get("prenom").toString());
                SessionManager.setLangue(user.get("langue").toString());
                SessionManager.setDescription(result.getAsString("profile/description"));

                if (user.size() > 0) {
                    resultat = req.getResponseCode() == 200;
                }

            } catch (Exception ex) {
                ex.printStackTrace();
            }

        });
        NetworkManager.getInstance().addToQueueAndWait(req);
        return resultat;
    }

    /*ArrayList<Profile> getCurrentProfile() {
        
    }*/
    public void editUser(int id, int profileId, TextField nom, TextField prenom, TextField email, TextField evaluation, TextField description, TextField image, TextField password, ComboBox role, ComboBox langue) {

        String url = Statics.BASE_URL + "/user/editUserMobile?id=" + id + "&profileId=" + profileId + "&nom=" + nom.getText().toString() + "&prenom=" + prenom.getText().toString() + "&image=" + image.getText().toString() + "&description=" + description.getText().toString() + "&email=" + email.getText().toString() + "&evaluation=" + evaluation.getText() + "&password=" + password.getText().toString() + "&roles=" + role.getSelectedItem().toString() + "&langue=" + langue.getSelectedItem().toString();
        // String url = Statics.BASE_URL + "/user/editUserMobile?id=" + id + "&nom=" + nom + "&prenom=" + prenom + "&password=" + password + "&email=" + email+ "&roles=" + role+ "&langue=" + langue;
        req.setUrl(url);
        req.addResponseListener((e) -> {

            String str = new String(req.getResponseData());
            System.out.println("data == " + str);
        });

        NetworkManager.getInstance().addToQueueAndWait(req);
    }

    
    
    public boolean deleteUser(int id) {
        String url = Statics.BASE_URL + "/mobile/user/delete?id=" + id;

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
    
    
    
    
    
    
    
    
    
    
    
    

    //public void SignUp(TextField nom, TextField prenom, TextField email, TextField password, ComboBox<String> roles, ComboBox<String> langue, Resources theme) {
    //  throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    //  }
}
