/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import com.codename1.ui.Button;
import com.codename1.ui.Command;
import com.codename1.ui.Dialog;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.TextField;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.layouts.BoxLayout;
import entities.Hotel;
import com.Travedia.myapp.HotelService;

/**
 *
 * @author bhk
 */
public class AddHotelForm extends Form{

    public AddHotelForm(Form previous) {
        setTitle("Add a new Hotel");
        setLayout(BoxLayout.y());
        
        TextField tfName = new TextField("","HotelName");
        TextField tfAdresse= new TextField("", "Adresse");
        TextField tfEmail= new TextField("", "Email");
        TextField tfNumtel= new TextField("", "NumTEl");
        Button btnValider = new Button("Add Hotel");
        
        btnValider.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                if ((tfName.getText().length()==0)||(tfAdresse.getText().length()==0)||(tfEmail.getText().length()==0)||(tfNumtel.getText().length()==0))
                    Dialog.show("Alert", "Please fill all the fields", new Command("OK"));
                else
                {
                    try {
                        Hotel t = new Hotel(tfName.getText(), tfAdresse.getText(),tfEmail.getText(),Integer.parseInt(tfNumtel.getText()));
                        if( HotelService.getInstance().ajouterHotel(t))
                        {
                           Dialog.show("Success","Connection accepted",new Command("OK"));
                        }else
                            Dialog.show("ERROR", "Server error", new Command("OK"));
                    } catch (NumberFormatException e) {
                        Dialog.show("ERROR", "Status must be a number", new Command("OK"));
                    }
                    
                }
                
                
            }
        });
        
        addAll(tfName,tfAdresse,tfEmail,tfNumtel,btnValider);
        getToolbar().addMaterialCommandToLeftBar("", FontImage.MATERIAL_ARROW_BACK, e-> previous.showBack());
                
    }
    
    
}
