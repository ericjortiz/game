package byog.Core;

import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;
import edu.princeton.cs.introcs.StdAudio;
import java.io.Serializable;

public class World implements Serializable {
    private TETile[][] world;
    private int startingXPos;
    private int startingYPos;
    private int playerXPos;
    private int playerYPos;
    private int numKeys = 0;
    private int numHearts = 3;
    private int teleport1xPos;
    private int teleport2xPos;
    private int teleport1yPos;
    private int teleport2yPos;
    private int guard1XPos;
    private int guard2XPos;
    private int guard3XPos;
    private int guard1YPos;
    private int guard2YPos;
    private int guard3YPos;
    private int key1XPos;
    private int key2XPos;
    private int key3XPos;
    private int key1YPos;
    private int key2YPos;
    private int key3YPos;
    private static final long serialVersionUID = 456456456456L;

    /**
     * Constructs a new world object
     * @param world the TETile array to create the world with
     * @param playerXPos the player's starting x Position
     * @param playerYPos the player's starting y Position
     */
    public World(TETile[][] world, int playerXPos, int playerYPos, int t1X, int t1Y, int t2X, int t2Y, int guard1XPos, int guard2XPos, int guard3XPos, int guard1YPos, int guard2YPos, int guard3YPos) {
        this.world = world;
        this.playerXPos = playerXPos;
        this.playerYPos = playerYPos;
        this.startingXPos = playerXPos;
        this.startingYPos = playerYPos;
        this.teleport1xPos = t1X;
        this.teleport1yPos = t1Y;
        this.teleport2xPos = t2X;
        this.teleport2yPos = t2Y;
        this.guard1XPos = guard1XPos;
        this.guard2XPos = guard2XPos;
        this.guard3XPos = guard3XPos;
        this.guard1YPos = guard1YPos;
        this.guard2YPos = guard2YPos;
        this.guard3YPos = guard3YPos;
        this.key1XPos = guard1XPos - 1;
        this.key1YPos = guard1YPos;
        this.key2XPos = guard2XPos - 1;
        this.key2YPos = guard2YPos;
        this.key3XPos = guard3XPos - 1;
        this.key3YPos = guard3YPos;

    }

    /**
     * @return the world so that it can be rendered
     */
    public TETile[][] getWorld() {
        return world;
    }

    /**
     * @return the player's x Position
     */
    public int getPlayerXPos() {
        return playerXPos;
    }

    /**
     * @return the player's y Position
     */
    public int getPlayerYPos() {
        return playerYPos;
    }

    /**
     * @return the number of keys that have been gathered
     */
    public int getNumKeys() {
        return numKeys;
    }

    /**
     * @return the number of lives the user has left
     */
    public int getNumHearts() {
        return numHearts;
    }

    /**
     * @return the x position of guard 1
     */
    public int getGuard1XPos() {
        return guard1XPos;
    }

    /**
     * @return the x position of guard 2
     */
    public int getGuard2XPos() {
        return guard2XPos;
    }

    /**
     * @return the x position of guard 3
     */
    public int getGuard3XPos() {
        return guard3XPos;
    }

    /**
     * @return the y position of guard 1
     */
    public int getGuard1YPos() {
        return guard1YPos;
    }

    /**
     * @return the y position of guard 2
     */
    public int getGuard2YPos() {
        return guard2YPos;
    }

    /**
     * @return the y position of guard 3
     */
    public int getGuard3YPos() {
        return guard3YPos;
    }

