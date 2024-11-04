package sandipchitale.jbpathtools;

import java.io.File;
import java.util.regex.MatchResult;
import java.util.regex.Pattern;

/**
 * This implements some utility methods.
 *
 * @author Sandip V. Chitale
 */
public class Utilities {
    private static Pattern VARIABLE_PATTERN = Pattern.compile("\\{([a-z-]+)\\}");

    static void launch(String command, File file) {
        // Launch the explore command
        CommandLauncher.launch(processCommand(command, file));
    }


//	private static String[] escapeBackslash(String[] paths) {
//		String[] escapedPaths = new String[paths.length];
//		for (int i = 0; i < paths.length; i++) {
//			escapedPaths[i] = paths[i].replaceAll(Pattern.quote("\\"), Matcher.quoteReplacement("\\\\"));
//		}
//		return escapedPaths;
//	}

//	static String convertParameters(String command) {
//		return command
//				.replaceAll(Pattern.quote(PathToolsPreferences.FILE_PATH), "\\$$0")
//				.replaceAll(Pattern.quote(PathToolsPreferences.FILE_PARENT_PATH), "\\$$0")
//				.replaceAll(Pattern.quote(PathToolsPreferences.FILE_PATH_SLASHES), "\\$$0")
//				.replaceAll(Pattern.quote(PathToolsPreferences.FILE_PARENT_PATH_SLASHES), "\\$$0")
//				.replaceAll(Pattern.quote(PathToolsPreferences.FILE_PATH_BACKSLASHES), "\\$$0")
//				.replaceAll(Pattern.quote(PathToolsPreferences.FILE_PARENT_PATH_BACKSLASHES), "\\$$0")
//				.replaceAll(Pattern.quote(PathToolsPreferences.FILE_NAME), "\\$$0")
//				.replaceAll(Pattern.quote(PathToolsPreferences.FILE_PARENT_NAME), "\\$$0");
//	}

    /**
     * <ul>
     * <li><code>${path}</code></li>
     * <li><code>${path-slashes}</code></li>
     * <li><code>${path-backslashes}</code></li>
     * <li><code>${parent-path}</code></li>
     * <li><code>${parent-path-slashes}</code></li>
     * <li><code>${parent-path-backslashes}</code></li>
     * <li><code>${name}</code></li>
     * <li><code>${name-sans-extension}</code></li>
     * <li><code>${extension}</code></li>
     * <li><code>${parent-path}</code></li>
     * <li><code>${parent-name}</code></li>
     * <li><code>${parent-name-sans-extension}</code></li>
     * <li><code>${parent-extension}</code></li>
     * </ul>
     *
     * @param command
     * @param file
     * @return
     */

    static String processCommand(String command, File file) {
        File parentFile = file.getParentFile();
        return VARIABLE_PATTERN.matcher(command).replaceAll((MatchResult matchResult) -> {
            String variableName = matchResult.group(0);
            switch (variableName) {
                case PathToolsPreferences.FILE_PATH:
                    return file.getAbsolutePath();
                case PathToolsPreferences.FILE_PATH_SLASHES:
                    return file.getAbsolutePath().replace('\\', '/');
                case PathToolsPreferences.FILE_PATH_BACKSLASHES:
                    return file.getAbsolutePath().replace('/', '\\');
                case PathToolsPreferences.FILE_NAME:
                    return file.getName();
                case PathToolsPreferences.FILE_NAME_SANS_EXTENSION:
                    return splitNameAndExtension(file.getName())[0];
                case PathToolsPreferences.FILE_EXTENSION:
                    return splitNameAndExtension(file.getName())[1];
                case PathToolsPreferences.FILE_PARENT_NAME:
                    return parentFile.getName();
                case "parent-name-sans-extension":
                    return splitNameAndExtension(parentFile.getName())[0];
                case "parent-extension":
                    return splitNameAndExtension(parentFile.getName())[1];
                case PathToolsPreferences.FILE_PARENT_PATH:
                    return parentFile.getAbsolutePath();
                case PathToolsPreferences.FILE_PARENT_PATH_SLASHES:
                    return parentFile.getAbsolutePath().replace('\\', '/');
                case PathToolsPreferences.FILE_PARENT_PATH_BACKSLASHES:
                    return parentFile.getAbsolutePath().replace('/', '\\');
            }
            return "{" + variableName + "}";
        });
    }

    /**
     * Return the name without extension and extension for a name.
     *
     * @param nameAndExtension
     * @return the name without extension and extension for a name.
     */
    private static String[] splitNameAndExtension(String nameAndExtension) {
        String[] nameAndExtensionArray = new String[]{nameAndExtension, ""};
        int lastIndexOfDot = nameAndExtension.lastIndexOf('.');
        if (lastIndexOfDot != -1) {
            nameAndExtensionArray[0] = nameAndExtension.substring(0,
                    lastIndexOfDot);
            nameAndExtensionArray[1] = nameAndExtension
                    .substring(lastIndexOfDot + 1);
        }
        return nameAndExtensionArray;
    }
}
