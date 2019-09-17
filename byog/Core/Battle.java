package byog.Core;
import edu.princeton.cs.introcs.StdDraw;
import edu.princeton.cs.introcs.StdAudio;

import java.awt.*;

// Class in which the battle takes place.
public class Battle {
    public static void main(String[] args) {
        StdAudio.loop("/byog/Media/MarioRemix.wav");
        //initialize screen
        initializeScreen();
        //burton gives instructions
        burtonInstructions();
        //burton asks for player archetype
        drawburton();
        char archetype = burtonAskForArchetype();
        //instantiate player
        Fighter player = new Fighter(archetype);
        //instantiate burton
        Fighter burton = new Fighter('b');
        //loop for fighting
        boolean mauricioTurn = true;
        for (int i = 0; i < 1;) {
            if (mauricioTurn) {
                mauricioMove(player, burton);
            } else {
                burtonMove(player, burton);
            } if (player.getHealth() == 0) {
                i++;
                edu.princeton.cs.algs4.StdAudio.close();
                StdAudio.close();
                Game.youLose();
            } else if (burton.getHealth() == 0) {
                i++;
                edu.princeton.cs.algs4.StdAudio.close();
                StdAudio.close();
                Game.youWin();
            }
            mauricioTurn = !mauricioTurn;
        }

    }

    /**
     * Initializes the screen by creating a new, blank canvas.
     */
    private static void initializeScreen() {
        StdDraw.enableDoubleBuffering();
        StdDraw.setCanvasSize(Game.WIDTH * 16, Game.HEIGHT * 16);
        StdDraw.setXscale(0, Game.WIDTH);
        StdDraw.setYscale(0, Game.HEIGHT);
        StdDraw.clear(Color.BLACK);
    }

    /**
     * Draws burton on the screen in talking position (does not call StdDraw.show()).
     */
    private static void drawburton() {
        StdDraw.picture(Game.WIDTH / 2, Game.HEIGHT / 2, "/byog/Media/bowsaHall.png", 125, 50);
        StdDraw.picture(Game.WIDTH / 2, Game.HEIGHT / 2 - 7, "/byog/Media/bowsa.png", 33.5, 33.5);
    }

    /**
     * Draws the basic fight scene with health and mana bars.
     */
    private static void drawFightScene(Fighter player, Fighter burton, boolean turn) {
        StdDraw.picture(Game.WIDTH / 2, Game.HEIGHT / 2, "/byog/Media/bowsaHall.png", 125, 50);
        StdDraw.picture(22, 15, "/byog/Media/bowsa.png", 25, 25, 10);
        StdDraw.picture(Game.WIDTH - 18, 10, "/byog/Media/mario.png", 10, 10);
        drawPlayerHealthBar(player);
        drawPlayerManaBar(player);
        drawPlayerName();
        drawBurtonHealthBar(burton);
        drawBurtonManaBar(burton);
        drawBurtonName();
        showTurn(turn);
    }

    /**
     * Draws the player's health bar
     * @param player the user playing the game
     */
    private static void drawPlayerHealthBar(Fighter player) {
        double playerHealth = player.getHealth();
        double maxHealth = player.getMaxHealth();
        double healthRatio = playerHealth / maxHealth;
        StdDraw.picture(82, 0.75, "/byog/Media/blackBar.png", 20, 1.25);
        StdDraw.picture(82, 0.75, "/byog/Media/redBar.png", 19.5 * healthRatio, 1);
        StdDraw.setFont(new Font("Monaco", Font.BOLD, 13));
        StdDraw.setPenColor(Color.WHITE);
        StdDraw.text(82, 0.70, Math.round(playerHealth * 100) / 100.0 + "/" + maxHealth);
    }

