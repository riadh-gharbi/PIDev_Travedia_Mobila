/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import com.codename1.components.FloatingHint;
import com.codename1.ui.Button;
import com.codename1.ui.Container;
import com.codename1.ui.Display;
import com.codename1.ui.Form;
import com.codename1.ui.Label;
import com.codename1.ui.TextField;
import com.codename1.ui.Toolbar;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.spinner.Picker;
import com.codename1.ui.util.Resources;
import entities.Evenement;
import com.Travedia.myapp.EvenementServices;

/**
 *
 * @author user
 */
public class ModifierEvenementForm extends BaseForm {
    Form current;
    public ModifierEvenementForm(Resources res,Evenement ev){
        super("Newsfeed",BoxLayout.y());
        
        Toolbar tb = new Toolbar (true);
        current = this ;
        setToolbar(tb);
        getTitleArea().setUIID("Container");
        setTitle("Modifier Evenement");
        getContentPane().setScrollVisible(false);
        
        super.addSideMenu(res);
        TextField nom = new TextField(ev.getNom(),"nom",20,TextField.ANY);
       // TextField image = new TextField(ev.getImage(),"image",20,TextField.ANY);
        TextField description = new TextField(ev.getDescription(),"description",20,TextField.ANY);
        //TextField datedeb = new TextField(ev.getDatedeb(),"datedeb",20,TextField.ANY);
        //Picker datedeb = new Picker ();
        //datedeb.setType(Display.PICKER_TYPE_DATE);
       // TextField datefin = new TextField(ev.getDatefin(),"datefin",20,TextField.ANY);
        //Picker datefin = new Picker ();
        //datefin.setType(Display.PICKER_TYPE_DATE);
        //TextField categorie = new TextField(ev.getCategorie(),"categorie",20,TextField.ANY);
        
        nom.setUIID("NewsTopLine");
       // image.setUIID("NewsTopLine");
        description.setUIID("NewsTopLine");
        //datedeb.setUIID("NewsTopLine");
        //datefin.setUIID("NewsTopLine");
        //categorie.setUIID("NewsTopLine");
        
        nom.setSingleLineTextArea(true);
        //image.setSingleLineTextArea(true);
        description.setSingleLineTextArea(true);
        //datedeb.setAlwaysTensile(true);
        //datefin.setAlwaysTensile(true);
        //categorie.setSingleLineTextArea(true);
        
        Button btnModifier = new Button ("Modifier");
        btnModifier.setUIID("Button");
        //event click
        btnModifier.addPointerPressedListener(l-> {
            ev.setNom(nom.getText());
            //ev.setImage(image.getText());
            if(EvenementServices.getInstance().modifierEvenement(ev)){
                new ListEvenementForm(res).show();
            }
        });
        
         Button btnAnnuler= new Button ("Annuler");
        btnAnnuler.addActionListener(e->{
            new ListEvenementForm(res).show();
        });
        
        Label l2= new Label("");
        Label l3= new Label("");
        Label l4= new Label("");
        Label l5= new Label("");
        Label l6= new Label("");
        Label l7= new Label("");
        Label l8= new Label("");
        Label l9= new Label("");
        Label l10= new Label("");
        Label l11= new Label("");
        Label l12= new Label("");
        Label l13= new Label("");
        Label l1= new Label();
        Container content = BoxLayout.encloseY(
                l1 , l2,
                new FloatingHint(nom),
                createLineSeparator(),
                new FloatingHint(description),
                createLineSeparator(),
                l4,l5,
               // new FloatingHint(image),
                createLineSeparator(),
                l6,l7,
               // new FloatingHint(image),
                createLineSeparator(),
                l8,l9,
                //new FloatingHint(image),
                createLineSeparator(),
                l10,l11,
               // new FloatingHint(image),
                createLineSeparator(),
                l12,l13,
                btnModifier,
                btnAnnuler
        );
        
        add(content);
        show();
    }
}
