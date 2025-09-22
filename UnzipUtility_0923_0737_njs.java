// 代码生成时间: 2025-09-23 07:37:22
 * This class is designed to be easily understandable and maintainable,
 * with clear code structure and proper error handling.
 */

import org.apache.commons.compress.archivers.zip.ZipArchiveEntry;
import org.apache.commons.compress.archivers.zip.ZipArchiveInputStream;
import org.apache.commons.compress.utils.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.*;

public class UnzipUtility {
    private static final Logger LOGGER = LoggerFactory.getLogger(UnzipUtility.class);

    /**<ol>
     * Unzips a file from a specified source path to a destination directory.
     *
     * @param sourcePath The path to the zip file to be unzipped.
     * @param destinationDirectory The directory where the unzipped files will be placed.
     * @throws IOException If an I/O error occurs.
     */
    public void unzipFile(String sourcePath, String destinationDirectory) throws IOException {
        try (ZipArchiveInputStream zipIn = new ZipArchiveInputStream(new FileInputStream(sourcePath))) {
            ZipArchiveEntry zipEntry = zipIn.getNextZipEntry();
            while (zipEntry != null) {
                String filePath = destinationDirectory + File.separator + zipEntry.getName();
                if (zipEntry.isDirectory()) {
                    File dir = new File(filePath);
                    dir.mkdirs();
                } else {
                    File file = new File(filePath);
                    file.getParentFile().mkdirs();

                    try (BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(file))) {
                        IOUtils.copy(zipIn, bos);
                    }
                }
                zipEntry = zipIn.getNextZipEntry();
            }
        }
    }

    public static void main(String[] args) {
        UnzipUtility unzipUtility = new UnzipUtility();
        try {
            // Replace with the actual paths
            String sourcePath = "path/to/your/zipfile.zip";
            String destinationDirectory = "path/to/your/destination/directory";
            unzipUtility.unzipFile(sourcePath, destinationDirectory);
            LOGGER.info("Unzipping completed successfully.");
        } catch (IOException e) {
            LOGGER.error("An error occurred during unzipping: 
", e);
        }
    }
}