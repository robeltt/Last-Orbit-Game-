package lastorbit.gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

import lastorbit.exceptions.AssetLoadException;
import lastorbit.exceptions.InvalidRuleException;
import lastorbit.rules.EffectOption;
import lastorbit.rules.Rule;
import lastorbit.rules.RuleCatalog;
import lastorbit.rules.RuleEngine;
import lastorbit.rules.TriggerOption;

public class RuleConfigScreen extends Screen {

    private static final int SLOTS = 3;
    private static final int MAX_NAME = 16;

    private final int width;
    private final int height;
    private final BufferedImage background;
    private final ScreenFactory factory;
    private final RuleCatalog catalog;

    private final int[] triggerIndex = new int[SLOTS];
    private final int[] effectIndex  = new int[SLOTS];
    private final int[] triggerValue = new int[SLOTS];
    private final int[] effectValue  = new int[SLOTS];

    private String configName = "";
    private boolean editingName = false;

    private final List<Hotspot> hotspots = new ArrayList<>();

    private final Button startButton;
    private final Button backButton;

    private String errorMessage = null;
    private int errorTimer = 0;

    public RuleConfigScreen(int width, int height, BufferedImage background,
                            ScreenFactory factory) {
        this.width = width;
        this.height = height;
        this.background = background;
        this.factory = factory;
        this.catalog = new RuleCatalog();
        for (int i = 0; i < SLOTS; i++) {
            triggerIndex[i] = -1;
            effectIndex[i] = -1;
        }
        this.startButton = new Button(width / 2 - 290, height - 90, 260, 56, "START GAME");
        this.backButton  = new Button(width / 2 + 30, height - 90, 260, 56, "BACK");
    }

    @Override
    public void update() {
    }

    @Override
    public void render(Graphics2D g) {
        hotspots.clear();
        g.drawImage(background, 0, 0, width, height, null);
        g.setColor(new Color(0, 0, 0, 160));
        g.fillRect(0, 0, width, height);

        g.setFont(new Font("Consolas", Font.BOLD, 40));
        g.setColor(new Color(120, 200, 255));
        g.drawString("CONFIGURE YOUR RULES", width / 2 - 250, 70);
        g.setFont(new Font("Consolas", Font.PLAIN, 16));
        g.setColor(Color.LIGHT_GRAY);
        g.drawString("Pair a trigger with an effect. Leave a slot on 'None' to skip it.",
                     width / 2 - 250, 100);

        g.setFont(new Font("Consolas", Font.PLAIN, 16));
        g.setColor(Color.CYAN);
        g.drawString("RULE NAME:", width / 2 - 250, 130);
        int nameBoxX = width / 2 - 110, nameBoxY = 114, nameBoxW = 360, nameBoxH = 26;
        g.setColor(new Color(15, 30, 55));
        g.fillRoundRect(nameBoxX, nameBoxY, nameBoxW, nameBoxH, 8, 8);
        g.setColor(editingName ? Color.YELLOW : new Color(80, 140, 200));
        g.drawRoundRect(nameBoxX, nameBoxY, nameBoxW, nameBoxH, 8, 8);
        String shown = configName;
        if (editingName) {
            g.setColor(Color.WHITE);
            shown = shown + "_";
        } else if (shown.isEmpty()) {
            g.setColor(Color.GRAY);
            shown = "(optional)";
        } else {
            g.setColor(Color.WHITE);
        }
        g.drawString(shown, nameBoxX + 8, nameBoxY + 19);
        hotspots.add(new Hotspot(nameBoxX, nameBoxY, nameBoxW, nameBoxH, "NAME"));

        int slotY = 160;
        for (int i = 0; i < SLOTS; i++) {
            drawSlot(g, i, slotY);
            slotY += 150;
        }

        if (errorMessage != null && errorTimer > 0) {
            g.setFont(new Font("Consolas", Font.BOLD, 18));
            g.setColor(new Color(255, 90, 90));
            g.drawString(errorMessage, 130, height - 110);
            errorTimer--;
            if (errorTimer == 0) {
                errorMessage = null;
            }
        }

        startButton.render(g, false);
        backButton.render(g, false);
    }

