/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import com.codename1.components.MultiButton;
import com.codename1.components.ScaleImageLabel;
import com.codename1.components.SpanLabel;
import com.codename1.ui.Component;
import static com.codename1.ui.Component.RIGHT;
import com.codename1.ui.Container;
import com.codename1.ui.Display;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.Image;
import com.codename1.ui.Label;
import com.codename1.ui.Tabs;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.FlowLayout;
import com.codename1.ui.layouts.LayeredLayout;
import com.codename1.ui.plaf.Style;
import entities.Planning;
import com.Travedia.myapp.PlanningService;

/**
 *
 * @author Razer
 */
public class ListePlanning extends BaseForm{
     Form  current;

   public ListePlanning(Form previous) {
        setTitle("List Plannings");

        current=this;
        SpanLabel sp = new SpanLabel();
        sp.setText(PlanningService.getInstance().affichagePlanning().toString());
        Container listPlanning = new Container(BoxLayout.y());
        listPlanning.setScrollableY(true);
        //System.out.println("PPPPPPPP"+PlanningService.getInstance().affichagePlanning());
        System.out.println(PlanningService.getInstance().affichagePlanning().size());
        for (Planning h : PlanningService.getInstance().affichagePlanning())
        {
            MultiButton mb = new MultiButton("date_depart"+h.getDateDepart()+"date_fin"+h.getDateFin()+"prix"+h.getPrix()+"type_plan"+h.getTypePlan()+"description"+h.getDescription()+"utilisateur"+h.getVoyageurId()+"evenements"+h.getEvenementId()+"destinations"+h.getDestinationId()+"hotels"+h.getHotelId());
            mb.setTextLine2(h.getVoyageurId()+" ");
            mb.addActionListener(e -> new afficherPla(current,h.getId()).show());
            listPlanning.add(mb);
        }
        add(listPlanning);
        getToolbar().addMaterialCommandToLeftBar("", FontImage.MATERIAL_ARROW_BACK, e -> previous.showBack());
        getToolbar().addSearchCommand(e -> 
        {
            String text= (String)e.getSource();
            if(text==null || text.length()==0)
            {
                //Search Bar Empty
                for(Component cmp : getContentPane())
                {
                    cmp.setHidden(false);
                    cmp.setVisible(true);
                }
                
            }else
            {
                text=text.toLowerCase();
                System.out.println(text);
                for (Component cmp: listPlanning.getChildrenAsList(focusScrolling))
                {
                    if (cmp instanceof MultiButton){
                    MultiButton mb = (MultiButton)cmp;
                    String line1 = mb.getTextLine1();
                    String line2 = mb.getTextLine2();
                    boolean show = line1 != null && line1.toLowerCase().indexOf(text)>-1 
                            || line2!=null && line2.toLowerCase().indexOf(text)>-1;
                        System.out.println(show);
                    mb.setHidden(!show);
                    mb.setVisible(show);
                    }
                }
                
            }
        });
    
    
    
    }

        private void addTab(Tabs swipe, Image img, Label spacer, String likesStr, String commentsStr, String text) {
        int size = Math.min(Display.getInstance().getDisplayWidth(), Display.getInstance().getDisplayHeight());
        if(img.getHeight() < size) {
            img = img.scaledHeight(size);
        }
        Label likes = new Label(likesStr);
        Style heartStyle = new Style(likes.getUnselectedStyle());
       // heartStyle.setFgColor(0xff2d55);
        //FontImage heartImage = FontImage.createMaterial(FontImage.MATERIAL_FAVORITE, heartStyle);
        //likes.setIcon(heartImage);
        likes.setTextPosition(RIGHT);

        Label comments = new Label(commentsStr);
        //FontImage.setMaterialIcon(comments, FontImage.MATERIAL_CHAT);
        if(img.getHeight() > Display.getInstance().getDisplayHeight() / 2) {
            img = img.scaledHeight(Display.getInstance().getDisplayHeight() / 2);
        }
        ScaleImageLabel image = new ScaleImageLabel(img);
        image.setUIID("Container");
        image.setBackgroundType(Style.BACKGROUND_IMAGE_SCALED_FILL);
        Label overlay = new Label(" ", "ImageOverlay");
        
        Container page1 = 
            LayeredLayout.encloseIn(
                image,
                overlay,
                BorderLayout.south(
                    BoxLayout.encloseY(
                            new SpanLabel(text, "LargeWhiteText"),
                            FlowLayout.encloseIn(likes, comments),
                            spacer
                        )
                )
            );

        swipe.addTab("", page1);
    }
}