    /**
     * Draws the player's health bar
     * @param player the user playing the game
     */
    private static void drawPlayerManaBar(Fighter player) {
        double playerMana = player.getMana();
        double maxPlayerMana = player.getMaxMana();
        double manaRatio = playerMana / maxPlayerMana;
        StdDraw.picture(82, 2.1, "/byog/Media/blackBar.png", 20, 1.25);
        StdDraw.picture(82, 2.1, "/byog/Media/blueBar.png", 19.5 * manaRatio, 1);
        StdDraw.setFont(new Font("Monaco", Font.BOLD, 13));
        StdDraw.setPenColor(Color.WHITE);
        StdDraw.text(82, 2.05, Math.ceil(playerMana * 100) / 100.0 + "/" + maxPlayerMana);
    }

    /**
     * Draws burton's health bar
     * @param burton the enemy of the user
     */
    private static void drawBurtonHealthBar(Fighter burton) {
        double burtonHealth = burton.getHealth();
        double maxHealth = burton.getMaxHealth();
        double healthRatio = burtonHealth / maxHealth;
        StdDraw.picture(22, 0.75, "/byog/Media/blackBar.png", 20, 1.25);
        StdDraw.picture(22, 0.75, "/byog/Media/redBar.png", 19.5 * healthRatio, 1);
        StdDraw.setFont(new Font("Monaco", Font.BOLD, 13));
        StdDraw.setPenColor(Color.WHITE);
        StdDraw.text(22, 0.70, Math.ceil(burtonHealth * 100) / 100.0 + "/" + maxHealth);
    }

    /**
     * Draws burton's mana bar
     * @param burton the enemy of the user
     */
    private static void drawBurtonManaBar(Fighter burton) {
        double burtonMana = burton.getMana();
        double burtonMaxMana = burton.getMaxMana();
        double manaRatio = burtonMana / burtonMaxMana;
        StdDraw.picture(22, 2.1, "/byog/Media/blackBar.png", 20, 1.25);
        StdDraw.picture(22, 2.1, "/byog/Media/blueBar.png", 19.5, 1);
        StdDraw.setFont(new Font("Monaco", Font.BOLD, 13));
        StdDraw.setPenColor(Color.WHITE);
        StdDraw.text(22, 2.05, "Infinite");
    }

    /**
     * Draws Mauricio's name above him
     */
    private static void drawPlayerName() {
        StdDraw.setFont(new Font("Monaco", Font.BOLD, 25));
        StdDraw.setPenColor(new Color(255, 127, 50));
        StdDraw.text(82, 17, "MAURICIO");
    }

    /**
     * Draws Burton's name above him
     */
    private static void drawBurtonName() {
        StdDraw.setFont(new Font("Monaco", Font.BOLD, 25));
        StdDraw.setPenColor(Color.RED);
        StdDraw.text(22, 30, "BURTON");
    }

    /**
     * Puts an indicator above the fighter whose turn it is
     * @param turn true if it is mauricio's turn. false otherwise
     */
    private static void showTurn(boolean turn) {
        if (turn) {
            StdDraw.picture(82, 19, "/byog/Media/triangle.png", 1, 1);
        } else {
            StdDraw.picture(22, 32, "/byog/Media/triangle.png", 1, 1);
        }
    }

    /**
     * Calls StdDraw.show() and clear() on the current screen.
     */
    private static void displayScreen() {
        StdDraw.show();
        StdDraw.clear(Color.BLACK);
    }

