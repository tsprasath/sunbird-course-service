package org.sunbird.content.util;

import static java.io.File.separator;
import static org.sunbird.common.models.util.JsonKey.*;
import static org.sunbird.common.models.util.ProjectUtil.getConfigValue;
import static org.sunbird.common.util.CloudStorageUtil.CloudStorageType.*;

import java.io.File;
import org.sunbird.common.util.CloudStorageUtil;
import org.sunbird.common.util.CloudStorageUtil.CloudStorageType;

public class ContentCloudStore {

  public static String FOLDER = getConfigValue(CLOUD_FOLDER_CONTENT);

  public static String getUri(String prefix, boolean isDirectory) {
    prefix = FOLDER + prefix;
    try {
      CloudStorageType storageType = storageType();
      return CloudStorageUtil.getUri(storageType, container(storageType), prefix, isDirectory);
    } catch (Exception e) {
      return null;
    }
  }

  public static String getUri(CloudStorageType storageType, String prefix, boolean isDirectory) {
    prefix = FOLDER + prefix;
    try {
      return CloudStorageUtil.getUri(storageType, container(storageType), prefix, isDirectory);
    } catch (Exception e) {
      return null;
    }
  }

  public static String upload(String objectKey, File file) {
    CloudStorageType storageType = storageType();
    objectKey = FOLDER + objectKey + separator;
    if (file.isFile()) {
      objectKey += file.getName();
      return CloudStorageUtil.upload(
          storageType, container(storageType), objectKey, file.getAbsolutePath());
    } else {
      return null;
    }
  }

  public static String upload(CloudStorageType storageType, String objectKey, File file) {
    objectKey = FOLDER + objectKey + separator;
    if (file.isFile()) {
      objectKey += file.getName();
      return CloudStorageUtil.upload(
          storageType, container(storageType), objectKey, file.getAbsolutePath());
    } else {
      return null;
    }
  }

  private static CloudStorageType storageType() {
    CloudStorageType storageType = null;
    switch (getConfigValue(CONTENT_CLOUD_STORAGE_TYPE)) {
      case AZURE_STR:
        storageType = AZURE;
        break;
      case AWS_STR:
        storageType = AWS;
        break;
      case GCLOUD_STR:
        storageType = GCLOUD;
        break;
      default:
        break;
    }
    return storageType;
  }

  private static String container(CloudStorageType type) {
    String container = null;
    switch (type) {
      case AZURE:
        container = getConfigValue(CONTENT_AZURE_STORAGE_CONTAINER);
        break;
      case AWS:
        container = getConfigValue(CONTENT_AWS_STORAGE_CONTAINER);
        break;
      case GCLOUD:
        container = getConfigValue(CONTENT_GCLOUD_STORAGE_CONTAINER);
        break;
      default:
        break;
    }
    return container;
  }
}
