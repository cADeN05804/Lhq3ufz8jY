// 代码生成时间: 2025-10-01 03:13:27
import io.dropwizard.Application;
import io.dropwizard.assets.AssetsBundle;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import io.dropwizard.views.ViewBundle;
import org.apache.commons.codec.binary.Base64;
import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.SecureRandom;
import java.util.Scanner;

@Path("/encrypt-decrypt")
public class EncryptionDecryptionTool extends Application<EncryptionDecryptionToolConfiguration> {

    @Override
    public void initialize(Bootstrap<EncryptionDecryptionToolConfiguration> bootstrap) {
        // Initialize any resources here.
    }

    @Override
    public void run(EncryptionDecryptionToolConfiguration configuration, Environment environment) {
        // Register a new resource.
        environment.jersey().register(new FileEncryptionResource());
    }

    public static void main(String[] args) throws Exception {
        new EncryptionDecryptionTool().run(args);
    }
}

/*
 * Resource class for handling file encryption and decryption.
 */
@Path("/files")
class FileEncryptionResource {

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    @Path("/encrypt")
    public Response encryptFile(String filePath) {
        try {
            // Generate a new secret key.
            SecretKey secretKey = KeyGenerator.getInstance("AES").generateKey();

            // Read the file and encrypt its content.
            File file = new File(filePath);
            FileInputStream inputStream = new FileInputStream(file);
            byte[] fileContent = new byte[(int) file.length()];
            inputStream.read(fileContent);
            inputStream.close();

            // Encrypt the content.
            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.ENCRYPT_MODE, secretKey);
            byte[] encryptedContent = cipher.doFinal(fileContent);
            return Response.ok(new String(Base64.encodeBase64(encryptedContent))).build();

        } catch (Exception e) {
            e.printStackTrace();
            return Response.serverError().entity("Error encrypting file: " + e.getMessage()).build();
        }
    }

    @POST
    @Produces(MediaType.TEXT_PLAIN)
    @Path("/decrypt")
    public Response decryptFile(String encryptedFileContent) {
        try {
            // Decode the base64 encoded encrypted file content.
            byte[] encryptedContent = Base64.decodeBase64(encryptedFileContent);

            // Decrypt the content.
            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.DECRYPT_MODE, generateKey());
            byte[] fileContent = cipher.doFinal(encryptedContent);

            // Write the decrypted content to a file.
            String outputFilePath = "decrypted_file.txt";
            FileOutputStream outputStream = new FileOutputStream(outputFilePath);
            outputStream.write(fileContent);
            outputStream.close();

            return Response.ok("Decrypted file saved to: " + outputFilePath).build();

        } catch (Exception e) {
            e.printStackTrace();
            return Response.serverError().entity("Error decrypting file: " + e.getMessage()).build();
        }
    }

    // Method to generate a key from a secure random generator.
    private SecretKey generateKey() throws Exception {
        KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
        SecureRandom random = new SecureRandom();
        keyGenerator.init(128, random);
        return keyGenerator.generateKey();
    }
}

/*
 * Configuration class for the application.
 */
class EncryptionDecryptionToolConfiguration extends Configuration {
    // Add configuration properties here.
}