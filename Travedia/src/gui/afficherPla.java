/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import com.Travedia.myapp.PaiementService;
import com.codename1.ui.Button;
import com.codename1.ui.Container;
import com.codename1.ui.Dialog;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.Label;
import com.codename1.ui.TextArea;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import entities.Planning;
import com.Travedia.myapp.PlanningService;
import com.codename1.ui.BrowserComponent;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentLink;
import com.stripe.model.Price;
import com.stripe.model.Product;
import com.stripe.param.PaymentLinkCreateParams;
import entities.Paiement;
import group.diamonddev.Stream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Razer
 */
public class afficherPla extends Form{
    Form current;
    public afficherPla(Form previous, int PlaId)
    {
        current = this;
        Planning p = Stream.of(PlanningService.getInstance().affichagePlanning()).filter(l->l.getId()==PlaId).findFirst().orElse(null);
        setLayout(BoxLayout.y());
        setTitle("Planning "+ p.getId());
        Label lbDateDepart = new Label();
        lbDateDepart.setText("Date Depart: " + p.getDateDepart());
        Label lbDateFin = new Label();
        lbDateFin.setText("Date Fin: " + p.getDateFin());
        Label lbVoyageur = new Label();
        lbVoyageur.setText("Voyageur: "+ p.getVoyageurId());
        Label lbDestination = new Label();
        lbDestination.setText("Destination"+ p.getDestinationId());
        Label lbEvenement = new Label();
        lbEvenement.setText("Evenement: "+ p.getEvenementId());
        Label lbTypePlan = new Label();
        lbTypePlan.setText("Type Plan: "+ p.getTypePlan());
        Label lbHotel = new Label();
        lbHotel.setText("Hotel"+ p.getHotelId());
        Label lbDescription = new Label();
        lbDescription.setText("Description"+ p.getDescription());
        Label lbPrix = new Label();
        lbPrix.setText("Prix"+ p.getPrix());
        
         Button editButton = new Button("Modifier Planning");
        editButton.addActionListener(l->new modfierPlanning(current, p).show());
        Button payButton = new Button("Payer");
        payButton.addActionListener((evt) -> {
            try {
                Paiement paiement = new Paiement();
                paiement.setClientId(SessionManager.getId());
                paiement.setOwnerId(p.getVoyageurId());
                paiement.setDate_creation(new Date(System.currentTimeMillis()));
                paiement.setPlanningId(p.getId());
                paiement.setPrix(p.getPrix());
                paiement.setStatut("En Cours");
                paiement.setType_paiement("En Ligne");
                paiement.setPlanningId(p.getId());
              
                PaiementService ps = new PaiementService();
                ps.ajouterPaiement(paiement);
                for(Paiement pay:ps.getAllPaiement())
                {
                    if(paiement.getId()== null){
                        paiement = pay;
                    }
                    else
                     if (paiement.getId()< pay.getId())
                    {
                        paiement = pay;
                    }
                    
                }
                //payment
                Stripe.apiKey = "sk_test_51KT8ejAISKORykYshnnbQcDPyMdeStYUi7Xtp05Lh1or86C6AHB8K3NsvA6CmiFXv4obHCq1p3gxp8oV8YHNizZ000pllSDFVs";
                
                
                
                
                
                Map<String, Object> productParams = new HashMap<>();
                productParams.put("name", "Planning "+p.getId());
                Product product = Product.create(productParams);
                
                
                Map<String, Object> priceParams = new HashMap<>();
                priceParams.put("unit_amount", p.getPrix());
                priceParams.put("currency", "eur");
                priceParams.put("product", product.getId());
                Map<String, String> initialMetadata = new HashMap<>();
                initialMetadata.put("id",String.valueOf( paiement.getId()));
                priceParams.put("metadata", initialMetadata);
                
                Price price = Price.create(priceParams);
                List<Object> lineItems = new ArrayList<>();
                Map<String, Object> lineItem1 = new HashMap<>();
                lineItem1.put(
                        "price",
                        price.getId()
                );
//        Map<String,Object> metadata = new HashMap<>();
//        metadata.put("id",p.getId() );
//        lineItem1.put("quantity", 1);
        
//        lineItems.add(lineItem1);
//        Map<String, Object> params = new HashMap<>();
//        params.put("line_items", lineItems);
//        params.put("metadata",metadata);

PaymentLinkCreateParams paymentLinkCreateParams = PaymentLinkCreateParams.builder()
        
        .addLineItem(PaymentLinkCreateParams.LineItem.builder()
                
                .setPrice(price.getId())
                .setQuantity(1L)
                
                .build()
        )
        .setAfterCompletion(PaymentLinkCreateParams.AfterCompletion.builder()
                .setType(PaymentLinkCreateParams.AfterCompletion.Type.HOSTED_CONFIRMATION)
                .setHostedConfirmation(new PaymentLinkCreateParams.AfterCompletion.HostedConfirmation.Builder().setCustomMessage("Paid").build()).build())
        
        .build();
        PaymentLink paymentLink =
        PaymentLink.create(paymentLinkCreateParams);
        new BrowserForm(paymentLink.getUrl(),current).show();
        
            } catch (StripeException ex) {
                System.err.println(ex.getMessage());
            }
        
        
            
        });
        Button deleteButton = new Button("Supprimer Planning");
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                Dialog d = new Dialog("Supprimer Planning?");
                TextArea dialogTextBody = new TextArea("Voulez vous vraiment supprimer cette Planning?");
                dialogTextBody.setEditable(false);
                d.setLayout(new BorderLayout());
                Button confirmButton = new Button("Confirmer");
                Button closeButton = new Button("Annuler");
                confirmButton.addActionListener(l->{
                    PlanningService.getInstance().SupprimerPlanning(PlaId);
                    new HomeForm().showBack();
                            });
                closeButton.addActionListener((ee) -> d.dispose());
                Container buttonContainer = new Container(BoxLayout.x());
                buttonContainer.addAll(confirmButton,closeButton);
                d.add(BorderLayout.CENTER, dialogTextBody);
                d.add(BorderLayout.SOUTH,buttonContainer);
                d.show();
            }
        });
        
        
        
        addAll(lbDateDepart,lbDateFin,lbDescription,lbDestination,lbEvenement,lbHotel,lbPrix,lbTypePlan,lbVoyageur,editButton,deleteButton,payButton);
        getToolbar().addMaterialCommandToLeftBar("", FontImage.MATERIAL_ARROW_BACK, e-> previous.showBack());

    }
}
    

