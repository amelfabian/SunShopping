package couchePresentation;

import coucheAccesBD.ExceptionAccesBD;
import coucheAccesBD.FabriqueDAO;
import javafx.application.Application;
import javafx.scene.*;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.stage.*;


public class FenetrePrincipale extends Application{
	
	private final int Largeur = 750;
	private final int Hauteur = 500;
	
	private static Stage Instance;
	
	private Scene SceneObj;
	private BorderPane BPane = new BorderPane();
	private MenuBar BarreMenus = new MenuBar();
	private Menu MenuCommande = new Menu("Commandes");	
	private MenuItem MICommandeLister = new MenuItem("Lister les Commandes");
	private MenuItem MIArticleCommande = new MenuItem("Lister Article de la commande");
	private Menu MenuProduit = new Menu("Produit");
	private MenuItem MIListerAlcool = new MenuItem("Lister Alcool");
	private MenuItem MIListerChemise = new MenuItem("Lister Chemise Hawaienne");
	private MenuItem MIAjouterAlcool = new MenuItem("Ajouter Alcool");
	private MenuItem MIAjouterChemise = new MenuItem("Ajouter Chemise Hawaienne");
	private MenuItem MISupprimerAlcool = new MenuItem("Supprimer Alcool");
	private MenuItem MISupprimerChemise = new MenuItem("Supprimer Chemise");
	private MenuItem MIModifierIndicLiv = new MenuItem("Modifier indice livraison");
	private Menu MenuClient = new Menu("Client");
	private MenuItem MICommandeClient = new MenuItem("Lister Commande client");
	private Menu MenuSortie= new Menu("Quitter");
	private MenuItem MISortieSortie = new MenuItem("Quitter le programme");

	public static Stage getInstance() {
		return Instance;
	}
	@Override
	public void start(Stage fenetre) throws Exception {
		Instance = fenetre ;
		// creer la barre des menus 
		MenuCommande.getItems().addAll(MICommandeLister,MIArticleCommande,MIModifierIndicLiv);
		MenuProduit.getItems().addAll(MIListerAlcool,MIAjouterAlcool, MISupprimerAlcool,MIListerChemise,MIAjouterChemise, MISupprimerChemise);
		MenuClient.getItems().add(MICommandeClient);
		MenuSortie.getItems().add(MISortieSortie);
		BarreMenus.getMenus().addAll(MenuCommande,MenuProduit,MenuClient,MenuSortie);
		BarreMenus.prefWidthProperty().bind(fenetre.widthProperty());
		
		//associer un gestionnaire d'évenements à chaque option des menus
		MICommandeLister.setOnAction(e -> { new ListerCommande(); });
		MIArticleCommande.setOnAction(e -> { new ListerArticleCommande(); });
		MIAjouterAlcool.setOnAction(e -> {new AjouterAlcool();});
		MIAjouterChemise.setOnAction(e -> {new AjouterChemise(); });
		MIListerAlcool.setOnAction(e -> {new ListerAlcool();});
		MIListerChemise.setOnAction(e -> { new ListerChemises();});
		MISupprimerAlcool.setOnAction(e -> {new SupprimerAlcool();});
		MISupprimerChemise.setOnAction(e -> {new SupprimerChemise();});
		MIModifierIndicLiv.setOnAction(e -> {new ModifierIndicLiv(); });
		MICommandeClient.setOnAction(e -> {new ListerCommandeClient(); });
		MISortieSortie.setOnAction(e -> { System.exit(0) ;});
		
		//barre des menus -> BPane; BPane -> Scene; Scene -> Stage
		
		BPane.setTop(BarreMenus);
		SceneObj =  new Scene(BPane,Largeur, Hauteur, Color.WHITE);
		fenetre.setScene(SceneObj);
		
		// charger les styles CSS pour changer l'aspect des menus
		
		SceneObj.getStylesheets().add("couchePresentation/stylesMenus.css");
		
		//paramétrer la fenêtre puis l'afficher
		
		fenetre.setTitle("SunShopping");
		fenetre.setResizable(false);
		fenetre.centerOnScreen();
		fenetre.show();
	}
		public static void main(String[] args) {
			try {
				FabriqueDAO.getInstance().creerConnexion();
			} catch (ExceptionAccesBD e) {
				GererErreur.GererErreurGen("FenetrePrincipale", "start()", e.getMessage());
				System.out.println("Problème pour se connecter à la base de données !");
				System.exit(0);
			}
			//charger la fenetre principale
			
			launch(args);
		}
	



}
