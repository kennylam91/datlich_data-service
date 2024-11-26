package datlich.online.provider;

import io.smallrye.mutiny.Uni;
import jakarta.inject.Inject;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;

@Path("providers")
public class ProviderResource {
  @Inject ProviderService providerService;

  @POST
  @Path("login")
  public Uni<Provider> login(BasicCredential credential) {
    return providerService.login(credential);
  }

  @POST
  public Uni<Void> create(Provider provider) {
    return providerService.add(provider);
  }
}
