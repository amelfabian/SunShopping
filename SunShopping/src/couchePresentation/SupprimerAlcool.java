package couchePresentation;

import beans.Alcool;
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

public class SupprimerAlcool {
	
	private final int Largeur = 320;
	private final int Hauteur = 95;
	private Stage Fenetre = new Stage();
	private Scene SceneObj;
	private HBox HBSelect = new HBox(15);
	private HBox HBBoutons = new HBox(15);
	private VBox VBZonesFenetre = new VBox(25);
	private ComboBox<Alcool> CBAlcool = new ComboBox<>();
	private Button BSupprimer = new Button("Supprimer");
	private Button BFermer = new Button("Fermer");
	
	public SupprimerAlcool() {
		try {
			CBAlcool.setItems(FXCollections.observableArrayList(FabriqueDAO.getInstance().getInstAlcool().ListerTous()));
		}catch(ExceptionAccesBD e) {
			GererErreur.GererErreurSQL("SupprimerAlcool", "SupprimerAlcool()", e.getMessage());
		new MessageBox(AlertType.ERROR, "Probleme de base de donnée lors du listage des alcools");
		return;
		}
		
		if(CBAlcool.getItems().size() == 0) {
			new MessageBox(AlertType.INFORMATION, "Il n'y a aucun élève dans la base de données!");
			return;
		}
		
		CBAlcool.setVisibleRowCount(5);
		CBAlcool.getSelectionModel().selectFirst();
		
		BSupprimer.setPrefSize(80, 20);
		BSupprimer.setOnAction(e -> {BSupprimerEleve();});
		BFermer.setPrefSize(80, 20);
		BFermer.setOnAction(e -> { Fenetre.close(); });
		
		HBSelect.getChildren().addAll(new Label("Choisissez l'alcool: "), CBAlcool);
		HBSelect.setAlignment(Pos.CENTER);
		
		HBBoutons.getChildren().addAll(BSupprimer,BFermer);
		HBBoutons.setAlignment(Pos.CENTER);
		
		VBZonesFenetre.getChildren().addAll(HBSelect, HBBoutons);
		VBZonesFenetre.setPadding(new Insets(15));
		
		SceneObj = new Scene(VBZonesFenetre, Largeur, Hauteur);
		Fenetre.setScene(SceneObj);
		
		SceneObj.getStylesheets().add("couchePresentation/styleComboBox.css");
		
		Fenetre.setTitle("Supprimer un alcool");
		Fenetre.setResizable(false);
		Fenetre.setX(FenetrePrincipale.getInstance().getX() +
		(FenetrePrincipale.getInstance().getWidth() - Largeur) / 2);
		Fenetre.setY(FenetrePrincipale.getInstance().getY() +
		(FenetrePrincipale.getInstance().getHeight() - Hauteur) / 2);
		Fenetre.initOwner(FenetrePrincipale.getInstance());
		Fenetre.initModality(Modality.APPLICATION_MODAL);
		Fenetre.showAndWait();
		
		
	}

	private void BSupprimerEleve() {
		try {
			if(FabriqueDAO.getInstance().getInstAlcool().Supprimer(CBAlcool.getSelectionModel().getSelectedItem().getNumProduit()) == false)
				new MessageBox(AlertType.INFORMATION, "LA suppression n'a pas eu lieu!");
			else
				new MessageBox(AlertType.INFORMATION, "La suppresionn s'est bien déroulée");
		} catch (ExceptionAccesBD e) {
			GererErreur.GererErreurSQL("SupprimerAlcool", "BSupprimerAlcool()", e.getMessage());
			new MessageBox(AlertType.ERROR,
			"Problème de base de données lors de la suppression de L'alcool!");
		}
		
	}
	
	
}
