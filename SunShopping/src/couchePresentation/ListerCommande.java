package couchePresentation;

import beans.Commande;
import coucheAccesBD.ExceptionAccesBD;
import coucheAccesBD.FabriqueDAO;
import javafx.collections.FXCollections;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class ListerCommande {
	private final int Largeur  = 625;
	private final int Hauteur = 300;
	
	private Stage Fenetre = new Stage();
	private Scene SceneObj;
	private AnchorPane APZonesFenetre = new AnchorPane();
	private TableView<Commande> TVCommande = new TableView<>();
	private Button BFermer = new Button("Fermer");
	private TableColumn<Commande, String> TCNumCmd;
	private TableColumn<Commande, String> TCdate;
	private TableColumn<Commande, String> TCheure;
	private TableColumn<Commande, String> TCClient;
	private TableColumn<Commande, String> TCMontantTot;
	private TableColumn<Commande, String> TCIndicLivraison;
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public ListerCommande() {
		//Creer colones dans la table TVCommande
		TCNumCmd = new TableColumn<>("numéro");
		TCNumCmd.setMinWidth(50);
		TCdate = new TableColumn<>("date");
		TCdate.setMinWidth(95);
		TCheure = new TableColumn<>("heure");
		TCheure.setMinWidth(95);
		TCClient = new TableColumn<>("Client");
		TCClient.setMinWidth(95);
		TCMontantTot = new TableColumn<>("Montant Total");
		TCMontantTot.setMinWidth(95);
		TCIndicLivraison = new TableColumn<>("Indice Livraison");
		TCIndicLivraison.setMinWidth(95);
		TVCommande.getColumns().addAll(TCNumCmd,TCdate, TCheure, TCClient, TCMontantTot,TCIndicLivraison);
		
		//parametrer la table TVCommande
		
		TVCommande.setPrefSize(50+95+95+95+95+95+25, Hauteur-60);
		TVCommande.setEditable(false);
		
		//indiiquer le nom de chaque propriété d'une commande
		//...a associer a chaque colonne dans la tabke
		TCNumCmd.setCellValueFactory(new PropertyValueFactory("numCommandeTxt"));
		TCdate.setCellValueFactory(new PropertyValueFactory("DateTxt"));
		TCheure.setCellValueFactory(new PropertyValueFactory("HeureTxt"));
		TCClient.setCellValueFactory(new PropertyValueFactory("ClientTxt"));
		TCMontantTot.setCellValueFactory(new PropertyValueFactory("montantTotalTxt"));
		TCIndicLivraison.setCellValueFactory(new PropertyValueFactory("indicLivraison"));
		
		//ajouter la liste des commande a la table commande
		try {
			TVCommande.itemsProperty().setValue(FXCollections.observableArrayList(FabriqueDAO.getInstance().getInstCommandeDAO().ListerTous()));
		} catch (ExceptionAccesBD e) {
			GererErreur.GererErreurSQL("ListerCommande", "ListerCommande()", e.getMessage());;
			new MessageBox(AlertType.ERROR, "problème de base de données lors du listage des professeurs!");
			return;
		}
		if(TVCommande.getItems().size() == 0) {
			new MessageBox(AlertType.INFORMATION, " il n'y a aucune commande dans la base de donnée");
			return;
		
		}
		
		//Selectionner la premiere commande dans la table TVCmd
		
		TVCommande.getSelectionModel().selectFirst();
		
		//parametrer le bouton BFermer
		
		BFermer.setPrefSize(80, 20);
		BFermer.setOnAction(e -> {Fenetre.close();});
		
		//BFermer, TVCommandes et IVImage -> APZonesFenetre
		
		APZonesFenetre.getChildren().addAll(TVCommande,BFermer);
		AnchorPane.setTopAnchor(TVCommande, 15.0);
		AnchorPane.setLeftAnchor(TVCommande, 15.0);
		AnchorPane.setBottomAnchor(BFermer, 15.0);
		AnchorPane.setRightAnchor(BFermer, 15.0);
		
		//APZonesFenetre -> Scene; Scene -> Stage
		
		SceneObj = new Scene(APZonesFenetre, Largeur, Hauteur);
		Fenetre.setScene(SceneObj);
		
		//Charger les style CSS
		
		SceneObj.getStylesheets().add("couchePresentation/stylesTableView.css");
		
		//Parametrer la fenetre puis l'afficher
		
		Fenetre.setTitle("Lister les commandes");
		Fenetre.setResizable(false);
		Fenetre.setX(FenetrePrincipale.getInstance().getX() + 
				(FenetrePrincipale.getInstance().getWidth() - Largeur)/2);
		Fenetre.setY(FenetrePrincipale.getInstance().getY() + 
				(FenetrePrincipale.getInstance().getHeight() - Hauteur)/2);
		Fenetre.initOwner(FenetrePrincipale.getInstance());
		Fenetre.initModality(Modality.APPLICATION_MODAL);
		
		Fenetre.showAndWait();
	}
	
}
