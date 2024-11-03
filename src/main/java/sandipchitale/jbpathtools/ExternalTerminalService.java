package sandipchitale.jbpathtools;

import java.io.File;

class ExternalTerminalService {
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