    private void drawSlot(Graphics2D g, int slot, int y) {
        g.setColor(new Color(20, 40, 70, 200));
        g.fillRoundRect(120, y, width - 240, 130, 14, 14);
        g.setColor(new Color(80, 140, 200));
        g.drawRoundRect(120, y, width - 240, 130, 14, 14);

        g.setFont(new Font("Consolas", Font.BOLD, 20));
        g.setColor(Color.WHITE);
        g.drawString("RULE " + (slot + 1), 145, y + 30);

        g.setFont(new Font("Consolas", Font.PLAIN, 16));
        g.setColor(Color.CYAN);
        g.drawString("TRIGGER:", 145, y + 62);
        String tLabel = triggerIndex[slot] < 0
            ? "None"
            : catalog.getTriggers().get(triggerIndex[slot]).describe(triggerValue[slot]);
        drawSelector(g, 230, y + 48, 480, tLabel, "T" + slot,
                     triggerIndex[slot] >= 0
                        && catalog.getTriggers().get(triggerIndex[slot]).hasValue());

        g.setColor(Color.ORANGE);
        g.drawString("EFFECT:", 145, y + 102);
        String eLabel = effectIndex[slot] < 0
            ? "None"
            : catalog.getEffects().get(effectIndex[slot]).describe(effectValue[slot]);
        drawSelector(g, 230, y + 88, 480, eLabel, "E" + slot,
                     effectIndex[slot] >= 0
                        && catalog.getEffects().get(effectIndex[slot]).hasValue());
    }

    private void drawSelector(Graphics2D g, int x, int y, int w,
                              String label, String tag, boolean showValueButtons) {
        g.setColor(new Color(40, 90, 140));
        g.fillRoundRect(x, y, 36, 32, 8, 8);
        g.setColor(Color.WHITE);
        g.drawString("<", x + 13, y + 22);
        hotspots.add(new Hotspot(x, y, 36, 32, tag + "_PREV"));

        g.setColor(new Color(15, 30, 55));
        g.fillRoundRect(x + 42, y, w, 32, 8, 8);
        g.setColor(Color.WHITE);
        g.setFont(new Font("Consolas", Font.PLAIN, 16));
        g.drawString(label, x + 54, y + 22);

        int rx = x + 42 + w + 6;
        g.setColor(new Color(40, 90, 140));
        g.fillRoundRect(rx, y, 36, 32, 8, 8);
        g.setColor(Color.WHITE);
        g.drawString(">", rx + 13, y + 22);
        hotspots.add(new Hotspot(rx, y, 36, 32, tag + "_NEXT"));

        if (showValueButtons) {
            int mx = rx + 48;
            g.setColor(new Color(90, 60, 40));
            g.fillRoundRect(mx, y, 32, 32, 8, 8);
            g.setColor(Color.WHITE);
            g.drawString("-", mx + 12, y + 22);
            hotspots.add(new Hotspot(mx, y, 32, 32, tag + "_DEC"));

            int px = mx + 38;
            g.setColor(new Color(40, 90, 60));
            g.fillRoundRect(px, y, 32, 32, 8, 8);
            g.setColor(Color.WHITE);
            g.drawString("+", px + 11, y + 22);
            hotspots.add(new Hotspot(px, y, 32, 32, tag + "_INC"));
        }
    }

    @Override
    public void handleClick(int x, int y) {
        if (startButton.contains(x, y)) {
            startGame();
            return;
        }
        if (backButton.contains(x, y)) {
            getManager().setScreen(factory.createMenu());
            return;
        }
        for (Hotspot h : hotspots) {
            if (h.contains(x, y)) {
                applyAction(h.getTag());
                return;
            }
        }
        editingName = false;
    }

