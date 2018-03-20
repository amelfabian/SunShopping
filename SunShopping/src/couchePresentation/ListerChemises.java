package couchePresentation;

import beans.ChemiseHawaienne;
import coucheAccesBD.ExceptionAccesBD;
import coucheAccesBD.FabriqueDAO;
import javafx.collections.FXCollections;
import javafx.scene.Scene;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class ListerChemises {

	private final int Largeur = 645;
			private final int Hauteur = 300;
			
			private Stage Fenetre = new Stage();
			private Scene SceneObj;
			private AnchorPane APZonesFenetre = new AnchorPane();
			private TableView<ChemiseHawaienne> TVChemise = new TableView<>(); 
			private Button BFermer = new Button("Fermer");
			private TableColumn<ChemiseHawaienne, String> TCNumProd;
			private TableColumn<ChemiseHawaienne, String> TCNomChemise;
			private TableColumn<ChemiseHawaienne, String> TCMatierChemise;
			private TableColumn<ChemiseHawaienne, String> TCCouleurChemise;
			private TableColumn<ChemiseHawaienne, String> TCTailleChemise;
			
			private ImageView IVImage = new ImageView();
			
			@SuppressWarnings({ "unchecked", "rawtypes" })
			public ListerChemises() {
				System.out.println("ListerChemises.ListerChemises()");
				TCNumProd = new TableColumn<>("Numéro");
				TCNumProd.setMinWidth(50);
				TCNomChemise = new TableColumn<>("Nom");
				TCNomChemise.setMinWidth(95);
				TCMatierChemise = new TableColumn<>("Matiere");
				TCMatierChemise.setMinWidth(95);
				TCCouleurChemise = new TableColumn<>("Couleur");
				TCCouleurChemise.setMinWidth(95);
				TCTailleChemise = new TableColumn<>("Taille");
				TCTailleChemise.setMinWidth(50);
				TVChemise.getColumns().addAll(TCNumProd,TCNomChemise,TCMatierChemise,TCCouleurChemise,TCTailleChemise);
				
				TVChemise.setPrefSize(50 + 95 + 95 + 95 + 75, Hauteur - 60);
				TVChemise.setEditable(false);
				
				TCNumProd.setCellValueFactory(new PropertyValueFactory("NumProduitTxt"));
				TCNomChemise.setCellValueFactory(new PropertyValueFactory("NomChemise"));
				TCMatierChemise.setCellValueFactory(new PropertyValueFactory("MatierChemise"));
				TCCouleurChemise.setCellValueFactory(new PropertyValueFactory("CouleurChemise"));
				TCTailleChemise.setCellValueFactory(new PropertyValueFactory("TailleChemiseTxt"));
				try {
						TVChemise.itemsProperty().setValue(FXCollections.observableArrayList(FabriqueDAO.getInstance().getInstChemiseHawaienneDAO().ListerTous()));
				}catch(ExceptionAccesBD e) {
					GererErreur.GererErreurSQL("ListerChemises", "ListerChemises()", e.getMessage());
					new MessageBox(AlertType.ERROR, "Probleme de base de donnée lors du listage des chemises");
					return;
				}
				
				if(TVChemise.getItems().size() == 0) {
					new MessageBox(AlertType.INFORMATION, "Il n'y a aucune chemises dans la base de donnée");
					return;
				}
				
				TVChemise.getSelectionModel().selectedItemProperty().addListener((obs,ancChe,nouvChe) -> {
					if(nouvChe != null) {
						IVImage.setImage(new Image("file: images/produit/" + nouvChe.getImageProduit()));
					}
				});
				
				TVChemise.getSelectionModel().selectFirst();
				
				BFermer.setPrefSize(80, 20);
				BFermer.setOnAction( e -> { Fenetre.close();});
				
				APZonesFenetre.getChildren().addAll(TVChemise,IVImage,BFermer);
				AnchorPane.setTopAnchor(TVChemise, 15.0);
				AnchorPane.setLeftAnchor(TVChemise, 15.0);
				AnchorPane.setTopAnchor(IVImage, 55.0);
				AnchorPane.setRightAnchor(IVImage, 15.0);
				AnchorPane.setBottomAnchor(BFermer, 15.0);
				AnchorPane.setRightAnchor(BFermer, 15.0);
				
				SceneObj = new Scene(APZonesFenetre, Largeur, Hauteur);
				Fenetre.setScene(SceneObj);
				SceneObj.getStylesheets().add("couchePresentation/stylesTableView.css");
				
				Fenetre.setTitle("Lister Chemise");
				Fenetre.setResizable(false);
				Fenetre.setX(FenetrePrincipale.getInstance().getX() + (FenetrePrincipale.getInstance().getWidth() - Largeur) / 2);
				Fenetre.setY(FenetrePrincipale.getInstance().getY() + (FenetrePrincipale.getInstance().getHeight() - Hauteur) / 2);
				
				Fenetre.initOwner(FenetrePrincipale.getInstance());
				Fenetre.initModality(Modality.APPLICATION_MODAL);
				
				Fenetre.showAndWait();
				
			}
			
	}