    /**
     * Displays burton on the screen giving instructions for the fight.
     */
    private static void burtonInstructions() {
        String phrase1 = "You may have managed to capture all of the keys,";
        String phrase1Final = phrase1;
        String phrase2 = "but you cannot hope to escape until you can best me in a fight. ";
        String displayPhrase1 = "";
        String displayPhrase2 = "";
        StdDraw.setFont(new Font("Monaco", Font.PLAIN, 23));
        StdDraw.setPenColor(Color.WHITE);
        StdAudio.play("/byog/Media/bowsaLaugh.wav");
        while (phrase1.length() > 0) {
            drawburton();
            displayPhrase1 += phrase1.substring(0, 1);
            phrase1 = phrase1.substring(1);
            StdDraw.text(Game.WIDTH/2, Game.HEIGHT - 4.5, displayPhrase1);
            displayScreen();
            StdDraw.pause(40);
        }
        StdDraw.pause(600);
        while (phrase2.length() > 0) {
            drawburton();
            StdDraw.text(Game.WIDTH/2, Game.HEIGHT - 4.5, phrase1Final);
            displayPhrase2 += phrase2.substring(0, 1);
            phrase2 = phrase2.substring(1);
            StdDraw.text(Game.WIDTH/2, Game.HEIGHT - 7.5, displayPhrase2);
            displayScreen();
            StdDraw.pause(40);
        }
        drawburton();
        StdDraw.setFont(new Font("Monaco", Font.PLAIN, 23));
        StdDraw.setPenColor(Color.WHITE);
        StdDraw.text(Game.WIDTH/2, Game.HEIGHT - 4.5, phrase1Final);
        StdDraw.text(Game.WIDTH/2, Game.HEIGHT - 7.5, displayPhrase2);
        StdDraw.setFont(new Font("Monaco", Font.PLAIN, 15));
        StdDraw.setPenColor(Color.WHITE);
        StdDraw.text(92, 1, "Press any key to continue.");
        displayScreen();
        while (!StdDraw.hasNextKeyTyped()) {
            //empty loop used to keep words on screen until player is ready
        }
    }

    /**
     * Shows burton on screen asking for the player's preferred style of fighting.
     * @return a char representing the style of fighting
     */
    private static char burtonAskForArchetype() {
        char archetype = '0';
        String phrase = "Before we begin, I must ask, which fighting style do you prefer?";
        String displayPhrase = "";
        StdDraw.setFont(new Font("Monaco", Font.PLAIN, 23));
        StdDraw.setPenColor(Color.WHITE);
        StdAudio.play("/byog/Media/bowsaLaugh.wav");
        while (phrase.length() > 0) {
            drawburton();
            displayPhrase += phrase.substring(0, 1);
            phrase = phrase.substring(1);
            StdDraw.text(Game.WIDTH/2, Game.HEIGHT - 4.5, displayPhrase);
            displayScreen();
            StdDraw.pause(40);
        }
        StdDraw.pause(400);
        drawburton();
        StdDraw.text(Game.WIDTH/2, Game.HEIGHT - 4.5, displayPhrase);
        StdDraw.setFont(new Font("Monaco", Font.BOLD, 40));
        StdDraw.text(35, 40, "MAGIC (a)");
        StdDraw.text(65, 40, "COMBAT (d)");
        displayScreen();
        if (StdDraw.hasNextKeyTyped()) {
            char ignore = StdDraw.nextKeyTyped(); //buffer in case user pressed a or d to continue
        }
        for (int i = 0; i < 1;) {
            if (StdDraw.hasNextKeyTyped()) {
                archetype = StdDraw.nextKeyTyped();
            }
            if (archetype == 'a' || archetype == 'd') {
                i++;
            }
        }
        return archetype;
    }

    /**
     * Displays
     * @param player the user
     * @param burton burton
     * @return the integer corresponding to the move the player selects
     */
    private static int displayMoves(Fighter player, Fighter burton) {
        int[] yPos = {40, 36, 32, 28};
        int index = 0;
        boolean notEnoughMana = false;
        for (int i = 0; i < 1;) {
            drawFightScene(player, burton, true);
            StdDraw.picture(82, 34, "/byog/Media/menu.png", 18, 20);
            StdDraw.setFont(new Font("Monaco", Font.PLAIN, 20));
            StdDraw.setPenColor(Color.BLUE);
            StdDraw.text(83, 40, "ATTACK      ");
            StdDraw.setPenColor(new Color(255, 80, 0));
            StdDraw.text(83, 36, "FIREBALL    ");
            StdDraw.setPenColor(Color.RED);
            StdDraw.text(83, 32, "HEALTH DRAIN");
            StdDraw.setPenColor(new Color(0, 190, 12));
            StdDraw.text(83, 28, "HEAL        ");
            StdDraw.picture(77.5, yPos[index] + 0.1, "byog/Media/triangle2.png", 1, 1, 90);
            StdDraw.setFont(new Font("Monaco", Font.PLAIN, 15));
            StdDraw.setPenColor(Color.WHITE);
            StdDraw.text(82, 23, "Press the spacebar when you have chosen your move.");
            if (notEnoughMana) {
                StdDraw.text(82, 21, "You do not have enough mana to cast this spell.");
            }
            displayScreen();
            if (StdDraw.hasNextKeyTyped()) {
                char direction = StdDraw.nextKeyTyped();
                if (direction == 'w') {
                    if (index == 0) {
                        notEnoughMana = false;
                        index = 3;
                    } else {
                        notEnoughMana = false;
                        index--;
                    }
                } else if (direction == 's') {
                    if (index == 3) {
                        notEnoughMana = false;
                        index = 0;
                    } else {
                        notEnoughMana = false;
                        index++;
                    }
                } else if (direction == ' ') {
                    if (checkMana(player, index)) {
                        i++;
                    } else {
                        notEnoughMana = true;
                    }
                }
            }
        }
        return index;
    }

