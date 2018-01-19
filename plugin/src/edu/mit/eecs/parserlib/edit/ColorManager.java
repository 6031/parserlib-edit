package edu.mit.eecs.parserlib.edit;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.widgets.Display;

public class ColorManager {

    public static final RGB COMMENT = new RGB(63, 127, 95);
    public static final RGB STRING = new RGB(41, 0, 255);
    public static final RGB REGEX = new RGB(127, 95, 63);
    public static final RGB KEYWORD = new RGB(127, 0, 85);

    private final Map<RGB, Color> colors = new HashMap<>();

    public void dispose() {
        colors.values().forEach(Color::dispose);
    }

    public Color getColor(RGB rgb) {
        return colors.computeIfAbsent(rgb, k -> new Color(Display.getCurrent(), k));
    }
}
