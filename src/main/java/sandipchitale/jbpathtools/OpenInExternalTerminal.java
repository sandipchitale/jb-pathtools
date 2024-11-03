package sandipchitale.jbpathtools;

import com.intellij.ide.SelectInContext;
import com.intellij.ide.SelectInTarget;
import com.intellij.openapi.fileEditor.FileEditor;
import com.intellij.openapi.fileEditor.impl.text.PsiAwareTextEditorImpl;

import java.io.File;
import java.util.Objects;

public class OpenInExternalTerminal implements SelectInTarget {
    @Override
    public boolean canSelect(SelectInContext context) {
        return context.getVirtualFile().isInLocalFileSystem();
    }

    @Override
    public void selectIn(SelectInContext context, boolean requestFocus) {
        FileEditor fileEditor = Objects.requireNonNull(context.getFileEditorProvider()).openFileEditor();
        if (fileEditor instanceof PsiAwareTextEditorImpl psiAwareTextEditor) {
            externalTerminal(new File(Objects.requireNonNull(context.getVirtualFile().getCanonicalPath())));
            return;
        }
        externalTerminal(new File(Objects.requireNonNull(context.getVirtualFile().getCanonicalPath())));
    }

    @Override
    public float getWeight() {
        return 1000;
    }

    @Override
    public String toString() {
        return "Open in External Terminal...";
    }

    public static void externalTerminal(File file) {
        // Get the configured explorer commands for folder and file
        if (file != null && file.exists()) {
            String exploreCommand;
            if (file.isDirectory()) {
                exploreCommand = PathToolsPreferences.getFolderShellCommand();
            } else {
                exploreCommand = PathToolsPreferences.getFileShellCommand();
            }
            Utilities.launch(exploreCommand, file);
        }
    }
}
