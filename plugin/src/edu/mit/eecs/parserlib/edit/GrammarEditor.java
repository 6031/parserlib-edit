package edu.mit.eecs.parserlib.edit;

import org.eclipse.ui.editors.text.TextEditor;

public class GrammarEditor extends TextEditor {

    private ColorManager colors;

    public GrammarEditor() {
        super();
        colors = new ColorManager();
        setSourceViewerConfiguration(new GrammarViewerConfiguration(colors));
        setDocumentProvider(new GrammarDocumentProvider());
    }
}
