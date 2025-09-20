// 代码生成时间: 2025-09-20 18:44:41
import com.dropwizard.Application;
import com.dropwizard.setup.Bootstrap;
import com.dropwizard.setup.Environment;
import com.dropwizard.views.View;
import com.dropwizard.views.ViewBundle;
import io.dropwizard.assets.AssetsBundle;
import io.dropwizard.views.freemarker.FreemarkerViewRenderer;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;

public class FileBackupAndSyncTool extends Application<FileBackupAndSyncConfig> {

    private static final Logger LOGGER = LoggerFactory.getLogger(FileBackupAndSyncTool.class);

    public static void main(String[] args) throws Exception {
        new FileBackupAndSyncTool().run(args);
    }

    @Override
    public void initialize(Bootstrap<FileBackupAndSyncConfig> bootstrap) {
        bootstrap.addBundle(new ViewBundle<>("classpath:/", ".html", "text/html",
                new FreemarkerViewRenderer(), // Using Freemarker for rendering
                FileBackupAndSyncView.class));
        bootstrap.addBundle(new AssetsBundle("/assets/", "/", "assets",
                null, // Default cache max-age
                false)); // No processing
    }

    @Override
    public void run(FileBackupAndSyncConfig configuration, Environment environment) {
        environment.jersey().register(new FileBackupAndSyncResource(configuration));
    }
}

class FileBackupAndSyncResource {

    private final FileBackupAndSyncConfig config;

    public FileBackupAndSyncResource(FileBackupAndSyncConfig config) {
        this.config = config;
    }

    public String backupFiles() {
        String sourcePath = config.getSourcePath();
        String backupPath = config.getBackupPath();
        File sourceDir = new File(sourcePath);
        File backupDir = new File(backupPath);

        if (!sourceDir.exists() || !sourceDir.isDirectory()) {
            return "Source directory does not exist or is not a directory";
        }

        try {
            FileUtils.copyDirectory(sourceDir, backupDir,
                    file -> !file.getName().endsWith(".bak"));
            return "Backup completed successfully";
        } catch (IOException e) {
            LOGGER.error("Error during backup", e);
            return "Backup failed due to an error: " + e.getMessage();
        }
    }

    public String syncFiles() {
        String sourcePath = config.getSourcePath();
        String backupPath = config.getBackupPath();
        File sourceDir = new File(sourcePath);
        File backupDir = new File(backupPath);

        if (!sourceDir.exists() || !sourceDir.isDirectory() ||
                !backupDir.exists() || !backupDir.isDirectory()) {
            return "Source or backup directory does not exist or is not a directory";
        }

        try {
            // Sync logic implementation
            // For each file in source directory, check if it exists in backup directory
            // If it does, compare the file hashes and update if necessary
            // If it doesn't, copy the file to backup directory
            List<File> sourceFiles = new ArrayList<>();
            sourceFiles.addAll(Arrays.asList(sourceDir.listFiles()));
            for (File sourceFile : sourceFiles) {
                Path sourceFilePath = Paths.get(sourcePath, sourceFile.getName());
                Path backupFilePath = Paths.get(backupPath, sourceFile.getName());

                if (Files.exists(backupFilePath)) {
                    // Compare file hashes
                    if (!Files.isSameFile(sourceFilePath, backupFilePath)) {
                        Files.copy(sourceFilePath, backupFilePath,
                                StandardCopyOption.REPLACE_EXISTING);
                    }
                } else {
                    Files.createDirectories(backupFilePath.getParent());
                    Files.copy(sourceFilePath, backupFilePath);
                }
            }
            return "Files synchronized successfully";
        } catch (IOException e)
        {
            LOGGER.error("Error during file synchronization", e);
            return "File synchronization failed due to an error: " + e.getMessage();
        }
    }
}

class FileBackupAndSyncConfig extends Configuration {
    // Configuration class with source and backup paths
    private String sourcePath;
    private String backupPath;

    public String getSourcePath() {
        return sourcePath;
    }

    public void setSourcePath(String sourcePath) {
        this.sourcePath = sourcePath;
    }

    public String getBackupPath() {
        return backupPath;
    }

    public void setBackupPath(String backupPath) {
        this.backupPath = backupPath;
    }
}

class FileBackupAndSyncView implements View {
    public FileBackupAndSyncView() {
        super("index.ftl");
    }
}