    /**
     * Makes the move, if possible, for the player
     * @param move the direction the player desires to move in
     * @return what direction the player actually moves
     */
    public String makeMove(char move) {
        if (move == 'w') {
            if (world[playerXPos][playerYPos + 1].equals(Tileset.FLOOR)) {
                world[playerXPos][playerYPos] = Tileset.FLOOR;
                world[playerXPos][playerYPos + 1] = Tileset.getPlayer();
                playerYPos++;
                return "w";
            } else if (world[playerXPos][playerYPos + 1].equals(Tileset.KEY)) {
                world[playerXPos][playerYPos] = Tileset.FLOOR;
                world[playerXPos][playerYPos + 1] = Tileset.getPlayer();
                numKeys++;
                playerYPos++;
                StdAudio.play("/byog/Media/coin.wav");
                return "w";
            } else if (world[playerXPos][playerYPos + 1].equals(Tileset.TELEPORT)) {
                StdAudio.play("/byog/Media/teleport.wav");
                if (playerXPos == teleport1xPos && playerYPos + 1 == teleport1yPos) {
                    world[playerXPos][playerYPos] = Tileset.FLOOR;
                    world[teleport2xPos][teleport2yPos + 1] = Tileset.getPlayer();
                    playerXPos = teleport2xPos;
                    playerYPos = teleport2yPos + 1;
                    return "w";
                } else {
                    world[playerXPos][playerYPos] = Tileset.FLOOR;
                    world[teleport1xPos][teleport1yPos + 1] = Tileset.getPlayer();
                    playerXPos = teleport1xPos;
                    playerYPos = teleport1yPos + 1;
                    return "w";
                }
            } else if (world[playerXPos][playerYPos + 1].equals(Tileset.GUARD)) {
                world[playerXPos][playerYPos] = Tileset.FLOOR;
                world[startingXPos][startingYPos] = Tileset.getPlayer();
                playerXPos = startingXPos;
                playerYPos = startingYPos;
                numHearts--;
                if (numHearts > 0) {
                    StdAudio.play("/byog/Media/lose.wav");
                }
                return "w";
            }
        } else if (move == 'a') {
            if (world[playerXPos - 1][playerYPos].equals(Tileset.FLOOR)) {
                world[playerXPos][playerYPos] = Tileset.FLOOR;
                world[playerXPos - 1][playerYPos] = Tileset.getPlayer();
                playerXPos--;
                return "a";
            } else if (world[playerXPos - 1][playerYPos].equals(Tileset.KEY)) {
                world[playerXPos][playerYPos] = Tileset.FLOOR;
                world[playerXPos - 1][playerYPos] = Tileset.getPlayer();
                numKeys++;
                playerXPos--;
                StdAudio.play("/byog/Media/coin.wav");
                return "a";
            } else if (world[playerXPos - 1][playerYPos].equals(Tileset.TELEPORT)) {
                StdAudio.play("/byog/Media/teleport.wav");
                if (playerXPos - 1 == teleport1xPos && playerYPos == teleport1yPos) {
                    world[playerXPos][playerYPos] = Tileset.FLOOR;
                    world[teleport2xPos - 1][teleport2yPos] = Tileset.getPlayer();
                    playerXPos = teleport2xPos - 1;
                    playerYPos = teleport2yPos;
                    return "a";
                } else {
                    world[playerXPos][playerYPos] = Tileset.FLOOR;
                    world[teleport1xPos - 1][teleport1yPos] = Tileset.getPlayer();
                    playerXPos = teleport1xPos - 1;
                    playerYPos = teleport1yPos;
                    return "a";
                }
            } else if (world[playerXPos - 1][playerYPos].equals(Tileset.GUARD)) {
                world[playerXPos][playerYPos] = Tileset.FLOOR;
                world[startingXPos][startingYPos] = Tileset.getPlayer();
                numHearts--;
                if (numHearts > 0) {
                    StdAudio.play("/byog/Media/lose.wav");
                }
                playerXPos = startingXPos;
                playerYPos = startingYPos;
                return "a";
            }
        } else if (move == 's') {
            if (world[playerXPos][playerYPos - 1].equals(Tileset.FLOOR)) {
                world[playerXPos][playerYPos] = Tileset.FLOOR;
                world[playerXPos][playerYPos - 1] = Tileset.getPlayer();
                playerYPos--;
                return "s";
            } else if (world[playerXPos][playerYPos - 1].equals(Tileset.KEY)) {
                world[playerXPos][playerYPos] = Tileset.FLOOR;
                world[playerXPos][playerYPos - 1] = Tileset.getPlayer();
                numKeys++;
                playerYPos--;
                StdAudio.play("/byog/Media/coin.wav");
                return "s";
            } else if (world[playerXPos][playerYPos - 1].equals(Tileset.TELEPORT)) {
                StdAudio.play("/byog/Media/teleport.wav");
                if (playerXPos == teleport1xPos && playerYPos - 1 == teleport1yPos) {
                    world[playerXPos][playerYPos] = Tileset.FLOOR;
                    world[teleport2xPos][teleport2yPos - 1] = Tileset.getPlayer();
                    playerXPos = teleport2xPos;
                    playerYPos = teleport2yPos - 1;
                    return "s";
                } else {
                    world[playerXPos][playerYPos] = Tileset.FLOOR;
                    world[teleport1xPos][teleport1yPos - 1] = Tileset.getPlayer();
                    playerXPos = teleport1xPos;
                    playerYPos = teleport1yPos - 1;
                    return "s";
                }
            } else if (world[playerXPos][playerYPos - 1].equals(Tileset.GUARD)) {
                world[playerXPos][playerYPos] = Tileset.FLOOR;
                world[startingXPos][startingYPos] = Tileset.getPlayer();
                numHearts--;
                if (numHearts > 0) {
                    StdAudio.play("/byog/Media/lose.wav");
                }
                playerXPos = startingXPos;
                playerYPos = startingYPos;
                return "s";
            }
        } else if (move == 'd') {
            if (world[playerXPos + 1][playerYPos].equals(Tileset.FLOOR)) {
                world[playerXPos][playerYPos] = Tileset.FLOOR;
                world[playerXPos + 1][playerYPos] = Tileset.getPlayer();
                playerXPos++;
                return "d";
            } else if (world[playerXPos + 1][playerYPos].equals(Tileset.KEY)) {
                world[playerXPos][playerYPos] = Tileset.FLOOR;
                world[playerXPos + 1][playerYPos] = Tileset.getPlayer();
                numKeys++;
                playerXPos++;
                StdAudio.play("/byog/Media/coin.wav");
                return "d";
            } else if (world[playerXPos + 1][playerYPos].equals(Tileset.TELEPORT)) {
                StdAudio.play("/byog/Media/teleport.wav");
                if (playerXPos + 1 == teleport1xPos && playerYPos == teleport1yPos) {
                    world[playerXPos][playerYPos] = Tileset.FLOOR;
                    world[teleport2xPos + 1][teleport2yPos] = Tileset.getPlayer();
                    playerXPos = teleport2xPos + 1;
                    playerYPos = teleport2yPos;
                    return "d";
                } else {
                    world[playerXPos][playerYPos] = Tileset.FLOOR;
                    world[teleport1xPos + 1][teleport1yPos] = Tileset.getPlayer();
                    playerXPos = teleport1xPos + 1;
                    playerYPos = teleport1yPos;
                    return "d";
                }
            } else if (world[playerXPos + 1][playerYPos].equals(Tileset.GUARD)) {
                world[playerXPos][playerYPos] = Tileset.FLOOR;
                world[startingXPos][startingYPos] = Tileset.getPlayer();
                numHearts--;
                if (numHearts > 0) {
                    StdAudio.play("/byog/Media/lose.wav");
                }
                playerXPos = startingXPos;
                playerYPos = startingYPos;
                return "d";
            }
        } else if (move == ':') {
            return ":";
        } else {
            return "";
        }
        return "";
    }

