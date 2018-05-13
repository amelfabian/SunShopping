package couchePresentation;

import java.io.File;
import java.nio.file.Files;

import beans.ChemiseHawaienne;
import beans.ExceptionMetier;
import coucheAccesBD.ExceptionAccesBD;
import coucheAccesBD.FabriqueDAO;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
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

public class AjouterChemise {
	private final int Largeur = 580;
	private final int Hauteur = 350;

	private Stage Fenetre = new Stage();
	private Scene SceneObj;
	private HBox HBSaisies = new HBox(15);
	private HBox HBBoutons = new HBox(15);
	private HBox HBImg = new HBox(15);
	private GridPane GPSaisies = new GridPane();
	private VBox VBZonesFenetre = new VBox();
	private TextField TFNomChemise = new TextField();
	private TextField TFMatiere = new TextField();
	private TextField TFCouleur = new TextField();
	private TextField TFNomImage = new TextField();
	private TextField TFTaille = new TextField();
	private TextField TFQuantiteStock =  new TextField();
	private TextField TFPrixUnite =  new TextField();
	private Button BChargerImage = new Button("...");
	private Button BAjouter = new Button("Ajouter");
	private Button BFermer = new Button("Fermer");
	private Separator SLigne = new Separator();
	private ImageView IVImage = new ImageView();

	private File FichierSrc = null;

	public AjouterChemise() {

		GPSaisies.add(new Label("Nom: "), 0, 1);
		GPSaisies.add(TFNomChemise, 1, 1);
		GPSaisies.add(new Label("Matiere: "), 0, 2);
		GPSaisies.add(TFMatiere, 1, 2);
		GPSaisies.add(new Label("Taille: "), 0, 3);
		GPSaisies.add(TFTaille, 1, 3);
		GPSaisies.add(new Label("Couleur: "), 0, 4);
		GPSaisies.add(TFCouleur, 1, 4);
		GPSaisies.add(new Label("Photo du produit: "), 0, 5);
		GPSaisies.add(HBImg, 1, 5);
		GPSaisies.add(new Label("Quantite en stock: "), 0, 6);
		GPSaisies.add(TFQuantiteStock, 1, 6);
		GPSaisies.add(new Label("Prix unitaire: "), 0, 7);
		GPSaisies.add(TFPrixUnite, 1, 7);
		
		IVImage.setFitWidth(163);
		IVImage.setFitHeight(163);
		// Espacement entre les cellules de GPSaisies
		
		GPSaisies.setHgap(8);
		GPSaisies.setVgap(8);

		// empecher l'ecriture dans la zine TFNomImage

		TFNomImage.setDisable(true);
		TFNomImage.setStyle("-fx-opacity: 1.0");

		// paramétrer le bouton BChargerImage

		BChargerImage.setPrefSize(20, 20);
		BChargerImage.setOnAction(e -> {
			OuvrirFichierimg();
		});

		// TFNomImage et BChargerImage -> HBImg
		HBImg.getChildren().addAll(TFNomImage, BChargerImage);

		// Charger l'image par défaut

		IVImage.setImage(new Image("file:images/ChemisesH/" + TFNomImage.getText()));

		// parametrer les boutons BAjouter et BFermer

		BAjouter.setPrefSize(80, 20);
		BAjouter.setOnAction(e -> {
			BAjouterChemise();
		});
		BFermer.setPrefSize(80, 20);
		BFermer.setOnAction(e -> {
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

		Fenetre.setTitle("Ajouter une nouvelle Chemise");
		Fenetre.setResizable(false);
		Fenetre.setX(
				FenetrePrincipale.getInstance().getX() + (FenetrePrincipale.getInstance().getWidth() - Largeur) / 2);

		Fenetre.setY(
				FenetrePrincipale.getInstance().getY() + (FenetrePrincipale.getInstance().getHeight() - Hauteur) / 2);
		Fenetre.initOwner(FenetrePrincipale.getInstance());
		Fenetre.initModality(Modality.APPLICATION_MODAL);
		Fenetre.showAndWait();

	}

	private void BAjouterChemise() {
		try {
			System.out.println("AjouterAlcool.BAjouterAlcool()");
			ChemiseHawaienne chemise = new ChemiseHawaienne();
			chemise.setNomChemise(TFNomChemise.getText());
			chemise.setMatierChemise(TFMatiere.getText());
			chemise.setTailleChemise(TFTaille.getText().charAt(0));
			chemise.setCouleurChemise(TFCouleur.getText());
			chemise.setImageProduit(TFNomImage.getText());
			chemise.setQuantiteStock(Integer.parseInt(TFQuantiteStock.getText()));
			chemise.setPrix_unite(Float.parseFloat(TFPrixUnite.getText()));

			if (FabriqueDAO.getInstance().getInstChemiseHawaienneDAO().Ajouter(chemise) == false)
				new MessageBox(AlertType.INFORMATION, "L'ajout n'a pas eu lieu!");
			else
				new MessageBox(AlertType.INFORMATION, "L'ajout s'est bien déroulé!");
			// copier le fichier de l'image vers le répertoire des images
			if (FichierSrc != null) {
				File FichierDst = new File(System.getProperty("user.dir") + "/images/Alcool" + FichierSrc.getName());
				Files.copy(FichierSrc.toPath(), FichierDst.toPath());
			}
		} catch (ExceptionAccesBD e) {
			GererErreur.GererErreurSQL("AjouterChemise", "BAjouterChemise()", e.getMessage());
			new MessageBox(AlertType.ERROR, "Problème de base de données lors de l'ajout de la chemise!");
		} catch (ExceptionMetier e) {
			new MessageBox(AlertType.WARNING, e.getMessage());
			return;
		} catch (Exception e) {
			GererErreur.GererErreurSQL("AjouterChemise", "BAjouterChemise()", e.getMessage());
			new MessageBox(AlertType.ERROR, "Problème inattendu lors de l'ajout de la chemise!");
		}
		Fenetre.close();
	}

	private void OuvrirFichierimg() {
		FileChooser btNomImage = new FileChooser();
		btNomImage.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("JPG", ".jpg"));

		FichierSrc = btNomImage.showOpenDialog(null);
		if (FichierSrc != null) {
			TFNomImage.setText(FichierSrc.getName());
			IVImage.setImage(new Image("file:" + FichierSrc.getPath()));
		}
	}
}
