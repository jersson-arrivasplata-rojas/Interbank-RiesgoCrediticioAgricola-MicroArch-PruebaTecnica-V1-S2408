package com.jersson.arrivasplata.agrrisk.serverconfig.config;

import com.azure.security.keyvault.secrets.SecretClient;
import com.azure.security.keyvault.secrets.models.SecretProperties;
import org.springframework.cloud.config.environment.Environment;
import org.springframework.cloud.config.environment.PropertySource;
import org.springframework.cloud.config.server.environment.EnvironmentRepository;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class KeyVaultEnvironmentRepository implements EnvironmentRepository {

    private final SecretClient secretClient;

    public KeyVaultEnvironmentRepository(SecretClient secretClient) {
        this.secretClient = secretClient;
    }

    @Override
    public Environment findOne(String application, String profile, String label) {
        Map<String, Object> properties = new HashMap<>();
        for (SecretProperties secretProperties : secretClient.listPropertiesOfSecrets()) {
            String secretName = secretProperties.getName();
            String secretValue = secretClient.getSecret(secretName).getValue();
            properties.put(secretName, secretValue);
        }

        PropertySource propertySource = new PropertySource("azure-keyvault", properties);
        Environment environment = new Environment(application, profile);
        environment.add(propertySource);

        return environment;
    }
}
