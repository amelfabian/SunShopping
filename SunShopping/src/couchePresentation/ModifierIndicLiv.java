package couchePresentation;

import java.io.File;

import beans.Commande;
import beans.ExceptionMetier;
import coucheAccesBD.ExceptionAccesBD;
import coucheAccesBD.FabriqueDAO;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class ModifierIndicLiv {

	private final int Largeur = 580;
	private final int Hauteur = 300;

	private Stage Fenetre = new Stage();
	private Scene SceneObj;
	private HBox HBBoutons = new HBox(15);
	private HBox HBSaisies = new HBox(15);
	private GridPane GPSaisies = new GridPane();
	private VBox VBZonesFenetre = new VBox();
	private ComboBox<Commande> CBCommande = new ComboBox<>();
	private ComboBox<String> CBIndicLivraison = new ComboBox<>();
	private Button BModifier = new Button("Modifier");
	private Button BFermer = new Button("Fermer");
	private Separator SLigne = new Separator();
	private Separator SLigne1 = new Separator();

	public ModifierIndicLiv() {
		System.out.println("ModifierIndicLiv.ModifierIndicLiv()");
		// Ajouter Commande dans la boite combo CBCommande

		try {

			CBCommande.setItems(
					FXCollections.observableArrayList(FabriqueDAO.getInstance().getInstCommandeDAO().ListerTous()));

		} catch (ExceptionAccesBD e) {
			GererErreur.GererErreurSQL("ModifierIndicLiv", "ModifierIndicLiv()", e.getMessage());
			new MessageBox(AlertType.ERROR, "Probleme de base de donnée lors du listage des commande");
			return;
		}

		if (CBCommande.getItems().size() == 0) {
			new MessageBox(AlertType.INFORMATION, "il n'y a aucune commande dans la base de donnée");
			return;
		}

		CBCommande.setVisibleRowCount(5);
		CBCommande.getSelectionModel().selectFirst();

		CBCommande.getSelectionModel().selectedItemProperty().addListener((obs, ancCommande, nouvCommande) -> {
			if (nouvCommande != null) {
				CBChangerCommande(nouvCommande);}
		});

		CBIndicLivraison.setMaxWidth(120);
		CBCommande.setMaxWidth(190);

		CBIndicLivraison.getItems().addAll("Livre", "Pas livre");

		CBChangerCommande(CBCommande.getItems().get(0));

		BModifier.setPrefSize(80, 20);
		BModifier.setOnAction(e -> {
			BModifierIndiceLiv();
		});
		BFermer.setPrefSize(80, 20);
		BFermer.setOnAction(e -> {
			Fenetre.close();
		});

		HBSaisies.getChildren().add(GPSaisies);
		GPSaisies.add(new Label("Choisissez la commande: "), 0, 0);
		GPSaisies.add(CBCommande, 1, 0);
		GPSaisies.add(SLigne, 0, 1);
		GridPane.setColumnSpan(SLigne, 2);
		GPSaisies.add(new Label("Indice de livraison : "), 0, 2);
		GPSaisies.add(CBIndicLivraison, 1,2);
		GPSaisies.setHgap(8);
		GPSaisies.setVgap(8);

		HBBoutons.getChildren().addAll(BModifier, BFermer);

		VBZonesFenetre.getChildren().addAll(HBSaisies, SLigne1, HBBoutons);

		VBox.setMargin(HBSaisies, new Insets(15, 15, 10, 15));
		VBox.setMargin(SLigne, new Insets(0, 15, 0, 15));
		VBox.setMargin(HBBoutons, new Insets(10, 0, 15, 400));

		SceneObj = new Scene(VBZonesFenetre, Largeur, Hauteur);
		Fenetre.setScene(SceneObj);

		SceneObj.getStylesheets().add("couchePresentation/styleComboBox.css");

		Fenetre.setTitle("Modifier l'indice de livraison");
		Fenetre.setResizable(false);
		Fenetre.setX(
				FenetrePrincipale.getInstance().getX() + (FenetrePrincipale.getInstance().getWidth() - Largeur) / 2);
		Fenetre.setY(
				FenetrePrincipale.getInstance().getY() + (FenetrePrincipale.getInstance().getHeight() - Hauteur) / 2);
		Fenetre.initModality(Modality.APPLICATION_MODAL);

		Fenetre.showAndWait();

	}

	private void CBChangerCommande(Commande commande) {
		System.out.println("ModifierIndicLiv.CBChangerCommande()");

		CBIndicLivraison.setValue(commande.getIndicLivraison());

	}

	private void BModifierIndiceLiv() {
		System.out.println("ModifierIndicLiv.BModifierIndiceLiv()");

		try {
			Commande commande = new Commande();
			
			commande.setNumCommande(CBCommande.getSelectionModel().getSelectedItem().getNumCommande());
		
			commande.setIndicLivraison(CBIndicLivraison.getValue());

			if (FabriqueDAO.getInstance().getInstCommandeDAO().Modifier(commande) == false) {
				new MessageBox(AlertType.INFORMATION, "La Modification n'a pas eu lieu");
				System.out.println("ModifierIndicLiv.BModifierIndiceLiv() -- false " + commande);
			} else {
				new MessageBox(AlertType.INFORMATION, "La modification s'est bien déroulée");
			}

		} catch (ExceptionAccesBD e) {
			GererErreur.GererErreurSQL("ModifierIndicLiv", "BModifierIndiceLiv()", e.getMessage());
			new MessageBox(AlertType.ERROR, "Probleme de base de donnée lors de la modification de la commande");

		} catch (Exception e) {
			GererErreur.GererErreurSQL("ModifierIndicLiv", "BModifierIndiceLiv()", e.getMessage());
			new MessageBox(AlertType.ERROR, "Problème inattendu lors de la modification de la commande");

		}

		Fenetre.close();

	}

}
