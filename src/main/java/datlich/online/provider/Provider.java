package datlich.online.provider;

import io.smallrye.mutiny.Uni;
import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.Setter;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbAttribute;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbBean;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbPartitionKey;

@DynamoDbBean
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class Provider {

  private String username;
  private String password;
  private String name;
  private String email;
  private String createdAt;

  @DynamoDbPartitionKey
  @DynamoDbAttribute("username")
  public String getUsername() {
    return username;
  }

  @DynamoDbAttribute("password")
  public String getPassword() {
    return password;
  }

  @DynamoDbAttribute("name")
  public String getName() {
    return name;
  }

  @DynamoDbAttribute("email")
  public String getEmail() {
    return email;
  }

  @DynamoDbAttribute("createdAt")
  public String getCreatedAt() {
    return createdAt;
  }
}
