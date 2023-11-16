package io.github.brewagebear;

import io.github.brewagebear.config.GoogleCredentialsConfig;
import org.apache.kafka.common.config.ConfigDef;

public interface CustomConnectConfig {
    ConfigDef CONFIG_DEF =
            new ConfigDef()
                    .define(SimpleGoogleSheetSourceConfig.TOPIC_NAME_CONFIG,
                            ConfigDef.Type.STRING,
                            ConfigDef.Importance.HIGH,
                            "Topic name.")
                    .define(SimpleGoogleSheetSourceConfig.POLL_INTERVAL_CONFIG,
                            ConfigDef.Type.LONG,
                            SimpleGoogleSheetSourceConfig.POLL_INTERVAL_DEFAULT,
                            ConfigDef.Importance.HIGH,
                            "Max interval between messages (ms)")
                    .define(GoogleCredentialsConfig.GOOGLE_SERVICE_PROJECT_ID_CONFIG,
                            ConfigDef.Type.STRING,
                            ConfigDef.Importance.HIGH,
                            "Google Cloud Platform Project id")
                    .define(GoogleCredentialsConfig.GOOGLE_SERVICE_PRIVATE_KEY_ID_CONFIG,
                            ConfigDef.Type.STRING,
                            ConfigDef.Importance.HIGH,
                            "Google Cloud Platform Private key id")
                    .define(GoogleCredentialsConfig.GOOGLE_SERVICE_PRIVATE_KEY_CONFIG,
                            ConfigDef.Type.STRING,
                            ConfigDef.Importance.HIGH,
                            "Google Cloud Platform Private key")
                    .define(GoogleCredentialsConfig.GOOGLE_SERVICE_CLIENT_EMAIL_CONFIG,
                            ConfigDef.Type.STRING,
                            ConfigDef.Importance.HIGH,
                            "Google Cloud Platform Service Account Email")
                    .define(GoogleCredentialsConfig.GOOGLE_SERVICE_CLIENT_ID_CONFIG,
                            ConfigDef.Type.STRING,
                            ConfigDef.Importance.HIGH,
                            "Google Cloud Platform Client id")
                    .define(GoogleCredentialsConfig.GOOGLE_SPREAD_SHEET_ID_CONFIG,
                            ConfigDef.Type.STRING,
                            ConfigDef.Importance.HIGH,
                            "Google Spread Sheet id")

                    // Default Google Api Config
                    .define(GoogleCredentialsConfig.GOOGLE_SERVICE_TYPE_CONFIG,
                            ConfigDef.Type.STRING,
                            GoogleCredentialsConfig.GOOGLE_SERVICE_TYPE_DEFAULT,
                            ConfigDef.Importance.MEDIUM,
                            "Google Api Service Type")
                    .define(GoogleCredentialsConfig.GOOGLE_SPREAD_SHEET_FETCH_SIZE_CONFIG,
                            ConfigDef.Type.INT,
                            GoogleCredentialsConfig.GOOGLE_SPREAD_SHEET_FETCH_SIZE_DEFAULT,
                            ConfigDef.Importance.MEDIUM,
                            "Google Spread Sheet Fetch Size")
                    .define(GoogleCredentialsConfig.GOOGLE_SERVICE_AUTH_URI_CONFIG,
                            ConfigDef.Type.STRING,
                            GoogleCredentialsConfig.GOOGLE_SERVICE_AUTH_URI_DEFAULT,
                            ConfigDef.Importance.LOW,
                            "Google Auth Uri")
                    .define(GoogleCredentialsConfig.GOOGLE_SERVICE_TOKEN_URI_CONFIG,
                            ConfigDef.Type.STRING,
                            GoogleCredentialsConfig.GOOGLE_SERVICE_TOKEN_URI_DEFAULT,
                            ConfigDef.Importance.LOW,
                            "Google Token Uri")
                    .define(GoogleCredentialsConfig.GOOGLE_SERVICE_AUTH_PROVIDER_CONFIG,
                            ConfigDef.Type.STRING,
                            GoogleCredentialsConfig.GOOGLE_SERVICE_AUTH_PROVIDER_DEFAULT,
                            ConfigDef.Importance.LOW,
                            "Google Provider x509 Cert Uri")
                    .define(GoogleCredentialsConfig.GOOGLE_SERVICE_CLIENT_CERT_CONFIG,
                            ConfigDef.Type.STRING,
                            GoogleCredentialsConfig.GOOGLE_SERVICE_CLIENT_CERT_DEFAULT,
                            ConfigDef.Importance.LOW,
                            "Google Client x509 Cert Uri")
                    .define(GoogleCredentialsConfig.GOOGLE_SERVICE_UNIVERSE_DOMAIN_CONFIG,
                            ConfigDef.Type.STRING,
                            GoogleCredentialsConfig.GOOGLE_SERVICE_UNIVERSE_DOMAIN_DEFAULT,
                            ConfigDef.Importance.LOW,
                            "Google Universe Domain");
}
