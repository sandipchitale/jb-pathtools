package sandipchitale.jbpathtools;

import java.io.File;

class FileManagerService {
    public static void fileManager(File file) {
        // Get the configured explorer commands for folder and file
        if (file != null && file.exists()) {
            String exploreCommand;
            if (file.isDirectory()) {
                exploreCommand = PathToolsPreferences.getFolderExploreCommand();
            } else {
                exploreCommand = PathToolsPreferences.getFileExploreCommand();
            }
            Utilities.launch(exploreCommand, file);
        }
    }
}
