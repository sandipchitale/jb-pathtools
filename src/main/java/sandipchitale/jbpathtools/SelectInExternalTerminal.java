package sandipchitale.jbpathtools;

import com.intellij.ide.SelectInContext;
import com.intellij.ide.SelectInTarget;

import java.io.File;
import java.util.Objects;

public class SelectInExternalTerminal implements SelectInTarget {
    @Override
    public boolean canSelect(SelectInContext context) {
        return context.getVirtualFile().isInLocalFileSystem();
    }

    @Override
    public void selectIn(SelectInContext context, boolean requestFocus) {
        ExternalTerminalService.externalTerminal(new File(Objects.requireNonNull(context.getVirtualFile().getCanonicalPath())));
    }

    @Override
    public float getWeight() {
        return 1000;
    }

    @Override
    public String toString() {
        return "External Terminal";
    }
}
