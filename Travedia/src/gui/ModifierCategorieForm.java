/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import com.codename1.components.FloatingHint;
import com.codename1.ui.Button;
import com.codename1.ui.Container;
import com.codename1.ui.Form;
import com.codename1.ui.Label;
import com.codename1.ui.TextField;
import com.codename1.ui.Toolbar;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.util.Resources;
import entities.Categorie;
import com.Travedia.myapp.CategorieServices;

/**
 *
 * @author user
 */
public class ModifierCategorieForm extends BaseForm {
    Form current;
    public ModifierCategorieForm(Resources res, Categorie cat){
       // super("Newsfeed",BoxLayout.y());
        
        Toolbar tb = new Toolbar (true);
        current = this ;
        //setToolbar(tb);
        //getTitleArea().setUIID("Container");
        setTitle("Modifier Categorie");
        getContentPane().setScrollVisible(false);
        
        super.addSideMenu(res);
        TextField nom = new TextField(cat.getNom(),"nom",20,TextField.ANY);
        //TextField image = new TextField(cat.getImage(),"image",20,TextField.ANY);
        
        nom.setUIID("NewsTopLine");
       // image.setUIID("NewsTopLine");
        
        nom.setSingleLineTextArea(true);
       // image.setSingleLineTextArea(true);
        
        Button btnModifier = new Button ("Modifier");
        btnModifier.setUIID("Button");
        //event click
        btnModifier.addPointerPressedListener(l-> {
            cat.setNom(nom.getText());
           // cat.setImage(image.getText());
            if(CategorieServices.getInstance().modifierCategorie(cat)){
                new ListCategorieForm(res).show();
            }
        });
        Button btnAnnuler= new Button ("Annuler");
        btnAnnuler.addActionListener(e->{
            new ListCategorieForm(res).show();
        });
        
        Label l2= new Label("");
        Label l3= new Label("");
        Label l4= new Label("");
        Label l5= new Label("");
        Label l1= new Label();
        
        Container content = BoxLayout.encloseY(
                l1 , l2,
                new FloatingHint(nom),
                createLineSeparator(),
               // new FloatingHint(image),
                createLineSeparator(),
                l4,l5,
                btnModifier,
                btnAnnuler
        );
        
        add(content);
        show();
    }
}
