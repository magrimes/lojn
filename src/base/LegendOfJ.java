package base;

import mapgen.CellularAutomataMapGenerator;
import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;

public class LegendOfJ extends BasicGame {

	public static final int gameWindowHeight = 480;
	public static final int gameWindowWidth = 640;
	public static final int consoleHeight = 128;
	public static final int consoleWidth = 384;
	public static final int updateDelay = 100;

	Camera camera;
	Player player;
	Inventory inventory;
	Console console;
	MiniMap minimap;
	int updateTimer = 0;
	boolean debug = true;

	Image testImage;
	
	public LegendOfJ() {
		super("The Legend of John Novak");
	}

	@Override
	public void init(GameContainer container) throws SlickException {

		// MapGenerator mg = new MapGenerator(40, 40, 3,
		// "maps/GeneratedMap01.map");
		// Map map = new Map("maps/GeneratedMap01.map");

		CellularAutomataMapGenerator camg = new CellularAutomataMapGenerator(
	    	200, 200, 20, 2, 0.43, 0.23, "maps/CAMGenMap01.map");

        Map map = new Map("maps/CAMGenMap01.map");

		Image playerImage = null;
		
		testImage = new Image("images/broad_sword_blue.png");
		
		try {
			playerImage = new Image("images/ice_wizard.png");
		} catch (SlickException e) {
			System.err.println("Error loading player image");
			System.exit(-1);
			e.printStackTrace();
		}

		int[] spawn = map.getValidSpawnLocation();
		player = new Player(spawn[0], spawn[1], playerImage, true, map, "John");
		camera = new Camera(map, player, gameWindowWidth / 32,
				gameWindowHeight / 32);
		console = new Console(consoleWidth, consoleHeight, 20, 0,
				gameWindowHeight);
		console.addMessage("Welcome to the Legend of John Novak!");
		inventory = new Inventory(384, 480);
		minimap = new MiniMap(512, 480, map, player);

	}

	// add event queue shit
	@Override
	public void update(GameContainer container, int delta)
			throws SlickException {
		Input input = container.getInput();
		if (input.isKeyPressed(Input.KEY_ESCAPE)) {
			container.exit();
		} else if (input.isKeyPressed(Input.KEY_J)) {
			console.scrollDown();
		} else if (input.isKeyPressed(Input.KEY_K)) {
			console.scrollUp();
		} else if (input.isKeyPressed(Input.KEY_A)) {
			console.addMessage("Hello this is a string");
		} else if (input.isKeyPressed(Input.KEY_B)) {
			console.addMessage("hello buts this is a butts hi butts");
		} else if (input.isKeyDown(Input.KEY_UP)) {
			if (updateTimer >= updateDelay) {
				player.moveUp();
				updateTimer = 0;
			} else {
				updateTimer += delta;
			}
		} else if (input.isKeyDown(Input.KEY_DOWN)) {
			if (updateTimer >= updateDelay) {
				player.moveDown();
				updateTimer = 0;
			} else {
				updateTimer += delta;
			}
		} else if (input.isKeyDown(Input.KEY_LEFT)) {
			if (updateTimer >= updateDelay) {
				player.moveLeft();
				updateTimer = 0;
			} else {
				updateTimer += delta;
			}
		} else if (input.isKeyDown(Input.KEY_RIGHT)) {
			if (updateTimer >= updateDelay) {
				player.moveRight();
				updateTimer = 0;
			} else {
				updateTimer += delta;
			}
		} else if (input.isKeyDown(Input.KEY_1)) {
			inventory.selectSlot(0);
		} else if (input.isKeyDown(Input.KEY_2)) {
			inventory.selectSlot(1);
		} else if (input.isKeyDown(Input.KEY_3)) {
			inventory.selectSlot(2);
		} else if (input.isKeyDown(Input.KEY_4)) {
			inventory.selectSlot(3);
		} else if (input.isKeyDown(Input.KEY_5)) {
			inventory.selectSlot(4);
		} else if (input.isKeyDown(Input.KEY_6)) {
			inventory.selectSlot(5);
		} else if (input.isKeyDown(Input.KEY_7)) {
			inventory.selectSlot(6);
		} else if (input.isKeyDown(Input.KEY_8)) {
			inventory.selectSlot(7);
		} else if (input.isKeyDown(Input.KEY_9)) {
			inventory.selectSlot(8);
		} else if (input.isKeyDown(Input.KEY_0)) {
			inventory.selectSlot(9);
		} else if (input.isKeyDown(Input.KEY_T)) {
			inventory.selectSlot(10);
		} else if (input.isKeyDown(Input.KEY_Y)) {
			inventory.selectSlot(11);
		} else if (input.isKeyDown(Input.KEY_U)) {
			inventory.selectSlot(12);
		} else if (input.isKeyDown(Input.KEY_I)) {
			inventory.selectSlot(13);
		} else if (input.isKeyDown(Input.KEY_O)) {
			inventory.selectSlot(14);
		} else if (input.isKeyDown(Input.KEY_P)) {
			inventory.selectSlot(15);
		}
		if (updateTimer >= updateDelay) {
			player.updateExploredTiles();
			updateTimer = 0;
		} else {
			updateTimer += delta;
		}
	}

	@Override
	public void render(GameContainer container, Graphics g)
			throws SlickException {
		camera.render(g);
		console.render(g);
		inventory.render(g);
		minimap.render(g);
		if (debug)
			renderDebug(container, g);
	}

	public void renderDebug(GameContainer container, Graphics g)
			throws SlickException {
		g.setColor(Color.darkGray);
		g.fillRect(2, 2, 100, 45);
		g.setColor(Color.orange);
		g.drawString("p: " + player.getX() + ", " + player.getY(), 7, 4);
		g.drawString("c: " + camera.getXTile() + ", " + camera.getYTile(), 7,
				25);
	}

	public static void main(String[] args) {
		try {
			AppGameContainer app = new AppGameContainer(new LegendOfJ());
			app.setDisplayMode(gameWindowWidth, gameWindowHeight
					+ consoleHeight, false);
			app.setShowFPS(false);
			app.setTargetFrameRate(15);
			app.start();
		} catch (SlickException e) {
			e.printStackTrace();
		}
	}
}
