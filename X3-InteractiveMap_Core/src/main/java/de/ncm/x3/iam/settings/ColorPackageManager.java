
package de.ncm.x3.iam.settings;


import java.awt.Image;
import java.awt.Insets;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Properties;

import javax.imageio.ImageIO;

import org.apache.log4j.Logger;

import de.ncm.x3.iam.util.FilePath;

public class ColorPackageManager {
	
	private static final Logger logger = Logger.getLogger(ColorPackageManager.class);
	private static final String folder = "color packs/";
	private static final String developmentPath = "src/main/externalRessources/";
	private static final String propertiesFileName = "pack.properties";
	private static ColorPackageManager instance;
	
	private String actualColorPackage = "";
	private ColorPackageChangedListener colorPackackageChangedListener = null;
	private Properties actualProperties = new Properties();
	private HashMap<Integer, BufferedImage> raceSectorImages = new HashMap<Integer, BufferedImage>();
	private HashMap<Integer, BufferedImage> gateImages = new HashMap<Integer, BufferedImage>();
	private BufferedImage sectorHighlightImage = null;
	private DecimalFormat raceIdFormatter = new DecimalFormat("00");;
	
	public static ColorPackageManager get() {
		if (instance == null) {
			instance = new ColorPackageManager();
		}
		return instance;
		
	}
	
	private ColorPackageManager() {
		setActualColorPackage(PropertyManager.get().getProperty("colorpackage.actual"));
	}
	
	public String[] listColorPackages() {
		String path = getPath();
		ArrayList<String> packList = new ArrayList<String>();
		for (File f : new File(path).listFiles()) {
			if (f.isDirectory()) {
				packList.add(f.getName());
			}
		}
		
		return packList.toArray(new String[packList.size()]);
	}
	
	public void setActualColorPackage(String colorPackage) {
		actualProperties.clear();
		clearImageBuffer();
		
		String propPath = FilePath.createPath(getPath(), colorPackage);
		try {
			actualProperties.load(new FileInputStream(FilePath.createPath(propPath, propertiesFileName)));
			if (colorPackackageChangedListener != null) {
				colorPackackageChangedListener.colorPackageChanged(new ColorPackageChangedEvent(actualColorPackage, colorPackage, actualProperties));
			}
			actualColorPackage = colorPackage;
			PropertyManager.get().setProperty("colorpackage.actual", colorPackage);
		} catch (FileNotFoundException e) {
			logger.error("ColorPackage '" + colorPackage + "' does not exist", e);
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	private void clearImageBuffer() {
		raceSectorImages.clear();
		gateImages.clear();
		sectorHighlightImage = null;
		
	}
	
	public ColorPackageChangedListener getColorPackackageChangedListener() {
		return colorPackackageChangedListener;
	}
	
	public void setColorPackackageChangedListener(ColorPackageChangedListener colorPackackageChangedListener) {
		this.colorPackackageChangedListener = colorPackackageChangedListener;
	}
	
	public String getActualColorPackage() {
		return actualColorPackage;
	}
	
	public Properties getActualColorPackageProperties() {
		return actualProperties;
	}
	
	public Image getSectorImage(Integer id) {
		BufferedImage image = raceSectorImages.get(id); // Buffering Images
		
		if (image == null) {
			File imageFile = new File(FilePath.createPath(getActualImageFolderForRace(id), "Sector.png"));
			try {
				image = ImageIO.read(imageFile);
				raceSectorImages.put(id, image);
			} catch (IOException e) {
				if (imageFile.exists()) {
					logger.error("Unknown Error:", e);
				} else {
					logger.error("File does not exist (File: " + imageFile.getAbsolutePath() + ")");
				}
			}
		}
		return image;
	}
	
	public Image getSectorHighlightImage() {
		if (sectorHighlightImage == null) {
			File imageFile = new File(FilePath.createPath(getActualColorPackagePath(), "images", "Highlight.png"));
			try {
				sectorHighlightImage = ImageIO.read(imageFile);
			} catch (IOException e) {
				if (imageFile.exists()) {
					logger.error("Unknown Error:", e);
				} else {
					logger.error("File does not exist (File: " + imageFile.getAbsolutePath() + ")");
				}
			}
		}
		return sectorHighlightImage;
	}
	
	public Insets getSectorBackgroundImageOffset() {
		return new Insets(5, 5, 5, 5);
	}
	
	private String getPath() {
		String path = folder;
		if (new Boolean(System.getProperty("isDevRunMode"))) {
			path = FilePath.createPath(developmentPath, path);
		}
		return path;
	}
	
	public String getActualColorPackagePath() {
		
		return FilePath.createPath(getPath(), getActualColorPackage());
	}
	
	public String getActualImageFolderForRace(Integer id) {
		String path = getActualColorPackagePath();
		String raceFolder = "race" + raceIdFormatter.format(id);
		path = FilePath.createPath(path, "images", raceFolder);
		
		return path;
	}
	
	public Image getGateImage(Integer id) {
		
		BufferedImage image = gateImages.get(id); // Buffering Images
		
		if (image == null) {
			File imageFile = new File(FilePath.createPath(getActualColorPackagePath(), "images", "gates", raceIdFormatter.format(id) + ".png"));
			try {
				image = ImageIO.read(imageFile);
				gateImages.put(id, image);
			} catch (IOException e) {
				if (imageFile.exists()) {
					logger.error("Unknown Error:", e);
				} else {
					logger.error("File does not exist (File: " + imageFile.getAbsolutePath() + ")");
				}
			}
		}
		return image;
	}
	
	public Image getGateImage(byte id) {
		return getGateImage(new Integer(id));
	}
}