    @Override
    public void handleKeyPressed(int keyCode) {
        if (!editingName) {
            return;
        }
        if (keyCode == KeyEvent.VK_ENTER) {
            editingName = false;
        } else if (keyCode == KeyEvent.VK_BACK_SPACE) {
            if (configName.length() > 0) {
                configName = configName.substring(0, configName.length() - 1);
            }
        } else if (configName.length() < MAX_NAME) {
            if (keyCode >= KeyEvent.VK_A && keyCode <= KeyEvent.VK_Z) {
                configName += (char) ('A' + (keyCode - KeyEvent.VK_A));
            } else if (keyCode >= KeyEvent.VK_0 && keyCode <= KeyEvent.VK_9) {
                configName += (char) ('0' + (keyCode - KeyEvent.VK_0));
            } else if (keyCode == KeyEvent.VK_SPACE) {
                configName += ' ';
            }
        }
    }

    private void applyAction(String tag) {
        if (tag.equals("NAME")) {
            editingName = true;
            return;
        }

        editingName = false;
        boolean isTrigger = tag.charAt(0) == 'T';
        int slot = tag.charAt(1) - '0';
        String action = tag.substring(3);

        int count = isTrigger ? catalog.getTriggers().size()
                              : catalog.getEffects().size();

        if (action.equals("PREV")) {
            if (isTrigger) {
                triggerIndex[slot] = step(triggerIndex[slot], -1, count);
                resetTriggerValue(slot);
            } else {
                effectIndex[slot] = step(effectIndex[slot], -1, count);
                resetEffectValue(slot);
            }
        } else if (action.equals("NEXT")) {
            if (isTrigger) {
                triggerIndex[slot] = step(triggerIndex[slot], +1, count);
                resetTriggerValue(slot);
            } else {
                effectIndex[slot] = step(effectIndex[slot], +1, count);
                resetEffectValue(slot);
            }
        } else if (action.equals("DEC")) {
            if (isTrigger) { adjustTriggerValue(slot, -1); }
            else           { adjustEffectValue(slot, -1); }
        } else if (action.equals("INC")) {
            if (isTrigger) { adjustTriggerValue(slot, +1); }
            else           { adjustEffectValue(slot, +1); }
        }
    }

    private int step(int current, int dir, int count) {
        int next = current + dir;
        if (next < -1) { next = count - 1; }
        if (next >= count) { next = -1; }
        return next;
    }

    private void resetTriggerValue(int slot) {
        if (triggerIndex[slot] >= 0) {
            triggerValue[slot] = catalog.getTriggers().get(triggerIndex[slot]).getDefaultValue();
        }
    }

    private void resetEffectValue(int slot) {
        if (effectIndex[slot] >= 0) {
            effectValue[slot] = catalog.getEffects().get(effectIndex[slot]).getDefaultValue();
        }
    }

    private void adjustTriggerValue(int slot, int dir) {
        if (triggerIndex[slot] < 0) { return; }
        TriggerOption opt = catalog.getTriggers().get(triggerIndex[slot]);
        if (!opt.hasValue()) { return; }
        triggerValue[slot] = clamp(triggerValue[slot] + dir,
                                   opt.getMinValue(), opt.getMaxValue());
    }

    private void adjustEffectValue(int slot, int dir) {
        if (effectIndex[slot] < 0) { return; }
        EffectOption opt = catalog.getEffects().get(effectIndex[slot]);
        if (!opt.hasValue()) { return; }
        effectValue[slot] = clamp(effectValue[slot] + dir,
                                  opt.getMinValue(), opt.getMaxValue());
    }

    private int clamp(int v, int lo, int hi) {
        if (v < lo) { return lo; }
        if (v > hi) { return hi; }
        return v;
    }

    private void startGame() {
        RuleEngine engine = new RuleEngine();
        for (int i = 0; i < SLOTS; i++) {
            if (triggerIndex[i] >= 0 && effectIndex[i] >= 0) {
                TriggerOption t = catalog.getTriggers().get(triggerIndex[i]);
                EffectOption e = catalog.getEffects().get(effectIndex[i]);
                try {
                    engine.addRule(new Rule(
                        t.build(triggerValue[i]),
                        e.build(effectValue[i]),
                        true));
                } catch (InvalidRuleException ex) {
                    errorMessage = "Rule " + (i + 1) + ": " + ex.getMessage();
                    errorTimer = 240;
                    return;
                }
            }
        }
        try {
            getManager().setScreen(factory.createGame(engine));
        } catch (AssetLoadException ex) {
            getManager().setScreen(factory.createMenu());
        }
    }
}