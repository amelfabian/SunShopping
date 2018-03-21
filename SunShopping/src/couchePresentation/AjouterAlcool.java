package couchePresentation;

import java.io.File;
import java.nio.file.Files;

import beans.Alcool;
import beans.ExceptionMetier;
import coucheAccesBD.ExceptionAccesBD;
import coucheAccesBD.FabriqueDAO;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class AjouterAlcool {
	private final int Largeur = 580;
	private final int Hauteur = 260;

	private Stage Fenetre = new Stage();
	private Scene SceneObj;
	private HBox HBSaisies = new HBox(15);
	private HBox HBBoutons = new HBox(15);
	private HBox HBImg = new HBox(15);
	private GridPane GPSaisies = new GridPane();
	private VBox VBZonesFenetre = new VBox();
	private TextField TFNomAlcool = new TextField();
	private TextField TFGoutAlcool = new TextField();
	private TextField TFProvenance = new TextField();
	private TextField TFNomImage = new TextField();
	private Button BChargerImage = new Button("...");
	private Button BAjouter = new Button("Ajouter");
	private Button BFermer = new Button("Fermer");
	private Separator SLigne = new Separator();
	private ImageView IVImage = new ImageView();

	private File FichierSrc = null;

	public AjouterAlcool() {
		System.out.println("AjouterAlcool.AjouterAlcool()");
		GPSaisies.add(new Label("Nom: "), 0, 1);
		GPSaisies.add(TFNomAlcool, 1, 1);
		GPSaisies.add(new Label("Goût: "), 0, 2);
		GPSaisies.add(TFGoutAlcool, 1, 2);
		GPSaisies.add(new Label("Provenance: "), 0, 3);
		GPSaisies.add(TFProvenance, 1, 3);
		GPSaisies.add(new Label("Photo du produit: "), 0, 5);
		GPSaisies.add(HBImg, 1, 5);

		// Espacement entre les cellules de GPSaisies

		GPSaisies.setHgap(8);
		GPSaisies.setVgap(8);
		IVImage.setFitWidth(163);
		IVImage.setFitHeight(163);
		// empecher l'ecriture dans la zine TFNomImage

		TFNomImage.setDisable(true);
		TFNomImage.setStyle("-fx-opacity: 1.0;");

		// paramétrer le bouton BChargerImage

		BChargerImage.setPrefSize(20, 20);
		BChargerImage.setOnAction(e -> {
			System.out.println("ouvrir image");
			OuvrirFichierimg();
		});

		// TFNomImage et BChargerImage -> HBImg
		HBImg.getChildren().addAll(TFNomImage, BChargerImage);

		// Charger l'image par défaut

		IVImage.setImage(new Image("file:images/produit/" + TFNomImage.getText()));

		// parametrer les boutons BAjouter et BFermer

		BAjouter.setPrefSize(80, 20);
		BAjouter.setOnAction(e -> {
			System.out.println("ajouter alcool");
			BAjouterAlcool();
		});
		BFermer.setPrefSize(80, 20);
		BFermer.setOnAction(e -> {
			System.out.println("fermer fenetre");
			Fenetre.close();
		});

		// GPSaisies et IVImage -> HBSaisies
		HBSaisies.getChildren().addAll(GPSaisies, IVImage);
		HBox.setMargin(IVImage, new Insets(15, 0, 15, 0));

		// BAjouter et BFermer -> HBBoutons
		HBBoutons.getChildren().addAll(BAjouter, BFermer);
		
		VBZonesFenetre.getChildren().addAll(HBSaisies,SLigne,HBBoutons);

		// HBSaisies,SLigne et HBouton -> VBZonesFenetre
		VBox.setMargin(HBSaisies, new Insets(15, 15, 10, 15));
		VBox.setMargin(SLigne, new Insets(0, 15, 0, 15));
		VBox.setMargin(HBBoutons, new Insets(10, 0, 15, 400));

		// VBZonesFenetre -> scene; Scene -> stage
		SceneObj = new Scene(VBZonesFenetre, Largeur, Hauteur);
		Fenetre.setScene(SceneObj);

		// Charger les style CSS

		SceneObj.getStylesheets().add("couchePresentation/styleComboBox.css");

		// Paramétrer la fenetre, puis l'afficher

		Fenetre.setTitle("Ajouter un nouvel Alcool");
		Fenetre.setResizable(false);
		Fenetre.setX(
				FenetrePrincipale.getInstance().getX() + (FenetrePrincipale.getInstance().getWidth() - Largeur) / 2);

		Fenetre.setY(
				FenetrePrincipale.getInstance().getY() + (FenetrePrincipale.getInstance().getHeight() - Hauteur) / 2);
		Fenetre.initOwner(FenetrePrincipale.getInstance());
		Fenetre.initModality(Modality.APPLICATION_MODAL);
		Fenetre.showAndWait();

	}

	private void BAjouterAlcool() {
		try {
			System.out.println("AjouterAlcool.BAjouterAlcool()");
			Alcool alcool = new Alcool();
			alcool.setNomAlcool(TFNomAlcool.getText());
			alcool.setGoutAlcool(TFGoutAlcool.getText());
			alcool.setProvenance(TFProvenance.getText());
			alcool.setImageProduit(TFNomImage.getText());

			if (FabriqueDAO.getInstance().getInstAlcool().Ajouter(alcool) == false)
				new MessageBox(AlertType.INFORMATION, "L'ajout n'a pas eu lieu!");
			else
				new MessageBox(AlertType.INFORMATION, "L'ajout s'est bien déroulé!");
			// copier le fichier de l'image vers le répertoire des images
			if (FichierSrc != null) {
				File FichierDst = new File(System.getProperty("user.dir") + "/images/produit/" + FichierSrc.getName());
				Files.copy(FichierSrc.toPath(), FichierDst.toPath());
			}
		} catch (ExceptionAccesBD e) {
			GererErreur.GererErreurSQL("AjouterAlcool", "BAjouterAlcool()", e.getMessage());
			new MessageBox(AlertType.ERROR, "Problème de base de données lors de l'ajout de l'alcool!");
		} catch (ExceptionMetier e) {
			new MessageBox(AlertType.WARNING, e.getMessage());
			return;
		} catch (Exception e) {
			GererErreur.GererErreurSQL("AjouterAlcool", "BAjouterAlcool()", e.getMessage());
			new MessageBox(AlertType.ERROR, "Problème inattendu lors de l'ajout de l'alcool!");
		}
		Fenetre.close();
	}

	private void OuvrirFichierimg() {
		System.out.println("AjouterAlcool.OuvrirFichierimg()");
		FileChooser btNomImage = new FileChooser();
		btNomImage.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("JPG", ".jpg"));

		FichierSrc = btNomImage.showOpenDialog(null);
		if (FichierSrc != null) {
			TFNomImage.setText(FichierSrc.getName());
			IVImage.setImage(new Image("file:" + FichierSrc.getPath()));
		}
	}
}
