/**
 * Sample Skeleton for 'Food.fxml' Controller Class
 */

package it.polito.tdp.food;

import java.net.URL;
import java.util.LinkedList;
import java.util.List;
import java.util.ResourceBundle;

import it.polito.tdp.food.model.Arco;
import it.polito.tdp.food.model.Model;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class FoodController {

	private Model model;

	@FXML // ResourceBundle that was given to the FXMLLoader
	private ResourceBundle resources;

	@FXML // URL location of the FXML file that was given to the FXMLLoader
	private URL location;

	@FXML // fx:id="txtCalorie"
	private TextField txtCalorie; // Value injected by FXMLLoader

	@FXML // fx:id="txtPassi"
	private TextField txtPassi; // Value injected by FXMLLoader

	@FXML // fx:id="btnAnalisi"
	private Button btnAnalisi; // Value injected by FXMLLoader

	@FXML // fx:id="btnCorrelate"
	private Button btnCorrelate; // Value injected by FXMLLoader

	@FXML // fx:id="btnCammino"
	private Button btnCammino; // Value injected by FXMLLoader

	@FXML // fx:id="boxPorzioni"
	private ComboBox<String> boxPorzioni; // Value injected by FXMLLoader

	@FXML // fx:id="txtResult"
	private TextArea txtResult; // Value injected by FXMLLoader

	@FXML
	void doCammino(ActionEvent event) {
		txtResult.clear();
		txtResult.appendText("Cerco cammino peso massimo...");
	}

	@FXML
	void doCorrelate(ActionEvent event) {
		try {
			String s = boxPorzioni.getValue();
			txtResult.setText("I vicini di"+s+"sono: \n");
			for(Arco a: model.getVicini(s)) {
				txtResult.appendText(String.format("%s - %d \n", a.getP2(), a.getPeso()));
			}
			
		} catch (Exception e) {
			txtResult.setText("ERRORE!!! "+e);
		}

	}

	@FXML
	void doCreaGrafo(ActionEvent event) {
		try {
			int C = Integer.parseInt(txtCalorie.getText());
			model.creaGrafo(C);

			List<String> ls = new LinkedList<String>(model.getAllVertex());
			boxPorzioni.getItems().addAll(ls);
			boxPorzioni.setValue(ls.get(0));
			
			txtResult.setText(String.format("Grafo creato con %d vertici e %d archi \n", model.numVertex(), model.numEdges()));
			
		} catch (NumberFormatException e) {
			txtResult.setText("NON HAI INSERITO UN NUMERO");
		} catch (Exception e) {
			txtResult.setText("ERRORE!!! "+e);
		}
	}

	@FXML // This method is called by the FXMLLoader when initialization is complete
	void initialize() {
		assert txtCalorie != null : "fx:id=\"txtCalorie\" was not injected: check your FXML file 'Food.fxml'.";
		assert txtPassi != null : "fx:id=\"txtPassi\" was not injected: check your FXML file 'Food.fxml'.";
		assert btnAnalisi != null : "fx:id=\"btnAnalisi\" was not injected: check your FXML file 'Food.fxml'.";
		assert btnCorrelate != null : "fx:id=\"btnCorrelate\" was not injected: check your FXML file 'Food.fxml'.";
		assert btnCammino != null : "fx:id=\"btnCammino\" was not injected: check your FXML file 'Food.fxml'.";
		assert boxPorzioni != null : "fx:id=\"boxPorzioni\" was not injected: check your FXML file 'Food.fxml'.";
		assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'Food.fxml'.";

	}

	public void setModel(Model model) {
		this.model = model;
	}
}
