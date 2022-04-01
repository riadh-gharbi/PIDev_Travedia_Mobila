/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.services;
import com.codename1.io.CharArrayReader;
import com.codename1.io.ConnectionRequest;
import com.codename1.io.JSONParser;
import com.codename1.io.MultipartRequest;
import com.codename1.io.NetworkEvent;
import com.codename1.io.NetworkManager;
import com.codename1.ui.events.ActionListener;
import com.mycompany.entities.Categorie;
import com.mycompany.services.utils.Statics;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
/**
 *
 * @author user
 */
public class CategorieServices {
    private boolean resultat;
   public static CategorieServices instance=null;
   public ArrayList<Categorie> Categories; 
    private MultipartRequest req;
    
    public static CategorieServices getInstance (){
        if (instance == null)
            instance = new CategorieServices();
        return instance;
    }
    
    public CategorieServices(){
        req=new MultipartRequest ();
    }
    //ajout
    public boolean ajoutCategorie(Categorie categorie){
      String url =Statics.BASE_URL+"/add?&nom="+categorie.getNom();
      
      req.setPost(true);
      
      req.setUrl(url);
      req.setFailSilently(true);
      if(!categorie.getImage().isEmpty())
        {
            try {
                        System.out.println("img field data " + categorie.getImage());
                        req.addData("image", categorie.getImage(), "image/jpeg");
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
    public ArrayList<Categorie> parse(String jsonTxt) {
        try{
        Categories =new ArrayList<>();
        
        JSONParser parser = new JSONParser();
        
        Map<String,Object> CategoriesJSON;
            CategoriesJSON = parser.parseJSON(new CharArrayReader(jsonTxt.toCharArray()));
        List<Map<String,Object>> listeRec;
            listeRec = (List<Map<String,Object>>)CategoriesJSON.get("root");
        
        
        for(Map<String, Object> item: listeRec) {
            Categorie cat = new Categorie();
            
            //Get id from json response and set it in local device
            float id= Float.parseFloat(item.get("id").toString());
            cat.setId((int)id);
            cat.setNom(item.get("nom").toString());
            cat.setImage(item.get("image").toString());
            System.out.println("image:");
            System.out.println(item.get("image").toString());
            //if (item.get("utilisateur_id")==  null  ){!
            //r.setUser_id((int)(item.get("utilistaeur_id")));}
            Categories.add(cat);
        }
        }
        catch(IOException | NumberFormatException ex)
        {
            ex.printStackTrace();
            System.out.println("aaa");
        }
        return Categories;
    
    }
    
    //affichage
    public ArrayList<Categorie>affichageCategorie(){
        req = new MultipartRequest();
        System.out.println("hello1");
        ArrayList<Categorie> result = new ArrayList<> ();
        String url = Statics.BASE_URL+"/afficherCategories";
        req.setUrl(url);
                System.out.println("hello2");

        req.addResponseListener(new ActionListener<NetworkEvent>(){
            @Override
            public void actionPerformed(NetworkEvent evt) {
                try{
                            System.out.println("hello3");

                    String dataaa = new String(req.getResponseData(),"utf-8");
                    System.out.println("our dataaa " + dataaa);
                    Categories = parse(dataaa);
                    req.removeResponseListener(this);
                }catch (Exception ex){
                
                }
            }
        
        });
                System.out.println("hello4");

    NetworkManager.getInstance().addToQueueAndWait(req);
    return Categories;
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
    
    public boolean modifierCategorie(Categorie categorie)
     {
        String url =Statics.BASE_URL+"/modifierCategories?id="+categorie.getId()+"&nom="+categorie.getNom();
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
    
    public boolean  SupprimerCategorie(int idCat){
       String url = "http://127.0.0.1:8000" + "/deleteCategories?id=" +idCat;

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
