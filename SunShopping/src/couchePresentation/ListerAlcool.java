package couchePresentation;

import beans.Alcool;
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

public class ListerAlcool {

		private final int Largeur = 645;
		private final int Hauteur = 300;
		
		private Stage Fenetre = new Stage();
		private Scene SceneObj;
		private AnchorPane APZonesFenetre = new AnchorPane();
		private TableView<Alcool> TVAlcool = new TableView<>(); 
		private Button BFermer = new Button("Fermer");
		private TableColumn<Alcool, String> TCNumProd;
		private TableColumn<Alcool, String> TCNomAlcool;
		private TableColumn<Alcool, String> TCGoutAlcool;
		private TableColumn<Alcool, String> TCProvenance;
		private ImageView IVImage = new ImageView();
		
		@SuppressWarnings({ "unchecked", "rawtypes" })
		public ListerAlcool() {
			TCNumProd = new TableColumn<>("Numéro");
			TCNumProd.setMinWidth(50);
			TCNomAlcool = new TableColumn<>("Nom");
			TCNomAlcool.setMinWidth(95);
			TCGoutAlcool = new TableColumn<>("Gout");
			TCGoutAlcool.setMinWidth(95);
			TCProvenance = new TableColumn<>("Provenance");
			TCProvenance.setMinWidth(95);
			TVAlcool.getColumns().addAll(TCNumProd,TCNomAlcool,TCGoutAlcool,TCProvenance);
			
			TVAlcool.setPrefSize(50 + 95 + 95 + 120, Hauteur - 60);
			TVAlcool.setEditable(false);
			
			TCNumProd.setCellValueFactory(new PropertyValueFactory("NumProduitTxt"));
			TCNomAlcool.setCellValueFactory(new PropertyValueFactory("NomAlcool"));
			TCGoutAlcool.setCellValueFactory(new PropertyValueFactory("GoutAlcool"));
			TCProvenance.setCellValueFactory(new PropertyValueFactory("Provenance"));
			
			try {
				TVAlcool.itemsProperty().setValue(FXCollections.observableArrayList(FabriqueDAO.getInstance().getInstAlcool().ListerTous()));
			}catch(ExceptionAccesBD e) {
				GererErreur.GererErreurSQL("ListerAlcool", "ListerAlcool()", e.getMessage());
				new MessageBox(AlertType.ERROR, "Probleme de base de donnée lors du listage des alcool");
				return;
			}
			
			if(TVAlcool.getItems().size() == 0) {
				new MessageBox(AlertType.INFORMATION, "Il n'y a aucun alcool dans la base de donnée");
				return;
			}
			
			TVAlcool.getSelectionModel().selectedItemProperty().addListener((obs,ancAlc,nouvAlc) -> {
				if(nouvAlc != null) {
					System.out.println("ListerAlcool.ListerAlcool().image");
					IVImage.setImage(new Image("file:E:/Workspace2018/SunShopping/images/produit/" + nouvAlc.getImageProduit()));
				}
			});
			
			TVAlcool.getSelectionModel().selectFirst();
			
			BFermer.setPrefSize(80, 20);
			BFermer.setOnAction( e -> { Fenetre.close();});
			
			APZonesFenetre.getChildren().addAll(TVAlcool,IVImage,BFermer);
			AnchorPane.setTopAnchor(TVAlcool, 15.0);
			AnchorPane.setLeftAnchor(TVAlcool, 15.0);
			AnchorPane.setTopAnchor(IVImage, 55.0);
			AnchorPane.setRightAnchor(IVImage, 15.0);
			AnchorPane.setBottomAnchor(BFermer, 15.0);
			AnchorPane.setRightAnchor(BFermer, 15.0);
			
			SceneObj = new Scene(APZonesFenetre, Largeur, Hauteur);
			Fenetre.setScene(SceneObj);
			SceneObj.getStylesheets().add("couchePresentation/stylesTableView.css");
			
			Fenetre.setTitle("Lister Alcool");
			Fenetre.setResizable(false);
			Fenetre.setX(FenetrePrincipale.getInstance().getX() + (FenetrePrincipale.getInstance().getWidth() - Largeur) / 2);
			Fenetre.setY(FenetrePrincipale.getInstance().getY() + (FenetrePrincipale.getInstance().getHeight() - Hauteur) / 2);
			
			Fenetre.initOwner(FenetrePrincipale.getInstance());
			Fenetre.initModality(Modality.APPLICATION_MODAL);
			
			Fenetre.showAndWait();
			
		}
		
}
