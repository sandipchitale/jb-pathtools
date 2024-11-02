package sandipchitale.jbpathtools;

import com.intellij.ide.plugins.PluginManagerCore;
import com.intellij.ide.util.PropertiesComponent;
import com.intellij.openapi.extensions.PluginId;
import com.intellij.openapi.util.SystemInfo;

import java.io.File;
import java.nio.file.Path;
import java.util.Objects;
import java.util.regex.Pattern;

/**
 * The preferences initializer for Path Tools
 *
 * @author Sandip V. Chitale
 */
public class PathToolsPreferences {
    static final String PREFIX = "sandipchitale.jb-pathtools.";

    static final String FOLDER_EXPLORE_COMMAND_KEY = PREFIX + "folderExploreCommand";
    static final String FILE_EXPLORE_COMMAND_KEY = PREFIX + "fileExploreCommand";

    static String defaultFolderExploreCommand = "";
    static String defaultFileExploreCommand = "";

    static final String FOLDER_SHELL_COMMAND_KEY = PREFIX + "folderShellCommand";
    static final String FILE_SHELL_COMMAND_KEY = PREFIX + "fileShellCommand";

    static String defaultFolderShellCommand = "";
    static String defaultFileShellCommand = "";

    static final String FOLDER_EDIT_COMMAND_KEY = PREFIX + "folderEditCommand";
    static final String FILE_EDIT_COMMAND_KEY = PREFIX + "fileEditCommand";

    static String defaultFolderEditCommand = "";
    static String defaultFileEditCommand = "";

    static final String FOLDER_COMMANDS_KEY = PREFIX + "folderCommands";
    static final String FILE_COMMANDS_KEY = PREFIX + "fileCommands";

    static final String TABLE_KEY = "table";

    static String defaultFolderCommands = "";
    static String defaultFileCommands = "";

    static final String FILE_NAME = "{name}";
    static final String FILE_NAME_SANS_EXTENSION = "{name-sans-extension}";
    static final String FILE_EXTENSION = "{extension}";
    static final String FILE_PATH = "{path}";
    static final String FILE_PATH_SLASHES = "{path-slashes}";
    static final String FILE_PATH_BACKSLASHES = "{path-backslashes}";

    static final String FILE_PARENT_NAME = "{parent-name}";
    static final String FILE_PARENT_PATH = "{parent-path}";
    static final String FILE_PARENT_PATH_SLASHES = "{parent-path-slashes}";
    static final String FILE_PARENT_PATH_BACKSLASHES = "{parent-path-backslashes}";

    static final String LAST_COPY_PATH_FORMAT = "lastCopyPathFormat";
    static final String defaultLLastCopyPathFormat = FILE_PATH;

