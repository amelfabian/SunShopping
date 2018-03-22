package couchePresentation;

import beans.ChemiseHawaienne;
import coucheAccesBD.ExceptionAccesBD;
import coucheAccesBD.FabriqueDAO;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class SupprimerChemise {
	private final int Largeur = 360;
	private final int Hauteur = 95;
	private Stage Fenetre = new Stage();
	private Scene SceneObj;
	private HBox HBSelect = new HBox(15);
	private HBox HBBoutons = new HBox(15);
	private VBox VBZonesFenetre = new VBox(25);
	private ComboBox<ChemiseHawaienne> CBChemise = new ComboBox<>();
	private Button BSupprimer = new Button("Supprimer");
	private Button BFermer = new Button("Fermer");

	public SupprimerChemise() {
		try {
			CBChemise.setItems(FXCollections.observableArrayList(FabriqueDAO.getInstance().getInstChemiseHawaienneDAO().ListerTous()));
			
		} catch(ExceptionAccesBD e) {
			GererErreur.GererErreurSQL("SupprimerChemise", "SupprimerChemise()", e.getMessage());
			new MessageBox(AlertType.ERROR, "Probleme de base de donnée lors du listage des chemises");
			return;
		}
		
		if(CBChemise.getItems().size() ==  0) {
			new MessageBox(AlertType.INFORMATION, "Il n'y a aucune chemise dans la base de données!");
			return;
		}
		
		CBChemise.setVisibleRowCount(5);
		CBChemise.getSelectionModel().selectFirst();
		
		BSupprimer.setPrefSize(80, 20);
		BSupprimer.setOnAction(e -> {BSupprimerChemise();});
		BFermer.setPrefSize(80, 20);
		BFermer.setOnAction(e -> { Fenetre.close();});
		
		HBSelect.getChildren().addAll(new Label("Choisissez la chemise: "), CBChemise);
		HBSelect.setAlignment(Pos.CENTER);
		HBBoutons.getChildren().addAll(BSupprimer,BFermer);
		HBBoutons.setAlignment(Pos.CENTER);
		VBZonesFenetre.getChildren().addAll(HBSelect, HBBoutons);
		VBZonesFenetre.setPadding(new Insets(15));
		SceneObj = new Scene(VBZonesFenetre, Largeur, Hauteur);
		Fenetre.setScene(SceneObj);
		
		SceneObj.getStylesheets().add("couchePresentation/styleComboBox.css");
		
		Fenetre.setTitle("Supprimer une chemise");
		Fenetre.setResizable(false);
		Fenetre.setX(FenetrePrincipale.getInstance().getX() +
		(FenetrePrincipale.getInstance().getWidth() - Largeur) / 2);
		Fenetre.setY(FenetrePrincipale.getInstance().getY() +
		(FenetrePrincipale.getInstance().getHeight() - Hauteur) / 2);
		Fenetre.initOwner(FenetrePrincipale.getInstance());
		Fenetre.initModality(Modality.APPLICATION_MODAL);
		Fenetre.showAndWait();
		
	}
		
	private void BSupprimerChemise() {
		try {
			if(FabriqueDAO.getInstance().getInstChemiseHawaienneDAO().Supprimer(CBChemise.getSelectionModel().getSelectedItem().getNumProduit()) == false)
				new MessageBox(AlertType.INFORMATION, "LA suppression n'a pas eu lieu!");
			else
				new MessageBox(AlertType.INFORMATION, "La suppresionn s'est bien déroulée");
		} catch (ExceptionAccesBD e) {
			GererErreur.GererErreurSQL("SupprimerChemise", "BSupprimerChemise()", e.getMessage());
			new MessageBox(AlertType.ERROR,
			"Problème de base de données lors de la suppression de La chemise!");
		}
		
	}
}