    /**
     * Returns true if the player has enough mana for the move and false otherwise
     * @param player the user to check the mana of
     * @param index the index of the move
     * @return true if enough mana and false otherwise
     */
    private static boolean checkMana(Fighter player, int index) {
        double mana = player.getMana();
        if (index == 0) {
            return true;
        } else if (index == 1) {
            return mana >= Fighter.FIREBALLCOST;
        } else if (index == 2) {
            return mana >= Fighter.HEALTHDRAINCOST;
        } else {
            return mana>= Fighter.HEALCOST;
        }
    }

    /**
     * Randomly selects a move for burton to make.
     * @return a double representing the amount of damage dealt
     */
    private static void randomAttack(Fighter player, Fighter burton) {
        double damage = 0.0;
        StdDraw.setFont(new Font("Monaco", Font.PLAIN, 40));
        StdDraw.setPenColor(Color.WHITE);
        if (2 * Math.random() < 1) {
            attackGraphics(player, burton, false);
            damage = Math.round(100 * burton.attack(player)) / 100.0;
            drawFightScene(player, burton, false);
            StdDraw.setFont(new Font("Monaco", Font.PLAIN, 40));
            StdDraw.setPenColor(Color.WHITE);
            StdDraw.text(50, 40, "Burton has attacked you and dealt " + damage + " damage!");
        } else {
            fireballGraphics(player, burton, false);
            damage = burton.castFireball(player);
            drawFightScene(player, burton, false);
            StdDraw.setFont(new Font("Monaco", Font.PLAIN, 40));
            StdDraw.setPenColor(Color.WHITE);
            StdDraw.text(50, 40, "Burton has cast Fireball and dealt " + damage + " damage!");
        }
    }

    /**
     * Causes Mauricio to actually perform his move.
     */
    private static void makeMove(Fighter player, Fighter burton, int move) {
        StdDraw.setFont(new Font("Monaco", Font.PLAIN, 40));
        StdDraw.setPenColor(Color.WHITE);
        double amount = 0.0;
        if (move == 0) {
            attackGraphics(player, burton, true);
            amount = Math.round(player.attack(burton) * 100) / 100.0;
            drawFightScene(player, burton, true);
            StdDraw.setFont(new Font("Monaco", Font.PLAIN, 40));
            StdDraw.setPenColor(Color.WHITE);
            StdDraw.text(50, 40, "You have attacked burton and dealt " + amount + " damage!");
        } else if (move == 1) {
            fireballGraphics(player, burton, true);
            amount = Math.round(player.castFireball(burton) * 100) / 100.0;
            drawFightScene(player, burton, true);
            StdDraw.setFont(new Font("Monaco", Font.PLAIN, 40));
            StdDraw.setPenColor(Color.WHITE);
            StdDraw.text(50, 40, "You have cast Fireball and dealt " + amount + " damage!");
        } else if (move == 2) {
            healthDrainGraphics(player, burton);
            amount = Math.round(player.castHealthDrain(burton) * 100) / 100.0;
            drawFightScene(player, burton, true);
            StdDraw.setFont(new Font("Monaco", Font.PLAIN, 40));
            StdDraw.setPenColor(Color.WHITE);
            StdDraw.text(50, 40, "You have cast Health Drain and dealt " + amount + " damage!");
            StdDraw.text(50, 37, "You have regenerated " + amount / 2 + " health!");
        } else {
            healGraphics(player, burton);
            amount = Math.round(player.castHeal() * 100) / 100;
            drawFightScene(player, burton, true);
            StdDraw.setFont(new Font("Monaco", Font.PLAIN, 40));
            StdDraw.setPenColor(Color.WHITE);
            StdDraw.text(50, 40, "You have cast Heal and regenerated " + amount + " health.");
        }
    }

