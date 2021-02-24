package ducnguyen.springredditclone.bucket;

public enum BucketName {
    POST_Image("reddit-image-upload");

    private final String bucketName;

    BucketName(String bucketName){
        this.bucketName = bucketName;
    }

    public String getBucketName() {
        return bucketName;
    }
}
