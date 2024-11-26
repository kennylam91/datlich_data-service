package datlich.online.provider;

import io.quarkus.amazon.dynamodb.enhanced.runtime.NamedDynamoDbTable;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.NotFoundException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import org.mindrot.jbcrypt.BCrypt;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbAsyncTable;
import software.amazon.awssdk.enhanced.dynamodb.Key;

@ApplicationScoped
public class ProviderService {
  @Inject
  @NamedDynamoDbTable("providers")
  private DynamoDbAsyncTable<Provider> providerDynamoDbTable;

  public Uni<Void> add(Provider provider) {
    provider.setCreatedAt(LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME));
    provider.setPassword(BCrypt.hashpw(provider.getPassword(), BCrypt.gensalt()));
    return Uni.createFrom().completionStage(() -> providerDynamoDbTable.putItem(provider));
  }

  public Uni<Provider> login(BasicCredential credential) {
    Key partitionKey = Key.builder().partitionValue(credential.username()).build();
    return Uni.createFrom()
        .completionStage(() -> providerDynamoDbTable.getItem(partitionKey))
        .onItem()
        .ifNotNull()
        .transform(
            provider -> {
              if (BCrypt.checkpw(credential.password(), provider.getPassword())) {
                return provider;
              } else {
                throw new NotFoundException();
              }
            });
  }
}
