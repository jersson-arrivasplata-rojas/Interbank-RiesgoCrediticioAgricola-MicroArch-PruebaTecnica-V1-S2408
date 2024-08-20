package com.jersson.arrivasplata.agrrisk.serverconfig.config;

import com.azure.identity.ClientSecretCredential;
import com.azure.identity.ClientSecretCredentialBuilder;
import com.azure.security.keyvault.secrets.SecretClient;
import com.azure.security.keyvault.secrets.SecretClientBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.ConfigurableEnvironment;

@Configuration
public class AzureKeyVaultConfig {
	private final ConfigurableEnvironment environment;

	public AzureKeyVaultConfig(ConfigurableEnvironment environment) {
		this.environment = environment;
	}

	@Bean
	public SecretClient secretClient() {
		String vaultUri = environment.getProperty("spring.cloud.config.server.composite[0].uri");
		String clientId = environment.getProperty("spring.cloud.config.server.composite[0].credential.client-id");
		String clientSecret = environment.getProperty("spring.cloud.config.server.composite[0].credential.client-secret");
		String tenantId = environment.getProperty("spring.cloud.config.server.composite[0].credential.tenant-id");

		ClientSecretCredential clientSecretCredential = new ClientSecretCredentialBuilder().clientId(clientId)
				.clientSecret(clientSecret).tenantId(tenantId).build();

		return new SecretClientBuilder().vaultUrl(vaultUri).credential(clientSecretCredential).buildClient();
	}

	@Bean
	public AzureKeyVaultPropertySource azureKeyVaultPropertySource(SecretClient secretClient) {
		AzureKeyVaultPropertySource propertySource = new AzureKeyVaultPropertySource("azure-keyvault", secretClient);
		environment.getPropertySources().addFirst(propertySource);
		return propertySource;
	}
}
