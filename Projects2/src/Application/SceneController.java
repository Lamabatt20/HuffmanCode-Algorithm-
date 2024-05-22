package Application;

import java.io.File;
import java.io.IOException;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.scene.control.Alert;
import javafx.scene.control.TextArea;

public class SceneController {
	
	private ObservableList<HuffmanCode> list;//intialize  ObservableList
	private static HuffmanCode[] huffmancode;//intialize array huffman code 
	@FXML
    private TableColumn<HuffmanCode, Integer> ASCII;
    @FXML
    private TableColumn<HuffmanCode, Character> Char;

    @FXML
    private TableColumn<HuffmanCode, Integer> Frequency;

    @FXML
    private TableColumn<HuffmanCode, String> huffmanCode;

    @FXML
    private TableColumn<HuffmanCode, Integer> Length;

    @FXML
    private TableView<HuffmanCode> Tabelhuffman;
	@FXML
	private Label labelperc;
	@FXML
	private TextField textfield1;
	@FXML
	private TextArea textarea;
	@FXML
	private TextField textfield2;
	@FXML
    private TextField textperc;
	@FXML
    private Label messat;
	@FXML
    private Label messat1;
	private File file;//intialize file compress
	private File file1;//intialize file decompress
	
	//intialize
	public void initialize() {
		huffmancode = new HuffmanCode[256];//intialize size array huffman 
		list = FXCollections.observableArrayList();
		Char.setCellValueFactory(new PropertyValueFactory<HuffmanCode, Character>("Char"));
		huffmanCode.setCellValueFactory(new PropertyValueFactory<HuffmanCode, String>("huffmanCode"));
		Length.setCellValueFactory(new PropertyValueFactory<HuffmanCode, Integer>("Length"));
		ASCII.setCellValueFactory(new PropertyValueFactory<HuffmanCode, Integer>("ASCII"));
		Frequency.setCellValueFactory(new PropertyValueFactory<HuffmanCode, Integer>("Frequency"));
		
		     
	}
	//Handle for choose compress file
	@FXML
	public void filebutton(ActionEvent event) {
		FileChooser fileChooser = new FileChooser();
	    file = fileChooser.showOpenDialog(textfield1.getScene().getWindow());

		if (file != null) {
			String filePath = file.getPath();
			textfield1.setText(filePath);
		} else {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Error");
			alert.setContentText("No file selected");
			alert.showAndWait();
		}

	}
	//Handle for compress file 
	@FXML
	public void compress(ActionEvent event) throws Exception {
		messat.setVisible(true);
		if (file!=null) {
		   Huffman.compression(file);
		   messat.setText("Compression successful");
		}else {
		   messat.setText("Compression not successful,try again");
		}
		
	     

	}
	//Handle for choose decompress file
	@FXML
	public void filebutton2(ActionEvent event) {
		FileChooser fileChooser = new FileChooser();
		ExtensionFilter filter = new ExtensionFilter("Huff Files (*.huff)", "*.huff");
		fileChooser.getExtensionFilters().add(filter);
	    file1 = fileChooser.showOpenDialog(textfield2.getScene().getWindow());

		if (file1 != null&&file1.getName().endsWith("huff")) {
			String filePath = file1.getAbsolutePath();
			textfield2.setText(filePath);
		} else {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Error");
			alert.setContentText("You should select files with extention .huff,Please try again");
			alert.showAndWait();
		}

	}
	//Handle for decompress file 
	@FXML
	public void decompress(ActionEvent event) throws Exception {
		messat1.setVisible(true);
		if (file1!=null) {
			Huffman.deCompression(file1);
			messat1.setText("Decompression successful");
		}else {
			messat1.setText("Decompression not successful,try again");
			
		}

	}
	//Handle for display header info in a text area 
	@FXML
    public void headerb(ActionEvent event) throws IOException {
		textarea.setText("");
		textperc.setText("");
		textarea.setVisible(true);
		textperc.setVisible(true);
		labelperc.setVisible(true);
		Tabelhuffman.setVisible(false);
		if (this.file!=null) {
			textarea.appendText(Huffman.header(file.getPath(),(int)file.length(),textperc));
		}else {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Error");
			alert.setContentText("Error, please check file");
			alert.showAndWait();
		}
		
		

    }
	//Handle for display huffman code in a tabelview
    @FXML
    public void stab(ActionEvent event) {
    	Tabelhuffman.getItems().clear();
    	Tabelhuffman.setVisible(true);
    	textperc.setVisible(false);
		labelperc.setVisible(false);
		for (int i = 0; i < Huffman.huffmancode.length; i++) {
	        if ( Huffman.huffmancode[i] != null && Huffman.huffmancode[i].ASCII != '\t' && Huffman.huffmancode[i].ASCII!= '\n') {
	            list.add(new HuffmanCode(
	            		Huffman.huffmancode [i].getChar(),
	            		Huffman.huffmancode [i].getFrequency(),
	            		Huffman.huffmancode[i].getHuffmanCode(),
	            		Huffman.huffmancode[i].getLength(),
	            		(int)Huffman.huffmancode[i].Char
	        
	            ));
	        }
	    }
		Tabelhuffman.setItems(list);
		
    }
   

}
