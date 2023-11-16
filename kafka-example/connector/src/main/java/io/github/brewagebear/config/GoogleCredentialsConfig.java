package io.github.brewagebear.config;

public class GoogleCredentialsConfig {
    public static final String GOOGLE_SERVICE_TYPE_CONFIG = "type";
    public static final String GOOGLE_SERVICE_PROJECT_ID_CONFIG = "project_id";
    public static final String GOOGLE_SERVICE_PRIVATE_KEY_ID_CONFIG = "private_key_id";
    public static final String GOOGLE_SERVICE_PRIVATE_KEY_CONFIG = "private_key";
    public static final String GOOGLE_SERVICE_CLIENT_EMAIL_CONFIG = "client_email";
    public static final String GOOGLE_SERVICE_CLIENT_ID_CONFIG = "client_id";
    public static final String GOOGLE_SERVICE_AUTH_URI_CONFIG = "auth_uri";
    public static final String GOOGLE_SERVICE_TOKEN_URI_CONFIG = "token_uri";
    public static final String GOOGLE_SERVICE_AUTH_PROVIDER_CONFIG = "auth_provider_x509_cert_url";
    public static final String GOOGLE_SERVICE_CLIENT_CERT_CONFIG = "client_x509_cert_url";
    public static final String GOOGLE_SERVICE_UNIVERSE_DOMAIN_CONFIG = "universe_domain";
    public static final String GOOGLE_SPREAD_SHEET_ID_CONFIG = "sheet_id";
    public static final String GOOGLE_SPREAD_SHEET_FETCH_SIZE_CONFIG = "sheet_fetch_size";

    public static final String GOOGLE_SERVICE_TYPE_DEFAULT = "service_account";
    public static final String GOOGLE_SERVICE_AUTH_URI_DEFAULT = "https://accounts.google.com/o/oauth2/auth";
    public static final String GOOGLE_SERVICE_TOKEN_URI_DEFAULT = "https://oauth2.googleapis.com/token";
    public static final String GOOGLE_SERVICE_AUTH_PROVIDER_DEFAULT = "https://www.googleapis.com/oauth2/v1/certs";
    public static final String GOOGLE_SERVICE_CLIENT_CERT_DEFAULT = "https://www.googleapis.com/robot/v1/metadata/x509/demo-544%40shaped-totem-404207.iam.gserviceaccount.com";
    public static final String GOOGLE_SERVICE_UNIVERSE_DOMAIN_DEFAULT = "googleapis.com";
    public static final int GOOGLE_SPREAD_SHEET_FETCH_SIZE_DEFAULT = 5;

}
