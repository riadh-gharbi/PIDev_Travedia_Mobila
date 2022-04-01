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
import entities.Hotel;
import com.Travedia.myapp.HotelService;

/**
 *
 * @author bhk
 */
public class ListeHotel extends BaseForm {
    Form  current;

    public ListeHotel(Form previous) {
        setTitle("List Hotels");
        
        current=this;
        SpanLabel sp = new SpanLabel();
        sp.setText(HotelService.getInstance().affichageHotel().toString());
        Container listHotel = new Container(BoxLayout.y());
        listHotel.setScrollableY(true);
        
        for (Hotel h : HotelService.getInstance().affichageHotel())
        {
            MultiButton mb = new MultiButton("Hotel "+h.getNom()+ "Tel "+ h.getNumTel());
            mb.setTextLine2(h.getEmail()+" ");
            //mb.addActionListener(e -> new AfficherPaiementForm(current,p.getId()).show());
            listHotel.add(mb);
        }
        add(listHotel);
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
                for (Component cmp: listHotel.getChildrenAsList(focusScrolling))
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