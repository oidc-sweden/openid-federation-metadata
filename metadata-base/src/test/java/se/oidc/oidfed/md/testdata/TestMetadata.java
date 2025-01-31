package se.oidc.oidfed.md.testdata;

import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.extern.slf4j.Slf4j;
import se.oidc.oidfed.md.MetadataUtils;
import se.oidc.oidfed.md.entities.FederationEntityMetadata;
import se.oidc.oidfed.md.entities.OpMetadata;
import se.oidc.oidfed.md.entities.RelyingPartyMetadata;
import se.oidc.oidfed.md.lang.LanguageObject;

import java.util.List;
import java.util.Map;

@Slf4j
public class TestMetadata {

  public static Map<String, Object> opMetadata;
  public static String opMetadataJson;
  public static Map<String, Object> opMetadata_claim123;
  public static String opMetadata_claim123Json;
  public static Map<String, Object> opMetadata_claim12;
  public static String opMetadata_claim12Json;
  public static Map<String, Object> rpMetadata;
  public static String rpMetadataJson;
  public static Map<String, Object> rpMetadata_code;
  public static String rpMetadata_codeJson;
  public static Map<String, Object> federationEntityMetadata;
  public static String federationEntityMetadataJson;

  static {
    try {
      opMetadata = OpMetadata.builder()
        .issuer("http:example.com/issuer")
        .requestParameterSupported(true)
        .jwkSet(TestCredentials.getJwkSet(TestCredentials.getP521Credential().getCertificate()))
        .claimsParameterSupported(true)
        .introspectionEndpoint("http:example.com/introspection")
        .scopesSupported(List.of("openid", "profile", "email", "address", "phone", "offline_access"))
        .acrValuesSupported(List.of("http://id.elegnamnden.se/loa/1.0/loa3"))
        .authorizationEndpoint("http:example.com/authorize")
        .tokenEndpoint("http:example.com/token")
        .serviceDocumentation("http:example.com/service-documentation")
        .displayValuesSupported(List.of("page", "popup", "touch"))
        .userinfoSigningAlgValuesSupported(List.of("RS256"))
        .claimsSupported(List.of("sub", "name", "given_name", "family_name"))
        .tokenEndpointAuthMethodsSupported(List.of("private_key_jwt"))
        .responseModesSupported(List.of("query", "fragment", "form_post"))
        .responseTypesSupported(List.of("code"))
        .uiLocalesSupported(List.of("en", "sv"))
        .userinfoEndpoint("http:example.com/userinfo")
        .tokenEndpointAuthSigningAlgValuesSupported(List.of("RS256"))
        .requireRequestUriRegistration(false)
        .build()
        .toJsonObject();
      opMetadataJson = MetadataUtils.OBJECT_MAPPER.writerWithDefaultPrettyPrinter().writeValueAsString(opMetadata);

      rpMetadata = RelyingPartyMetadata.builder()
        .redirectUris(List.of("http:example.com/redirect"))
        .jwkSet(TestCredentials.getJwkSet(TestCredentials.getP521Credential().getCertificate()))
        .grantTypes(List.of("authorization_code"))
        .subjectType("pairwise")
        .tokenEndpointAuthMethod("private_key_jwt")
        .responseTypes(List.of("code"))
        .organizationName(LanguageObject.builder(String.class)
          .defaultValue("DIGG")
          .langValue("sv", "Myndigheten för digital förvaltning")
          .langValue("en", "Government Agency for Digital Government")
          .build())
        .build().toJsonObject();
      rpMetadataJson = MetadataUtils.OBJECT_MAPPER.writerWithDefaultPrettyPrinter().writeValueAsString(rpMetadata);

      federationEntityMetadata = FederationEntityMetadata.builder()
        .federationFetchEndpoint("https://example.com/fetchEndpoint")
        .federationListEndpoint("https://example.com/listEndpoint")
        .federationTrustMarkEndpoint("https://example.com/trustMarkEndpoint")
        .build().toJsonObject();
      federationEntityMetadataJson = MetadataUtils.OBJECT_MAPPER.writerWithDefaultPrettyPrinter().writeValueAsString(federationEntityMetadata);

      opMetadata_claim123 = OpMetadata.builder()
        .scopesSupported(List.of("openid"))
        .claimsSupported(List.of("claim1", "claim2","claim3"))
        .build().toJsonObject();
      opMetadata_claim123Json = MetadataUtils.OBJECT_MAPPER.writerWithDefaultPrettyPrinter().writeValueAsString(opMetadata_claim123);

      opMetadata_claim12 = OpMetadata.builder()
        .scopesSupported(List.of("openid"))
        .claimsSupported(List.of("claim1", "claim2"))
        .build().toJsonObject();
      opMetadata_claim12Json = MetadataUtils.OBJECT_MAPPER.writerWithDefaultPrettyPrinter().writeValueAsString(opMetadata_claim12);

      rpMetadata_code = RelyingPartyMetadata.builder()
        .responseTypes(List.of("code"))
        .build().toJsonObject();
      rpMetadata_codeJson = MetadataUtils.OBJECT_MAPPER.writerWithDefaultPrettyPrinter().writeValueAsString(rpMetadata_code);


    } catch (JsonProcessingException e) {
      throw new RuntimeException(e);
    }
  }


}
