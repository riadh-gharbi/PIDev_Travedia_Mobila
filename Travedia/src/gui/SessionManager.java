/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import com.codename1.io.Preferences;


/**
 *
 * @author Ibtihel
 */
public class SessionManager {
  


    public static Preferences pref;

    private static int id;
    private static int profileId;
     private static int evaluation;
    private static String nom;
    private static String image;
    private static String description;
    private static String prenom;
    private static String email;
    private static String password;
    private static String langue;
    private static String role;
      
    
    
    
    
    public static Preferences getPref() {
        return pref;
    }

    public static void setPref(Preferences pref) {
        SessionManager.pref = pref;
    }

    public static int getId() {
        return pref.get("id", id);
    }

    public static void setId(int id) {
        pref.set("id", id);//nsajl id user connecté  w na3tiha identifiant "id";
    }
    
    
    
     public static int getProfileId() {
        return pref.get("id", profileId);
    }

    public static void setProfileId(int profileId) {
        pref.set("id", profileId);//nsajl id user connecté  w na3tiha identifiant "id";
    }
    

    public static String getNom() {
        return pref.get("nom", nom);
    }

    public static void setNom(String nom) {
        pref.set("nom", nom);
    }

public static String getRoles() {
        return pref.get("role", role);
    }

    public static void setRole(String role) {
        pref.set("role", role);
    }

    public static String getPrenom() {
        return pref.get("prenom", prenom);
    }

    public static void setPrenom(String prenom) {
        pref.set("prenom", prenom);
    }

    public static String getLangue() {
        return pref.get("langue", langue);
    }

    public static void setLangue(String langue) {
        pref.set("langue", langue);
    }

    public static String getEmail() {
        return pref.get("email", email);
    }

    public static void setEmail(String email) {
        pref.set("email", email);
    }

    public static String getPassowrd() {
        return pref.get("passowrd", password);
    }

    public static void setPassowrd(String passowrd) {
        pref.set("passowrd", passowrd);
    }

    public static int getEvaluation() {
        return evaluation;
    }

    public static void setEvaluation(int evaluation) {
        SessionManager.evaluation = evaluation;
    }

    public static String getImage() {
        return image;
    }

    public static void setImage(String image) {
        SessionManager.image = image;
    }

    public static String getDescription() {
        return description;
    }

    public static void setDescription(String description) {
        SessionManager.description = description;
    }

   
    
    
    
    
    
    
}
