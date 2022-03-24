/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import com.Travedia.myapp.ReclamationService;
import com.codename1.components.MultiButton;
import com.codename1.components.SpanLabel;
import com.codename1.ui.Container;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.layouts.BoxLayout;
import entities.Reclamation;
import entities.ReclamationReponse;
import java.util.Map;

/**
 *
 * @author riadh
 */
public class ListeReclamationForm extends Form {
    Form current;
    public ListeReclamationForm(Form previous) {
        setTitle("List Reclamation");
        current=this;
        SpanLabel sp = new SpanLabel();
        sp.setText(ReclamationService.getInstance().getAllReclamation().toString());
        Container listRec = new Container(BoxLayout.y());
        listRec.setScrollableY(true);
        
        for (Map.Entry<Reclamation,ReclamationReponse> entry : ReclamationService.getInstance().getAllReclamation().entrySet())
        {
            MultiButton mb = new MultiButton(entry.getKey().getSujet());
            mb.setTextLine2(entry.getKey().getContenu());
            mb.addActionListener(e -> new AfficherRecForm(current,entry.getKey().getId()).show());
            listRec.add(mb);
        }
        add(listRec);
        getToolbar().addMaterialCommandToLeftBar("", FontImage.MATERIAL_ARROW_BACK, e -> previous.showBack());
    }
    
}
