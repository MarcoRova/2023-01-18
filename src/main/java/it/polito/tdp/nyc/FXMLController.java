/**
 * Sample Skeleton for 'Scene.fxml' Controller Class
 */

package it.polito.tdp.nyc;

import java.net.URL;
import java.util.Collections;
import java.util.List;
import java.util.ResourceBundle;
import it.polito.tdp.nyc.model.Model;
import it.polito.tdp.nyc.model.Vicini;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class FXMLController {

	private Model model;
	
    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="btnAnalisi"
    private Button btnAnalisi; // Value injected by FXMLLoader

    @FXML // fx:id="btnCreaGrafo"
    private Button btnCreaGrafo; // Value injected by FXMLLoader

    @FXML // fx:id="btnPercorso"
    private Button btnPercorso; // Value injected by FXMLLoader

    @FXML // fx:id="cmbProvider"
    private ComboBox<String> cmbProvider; // Value injected by FXMLLoader

    @FXML // fx:id="txtDistanza"
    private TextField txtDistanza; // Value injected by FXMLLoader

    @FXML // fx:id="txtResult"
    private TextArea txtResult; // Value injected by FXMLLoader

    @FXML // fx:id="txtStringa"
    private TextField txtStringa; // Value injected by FXMLLoader
    
    @FXML // fx:id="txtTarget"
    private ComboBox<String> txtTarget; // Value injected by FXMLLoader

    @FXML
    void doAnalisiGrafo(ActionEvent event) {
    	
    	this.txtResult.appendText("\n\nVERTICI CON PIU' VICINI:");
    	
    	List<Vicini> vicini = this.model.doAnalisi();
    	
    	for(Vicini v : vicini) {
    		this.txtResult.appendText("\n"+v.getLoc()+" #vicini: "+v.getVicini());
    	}
    	
    }

    @FXML
    void doCalcolaPercorso(ActionEvent event) {
    	
    	this.txtResult.clear();
    	
    	String target = this.txtTarget.getValue();
    	
    	if(target == null) {
    		this.txtResult.appendText("Selezionare una località target per continuare.");
    		return;
    	}
    	
    	String evita = this.txtStringa.getText();
    	
    	if(evita.isEmpty() || evita.isBlank()) {
    		this.txtResult.appendText("Inserire una stringa per continuare.");
    		return;
    	}
    	
    	
    	List<String> percorso = this.model.findPath(target, evita);
    	
    	for(String s : percorso) {
    		this.txtResult.appendText("\n"+s);
    	}
    	
    	
    }

    @FXML
    void doCreaGrafo(ActionEvent event) {
    	
    	this.txtResult.clear();
    	
    	String provider = this.cmbProvider.getValue();
    	
    	if(provider == null) {
    		this.txtResult.appendText("Selezionare provider per continuare.");
    		return;
    	}
    	
    	Double x = 0.0;
    	
    	try {
    		x= Double.parseDouble(this.txtDistanza.getText());
    		
    		if(x<=0){
    			this.txtResult.setText("Inserire un numero positivo.");
    			return;
    		}
    	}catch(NumberFormatException e) {
    		txtResult.setText("Formato non corretto per la distanza.");
    		return;
    	}
    	
    	this.model.creaGrafo(provider, x);
    	
    	this.txtResult.appendText(this.model.infoGrafo());
    	
    	this.btnAnalisi.setDisable(false);
    	this.btnPercorso.setDisable(false);	
    	
    	List<String> localita = this.model.getVertici();
    	
    	Collections.sort(localita);
    	
    	this.txtTarget.getItems().addAll(localita);
    }

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert btnAnalisi != null : "fx:id=\"btnAnalisi\" was not injected: check your FXML file 'Scene.fxml'.";
        assert btnCreaGrafo != null : "fx:id=\"btnCreaGrafo\" was not injected: check your FXML file 'Scene.fxml'.";
        assert btnPercorso != null : "fx:id=\"btnPercorso\" was not injected: check your FXML file 'Scene.fxml'.";
        assert cmbProvider != null : "fx:id=\"cmbProvider\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtDistanza != null : "fx:id=\"txtDistanza\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtStringa != null : "fx:id=\"txtStringa\" was not injected: check your FXML file 'Scene.fxml'.";

    }

    public void setModel(Model model) {
    	this.model = model;
    	this.btnAnalisi.setDisable(true);
    	this.btnPercorso.setDisable(true);
    	this.cmbProvider.getItems().addAll(this.model.getProvider());
    }
}
