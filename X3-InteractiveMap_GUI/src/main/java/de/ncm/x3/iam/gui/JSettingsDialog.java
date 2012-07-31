
package de.ncm.x3.iam.gui;


import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.WindowConstants;

import com.jgoodies.forms.factories.FormFactory;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.RowSpec;

import de.ncm.x3.iam.gui.component.verify.JTextFieldFileInputVerifier;
import de.ncm.x3.iam.settings.PropertyManager;
import de.ncm.x3.iam.util.PathBuilder;

public class JSettingsDialog extends JDialog {
	
	private JTextField txtGamePath;
	private JTextField txtLogpath;
	private JButton btnBrowseLogPath;
	private JButton btnBrowseGamePath;
	private JLabel lblLogfilePath;
	private JLabel lblPathToGame;
	private JPanel saveButtonPanel;
	private JButton btnSave;
	private JButton btnCancel;
	
	public JSettingsDialog(Frame comp) {
		super(comp);
		
		setLocationByPlatform(false);
		setTitle("Settings");
		setSize(450, 300);
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		if (comp != null) {
			setLocation(comp.getX() + (comp.getWidth() - getWidth()) / 2, comp.getY() + (comp.getHeight() - getHeight()) / 2);
		}
		
		getContentPane().setLayout(
				new FormLayout(new ColumnSpec[] { FormFactory.RELATED_GAP_COLSPEC, FormFactory.DEFAULT_COLSPEC, FormFactory.RELATED_GAP_COLSPEC,
						ColumnSpec.decode("default:grow"), FormFactory.RELATED_GAP_COLSPEC, FormFactory.DEFAULT_COLSPEC, }, new RowSpec[] {
						FormFactory.RELATED_GAP_ROWSPEC, FormFactory.DEFAULT_ROWSPEC, FormFactory.RELATED_GAP_ROWSPEC, FormFactory.DEFAULT_ROWSPEC,
						FormFactory.RELATED_GAP_ROWSPEC, FormFactory.DEFAULT_ROWSPEC, FormFactory.RELATED_GAP_ROWSPEC, FormFactory.DEFAULT_ROWSPEC,
						FormFactory.RELATED_GAP_ROWSPEC, FormFactory.DEFAULT_ROWSPEC, FormFactory.RELATED_GAP_ROWSPEC, FormFactory.DEFAULT_ROWSPEC,
						FormFactory.RELATED_GAP_ROWSPEC, FormFactory.DEFAULT_ROWSPEC, FormFactory.RELATED_GAP_ROWSPEC, FormFactory.DEFAULT_ROWSPEC,
						FormFactory.RELATED_GAP_ROWSPEC, FormFactory.DEFAULT_ROWSPEC, FormFactory.RELATED_GAP_ROWSPEC, RowSpec.decode("default:grow"), }));
		
		lblLogfilePath = ComponentFactory.createJLabel("JSettingsDialog.lblLogfilePath.label.text");
		getContentPane().add(lblLogfilePath, "2, 2, right, default");
		
		txtLogpath = new JTextField(getLogPathDefaultText());
		getContentPane().add(txtLogpath, "4, 2, fill, default");
		txtLogpath.setColumns(10);
		setupFileTextField(txtLogpath);
		
		btnBrowseLogPath = createLogPathBrowseButton(txtLogpath);
		getContentPane().add(btnBrowseLogPath, "6, 2");
		
		lblPathToGame = ComponentFactory.createJLabel("JSettingsDialog.lblPathToGame.label.text");
		getContentPane().add(lblPathToGame, "2, 4, left, default");
		
		txtGamePath = new JTextField(getGamePathDefaultText());
		getContentPane().add(txtGamePath, "4, 4, fill, default");
		txtGamePath.setColumns(10);
		setupFileTextField(txtGamePath);
		
		btnBrowseGamePath = createGamePathBrowseButton(txtGamePath);
		getContentPane().add(btnBrowseGamePath, "6, 4");
		
		saveButtonPanel = new JPanel();
		FlowLayout flowLayout = (FlowLayout) saveButtonPanel.getLayout();
		flowLayout.setAlignment(FlowLayout.RIGHT);
		getContentPane().add(saveButtonPanel, "2, 18, 5, 1, fill, fill");
		btnSave = ComponentFactory.createJButton("SaveButton.text");
		setupSaveButton(btnSave);
		saveButtonPanel.add(btnSave);
		
		btnCancel = ComponentFactory.createJButton("CancelButton.text");
		setupCancelButton(btnCancel);
		saveButtonPanel.add(btnCancel);
	}
	
	/**
	 * Create the dialog.
	 */
	public JSettingsDialog() {
		this(null);
	}
	
	private void setupCancelButton(JButton button) {
		button.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		
	}
	
	private void setupSaveButton(JButton button) {
		button.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				boolean error = fileTextFieldHasError(txtLogpath) || fileTextFieldHasError(txtGamePath);
				
				if (!error) {
					PropertyManager.get().setLogPath(txtLogpath.getText());
					PropertyManager.get().setGameFolder(txtGamePath.getText());
					dispose();
				}
			}
		});
		
	}
	
	private boolean fileTextFieldHasError(JTextField field) {
		return ((JTextFieldFileInputVerifier) field.getInputVerifier()).hasError();
	}
	
	private String getLogPathDefaultText() {
		String path = PropertyManager.get().getUniverseMapLogFile();
		if (!new File(path).exists()) {
			path = PathBuilder.createPath(System.getProperty("user.home"), "Documents", "Egosoft", "X3TC");
			if (!new File(path).exists()) {
				path = System.getProperty("user.home");
			}
		}
		return path;
	}
	
	private String getGamePathDefaultText() {
		String path = PropertyManager.get().getGameFolder();
		
		return path;
	}
	
	private JButton createLogPathBrowseButton(final JTextField logPathField) {
		
		PropertyChangeListener logpathChangeListener = new PropertyChangeListener() {
			
			@Override
			public void propertyChange(PropertyChangeEvent evt) {
				logPathField.setText(((File) evt.getNewValue()).getAbsolutePath());
			}
		};
		JButton button = ComponentFactory.createBrowseButtonTextFieldBinding(logPathField, Messages.getString("JSettingsDialog.chooseLogPath.title"),
				JFileChooser.DIRECTORIES_ONLY, logpathChangeListener);
		
		return button;
		
	}
	
	private JButton createGamePathBrowseButton(final JTextField gamePathField) {
		
		PropertyChangeListener gamePathChangeListener = new PropertyChangeListener() {
			
			@Override
			public void propertyChange(PropertyChangeEvent evt) {
				gamePathField.setText(((File) evt.getNewValue()).getAbsolutePath());
			}
		};
		JButton button = ComponentFactory.createBrowseButtonTextFieldBinding(gamePathField, Messages.getString("JSettingsDialog.chooseGamePath.title"),
				JFileChooser.DIRECTORIES_ONLY, gamePathChangeListener);
		
		return button;
		
	}
	
	private void setupFileTextField(JTextField textField) {
		textField.setInputVerifier(new JTextFieldFileInputVerifier());
		
	}
	
}
