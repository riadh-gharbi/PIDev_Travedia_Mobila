/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.main;


import com.codename1.components.ScaleImageLabel;
import com.codename1.components.SpanLabel;
import com.codename1.ui.Button;
import com.codename1.ui.ButtonGroup;
import com.codename1.ui.Component;
import static com.codename1.ui.Component.BOTTOM;
import static com.codename1.ui.Component.CENTER;
import static com.codename1.ui.Component.LEFT;
import static com.codename1.ui.Component.RIGHT;
import com.codename1.ui.Container;
import com.codename1.ui.Dialog;
import com.codename1.ui.Display;
import com.codename1.ui.EncodedImage;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.Graphics;
import com.codename1.ui.Image;
import com.codename1.ui.Label;
import com.codename1.ui.RadioButton;
import com.codename1.ui.Tabs;
import com.codename1.ui.TextArea;
import com.codename1.ui.Toolbar;
import com.codename1.ui.URLImage;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.FlowLayout;
import com.codename1.ui.layouts.GridLayout;
import com.codename1.ui.layouts.LayeredLayout;
import com.codename1.ui.plaf.Style;
import com.codename1.ui.util.Resources;
import com.mycompany.entites.Destination;
import com.mycopany.services.serviceDestination;
import java.util.ArrayList;

/**
 *
 * @author user
 */
public class ListDestinationForm extends BaseForm{
   Form current; 
   public ListDestinationForm(Resources res){
       super("Newsfeed",BoxLayout.y());

        Toolbar tb = new Toolbar (true);
        current = this ;
        setToolbar(tb);
        getTitleArea().setUIID("Container");
        setTitle("List Destinations");
        getContentPane().setScrollVisible(false);
        
        tb.addSearchCommand(e ->{});
        Tabs swipe = new Tabs();
        Label s1= new Label();
        Label s2= new Label();
                super.addSideMenu(res);

       // addTab(swipe,res.getImage("logo2-sansbg.png"),"","", res);
        addTab(swipe, res.getImage("trav.jpg"), s1,"" , "", "");

        ///////////////////////////////////////////////////////////////
       
       swipe.setUIID("Container");
        swipe.getContentPane().setUIID("Container");
        swipe.hideTabs();
        
        ButtonGroup bg = new ButtonGroup();
        int size = Display.getInstance().convertToPixels(1);
        Image unselectedWalkthru = Image.createImage(size, size, 0);
        Graphics g = unselectedWalkthru.getGraphics();
        g.setColor(0xffffff);
        g.setAlpha(100);
        g.setAntiAliased(true);
        g.fillArc(0, 0, size, size, 0, 360);
        Image selectedWalkthru = Image.createImage(size, size, 0);
        g = selectedWalkthru.getGraphics();
        g.setColor(0xffffff);
        g.setAntiAliased(true);
        g.fillArc(0, 0, size, size, 0, 360);
        RadioButton[] rbs = new RadioButton[swipe.getTabCount()];
        FlowLayout flow = new FlowLayout(CENTER);
        flow.setValign(BOTTOM);
        Container radioContainer = new Container(flow);
        for(int iter = 0 ; iter < rbs.length ; iter++) {
            rbs[iter] = RadioButton.createToggle(unselectedWalkthru, bg);
            rbs[iter].setPressedIcon(selectedWalkthru);
            rbs[iter].setUIID("Label");
            radioContainer.add(rbs[iter]);
        }
                
        rbs[0].setSelected(true);
        swipe.addSelectionListener((i, ii) -> {
            if(!rbs[ii].isSelected()) {
                rbs[ii].setSelected(true);
            }
        });
        
        Component.setSameSize(radioContainer, s1, s2);
        add(LayeredLayout.encloseIn(swipe, radioContainer));
        ButtonGroup barGroup = new ButtonGroup();
        RadioButton all = RadioButton.createToggle("  ", barGroup);
        all.setUIID("SelectBar");
        RadioButton featured = RadioButton.createToggle("  ", barGroup);
        featured.setUIID("SelectBar");
        RadioButton popular = RadioButton.createToggle("  ", barGroup);
        popular.setUIID("SelectBar");
        RadioButton myFavorite = RadioButton.createToggle("  ", barGroup);
        myFavorite.setUIID("SelectBar");
        Label arrow = new Label(res.getImage("news-tab-down-arrow.png"), "Container");
        
        add(LayeredLayout.encloseIn(
                GridLayout.encloseIn(4, all, featured, popular, myFavorite),
                FlowLayout.encloseBottom(arrow)
        ));
//             getToolbar().addCommandToSideMenu("Region", null, e ->{
//     new ListRegionForm(res).show();
//        });
//                  getToolbar().addCommandToSideMenu("Destination", null, e ->{
//     new ListDestinationForm(res).show();
//        });

        all.setSelected(true);
        arrow.setVisible(false);
        addShowListener(e -> {
            arrow.setVisible(true);
            updateArrowPosition(all, arrow);
        });
        bindButtonSelection(all, arrow);
        bindButtonSelection(featured, arrow);
        bindButtonSelection(popular, arrow);
        bindButtonSelection(myFavorite, arrow);
        
        // special case for rotation
        addOrientationListener(e -> {
            updateArrowPosition(barGroup.getRadioButton(barGroup.getSelectedIndex()), arrow);
        });
        
        ArrayList<Destination>List =serviceDestination.getInstance().getAllDestination();
        for (Destination dest : List){
           String urlImage = "http://127.0.0.1:8000/uploads/brochures/" + dest.getImage();
            Image placeHolder = Image.createImage(120,90);
            EncodedImage enc = EncodedImage.createFromImage(placeHolder,false);
            URLImage urlim = URLImage.createToStorage(enc, urlImage, urlImage,URLImage.RESIZE_SCALE);
            addButton(urlim,dest,res);
            ScaleImageLabel image = new ScaleImageLabel(urlim);
            Container containerImg = new Container();
            image.setBackgroundType(Style.BACKGROUND_IMAGE_SCALED_FILL);
            System.out.println(dest.getNom());
        }
        Button btnEv= new Button ("Ajouter Destination");
               addStringValue("", btnEv);
               btnEv.addActionListener(e->{
            new AjouterDestinationForm(res).show(); }); 
               
               Button btnmap= new Button ("Afficher map");
               addStringValue("", btnmap);
               btnmap.addActionListener(e->{
            new MapForm(); 
               });   
   }
      private void addStringValue(String s, Component v) {
        add(BorderLayout.west(new Label(s,"PaddedLabel"))
        .add(BorderLayout.CENTER,v));
        add(createLineSeparator (0xeeeeee));
    } 
   
