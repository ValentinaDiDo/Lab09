package it.polito.tdp.borders;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultEdge;

import it.polito.tdp.borders.model.Country;
import it.polito.tdp.borders.model.Model;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class FXMLController {
	Model model = new Model();
    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private ComboBox<Country> cmbPaese;

    @FXML
    private TextField txtAnno;

    @FXML
    private TextArea txtResult;

    @FXML
    void cercaStatiRaggiungibili(ActionEvent event) {

    	Country paese = cmbPaese.getValue();
    	
    	String sAnno = txtAnno.getText();
    	int anno = Integer.parseInt(sAnno);
    	
    	this.model.creaGrafo(anno);
    	Graph<Country, DefaultEdge> grafo = this.model.getGrafo();
    	
    	if(!grafo.vertexSet().contains(paese)) {
    		txtResult.setText("IL PAESE SELEZIONATO NON HA CONFINI VIA TERRA");
    		
    	}else if(Graphs.neighborListOf(grafo, paese).size() == 0){
    		txtResult.setText("IL PAESE SELEZIONATO NON HA CONFINI VIA TERRA");
    		
    	}else {
    		
    	List<Country> statiRaggiungibili = this.model.cercaStatiRaggiungibili(paese);
    	txtResult.appendText("METODO 1 : NUMERO DI STATI RAGGIUNGIBILI DA: "+paese.getName()+" = "+statiRaggiungibili.size());
    	/*for(Country c : statiRaggiungibili) {
    		txtResult.appendText("\n"+c.getName());
    	}*/
    	
    //  IL METODO 2 E' ESTREMAMENTE LENTO -> RISCHIO CHE ITERI ALL'INFITITO (IN OGNI CASO NON ERA MOLTO SMART)	
    //	List<Country> statiRaggiungibili2 = this.model.cercaStatiRaggiungibili2(paese);
    //	txtResult.appendText("\nMETODO 2 : NUMERO DI STATI RAGGIUNGIBILI DA: "+paese.getName()+" = "+statiRaggiungibili2.size());
   
    	List<Country> statiRaggiungibili3 = this.model.cercaStatiRaggiungibili3(paese);
    	txtResult.appendText("\nMETODO 3 : NUMERO DI STATI RAGGIUNGIBILI DA: "+paese.getName()+" = "+statiRaggiungibili3.size());
    	}
    }

    @FXML
    void doCalcolaConfini(ActionEvent event) {

    	String sAnno = txtAnno.getText();
    	if(sAnno.equals(""))
    		txtResult.setText("inserisci un anno");
    	int anno = Integer.parseInt(sAnno);
    	if(anno < 1816 || anno > 2016)
    		txtResult.setText("Inserisci un anno valido compleso tra 1816 e 2016");
    	else
    		txtResult.setText("Anno inserito valido\n");
    	
    	
    	//chiamo metodo nel model
    	model.creaGrafo(anno);
    	//prendo grafo
    	Graph<Country, DefaultEdge> grafo = this.model.getGrafo();
    	//stampo varie cose
    	for(Country c : grafo.vertexSet()) {
    		txtResult.appendText("Numero archi "+c.getName()+": "+grafo.degreeOf(c)+"\n");
    	}
    }

    @FXML
    void initialize() {
        assert cmbPaese != null : "fx:id=\"cmbPaese\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtAnno != null : "fx:id=\"txtAnno\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'Scene.fxml'.";

    }

	public void setModel(Model model) {
		this.model = model;
		

		cmbPaese.getItems().clear();
		for(Country c : model.getCountries()) {
			cmbPaese.getItems().add(c);
		}
	}

   
}
