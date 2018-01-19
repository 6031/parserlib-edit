package edu.mit.eecs.parserlib.edit;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.IDocumentPartitioner;
import org.eclipse.jface.text.rules.FastPartitioner;
import org.eclipse.ui.editors.text.FileDocumentProvider;

public class GrammarDocumentProvider extends FileDocumentProvider {

    @Override protected IDocument createDocument(Object element) throws CoreException {
        final IDocument document = super.createDocument(element);
        if (document == null) { return null; }
        
        final IDocumentPartitioner partitioner = new FastPartitioner(
                new GrammarPartitionScanner(),
                new String[] { GrammarPartitionScanner.COMMENT });
        partitioner.connect(document);
        document.setDocumentPartitioner(partitioner);
        return document;
    }
}
