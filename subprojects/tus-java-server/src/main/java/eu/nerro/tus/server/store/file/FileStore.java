package eu.nerro.tus.server.store.file;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

import org.slf4j.Logger;

import eu.nerro.tus.server.configuration.ConfigVarHelper;
import eu.nerro.tus.server.exception.TusException;
import eu.nerro.tus.server.store.FileInfo;
import eu.nerro.tus.server.store.Store;

import static eu.nerro.tus.server.configuration.ConfigVar.FILESTORE_UPLOAD_DIRECTORY;
import static org.slf4j.LoggerFactory.getLogger;

/**
 * Represents an upload storage mechanism based on local file system.
 * <p>
 * It stores the uploads in a directory specified in two different files:
 * <ul>
 * <li>{@code [id].info} files are used to store {@link FileInfo}
 * <li>{@code [id].bin} files contain the raw binary data uploaded
 * </ul>
 * <p>
 * Note: no cleanup is performed so you may want to run a cronjob to ensure
 * your disk is not filled up with old and finished uploads.
 */
public class FileStore implements Store {

  private static final Logger LOG = getLogger(FileStore.class);

  private static final Path CURRENT_DIRECTORY = Paths.get(".").toAbsolutePath().normalize();

  private final Path uploadDirectory;

  public FileStore() {
    LOG.info("File store initializing");
    final long startTime = System.nanoTime();

    uploadDirectory = getConfiguredUploadedDirectory();

    final long endTime = System.nanoTime();
    LOG.info("File store initialized in {} ms", MILLISECONDS.convert(endTime - startTime, NANOSECONDS));
  }

  private Path getConfiguredUploadedDirectory() {
    final String uploadDirectoryConfigVar = ConfigVarHelper.getEnvValue(FILESTORE_UPLOAD_DIRECTORY);
    if (uploadDirectoryConfigVar == null || uploadDirectoryConfigVar.isEmpty()) {
      LOG.warn("Configuration variable '{}' is not defined, fallback to current directory '{}'", FILESTORE_UPLOAD_DIRECTORY, CURRENT_DIRECTORY);
      return CURRENT_DIRECTORY;
    }

    final Path uploadDirectory;
    try {
      uploadDirectory = Paths.get(uploadDirectoryConfigVar).toAbsolutePath().normalize();
    } catch (InvalidPathException e) {
      LOG.warn("Path string could not be converted, fallback to current directory '{}'", CURRENT_DIRECTORY);
      LOG.error("Configuration variable '{}={}' is not valid", FILESTORE_UPLOAD_DIRECTORY, uploadDirectoryConfigVar, e);
      return CURRENT_DIRECTORY;
    }

    if (Files.notExists(uploadDirectory)) {
      LOG.info("Upload directory '{}' does not exists, creating a new one", uploadDirectory);
      try {
        Files.createDirectory(uploadDirectory);
      } catch (IOException e) {
        LOG.error("Could not create upload directory '{}', fallback to current directory '{}'", uploadDirectory, CURRENT_DIRECTORY, e);
        return CURRENT_DIRECTORY;
      }
    }

    if (!Files.isWritable(uploadDirectory)) {
      LOG.warn("Upload directory '{}' is not writable, fallback to current directory '{}'", uploadDirectory, CURRENT_DIRECTORY);
      return CURRENT_DIRECTORY;
    }

    LOG.info("Use '{}' as upload directory", uploadDirectory);
    return uploadDirectory;
  }

  @Override
  public String createNewUpload(FileInfo fileInfo) {
    return null;
  }

  @Override
  public long writeChunk(String id, long offset) {
    return 0;
  }

  @Override
  public FileInfo getFileInfo(String id) {
    return null;
  }
}
