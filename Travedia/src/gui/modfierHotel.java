/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import com.codename1.ui.Button;
import com.codename1.ui.ComboBox;
import com.codename1.ui.Command;
import com.codename1.ui.Dialog;
import com.codename1.ui.Display;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.TextArea;
import com.codename1.ui.TextField;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.spinner.Picker;
import entities.Hotel;
import com.Travedia.myapp.HotelService;
import java.util.Date;

/**
 *
 * @author Razer
 */
public class modfierHotel extends Form {
 public modfierHotel(Form previous,Hotel h)
    {
        setTitle("Modifier Hotel");
        setLayout(BoxLayout.y());
        
        TextField tfNom = new TextField("","Nom",4,TextArea.ANY);
        
        TextField tfAdresse= new TextField("", "Adressse",4,TextArea.ANY);
        
        TextField tfEmail = new TextField("", "Email",4,TextArea.EMAILADDR);
        TextField tfNumTel= new TextField("", "NumTel",4,TextArea.NUMERIC);
        
        
      
        Button btnValider = new Button("Sauvegarder Hotel");
        
        btnValider.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                if (tfNom.getText().length()==0||tfAdresse.getText().length()==0
                        || tfEmail.getText().length()==0||tfNumTel.getText().length()==0)
                {
                     Dialog.show("Alert", "Please fill all the fields", new Command("OK"));
                }else
                {
                    String HotelNom= (tfNom.getText());
                    h.setNom(HotelNom);
                    String Adresse= (tfAdresse.getText());
                    h.setAdresse(Adresse);
                    String Email = (tfEmail.getText());
                    h.setEmail(Email);
                    int NumTel = Integer.parseInt(tfNumTel.getText());
                    h.setNumTel(NumTel);
                    if (HotelService.getInstance().modifierHotel(h))
                    {
                       Dialog.show("Success","Connection accepted",new Command("OK"));
                    }else
                    {
                        Dialog.show("ERROR", "Server error", new Command("OK"));
                    }
                }
            }
        });
        
        
        
        addAll(tfNom,tfAdresse,tfEmail,tfNumTel,btnValider);
        getToolbar().addMaterialCommandToLeftBar("", FontImage.MATERIAL_ARROW_BACK, e-> previous.showBack());

    }
}
