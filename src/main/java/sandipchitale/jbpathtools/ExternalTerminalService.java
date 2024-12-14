package sandipchitale.jbpathtools;

import java.io.File;

class ExternalTerminalService {
    public static void externalTerminal(File file) {
        // Get the configured external terminal commands for folder and file
        if (file != null && file.exists()) {
            String externalTerminalCommand;
            if (file.isDirectory()) {
                externalTerminalCommand = PathToolsPreferences.getFolderShellCommand();
            } else {
                externalTerminalCommand = PathToolsPreferences.getFileShellCommand();
            }
            Utilities.launch(externalTerminalCommand, file);
        }
    }
}