    static {
        if (SystemInfo.isMac) {
            defaultFolderExploreCommand = "/usr/bin/open -a /System/Library/CoreServices/Finder.app \""
                    + PathToolsPreferences.FILE_PATH + "\"";
            defaultFileExploreCommand = "/usr/bin/open -a /System/Library/CoreServices/Finder.app -R \""
                    + PathToolsPreferences.FILE_PATH + "\"";
            defaultFolderEditCommand = "/usr/bin/open -a /System/Library/CoreServices/Finder.app -R \""
                    + PathToolsPreferences.FILE_PATH + "\"";
            defaultFileEditCommand = "/usr/bin/open -a /Applications/TextEdit.app \""
                    + PathToolsPreferences.FILE_PATH + "\"";
            defaultFolderShellCommand = "open -a /Applications/Terminal.app";
            defaultFileShellCommand = "open -a /Applications/Terminal.app";
        } else if (SystemInfo.isWindows) {
            defaultFolderExploreCommand = "cmd /C start explorer /select,/e, \""
                    + PathToolsPreferences.FILE_PATH + "\"";
            defaultFileExploreCommand = "cmd /C start explorer /select,/e, \""
                    + PathToolsPreferences.FILE_PATH + "\"";
            defaultFolderShellCommand = "cmd /K start cd /D \""
                    + PathToolsPreferences.FILE_PATH + "\"";
            defaultFileShellCommand = "cmd /K start cd /D \""
                    + PathToolsPreferences.FILE_PARENT_PATH + "\"";
            defaultFolderEditCommand = "cmd /C start explorer /select,/e, \""
                    + PathToolsPreferences.FILE_PATH + "\"";
            defaultFileEditCommand = "cmd /C start notepad \""
                    + PathToolsPreferences.FILE_PATH + "\"";
        } else if (SystemInfo.isLinux) {
            if (new File("/usr/bin/konqueror").exists()) {
                defaultFolderExploreCommand = "/usr/bin/konqueror \""
                        + PathToolsPreferences.FILE_PATH + "\"";
                defaultFileExploreCommand = "/usr/bin/konqueror \""
                        + PathToolsPreferences.FILE_PARENT_PATH + "\"";
                defaultFolderEditCommand = "/usr/bin/konqueror \""
                        + PathToolsPreferences.FILE_PATH + "\"";
            } else if (new File("/usr/bin/nautilus").exists()) {
                defaultFolderExploreCommand = "/usr/bin/nautilus \""
                        + PathToolsPreferences.FILE_PATH + "\"";
                defaultFileExploreCommand = "/usr/bin/nautilus \""
                        + PathToolsPreferences.FILE_PARENT_PATH + "\"";
                defaultFolderEditCommand = "/usr/bin/nautilus \""
                        + PathToolsPreferences.FILE_PATH + "\"";
            }
            if (new File("/usr/bin/gnome-terminal").exists()) {
                defaultFolderShellCommand = "gnome-terminal --working-directory=\""
                        + PathToolsPreferences.FILE_PATH + "\"";
                defaultFileShellCommand = "gnome-terminal --working-directory=\""
                        + PathToolsPreferences.FILE_PARENT_PATH + "\"";
            } else {
                defaultFolderShellCommand = "xterm -e \"cd \\\"" + PathToolsPreferences.FILE_PATH + "\\\" && /bin/bash\"";
                defaultFileShellCommand = "xterm -e \"cd \\\"" + PathToolsPreferences.FILE_PARENT_PATH + "\\\" && /bin/bash\"";
            }
            if (new File("/usr/bin/kedit").exists()) {
                defaultFileEditCommand = "/usr/bin/kedit \""
                        + PathToolsPreferences.FILE_PATH + "\"";
            } else if (new File("/usr/bin/gedit").exists()) {
                defaultFileEditCommand = "/usr/bin/gedit \""
                        + PathToolsPreferences.FILE_PATH + "\"";
            }
        }
        if (SystemInfo.isMac) {
            String terminalDotScpt = Path.of(
                    Objects.requireNonNull(PluginManagerCore.getPlugin(PluginId.getId("sandipchitale.jb-pathtools"))).getPluginPath().toFile().getAbsolutePath(),
                    "scripts",
                    "cdterminal.scpt"
            ).toString();
            defaultFolderShellCommand = "/usr/bin/osascript \"" + terminalDotScpt + "\" \"" + PathToolsPreferences.FILE_PATH + "\"";
            defaultFileShellCommand = "/usr/bin/osascript \"" + terminalDotScpt + "\" \"" + PathToolsPreferences.FILE_PARENT_PATH + "\"";
        }
    }

    static String getFolderExploreCommand() {
        return PropertiesComponent.getInstance().getValue(FOLDER_EXPLORE_COMMAND_KEY, defaultFolderExploreCommand);
    }

    static String getFileExploreCommand() {
        return PropertiesComponent.getInstance().getValue(FILE_EXPLORE_COMMAND_KEY, defaultFileExploreCommand);
    }

    static String getFolderShellCommand() {
        return PropertiesComponent.getInstance().getValue(FOLDER_SHELL_COMMAND_KEY, defaultFolderShellCommand);
    }

    static String getFileShellCommand() {
        return PropertiesComponent.getInstance().getValue(FILE_SHELL_COMMAND_KEY, defaultFileShellCommand);
    }

    private static String LINE_SEPARATOR = "@@@@";
    private static String FIELD_SEPARATOR = "####";

    static String createList(String[][] commands) {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < commands.length; i++) {
            if (i > 0) {
                stringBuilder.append(LINE_SEPARATOR);
            }
            String[] command = commands[i];
            for (int j = 0; j < command.length; j++) {
                if (j > 0) {
                    stringBuilder.append(FIELD_SEPARATOR);
                }
                stringBuilder.append(command[j]);
            }
        }
        return stringBuilder.toString();
    }

    static String[][] parseString(String commandsString) {
        if (commandsString != null && commandsString.length() > 0) {
            String[] commands = commandsString.split(Pattern.quote(LINE_SEPARATOR));
            String[][] parsedCommands = new String[commands.length][];
            for (int i = 0; i < commands.length; i++) {
                String command = commands[i];
                if (command.indexOf(FIELD_SEPARATOR) == -1) {
                    parsedCommands[i] = new String[]{command, "*", command};
                } else {
                    String[] fields = command.split(Pattern.quote(FIELD_SEPARATOR));
                    parsedCommands[i] = new String[fields.length];
                    for (int j = 0; j < fields.length; j++) {
                        parsedCommands[i][j] = fields[j];
                    }
                }
            }
            return parsedCommands;
        }
        return new String[0][0];
    }

}