   private void updateArrowPosition(Button btn, Label l) {
        l.getUnselectedStyle().setMargin(LEFT,btn.getX()+btn.getWidth()/2-l.getWidth()/2);
        l.getParent().repaint();
    }

    private void bindButtonSelection(Button btn, Label l) {
        btn.addActionListener(e->{
            if (btn.isSelected()){
                updateArrowPosition(btn,l);}
            }
        );
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
  private void addButton(Image img,Destination dest,Resources res){
        int height = Display.getInstance().convertToPixels(11.5f);
        int width = Display.getInstance().convertToPixels(14f);
        Button image;
        image = new Button (img.fill(width, height));
        image.setUIID("Label");
        Container cnt= BorderLayout.west(image);
        
        Label nomTxt = new Label(dest.getNom(),"NewsTopLine1");
        Label descTxt = new Label(dest.getDescription(),"NewsBottomLine");
        Label regTxt = new Label(dest.getRegion(),"NewsBottomLine");
      //  Label descTxt = new Label("Description : "+dest.getDescription(),"NewsTopLine1");

       // Label imageTxt = new Label("Image : "+image,"NewsTopLine");
        // Supprimer button
       Label lSupprimer = new Label (" ");
       lSupprimer.setUIID("NewsTopLine");
       Style supprimerStyle = new Style(lSupprimer.getUnselectedStyle());
       supprimerStyle.setFgColor(0xf21f1f);
       FontImage supprimerImage = FontImage.createMaterial(FontImage.MATERIAL_DELETE, supprimerStyle);
       lSupprimer.setIcon(supprimerImage);
       lSupprimer.setTextPosition(RIGHT);
       //click
       lSupprimer.addPointerPressedListener(l->{
           Dialog dig = new Dialog("Suppression");
           if (dig.show("Suppression", "Vous voulez supprimer cette destination ?","Annuler ","Oui ")){
               dig.dispose();
           }
           else {
               dig.dispose();
               if(serviceDestination.getInstance().Delete(dest.getId())){
                   new ListDestinationForm(res).show();
                   //refreshTheme();
               }     
           }
       });
       
       //Update button
       Label lModifier = new Label (" ");
       lModifier.setUIID("NewsTopLine");
       Style modifierStyle = new Style(lModifier.getUnselectedStyle());
       modifierStyle.setFgColor(0xf7ad02);
       FontImage mFontImage = FontImage.createMaterial(FontImage.MATERIAL_MODE_EDIT, modifierStyle);
       lModifier.setIcon(mFontImage);
       lModifier.setTextPosition(LEFT);
       lModifier.addPointerPressedListener(l->{
           new ModifierDestinationForm(res,dest).show();});
       
       cnt.add(BorderLayout.CENTER,BoxLayout.encloseY(BoxLayout.encloseX(nomTxt,descTxt,regTxt,lModifier,lSupprimer)));
       
       add(cnt);
    }
         
}