    /**
     * Makes guard 1 move
     * @param pos guard 1 will move to the corresponding position around the key
     */
    private void makeGuard1Move(int pos) {
        world[guard1XPos][guard1YPos] = Tileset.FLOOR;
        if (pos == 0) {
            world[key1XPos - 1][key1YPos + 1] = Tileset.GUARD;
            guard1XPos = key1XPos - 1;
            guard1YPos = key1YPos + 1;
        } else if (pos == 1) {
            world[key1XPos][key1YPos + 1] = Tileset.GUARD;
            guard1XPos = key1XPos;
            guard1YPos = key1YPos + 1;
        } else if (pos == 2) {
            world[key1XPos + 1][key1YPos + 1] = Tileset.GUARD;
            guard1XPos = key1XPos + 1;
            guard1YPos = key1YPos + 1;
        } else if (pos == 3) {
            world[key1XPos - 1][key1YPos] = Tileset.GUARD;
            guard1XPos = key1XPos - 1;
            guard1YPos = key1YPos;
        } else if (pos == 4) {
            world[key1XPos + 1][key1YPos] = Tileset.GUARD;
            guard1XPos = key1XPos + 1;
            guard1YPos = key1YPos;
        } else if (pos == 5) {
            world[key1XPos - 1][key1YPos - 1] = Tileset.GUARD;
            guard1XPos = key1XPos - 1;
            guard1YPos = key1YPos - 1;
        } else if (pos == 6) {
            world[key1XPos][key1YPos - 1] = Tileset.GUARD;
            guard1XPos = key1XPos;
            guard1YPos = key1YPos - 1;
        } else if (pos == 7) {
            world[key1XPos + 1][key1YPos - 1] = Tileset.GUARD;
            guard1XPos = key1XPos + 1;
            guard1YPos = key1YPos - 1;
        }
    }

