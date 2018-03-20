package couchePresentation;

import java.util.Iterator;
import java.util.List;

import javax.swing.plaf.synth.SynthSeparatorUI;

import beans.Commande;
import beans.Produit;
import coucheAccesBD.ExceptionAccesBD;
import coucheAccesBD.FabriqueDAO;
import javafx.collections.FXCollections;
import javafx.geometry.HPos;
import javafx.scene.Scene;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class ListerArticleCommande {
	private final int Largeur = 315;
	private final int Hauteur = 310;

	private Stage Fenetre = new Stage();
	private Scene SceneObj;
	private AnchorPane APZonesFenetre = new AnchorPane();
	private GridPane GPSaisies = new GridPane();
	private ComboBox<Commande> CBCommande = new ComboBox<>();
	private Button BFermer = new Button("Fermer");
	private Separator SLigne = new Separator();
	private Separator SLigne1 = new Separator();
	private TableView<Produit> TVProduit = new TableView<>();
	private Label Lproduit = new Label("Liste des produit par commande");
	private TableColumn<Produit, String> TCNumProduit;
	private TableColumn<Produit, String> TCPrixUnite;
	private TableColumn<Produit, String> TCQuantiteStock;
	private ImageView IVImage = new ImageView();

	@SuppressWarnings("unchecked")
	public ListerArticleCommande() {
		// ajouter commande dans CBCommande
		try {
			CBCommande.setItems(
					FXCollections.observableArrayList(FabriqueDAO.getInstance().getInstCommandeDAO().ListerTous()));

		} catch (ExceptionAccesBD e) {
			GererErreur.GererErreurSQL("ListerArticleCOmmande", "ListerArticleCommande()", e.getMessage());
			new MessageBox(AlertType.ERROR, "Il n'y a aucune commande dans la base de donnée!");
			return;
		}

		if (CBCommande.getItems().size() == 0) {
			new MessageBox(AlertType.INFORMATION, "Il n'y a aucun élève dans la base de donnée!");
			return;
		}

		CBCommande.setVisibleRowCount(5);
		CBCommande.getSelectionModel().selectFirst();

		// gérer le changement de la commande courrante dans la boite combo CBCommande

		CBCommande.getSelectionModel().selectedItemProperty().addListener((obs, ancCommande, nouvCommande) -> {
			if (nouvCommande != null) {
				CBChangertCommande(nouvCommande.getNumCommande());
			}
		});

		// selectionner la premiere commande dans la boîte combo

		CBCommande.getSelectionModel().selectFirst();

		// Mettre à jour une première fois le contenu de la fenêtre

		CBChangertCommande(CBCommande.getItems().get(0).getNumCommande());

		// parametrer le bouton BFermer

		BFermer.setPrefSize(80, 20);
		BFermer.setOnAction(e -> {
			Fenetre.close();
		});

		// creer les colonnes dans la table

		TCNumProduit = new TableColumn<>("Numéro");
		TCNumProduit.setMinWidth(50);
		TCPrixUnite = new TableColumn<>("prix unité");
		TCPrixUnite.setMinWidth(50);
		TCQuantiteStock = new TableColumn<>("Quantite stock");
		TCQuantiteStock.setMinWidth(50);

		// Ajouter les colones à la table

		TVProduit.getColumns().addAll(TCNumProduit, TCPrixUnite, TCQuantiteStock);
		TVProduit.setPrefSize(50 + 50 + 50 + 35, 146);
		TVProduit.setEditable(false);

		// indiquer le nom de chaque propriété de la classe Produit a associer

		TCNumProduit.setCellValueFactory(new PropertyValueFactory<>("NumProduitTxt"));
		TCPrixUnite.setCellValueFactory(new PropertyValueFactory<>("Prix_uniteTxt"));
		TCQuantiteStock.setCellValueFactory(new PropertyValueFactory<>("QuentiteStockTxt"));

		// paramettrer imageView

		TVProduit.getSelectionModel().selectedItemProperty().addListener((obs, ancProd, nouvProd) -> {
			if (nouvProd != null) {
				IVImage.setImage(new Image("file:images/produit/" + nouvProd.getImageProduit()));

			}
		});
		// ajouter des objet dans les cellules du GridPane GPSaisies

		GPSaisies.add(new Label("Choisissez la commande: "), 0, 0);
		GPSaisies.add(CBCommande, 1, 0);
		GPSaisies.add(SLigne, 1, 0);
		GPSaisies.add(TVProduit, 0, 1);
		GPSaisies.add(IVImage, 0, 2);
		GPSaisies.add(SLigne1, 0, 3);

		GridPane.setColumnSpan(SLigne, 2);
		GridPane.setColumnSpan(SLigne1, 2);
		GridPane.setColumnSpan(TVProduit, 2);
		GridPane.setColumnSpan(IVImage, 2);
		GridPane.setHalignment(CBCommande, HPos.RIGHT);

		GPSaisies.setHgap(8);
		GPSaisies.setVgap(8);

		APZonesFenetre.getChildren().addAll(GPSaisies, BFermer);
		AnchorPane.setTopAnchor(GPSaisies, 15.0);
		AnchorPane.setLeftAnchor(GPSaisies, 15.0);
		AnchorPane.setBottomAnchor(BFermer, 15.0);
		AnchorPane.setRightAnchor(BFermer, 15.0);

		//APZonesFenetre -> Scene; Scene -> Stage
		
				SceneObj = new Scene(APZonesFenetre, Largeur, Hauteur);
				Fenetre.setScene(SceneObj);
				
				//Charger les style CSS
		
		SceneObj.getStylesheets().add("couchePresentation/styleComboBox.css");
		SceneObj.getStylesheets().add("couchePresentation/stylesTableView.css");

		Fenetre.setTitle("Lister produit d'une commande");
		Fenetre.setResizable(false);
		Fenetre.setX(
				FenetrePrincipale.getInstance().getX() + (FenetrePrincipale.getInstance().getWidth() - Largeur) / 2);
		Fenetre.setY(
				FenetrePrincipale.getInstance().getY() + (FenetrePrincipale.getInstance().getHeight() - Hauteur) / 2);
		Fenetre.initOwner(FenetrePrincipale.getInstance());
		Fenetre.initModality(Modality.APPLICATION_MODAL);

		Fenetre.showAndWait();

	}

	private void CBChangertCommande(int numCommande) {
		System.out.println("ListerArticleCommande.CBChangertCommande() --> " + numCommande );
		List<Produit> liste = null;
		try {
			liste = FabriqueDAO.getInstance().getInstProduitDAO().ListerTous(numCommande);
			TVProduit.itemsProperty().setValue(FXCollections.observableArrayList(liste));
				
		} catch (ExceptionAccesBD e) {
			GererErreur.GererErreurSQL("ListerArticleCommande", "CBChangertCommande()", e.getMessage());
			new MessageBox(AlertType.ERROR, "Problème de base de données lors du listage des produits!");
			return;
		}

	
	}
}
