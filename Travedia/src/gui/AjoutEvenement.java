/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import com.codename1.components.InfiniteProgress;
import com.codename1.components.ScaleImageLabel;
import com.codename1.components.SpanLabel;
import com.codename1.ext.filechooser.FileChooser;
import com.codename1.l10n.SimpleDateFormat;
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
import com.codename1.ui.Form;
import com.codename1.ui.Graphics;
import com.codename1.ui.Image;
import com.codename1.ui.Label;
import com.codename1.ui.RadioButton;
import com.codename1.ui.Tabs;
import com.codename1.ui.TextField;
import com.codename1.ui.Toolbar;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.FlowLayout;
import com.codename1.ui.layouts.GridLayout;
import com.codename1.ui.layouts.LayeredLayout;
import com.codename1.ui.plaf.Style;
import com.codename1.ui.spinner.Picker;
import com.codename1.ui.util.Resources;
import entities.Evenement;
import entities.Helper;
import com.Travedia.myapp.EvenementServices;
import com.mycompany.services.utils.Statics;

/**
 *
 * @author user
 */
public class AjoutEvenement extends BaseForm {
    Form current;
    String filePath = "";
    public AjoutEvenement(Resources res){
        //super("Newsfeed",BoxLayout.y());
        setLayout(BoxLayout.y());
        Toolbar tb = new Toolbar (true);
        current = this ;
        setToolbar(tb);
        getTitleArea().setUIID("Container");
        setTitle("Ajout Evenement");
        getContentPane().setScrollVisible(false);
        
        tb.addSearchCommand(e ->{});
        Tabs swipe = new Tabs();
        Label s1= new Label();
        Label s2= new Label();
       // addTab(swipe,res.getImage("logo2-sansbg.png"),"","", res);
       addTab(swipe, res.getImage("ev.jpeg"), s1, "", "", "");
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
        RadioButton popular = RadioButton.createToggle(" ", barGroup);
        popular.setUIID("SelectBar");
        RadioButton myFavorite = RadioButton.createToggle(" ", barGroup);
        myFavorite.setUIID("SelectBar");
        Label arrow = new Label(res.getImage("news-tab-down-arrow.png"), "Container");
        
        add(LayeredLayout.encloseIn(
                GridLayout.encloseIn(4, all, featured, popular, myFavorite),
                FlowLayout.encloseBottom(arrow)
        ));
        
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
       
       ///////////////////////////////////////////////////////////////
       
       TextField nom = new TextField("","enter name !");
        nom.setUIID("textFieldBlack");
        addStringValue("nom",nom);
        
         //Upload Image
        Button btCapture = new Button("Upload");
       //Label lbImage = new Label();
        //addStringValue("", btCapture);
        add(btCapture);
        btCapture.addActionListener((e)->{
            ActionListener callback = ev -> {
                if (ev != null && ev.getSource() != null) {
                    System.out.println("");
                    filePath = (String) ev.getSource();
                }
            };

            if (FileChooser.isAvailable()) {
                FileChooser.showOpenDialog(".gif,image/gif,.png,image/png,.jpg,image/jpg,.tif,image/tif,.jpeg", callback);
            } else {
                Display.getInstance().openGallery(callback, Display.GALLERY_IMAGE);
            }

        });
        
        TextField description = new TextField("","enter description !");
        description.setUIID("textFieldBlack");
        addStringValue("description",description);
        
        Picker datePicker2 = new Picker ();
        datePicker2.setType(Display.PICKER_TYPE_DATE);
        datePicker2.setUIID("textFieldBlack");
        addStringValue("datedeb",datePicker2);
        
        Picker datePicker1 = new Picker ();
        datePicker1.setType(Display.PICKER_TYPE_DATE);
        datePicker1.setUIID("textFieldBlack");
        addStringValue("datefin",datePicker1);
        
        Helper.ComboBoxFarhadni CategorieBox = Helper.createComboBox(Statics.BASE_URL+"/afficherCategories", "nom", "id", "id");
        addStringValue("Categorie", CategorieBox.Box);
        TextField categorie = new TextField("","choose category!");
        categorie.setUIID("textFieldBlack");
        //addStringValue("categorie",categorie);
        
        Button btnAjouter = new Button("Ajouter");
        addStringValue("", btnAjouter);
        
        btnAjouter.addActionListener((e) ->{
            
            try {
            if(nom.getText() == "" || description.getText()== ""){
                Dialog.show("verifier les données","","annuler","ok");
            }
            else {
                InfiniteProgress ip = new InfiniteProgress();
                final Dialog iDialog = ip.showInfiniteBlocking();
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                Evenement ev = new Evenement (String.valueOf(nom.getText()
                    ).toString(),
                    filePath,
                    String.valueOf(description.getText()),
                    format.format(datePicker2.getDate()),    
                    format.format(datePicker1.getDate()),
                    String.valueOf(CategorieBox.getSelected().uniqueId)
                );
                System.out.println("date "  + format.format(datePicker1.getDate()));
                System.out.println("data evenement =="+ev);
                
                EvenementServices.getInstance().ajoutEvenement(ev);
                
                iDialog.dispose();
                new ListEvenementForm(res).show();
                
                refreshTheme();
            }
            }catch(Exception ex){
                ex.printStackTrace();
            }
            
        });
        
        Button btnCat= new Button ("Ajouter Categorie");
               addStringValue("", btnCat);
               btnCat.addActionListener(e->{
            new AjoutCategorie(res).show();
        });
               
        Button btnCatL= new Button ("List Categories");
               addStringValue("", btnCatL);
               btnCat.addActionListener(e->{
            new ListCategorieForm(res).show();
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
    
     private void addStringValue(String s, Component v) {
        add(BorderLayout.west(new Label(s,"PaddedLabel"))
        .add(BorderLayout.CENTER,v));
        add(createLineSeparator (0xeeeeee));
    } 
    
}
