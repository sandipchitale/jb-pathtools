package sandipchitale.jbpathtools;

import com.intellij.openapi.actionSystem.*;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.vfs.VirtualFile;
import org.jetbrains.annotations.NotNull;

import java.io.File;

public class ExternalTerminalAction extends AnAction {
    @Override
    public void actionPerformed(@NotNull AnActionEvent e) {
        VirtualFile virtualFile = null;
        Editor editor = CommonDataKeys.EDITOR.getData(e.getDataContext());
        if (editor != null) {
            virtualFile = editor.getVirtualFile();
        } else {
            VirtualFile[] virtualFileArray = PlatformDataKeys.VIRTUAL_FILE_ARRAY.getData(e.getDataContext());
            if (virtualFileArray != null && virtualFileArray.length > 0) {
                virtualFile = virtualFileArray[0];
            }
        }

        if (virtualFile != null) {
            explore(new File(virtualFile.getPath()));
        }
    }

    @Override
    public void update(@NotNull AnActionEvent e) {
        e.getPresentation().setEnabled(
                PlatformDataKeys.VIRTUAL_FILE_ARRAY.getData(e.getDataContext()) != null || CommonDataKeys.EDITOR.getData(e.getDataContext()) != null);
    }

    @Override
    public @NotNull ActionUpdateThread getActionUpdateThread() {
        return ActionUpdateThread.EDT;
    }

    public static void explore(File file) {
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
