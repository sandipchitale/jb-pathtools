package sandipchitale.jbpathtools;

import com.intellij.openapi.actionSystem.ActionUpdateThread;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.CommonDataKeys;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.util.SystemInfo;
import com.intellij.openapi.vfs.VirtualFile;
import org.jetbrains.annotations.NotNull;

import java.io.File;

public class FileManagerAction extends AnAction {
    @Override
    public void actionPerformed(@NotNull AnActionEvent actionEvent) {
        VirtualFile virtualFile = null;
        Editor editor = CommonDataKeys.EDITOR.getData(actionEvent.getDataContext());
        if (editor == null) {
            virtualFile = actionEvent.getDataContext().getData(CommonDataKeys.VIRTUAL_FILE);
        } else {
            virtualFile = editor.getVirtualFile();
        }

        if (virtualFile != null) {
            FileManagerService.fileManager(new File(virtualFile.getPath()));
        }
    }

    @Override
    public void update(@NotNull AnActionEvent actionEvent) {
        actionEvent.getPresentation().setEnabledAndVisible(SystemInfo.isLinux);
    }

    @Override
    public @NotNull ActionUpdateThread getActionUpdateThread() {
        return ActionUpdateThread.EDT;
    }
}
