package io.mediamachine.models;

/**
 * Amazon Credentials
 */
public class AWSCreds implements Credentials {
   private String accessKeyId;
   private String secretAccessKey;
   private String region;

   /**
    * Creates a new instance of an Amazon Credentials with the provided configuration.
    *
    * @param accessKeyId Amazon access key id.
    * @param secretAccessKey Amazon secret access key.
    * @param region Amazon region.
    */
   public AWSCreds(String accessKeyId, String secretAccessKey, String region) {
      this.accessKeyId = accessKeyId;
      this.secretAccessKey = secretAccessKey;
      this.region = region;
   }
}
