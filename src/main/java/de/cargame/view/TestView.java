package de.cargame.view;

import de.cargame.config.ConfigKey;
import de.cargame.config.GameConfigService;
import de.cargame.controller.GameStateController;
import de.cargame.controller.api.GameStateAPI;
import de.cargame.controller.entity.GameModelData;
import de.cargame.model.GameInstance;
import de.cargame.model.entity.player.PlayerObserver;

import javax.swing.*;
import java.awt.*;

public class TestView extends JFrame implements GamePlayScene {


    private final GamePanel foregroundPanel = new ForegroundPanel();
    private final GamePanel backgroundPanel = new BackgroundPanel();

    private final JLayeredPane layeredPane = new JLayeredPane();

    GameStateController gameStateController;

    public TestView(GameStateAPI gameStateController) {

        this.gameStateController = (GameStateController) gameStateController;
        setPreferredSize(new Dimension(GameConfigService.getInstance().loadInteger(ConfigKey.SCREEN_WIDTH), GameConfigService.getInstance().loadInteger(ConfigKey.SCREEN_HEIGHT) + 28));
        setMinimumSize(new Dimension(GameConfigService.getInstance().loadInteger(ConfigKey.SCREEN_WIDTH), GameConfigService.getInstance().loadInteger(ConfigKey.SCREEN_HEIGHT)));

        layeredPane.setLayout(null);

        backgroundPanel.setBounds(0, 0, GameConfigService.getInstance().loadInteger(ConfigKey.SCREEN_WIDTH), GameConfigService.getInstance().loadInteger(ConfigKey.SCREEN_HEIGHT));
        foregroundPanel.setBounds(0, 0, GameConfigService.getInstance().loadInteger(ConfigKey.SCREEN_WIDTH), GameConfigService.getInstance().loadInteger(ConfigKey.SCREEN_HEIGHT));

        backgroundPanel.setVisible(true);
        foregroundPanel.setVisible(true);

        layeredPane.add(backgroundPanel, 0); // Hintergrundebene
        layeredPane.add(foregroundPanel, 1); // Vordergrundebene

        add(layeredPane, BorderLayout.CENTER);

        setDefaultCloseOperation(EXIT_ON_CLOSE);
        pack();
        setVisible(true);
    }


    /**
     * Renders the current state of the game by invoking the render method on the background and foreground panels
     * with the game's model data.
     *
     * @param gameInstance the current instance of the game, which contains the game's state and model data
     */
    @Override
    public void render(GameInstance gameInstance) {
        GameModelData gameModelData = gameInstance.getGameModelData();
        backgroundPanel.render(gameModelData);
        foregroundPanel.render(gameModelData);
    }

    public PlayerObserver getJPanel() {
        return (PlayerObserver) foregroundPanel;
    }

}