    /**
     * Makes guard 2 move
     * @param pos guard 2 will move to the corresponding position around the key
     */
    private void makeGuard2Move(int pos) {
        world[guard2XPos][guard2YPos] = Tileset.FLOOR;
        if (pos == 0) {
            world[key2XPos - 1][key2YPos + 1] = Tileset.GUARD;
            guard2XPos = key2XPos - 1;
            guard2YPos = key2YPos + 1;
        } else if (pos == 1) {
            world[key2XPos][key2YPos + 1] = Tileset.GUARD;
            guard2XPos = key2XPos;
            guard2YPos = key2YPos + 1;
        } else if (pos == 2) {
            world[key2XPos + 1][key2YPos + 1] = Tileset.GUARD;
            guard2XPos = key2XPos + 1;
            guard2YPos = key2YPos + 1;
        } else if (pos == 3) {
            world[key2XPos - 1][key2YPos] = Tileset.GUARD;
            guard2XPos = key2XPos - 1;
            guard2YPos = key2YPos;
        } else if (pos == 4) {
            world[key2XPos + 1][key2YPos] = Tileset.GUARD;
            guard2XPos = key2XPos + 1;
            guard2YPos = key2YPos;
        } else if (pos == 5) {
            world[key2XPos - 1][key2YPos - 1] = Tileset.GUARD;
            guard2XPos = key2XPos - 1;
            guard2YPos = key2YPos - 1;
        } else if (pos == 6) {
            world[key2XPos][key2YPos - 1] = Tileset.GUARD;
            guard2XPos = key2XPos;
            guard2YPos = key2YPos - 1;
        } else if (pos == 7) {
            world[key2XPos + 1][key2YPos - 1] = Tileset.GUARD;
            guard2XPos = key2XPos + 1;
            guard2YPos = key2YPos - 1;
        }
    }

    /**
     * Makes guard 3 move
     * @param pos guard 3 will move to the corresponding position around the key
     */
    private void makeGuard3Move(int pos) {
        world[guard3XPos][guard3YPos] = Tileset.FLOOR;
        if (pos == 0) {
            world[key3XPos - 1][key3YPos + 1] = Tileset.GUARD;
            guard3XPos = key3XPos - 1;
            guard3YPos = key3YPos + 1;
        } else if (pos == 1) {
            world[key3XPos][key3YPos + 1] = Tileset.GUARD;
            guard3XPos = key3XPos;
            guard3YPos = key3YPos + 1;
        } else if (pos == 2) {
            world[key3XPos + 1][key3YPos + 1] = Tileset.GUARD;
            guard3XPos = key3XPos + 1;
            guard3YPos = key3YPos + 1;
        } else if (pos == 3) {
            world[key3XPos - 1][key3YPos] = Tileset.GUARD;
            guard3XPos = key3XPos - 1;
            guard3YPos = key3YPos;
        } else if (pos == 4) {
            world[key3XPos + 1][key3YPos] = Tileset.GUARD;
            guard3XPos = key3XPos + 1;
            guard3YPos = key3YPos;
        } else if (pos == 5) {
            world[key3XPos - 1][key3YPos - 1] = Tileset.GUARD;
            guard3XPos = key3XPos - 1;
            guard3YPos = key3YPos - 1;
        } else if (pos == 6) {
            world[key3XPos][key3YPos - 1] = Tileset.GUARD;
            guard3XPos = key3XPos;
            guard3YPos = key3YPos - 1;
        } else if (pos == 7) {
            world[key3XPos + 1][key3YPos - 1] = Tileset.GUARD;
            guard3XPos = key3XPos + 1;
            guard3YPos = key3YPos - 1;
        }
    }

    /**
     * makes the calls to make the guards move randomly
     * @param num1 the position guard 1 will move to
     * @param num2 the position guard 2 will move to
     * @param num3 the position guard 3 will move to
     */
    public void makeGuardMove(int num1, int num2, int num3) {
        makeGuard1Move(num1);
        makeGuard2Move(num2);
        makeGuard3Move(num3);
    }

    /**
     * updates the player's attributes after the guards have moved
     */
    public void updatePlayer() {
        this.numHearts--;
        this.playerXPos = this.startingXPos;
        this.playerYPos = this.startingYPos;
        world[playerXPos][playerYPos] = Tileset.getPlayer();
    }
}
