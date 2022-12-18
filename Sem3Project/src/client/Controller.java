package client;

import java.awt.image.RenderedImage;
import java.io.File;
import java.io.IOException;
import java.util.ListIterator;
import java.util.NoSuchElementException;

import javax.imageio.ImageIO;

import client.model.AuthUpdatable;
import client.model.GraphUpdatable;
import client.model.IAuth;
import client.model.IGraph;
import client.model.ModelF;
import javafx.application.Platform;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.layout.Pane;
import javafx.scene.canvas.*;
import javafx.scene.paint.*;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import utility.AuthData;
import utility.AuthDataEnum;
import utility.VectorData;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.WritableImage;
import javafx.scene.input.MouseEvent;


public class Controller {

	@FXML
	private Pane login_pane;
	@FXML
	private TextField login_text_login;
	@FXML
	private PasswordField login_text_pass;
	@FXML
	private Button login_btn_login;
	@FXML
	private Button login_btn_reg;
	@FXML
	private Label login_lbl_message;
	
	@FXML
	private Pane app_pane;
	@FXML
	private Canvas app_canvas;
	@FXML
	private Button app_btn_path;
	@FXML
	private Button app_btn_clear;
	@FXML
	private Button app_btn_save;
	@FXML
	private Label app_lbl_dir;
	@FXML
	private Button app_btn_incsize;
	@FXML
	private Button app_btn_decsize;
	
	private boolean mClick_lock_print = false;
	
	private GraphicsContext gc; 
	private double CanvasDotSize = 5.0;
	
	final double CanvasDotSizeMax = 10.0;
	final double CanvasDotSizeMin = 0.5;
	
	FileChooser dirChooser = new FileChooser();
	File selectedDir;
	
	private IGraph graphM;
	private IAuth authM;
	
	public void initialize() {
		
		graphM = ModelF.createGraphModel();
		authM = ModelF.createAuthModel();
		
		gc = app_canvas.getGraphicsContext2D();
		ClearCanvas();
		
		dirChooser.setInitialDirectory(new File("."));
		
		app_canvas.addEventHandler(MouseEvent.MOUSE_CLICKED, 
			new EventHandler<MouseEvent>() {
			 @Override
	            public void handle(MouseEvent e) {
				 	if (!mClick_lock_print) {
				 	gc.setFill(Color.BLACK);
				 	gc.fillOval(e.getX(), e.getY(), CanvasDotSize/2, CanvasDotSize/2);
				 	graphM.addNode(e.getX(), e.getY());
				 	}
			 	}
			}
		);
	
	}
	
	@FXML
	private void Login() {
		authM.login(login_text_login.getText(), login_text_pass.getText(), new AuthUpdatable() {
			
			@Override
			public void update(AuthData updatable) {
				Platform.runLater(new Runnable() {
					
					@Override
					public void run() {
						if (updatable.param == AuthDataEnum.allow) {
							login_pane.setVisible(false);
							login_pane.setDisable(true);
							app_pane.setDisable(false);
							app_pane.setVisible(true);
						}else if(updatable.param == AuthDataEnum.deny) {
							login_lbl_message.setText("Cannot login with this name and password");
						}
					}
				});
				
			}
		});
	}
	@FXML
	private void Register() {
		authM.register(login_text_login.getText(), login_text_pass.getText(), new AuthUpdatable() {
			
			@Override
			public void update(AuthData updatable) {
				Platform.runLater(new Runnable() {
					
					@Override
					public void run() {
						if (updatable.param == AuthDataEnum.allow) {
							login_lbl_message.setText("New user created");
						}else if(updatable.param == AuthDataEnum.deny) {
							login_lbl_message.setText("Cannot create a new user with this name and password");
						}
					}
				});
				
			}
		});
	}
	
	@FXML
	private void ClearCanvas() {
		gc.setFill(Color.WHITE);
		gc.fillRect(0, 0, app_canvas.getWidth(), app_canvas.getHeight());
		graphM.clearNodes();
		mClick_lock_print = false;
	}
	@FXML
	private void IncreaseDotSize() {
		if (CanvasDotSize < CanvasDotSizeMax) CanvasDotSize += 0.5;
	}
	@FXML
	private void DecreaseDotSize() {
		if (CanvasDotSize > CanvasDotSizeMin) CanvasDotSize -= 0.5;
	}
	@FXML
	private void SolvePath() {
		graphM.requestSolve(new GraphUpdatable() {
			
			@Override
			public void update(VectorData _VD) {
				Platform.runLater(new Runnable() {
					@Override
					public void run() {
						mClick_lock_print = true;
						ListIterator<Double> i = _VD.getList().listIterator();
						if (i == null || !i.hasNext())  return;
						gc.setStroke(Color.BLACK);
						double xO,yO,x0, y0, x1, y1;
						try {
							xO = x0 = i.next();	yO = y0 = i.next();
							x1 = i.next(); 	y1 = i.next();
							gc.strokeLine(x0, y0, x1, y1);
						}catch (NoSuchElementException e) {
							return;
						}
						while (i.hasNext()) {
							try {
								x0 = x1; y0 =  y1;
								x1 = i.next(); y1 = i.next();
								gc.strokeLine(x0, y0, x1, y1);
							}catch (NoSuchElementException e) {
								break;
							}
						}
						gc.strokeLine(x1,y1,xO,yO);
					}
				});
			}
		});
	}
	@FXML
	private void SavePath() {
		if (selectedDir != null) {
		WritableImage wImage = new WritableImage((int) app_canvas.getWidth(), (int) app_canvas.getHeight());
		app_canvas.snapshot(null, wImage);
		RenderedImage rImage = SwingFXUtils.fromFXImage(wImage, null);
		try {
			ImageIO.write(rImage, "png", selectedDir);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		}
	}
	@FXML
	private void ChooseDir() {
		dirChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("png files (*.png)", "*.png"));
		dirChooser.setInitialFileName("graph");
		selectedDir = dirChooser.showSaveDialog(new Stage());
		if (selectedDir != null)
		app_lbl_dir.setText(selectedDir.getAbsolutePath());
	}	
}

