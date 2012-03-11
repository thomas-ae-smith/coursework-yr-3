//package lights.gui;
//
//import java.awt.Color;
//import java.awt.DisplayMode;
//import java.awt.GraphicsDevice;
//import java.awt.GridBagLayout;
//import java.awt.GridLayout;
//import java.awt.KeyboardFocusManager;
//import java.awt.LayoutManager;
//import java.awt.event.ComponentEvent;
//import java.awt.event.ComponentListener;
//import java.awt.event.WindowEvent;
//import java.awt.event.WindowListener;
//import java.awt.image.BufferedImage;
//import java.io.File;
//import java.io.FilenameFilter;
//import java.io.IOException;
//import java.util.ArrayList;
//import java.util.Iterator;
//import java.util.List;
//
//import javax.imageio.ImageIO;
//import javax.swing.ImageIcon;
//import javax.swing.JComboBox;
//import javax.swing.JFrame;
//import javax.swing.JOptionPane;
//import javax.swing.JPanel;
//import javax.swing.JWindow;
//
//import lights.categories.Category;
//import lights.colour.BasicColourConstants;
//import lights.colour.DayNightColours.SceneColours;
//import lights.controller.KeyDispatcher;
//import lights.glossary.GlossaryWindow;
//import lights.model.AirportModel;
//import lights.quiz.QuizWindow;
//import lights.settings.GraphicsSettingsGUI;
//import lights.settings.Settings;
//import lights.viewer.Camera;
//import lights.viewer.MiniMap;
//import lights.viewer.ViewerComposite;
//
///**
// * Allows the use of parameters to start LIGHTS with a number of options already
// * selected: eg: lights -airport=complex.apx -category=cat3
// * -operationallydesired -day -3d -camera=runway1 -fullscreen
// * 
// * @author pw9g09
// * 
// */
//@SuppressWarnings("serial")
//public class GUIMainWindow extends JFrame implements ComponentListener,
//		WindowListener {
//
//	public static File airportDirectory = new File(Settings.airportPath);
//
//	/**
//	 * JPanel shown on start that allows selecting of airport
//	 */
//	private AirportSelect airportSelect;
//
//	private GlossaryWindow glossary;
//	private JPanel contentPane;
//	private JFrame settings, quiz;
//	public JWindow minimap;
//	private Toolbar toolbar;
//
//	private ViewerComposite viewer;
//
//	private AirportModel airportModel;
//
//	private boolean fullscreen;
//	
//	private LayoutManager oldLayout;
//
//	private boolean minimapShown = false;
//
//	private boolean toolbarShown = false;
//
//	private boolean openedAirport = false;
//
//	private Loading loading;
//
//	private boolean showMinimap = true;
//
//	private ControllPrompt help;
//
//	private boolean loadingByParam = false;
//
//	private static FilenameFilter airportFilenameFilter = new FilenameFilter() {
//		public boolean accept(File dir, String name) {
//			return name.endsWith(".apx");
//		}
//	};
//
//	public GUIMainWindow(AirportModel airportModel, String[] args) {
//		// TODO: Handle arguments (eg: lights -airport=complex.apx -category=cat3 -operationallydesired -day -3d -camera=runway1 -fullscreen)
//
//		MiniMap m = new MiniMap();
//		minimap = new JWindow();
//		minimap.add(m);
//		minimap.setSize(100, 100);
//		minimap.setAlwaysOnTop(true);
//		// minimap.setUndecorated(true);
//
//		this.viewer = new ViewerComposite(m);
//		this.viewer.setGUI(this);
//		this.airportModel = airportModel;
//
//		
//		// Make the GUI
//
//		loading = new Loading();
//		
//		settings = new GraphicsSettingsGUI(viewer, this);
//		
//		glossary = new GlossaryWindow(viewer);
//
//		quiz = new QuizWindow();
//		
//		help = new ControllPrompt();
//
//		makeGUI();
//
//		parseArguments(args);
//		
//
//		this.setTitle("LIGHTS - Airport Class Training Tool");
//		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//
//		this.addComponentListener(this);
//		this.addWindowListener(this);
//
//		this.setFocusable(true);
//		this.pack();
//		this.setSize(1024, 630);
//		
//		BufferedImage icon = null;
//		try {
//			icon = ImageIO.read(new File(Settings.texturePath+"icon.png"));
//		} catch (IOException e){
//			e.printStackTrace();
//		}
//		this.setIconImage(icon);
//	    this.setLocationRelativeTo(null);
//		this.setVisible(true);
//		alignFrames();
//
//
//		viewer.getViewerGraphics().annotations().getPickHandler().setGlossaryWindow(glossary);
//		
//		KeyboardFocusManager.getCurrentKeyboardFocusManager()
//				.addKeyEventDispatcher(new KeyDispatcher(this));
//		
//
//		Settings.fogOverlay.setColour(viewer.getViewerGraphics().getDayNightColours().get(SceneColours.FOG));
//		Settings.cockpitOverlay.setColour(viewer.getViewerGraphics().getDayNightColours().get(SceneColours.COCKPIT));
//		Settings.buildingShape.setColour(BasicColourConstants.blue.translucent(0.35f));
//		Settings.buildingShape.setOutline(true);
//		
//	}
//
//	private void parseArguments(String[] args) {
//		String airport = null;
//		for (String arg : args) {
//
//			if (arg.matches("^-airport=([a-zA-Z0_9]+)?.apx$")) {
//				openAirport(arg.substring(arg.indexOf('=') + 1), false);
//
//				showLoading();
//
//				airport = arg.substring(arg.indexOf('=') + 1);
//				loadingByParam = true;
//
//			} else if (arg.matches("^-category=[a-zA-Z0-9-]+$")) {
//				// TODO: HACK replacing - width space
//				String categoryStr = arg.substring(arg.indexOf('=') + 1).replace('-', ' ');
//				Category category = null;
//				for (Category cat : Category.CategoryList) {
//					//System.out.println(cat.toString().toLowerCase() + " == " + categoryStr.toLowerCase());
//					if (cat.toString().toLowerCase().equals(categoryStr.toLowerCase())) {
//						category = cat;
//						break;
//					}
//				}
//				
//				if (category == null) {
//					// TODO: Open xml category
//				}
//
//				if (category != null)
//					getViewer().setCategory(category);
//				else
//					System.err.println("Unrecognised category \"" + categoryStr + "\".");
//
//			} else if (arg.matches("^-operationallydesired$")) {
//				Category.setOperationallyDesirable(true);
//				
//			} else if (arg.matches("^-day$"))
//				viewer.setDay(true);
//
//			else if (arg.matches("^-night$"))
//				viewer.setDay(false);
//
//			//else if (arg.matches("^-camera=[a-zA-Z0-9]+$"))
//				//System.out.println("!");// TODO: camera =
//										// arg.substring(arg.indexOf('='));
//
//			else if (arg.matches("^-fullscreen+$"))
//				setFullscreen(true);
//
//			else
//				System.err.println("Unknown argument: " + arg);
//
//		}
//		
//		if (airport != null)
//			openAirport(airport, true);
//	}
//
//	/**
//	 * Create and display all elements of the GUI.
//	 */
//	private void makeGUI() {
//
//		airportSelect = new AirportSelect(this);
//		getContentPane().setBackground(Color.black);
//		getContentPane().add(airportSelect);		
//		
//		oldLayout = getContentPane().getLayout();
//		
//		contentPane = new JPanel(new GridLayout(1, 1));
//		contentPane.setVisible(false);
//		add(contentPane);
//
//		toolbar = new Toolbar(this);
//		contentPane.add(viewer);
//		getContentPane().setLayout(new GridBagLayout());
//	}
//
//	public void updateZoomSlider() {
//		toolbar.updateZoomSlider();
//		//viewer.repaint();
//	}
//	
//
//
//	public void updateRotateSlider() {
//		toolbar.updateRotateSlider();
//		//viewer.repaint();
//	}
//
//	public void showQuiz(boolean b) {
//		quiz.setVisible(b);
//		quiz.setAlwaysOnTop(true);
//		quiz.setLocationRelativeTo(this);
//	}
//	
//	public void showSettings(boolean b) {
//		settings.setVisible(b);
//		settings.setAlwaysOnTop(true);
//		settings.setLocationRelativeTo(this);
//	}
//
//	/**
//	 * This should all get refactored into the controller
//	 * 
//	 * @param file
//	 */
//	public void openAirport(File file) {
//		Settings.start = System.nanoTime();
//		boolean day = viewer.getDay();
//		viewer.setDay(true);
//		toolbar.rDay.setSelected(true);
//		airportModel = new AirportModel(file);
//		viewer.setAirportModel(airportModel);
//		viewer.generateScene();
//		viewer.generateCameras();
//
//
//		//System.out.println("Start camera->toolbar");
//		double start = System.nanoTime();
//		toolbar.cCamera.removeAllItems();
//		Iterator<Camera> cameraIterator = viewer.getCameras().iterator();
//		while (cameraIterator.hasNext()) {
//			toolbar.cCamera.addItem(cameraIterator.next());
//		}
//
//		//System.out.println("End camera->toolbar: " + (System.nanoTime() - start) / 1000000);
//
//		refreshMiniMap();
//		if (!day) {
//			//System.out.println("Make night");
//			viewer.setDay(false);
//			viewer.draw();
//		}
//		viewer.repaint();
//	}
//
//	public static List<String> getListOfAirports() {
//		ArrayList<String> airports = new ArrayList<String>();
//
//		if (GUIMainWindow.airportDirectory.exists()) {
//			for (String file : GUIMainWindow.airportDirectory
//					.list(GUIMainWindow.airportFilenameFilter)) {
//				airports.add(file);
//			}
//		} else {
//			System.err.println("Could not find directory.");
//		}
//
//		return airports;
//	}
//	
//	
//	public JComboBox generateAirportComboBox() {
//		JComboBox airportsList = new JComboBox();
//		
//		List<String> airports = GUIMainWindow.getListOfAirports();
//		
//		Iterator<String> airportIterator = airports.iterator();
//		
//		int index = 0;
//		while (airportIterator.hasNext()) {
//			String airportFileName = airportIterator.next();
//			
//			int curFNameLen = airportFileName.length() - 4;
//			String curName = airportFileName.substring(0, curFNameLen);
//			
//			if (curName.equals("ultra-simple")) {
//				airportsList.addItem("<html><strong>" + curName + "</strong><font size=-2><br>Small, single runway airport</font></html>");
//			} else if (curName.equals("simple")) {
//				airportsList.addItem("<html><strong>" + curName + "</strong><font size=-2><br>Medium, 2 perpendicular runway airport</font></html>");
//			} else if (curName.equals("standard")) {
//				airportsList.addItem("<html><strong>" + curName + "</strong><font size=-2><br>Medium, 2 parallel runway airport</font></html>");
//			} else if (curName.equals("complex")) {
//				airportsList.addItem("<html><strong>" + curName + "</strong><font size=-2><br>Large, 4 runway airport and taxiways</font></html>");
//			} else if (curName.equals("southampton")) {
//				airportsList.addItem("<html><strong>" + curName + "</strong><font size=-2><br>Small, 1 runway airport and taxiways</font></html>");
//			} else {
//				airportsList.addItem(curName);
//			}
//			index++;
//		}
//		
//		return airportsList;
//	}
//
//	public void openAirport(String name, boolean threaded) {
//		if (threaded)
//			(new Thread(new OpenAirportThread(this, name))).start();
//		else
//			openAirport(new File((String) airportDirectory.getAbsolutePath() + "/"
//					+ name));
//	}
//
//	public void openAirportFromThread(String name) {
//		openAirport(new File((String) airportDirectory.getAbsolutePath() + "/"
//				+ name));
//
//		openedAirport = true;
//		getContentPane().setLayout(oldLayout);
//		contentPane.setVisible(true);
//	}
//
//	public void setFullscreen(boolean fullscreen) {
//		if (true) {
//			JOptionPane.showMessageDialog(this, "Full screen is not supported at this resolution.");
//			return;
//		}
//		// FIXME: "Fullscreen not supported"?
//		GraphicsDevice screen = this.getGraphicsConfiguration().getDevice();
//
//		if (!screen.isFullScreenSupported())
//			System.out.println("Fullscreen not supported");
//		if (this.fullscreen == fullscreen)
//			return;
//
//		this.fullscreen = fullscreen;
//
//		dispose();
//		if (fullscreen) {
//			setUndecorated(true);
//			setResizable(false);
//
//			// Note - you may have set -Dsun.java2d.d3d=false
//			// (In eclipse: Project -> Properties -> Run/Debug Settings ->
//			// AiportController -> Arguments -> Put -Dsun.java2d.d3d=false into
//			// the VM arguments box)
//			// screen.
//			screen.setFullScreenWindow(this);
//			try {
//				if (toolbar.cResolution.getSelectedIndex() > 0)
//					screen.setDisplayMode((DisplayMode) toolbar.cResolution
//							.getSelectedItem());
//			} catch (Exception e) {
//				System.err.println(e.getMessage());
//				return;
//			}
//			setVisible(true);
//		} else {
//			setUndecorated(false);
//			setResizable(true);
//			screen.setFullScreenWindow(null);
//			setVisible(true);
//			viewer.generateScene();
//			viewer.draw();
//			viewer.repaint();
//		}
//	}
//
//	public void toggleFullscreen() {
//		setFullscreen(!fullscreen);
//	}
//
//	public ViewerComposite getViewer() {
//		return viewer;
//	}
//
//	public void showGlossary(boolean b) {
//		glossary.setVisible(b);
//		glossary.showDefn();
//	}
//
//	@Override
//	public void componentMoved(ComponentEvent arg0) {
//		alignFrames();
//	}
//
//	@Override
//	public void componentResized(ComponentEvent arg0) {
//		alignFrames();
//	}
//
//	private void alignFrames() {
//		toolbar.setBounds(getX() + getWidth() - toolbar.getWidth() - 50,
//				getY() + 50, toolbar.getWidth(), toolbar.getHeight());
//		minimap.setBounds(getX() + 50,
//				getY() + getHeight() - minimap.getHeight() - 50,
//				minimap.getWidth(), minimap.getHeight());
//		glossary.setBounds(getX() + 50, getY() + 50, glossary.getWidth(),
//				glossary.getHeight());
//		loading.setLocation(getX() + (getWidth() / 2) - (loading.getWidth() / 2),
//				getY() + (getHeight() / 2) - (loading.getHeight() / 2) + 30);
//		help.setLocation(getX() + (getWidth() / 2) - (help.getWidth() / 2),
//				getY() + (getHeight() / 2) - (help.getHeight() / 2));
//	}
//
//	@Override
//	public void windowActivated(WindowEvent arg0) {
//		toolbar.setVisible(toolbarShown);
//		minimap.setVisible(minimapShown && showMinimap);
//		glossary.setVisible(glossary.isShown());
//		//settings.setState(NORMAL);
//	}
//
//	@Override
//	public void windowDeactivated(WindowEvent arg0) {
//		//System.out.println("io");
//		toolbar.setVisible(false);
//		minimap.setVisible(false);
//		glossary.setVisible(false);
//		//settings.setState(ICONIFIED);
//	}
//
//	@Override
//	public void windowDeiconified(WindowEvent arg0) {
//		toolbar.setVisible(toolbarShown);
//		minimap.setVisible(minimapShown && showMinimap);
//		glossary.setVisible(glossary.isShown());
//		settings.setState(NORMAL);
//		quiz.setState(NORMAL);
//	}
//
//	@Override
//	public void windowIconified(WindowEvent arg0) {
//		toolbar.setVisible(false);
//		minimap.setVisible(false);
//		glossary.setVisible(false);
//		settings.setState(ICONIFIED);
//		quiz.setState(NORMAL);
//	}
//
//	@Override
//	public void windowClosed(WindowEvent e) {
//	}
//
//	@Override
//	public void windowClosing(WindowEvent e) {
//	}
//
//	@Override
//	public void windowOpened(WindowEvent e) {
//	}
//
//	@Override
//	public void componentHidden(ComponentEvent arg0) {
//		
//	}
//
//	@Override
//	public void componentShown(ComponentEvent arg0) {
//		
//	}
//
//	public void loaded() {
//		//if (openedAirport) {
//			help.setVisible(true);
//			toolbar.setVisible(true);
//			toolbarShown = true;
//			minimap.setVisible(true && showMinimap);
//			minimapShown = true;
//			loading.setVisible(false);
//			refreshMiniMap();
//			//System.out.println("Finished: " + (System.nanoTime() - Settings.start) / 1000000);
//			toolbar.selectCategory(viewer.getCategory());
//			toolbar.updateRotateSlider();
//			toolbar.updateZoomSlider();
//			if (loadingByParam )
//				toolbar.selectAirport(0);
//		//}
//
//	}
//
//	public void selectAirport(int selectedIndex) {
//		toolbar.selectAirport(selectedIndex);
//	}
//
//	public void showLoading() {
//		loading.setVisible(true);
//		airportSelect.setVisible(false);
//	}
//	
//	public void showHelp() {
//		help.setVisible(true);
//	}
//
//	public void refreshMiniMap() {
//		viewer.generateOverview();
//		minimap.repaint();
//	}
//
//	public void showMinimap(boolean minimap2) {
//		showMinimap = minimap2;
//		minimap.setVisible(minimapShown && minimap2);
//	}
//
//	public File getCurrentAirportFile() {
//		return airportModel.getBaseFile();
//	}
//}
