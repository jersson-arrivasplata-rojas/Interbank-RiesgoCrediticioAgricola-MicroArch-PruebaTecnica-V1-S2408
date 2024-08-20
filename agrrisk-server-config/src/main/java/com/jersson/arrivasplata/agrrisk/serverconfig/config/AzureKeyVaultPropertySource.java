package com.jersson.arrivasplata.agrrisk.serverconfig.config;

import com.azure.security.keyvault.secrets.SecretClient;
import com.azure.security.keyvault.secrets.models.SecretProperties;
import org.springframework.core.env.EnumerablePropertySource;

import java.util.HashMap;
import java.util.Map;

public class AzureKeyVaultPropertySource extends EnumerablePropertySource<SecretClient> {

    private final Map<String, String> properties = new HashMap<>();

    public AzureKeyVaultPropertySource(String name, SecretClient source) {
        super(name, source);
        loadProperties();
    }

    private void loadProperties() {
        for (SecretProperties secretProperties : source.listPropertiesOfSecrets()) {
            String secretName = secretProperties.getName();
            String secretValue = source.getSecret(secretName).getValue();
            properties.put(secretName, secretValue);
        }
    }

    @Override
    public String[] getPropertyNames() {
        return properties.keySet().toArray(new String[0]);
    }

    @Override
    public Object getProperty(String name) {
        return properties.get(name);
    }
}
