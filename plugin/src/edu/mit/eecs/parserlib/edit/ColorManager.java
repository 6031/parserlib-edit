package edu.mit.eecs.parserlib.edit;

import org.eclipse.jdt.ui.JavaUI;
import org.eclipse.jdt.ui.text.IJavaColorConstants;
import org.eclipse.swt.graphics.Color;

public class ColorManager {

    public static final String COMMENT = IJavaColorConstants.JAVA_SINGLE_LINE_COMMENT;
    public static final String STRING = IJavaColorConstants.JAVA_STRING;
    public static final String REGEX = IJavaColorConstants.JAVADOC_KEYWORD;
    public static final String KEYWORD = IJavaColorConstants.JAVA_KEYWORD;

    public Color getColor(String key) {
        return JavaUI.getColorManager().getColor(key);
    }
}
