package sandipchitale.jbpathtools;

import com.intellij.ide.SelectInContext;
import com.intellij.ide.SelectInTarget;
import com.intellij.openapi.util.SystemInfo;

import java.io.File;
import java.util.Objects;

public class SelectInFileManager implements SelectInTarget {
    @Override
    public boolean canSelect(SelectInContext context) {
        return SystemInfo.isLinux && context.getVirtualFile().isInLocalFileSystem();
    }

    @Override
    public void selectIn(SelectInContext context, boolean requestFocus) {
        FileManagerService.fileManager(new File(Objects.requireNonNull(context.getVirtualFile().getPresentableUrl())));
    }

    @Override
    public float getWeight() {
        return 1000;
    }

    @Override
    public String toString() {
        return "File Manager";
    }
}
