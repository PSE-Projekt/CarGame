package de.cargame.config;

public abstract class GameConfig {

    //----------------------UI------------------------------

    public static final int SCREEN_WIDTH = 1280;
    public static final int SCREEN_HEIGHT = 720;

    //----------------------UI------------------------------
    //----------------------GENERAL-------------------------

    public static final int MAX_LIVES = 3;
    public static final int GAME_SPEED = 400;
    public static final int GAME_SPEED_FAST_FORWARD = GAME_SPEED + 50;
    public static final int CRASH_COOLDOWN_TIME = 3000;
    public static final double SCORE_INCREASE_NORMAL_SPEED = 0.1;
    public static final double SCORE_INCREASE_FAST_FORWARD_SPEED = 0.15;

    /**
     * Value:
     * 8 -> ~120 FPS
     * 16 -> ~60 FPS
     * 32 -> ~30 FPS
     */
    public static final int FPS = 8;

    //----------------------GENERAL--------------------------
    //----------------------AI CAR GENERAL-------------------

    public static final int AI_CAR_WIDTH = 100;
    public static final int AI_CAR_HEIGHT = 60;
    public static final double AI_CAR_SPEED = 1.5;
    public static final int AI_CAR_SPAWN_TIME_MIN = 300;
    public static final int AI_CAR_SPAWN_TIME_MAX = 500;

    //----------------------AI CAR GENERAL-------------------
    //----------------------FAST CAR-------------------------

    public static final int FAST_CAR_WIDTH = 90;
    public static final int FAST_CAR_HEIGHT = 45;
    public static final int FAST_CAR_SPEED = 350;
    public static final double FAST_CAR_INERTIA = 100;

    //----------------------FAST CAR-------------------------
    //----------------------AGILE CAR------------------------

    public static final int AGILE_CAR_WIDTH = 80;
    public static final int AGILE_CAR_HEIGHT = 36;
    public static final int AGILE_CAR_SPEED = 170;
    public static final double AGILE_CAR_INERTIA = 70;

    //----------------------AGILE CAR------------------------
    //----------------------BUILDING------------------------
    public static final int BUILDING_WIDTH = 60;
    public static final int BUILDING_HEIGHT = 66;
    /**
     * Pixelcount how wide the spawn area for buildings is
     */
    public static final int BUILDING_SPAWN_AREA_WIDTH = 15;
    public static final int BUILDING_SPAWN_TIME_MIN = 100;
    public static final int BUILDING_SPAWN_TIME_MAX = 300;

    //----------------------BUILDING------------------------
    //----------------------ROAD MARK-----------------------

    public static final int ROAD_MARK_WIDTH = 40;
    public static final int ROAD_MARK_HEIGHT = 10;
    public static final int ROAD_MARK_SPAWN_TIME_MIN = 500;
    public static final int ROAD_MARK_SPAWN_TIME_MAX = 501;

    //----------------------ROAD MARK-----------------------
    //----------------------OBSTACLE------------------------

    public static final int OBSTACLE_WIDTH = 100;
    public static final int OBSTACLE_HEIGHT = 55;
    public static final int OBSTACLE_SPAWN_TIME_MIN = 700;
    public static final int OBSTACLE_SPAWN_TIME_MAX = 1400;


    //----------------------OBSTACLE-----------------------
    //----------------------REWARD-------------------------

    public static final int REWARD_WIDTH = 52;
    public static final int REWARD_HEIGHT = 40;
    public static final int REWARD_SPAWN_TIME_MIN = 15000;
    public static final int REWARD_SPAWN_TIME_MAX = 20000;

    //----------------------REWARD-------------------------

}