    /**
     * Handles Mauricio's move.
     */
    private static void mauricioMove(Fighter player, Fighter burton) {
        int move = displayMoves(player, burton);
        drawFightScene(player, burton, true);
        makeMove(player, burton, move);
        drawBurtonHealthBar(burton);
        drawPlayerManaBar(player);
        displayScreen();
        StdDraw.pause(3000);
    }

    /**
     * Handles Burton's move.
     */
    private static void burtonMove(Fighter player, Fighter burton) {
        drawFightScene(player, burton, true);
        randomAttack(player, burton);
        player.increaseMana();
        drawPlayerHealthBar(player);
        displayScreen();
        StdDraw.pause(3000);
    }

    /**
     * Displays the attack graphics.
     */
    private static void attackGraphics(Fighter player, Fighter burton, boolean turn) {
        double pos;
        double end;
        StdAudio.play("/byog/Media/shell.wav");
        if (turn) {
            pos = 82;
            end = 22;
            while (pos > end) {
                drawFightScene(player, burton, true);
                StdDraw.picture(pos, 8, "/byog/Media/shell.png", 5, 5);
                displayScreen();
                pos -= 1.3;
            }
            StdAudio.play("/byog/Media/bowsatakedamage.wav");
        } else {
            pos = 22;
            end = 82;
            while (pos < end) {
                drawFightScene(player, burton, false);
                StdDraw.picture(pos, 8, "/byog/Media/shell.png", 5, 5);
                displayScreen();
                pos += 1.3;
            }
            StdAudio.play("/byog/Media/marioDamage.wav");
        }
    }

    /**
     * Displays the fireball graphics.
     */
    private static void fireballGraphics(Fighter player, Fighter burton, boolean turn) {
        double pos;
        double end;
        StdAudio.play("/byog/Media/fireball.wav");
        if (turn) {
            pos = 82;
            end = 22;
            while (pos > end) {
                drawFightScene(player, burton, true);
                StdDraw.picture(pos, 9.5, "/byog/Media/fireball.png", 5, 5, 210);
                displayScreen();
                pos -= 1.3;
            }
            StdAudio.play("/byog/Media/bowsatakesfireball.wav");
        } else {
            pos = 22;
            end = 82;
            while (pos < end) {
                drawFightScene(player, burton, false);
                StdDraw.picture(pos, 9.5, "/byog/Media/fireball.png", 11, 11, 30);
                displayScreen();
                pos += 1.3;
            }
            StdAudio.play("/byog/Media/mariotakesfireball.wav");
        }
    }

    /**
     * Displays the health drain graphics.
     */
    private static void healthDrainGraphics(Fighter player, Fighter burton) {
        double pos = 22;
        double end = 82;
        StdAudio.play("/byog/Media/bowsatakeshealthdrain.wav");
        while (pos < end) {
            drawFightScene(player, burton, true);
            StdDraw.picture(pos, 9.5, "/byog/Media/heart.png", 5, 5);
            displayScreen();
            pos += 1.3;
        }
    }

    /**
     * Displays the heal graphics.
     */
    private static void healGraphics(Fighter player, Fighter burton) {
        int pos = 40;
        int end = 10;
        while (pos > end) {
            drawFightScene(player, burton, true);
            StdDraw.picture(82, pos, "/byog/Media/mushroom.png", 4, 4);
            displayScreen();
            pos -= 1;
        }
        StdAudio.play("/byog/Media/1up.wav");
    }
}