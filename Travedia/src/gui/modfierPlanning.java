/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import com.codename1.l10n.SimpleDateFormat;
import com.codename1.ui.Button;
import com.codename1.ui.ComboBox;
import com.codename1.ui.Command;
import com.codename1.ui.Dialog;
import com.codename1.ui.Display;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.Label;
import com.codename1.ui.TextArea;
import com.codename1.ui.TextField;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.spinner.Picker;
import entities.Planning;
import com.Travedia.myapp.PlanningService;

/**
 *
 * @author Razer
 */
public class modfierPlanning extends Form{
     public modfierPlanning(Form previous,Planning p)
{
        setTitle("Add a new Planning");
        setLayout(BoxLayout.y());
        
        Picker datePickerD = new Picker();
        datePickerD.setType(Display.PICKER_TYPE_DATE);
        Picker datePickerF = new Picker();
        datePickerF.setType(Display.PICKER_TYPE_DATE);               
        TextField Description = new TextField("", "Description", 20, TextArea.ANY);
        TextField Voyageur = new TextField("", "Voyageur", 20, TextArea.ANY);
        Label Destination = new Label("Destination");
        ComboBox DestinationT = new ComboBox("testLO");
        Label Evenement = new Label("Evenement");
        ComboBox EvenementT = new ComboBox("alo");
        Label Hotels = new Label("Hotels");
        ComboBox HotelsT = new ComboBox("test");
        Label TypePlan = new Label("Type Plan");
        ComboBox TypePlanT = new ComboBox("Premium","Standard");
        TextField prix = new TextField("","Prix",5,TextArea.NUMERIC);
        
        
        
        Button btnValider = new Button("Sauvgarder Planning");
        btnValider.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                if ((datePickerD.toString().length()==0)||(datePickerF.toString().length()==0)||(Description.getText().length()==0))
                    Dialog.show("Alert", "Please fill all the fields", new Command("OK"));
                else
                {
                    try {
                        Planning p = new Planning();
                        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                        
                        p.setDateDepart(datePickerD.getDate());
                        p.setDateFin(datePickerF.getDate());
                        p.setDescription(Description.getText());
                        p.setVoyageurId(Integer.parseInt(Voyageur.getText()));
                        p.setDestinationId(Integer.parseInt((String)DestinationT.getSelectedItem()));
                        p.setEvenementId(Integer.parseInt((String)EvenementT.getSelectedItem()));
                        p.setHotelId(Integer.parseInt((String)EvenementT.getSelectedItem()));
                        p.setTypePlan((String)TypePlanT.getSelectedItem());
                        p.setPrix(Integer.parseInt(prix.getText()));
                    
                    
                        
                        if( PlanningService.getInstance().ajouterPlanning(p))
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
        addAll(datePickerD,datePickerF,Description,Voyageur,Destination,DestinationT,Evenement,
              EvenementT,Hotels,HotelsT,TypePlan,TypePlanT,prix ,btnValider);
    
}
}
