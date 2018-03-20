package couchePresentation;

import java.util.List;

import beans.Client;
import beans.Commande;
import coucheAccesBD.ExceptionAccesBD;
import coucheAccesBD.FabriqueDAO;
import javafx.collections.FXCollections;
import javafx.geometry.HPos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class ListerCommandeClient {

	private final int Largeur = 645;
	private final int Hauteur = 310;
	
	private Stage Fenetre = new Stage();
	private Scene SceneObj;
	private AnchorPane APZonesFenetre = new AnchorPane();
	private GridPane GPSaisies = new GridPane();
	private ComboBox<Client> CBClient = new ComboBox<>();
	private Button BFermer = new Button("Fermer");
	private Separator SLigne = new Separator();
	private Separator SLigne1 = new Separator();
	private TableView<Commande> TVCommande = new TableView<>();
	private Label LCommandes = new Label("Liste des commandes du client: ");
	private TableColumn<Commande, String> TCNumCommande;
	private TableColumn<Commande, String> TCDate;
	private TableColumn<Commande, String> TCHeure;
	private TableColumn<Commande, String> TCMontantTotal;
	private TableColumn<Commande, String> TCIndicationLivraison;
	
	@SuppressWarnings("unchecked")
	public ListerCommandeClient() {
		// ajouter les clients dans la boite combo CBClient
		try {
			CBClient.setItems(FXCollections.observableArrayList(FabriqueDAO.getInstance().getInstClientDAO().ListerTous()));
		}catch(ExceptionAccesBD e) {
			GererErreur.GererErreurSQL("ListerCommandeClient", "ListerCommandeClient()", e.getMessage());
			new MessageBox(AlertType.ERROR, "Probleme de base de données lors du listage des clients !");
			return;
		}
		if(CBClient.getItems().size() == 0)
		{
			new MessageBox(AlertType.INFORMATION,"Il n'y a aucun client dans la base de donnée");
			return;
		}
		
		CBClient.setVisibleRowCount(5);
		CBClient.getSelectionModel().selectFirst();
		
		// gerer le changement du client courant dans la boîte combo eleves
		
		CBClient.getSelectionModel().selectedItemProperty().addListener((obs,ancClient,nouvClient) ->
		{
			if(nouvClient != null)
				CBChangertClient(nouvClient.getNumClient());
		}
				);
		
		//Selectionner le 1er client dans la boite combo
		CBClient.getSelectionModel().selectFirst();
		//mettre à jour une première fois le contenu de la fenêtre
		
		CBChangertClient(CBClient.getItems().get(0).getNumClient());
		
		//paramètrer le bouton BFermer
		
		BFermer.setPrefSize(80, 20);
		BFermer.setOnAction(e -> { Fenetre.close(); });
		
		//créer les colonnes dans la table affichant le résultat dans chaque commande
		
		TCNumCommande = new TableColumn<>("Numéro");
		TCNumCommande.setMinWidth(50);
		TCDate = new TableColumn<>("Date");
		TCDate.setMinWidth(50);
		TCHeure = new TableColumn<>("Heure");
		TCHeure.setMinWidth(50);
		TCMontantTotal = new TableColumn<>("Montant Total");
		TCMontantTotal.setMinWidth(50);
		TCIndicationLivraison = new TableColumn<>("Indication livraison");
		
		// Ajouter les colonnes a la table TVCommande
		
		TVCommande.getColumns().addAll(TCNumCommande,TCDate,TCHeure,TCMontantTotal,TCIndicationLivraison);
		TVCommande.setPrefSize(50 + 80 + 50 + 80 + 35,146);
		TVCommande.setEditable(false);
		
		//Indiquer le nom de chaque propriété de la classe Commande
		//à associer à chaque colonne de la table
		
		TCNumCommande.setCellValueFactory(new PropertyValueFactory<>("NumClientTxt"));
		TCDate.setCellValueFactory(new PropertyValueFactory("DateTxt"));
		TCHeure.setCellValueFactory(new PropertyValueFactory("TimeTxt"));
		TCMontantTotal.setCellValueFactory(new PropertyValueFactory("MontantTotalTxt"));
		TCIndicationLivraison.setCellValueFactory(new PropertyValueFactory("IndicLivraison"));
		
		// ajouter les objets dans les cellules du GridPane GPSaisies
		
		GPSaisies.add(new Label("Choisissez le client: "), 0, 0);
		GPSaisies.add(CBClient, 1, 0);
		GPSaisies.add(SLigne, 0, 1);
		GPSaisies.add(LCommandes, 0, 2);
		GPSaisies.add(TVCommande, 0, 3);
		GPSaisies.add(SLigne1, 0, 4);
		
		GridPane.setColumnSpan(SLigne, 2);
		GridPane.setColumnSpan(SLigne1, 2);
		GridPane.setColumnSpan(LCommandes, 2);
		GridPane.setColumnSpan(TVCommande, 2);
		GridPane.setHalignment(CBClient, HPos.RIGHT);
		
		// espacement entre les cellules de GPSaisies
		
		GPSaisies.setHgap(8);
		GPSaisies.setVgap(8);
		
		// GPSaisies et Bfermer -> APZonesFenetre
		
		APZonesFenetre.getChildren().addAll(GPSaisies,BFermer);
		AnchorPane.setTopAnchor(GPSaisies, 15.0);
		AnchorPane.setLeftAnchor(GPSaisies, 15.0);
		AnchorPane.setBottomAnchor(BFermer, 15.0);
		AnchorPane.setRightAnchor(BFermer, 15.0);
		
		// APZonesFenetre -> Scene; Scene -> Stage
		
		SceneObj = new Scene(APZonesFenetre, Largeur, Hauteur);
		Fenetre.setScene(SceneObj);
			
		// Charger les style CSS
		
		SceneObj.getStylesheets().add("couchePresentation/styleComboBox.css");
		SceneObj.getStylesheets().add("couchePresentation/stylesTableView.css");
		
		//Paramètrer la fenetre, puis l'afficher
		
		Fenetre.setTitle("Liste des commande d'un client");
		
		Fenetre.setResizable(false);
		Fenetre.setX(FenetrePrincipale.getInstance().getX() + 
					(FenetrePrincipale.getInstance().getWidth() - Largeur) /2 );
	
		Fenetre.setY(FenetrePrincipale.getInstance().getY() + 
				(FenetrePrincipale.getInstance().getHeight() - Hauteur) /2 );
		
		Fenetre.initOwner(FenetrePrincipale.getInstance());
		Fenetre.initModality(Modality.APPLICATION_MODAL);
		
		Fenetre.showAndWait();
		
		
	}

	private void CBChangertClient(int numClient) {
		System.out.println("ListerCommandeClient.CBChangertClient()");
		List<Commande> liste = null;
		
		try {
			// assigner la liste des commande dans la table
			
			liste = FabriqueDAO.getInstance().getInstCommandeDAO().ListerTous(numClient);
			
			TVCommande.itemsProperty().setValue(FXCollections.observableArrayList(liste));	
		}
		catch(ExceptionAccesBD e) {
			GererErreur.GererErreurSQL("ListerCommandeClient", "CBChangertClient()", e.getMessage());
			new MessageBox(AlertType.ERROR, "Problème de base de données lors du listage des commande!");
			return;
		}
	}
	
	
}
