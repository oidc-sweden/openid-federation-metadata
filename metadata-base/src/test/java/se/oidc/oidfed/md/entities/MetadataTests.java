/*
 * // SPDX-FileCopyrightText: 2025 diggsweden/eudiw-wallet-token-lib
 * //
 * // SPDX-License-Identifier: Apache-2.0
 */
package se.oidc.oidfed.md.entities;

import com.nimbusds.jose.jwk.JWK;
import com.nimbusds.jose.jwk.JWKSet;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import se.oidc.oidfed.md.lang.LanguageObject;
import se.oidc.oidfed.md.testdata.TestCredentials;
import se.oidc.oidfed.md.testdata.TestMetadata;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Tests for supported metadata types
 */
@Slf4j
class MetadataTests {


  @Test
  void testMetadatabuilders() throws Exception {
    log.info("OP metadata:\n{}", TestMetadata.opMetadataJson);
    log.info("RP metadata:\n{}", TestMetadata.rpMetadataJson);
    log.info("Federation entity metadata:\n{}", TestMetadata.federationEntityMetadataJson);
    log.info("OP metadata claims 123:\n{}", TestMetadata.opMetadata_claim123Json);
    log.info("OP metadata claims 12:\n{}", TestMetadata.opMetadata_claim12Json);
    log.info("RP metadata resp type code:\n{}", TestMetadata.rpMetadata_codeJson);
  }

  @Test
  void resourceServerMetadataTest() throws Exception {

    final ResourceServerMetadata resourceServerMetadata = ResourceServerMetadata.builder()
        .contacts(List.of("nisse@example.com", "jd@example.com"))
        .organizationUri("https://example.com/homepage")
        .jwkSet(new JWKSet(List.of(JWK.parse(TestCredentials.getP521Credential().getCertificate()))))
        .jwksUri("https://example.com/jwks-uri")
        .signedJwksUri("https://example.com/signed-jwks-uri")
        .policyUri("https://example.com/policy")
        .logoUri(LanguageObject.builder(String.class)
            .defaultValue("https://example.com/logo")
            .langValue("en", "https://example.com/en-logo")
            .build())
        .organizationName(LanguageObject.builder(String.class)
            .defaultValue("Organization")
            .langValue("en", "English Organization name")
            .build())
        .build();

    assertNotNull(resourceServerMetadata);
    this.logMetadataValues(resourceServerMetadata);

    log.info("Parsing metadata JSON object");
    final ResourceServerMetadata parsedMetadata = ResourceServerMetadata.getJsonSerializer()
        .parse(resourceServerMetadata.toJsonObject());

    // Test JWKS
    final JWKSet jwkSet = parsedMetadata.getJwkSet();
    assertNotNull(jwkSet);
    parsedMetadata.setJwkSet(new JWKSet(List.of(JWK.parse(TestCredentials.getRsa3072Credential().getCertificate()))));
    this.logMetadataValues(parsedMetadata);
  }

  private void logMetadataValues(final AbstractOidcFedMetadata metadata) throws Exception {

    final String metadataJson = metadata.toJson(true);
    log.info("Content of {}\n{}", metadata.getClass().getSimpleName(), metadataJson);

  }

  @Test
  void testRelyingPartyMetadata() throws Exception {

    final RelyingPartyMetadata relyingPartyMetadata = RelyingPartyMetadata.builder()
        .requireAuthTime(true)
        .defaultMaxAge(240)
        .applicationType("type")
        .defaultAcrValues(List.of("loa3", "loa4"))
        .idTokenEncryptedResponseAlg("alg")
        .initiateLoginUri("uri")
        .idTokenSignedResponseAlg("alg")
        .idTokenEncryptedResponseAlg("alg")
        .requestObjectEncryptionAlg("alg")
        .requestObjectEncryptionAlg("alg")
        .requestObjectEncryptionEnc("alg")
        .tokenEndpointAuthSigningAlg("alg")
        .tokenEndpointAuthMethod("private_key_jwt")
        .userinfoSignedResponseAlg("alg")
        .userinfoEncryptedResponseEnc("alg")
        .userinfoEncryptedResponseAlg("alg")
        .clientName(LanguageObject.builder(String.class)
            .defaultValue("Client name")
            .langValue("sv", "Klientnamn")
            .build())
        .clientUri("uri")
        .grantTypes(List.of("type1", "type2"))
        .redirectUris(List.of("Redirect URI", "Redirect URI2"))
        .responseTypes(List.of("Response types"))
        .signedJwksUri("signed JWKS URI")
        .contacts(List.of("nisse@example.com", "jd@example.com"))
        .organizationUri("https://example.com/homepage")
        .jwkSet(new JWKSet(List.of(JWK.parse(TestCredentials.getP521Credential().getCertificate()))))
        .jwksUri("https://example.com/jwks-uri")
        .signedJwksUri("https://example.com/signed-jwks-uri")
        .policyUri("https://example.com/policy")
        .logoUri(LanguageObject.builder(String.class)
            .defaultValue("https://example.com/logo")
            .langValue("en", "https://example.com/en-logo")
            .build())
        .organizationName(LanguageObject.builder(String.class)
            .defaultValue("Organization")
            .langValue("en", "English Organization name")
            .build())
        .build();

    assertNotNull(relyingPartyMetadata);
    this.logMetadataValues(relyingPartyMetadata);

    log.info("Parsing metadata JSON object");
    final RelyingPartyMetadata parsedMetadata = RelyingPartyMetadata.getJsonSerializer()
        .parse(relyingPartyMetadata.toJsonObject());

    // Test JWKS
    final JWKSet jwkSet = parsedMetadata.getJwkSet();
    assertNotNull(jwkSet);
    parsedMetadata.setJwkSet(new JWKSet(List.of(JWK.parse(TestCredentials.getRsa3072Credential().getCertificate()))));
    this.logMetadataValues(parsedMetadata);

  }

  @Test
  void testOpMetadata() throws Exception {

    final OpMetadata opMetadata = OpMetadata.builder()
        // Authorization Server parameters, inherited from BasicASMetadata
        .issuer("https://op.example.com")
        .authorizationEndpoint("https://op.example.com/authorize")
        .tokenEndpoint("https://op.example.com/token")
        .scopesSupported(List.of("openid", "profile"))
        .responseTypesSupported(List.of("code"))
        .grantTypesSupported(List.of("authorization_code"))
        .tokenEndpointAuthMethodsSupported(List.of("private_key_jwt"))
        .tokenEndpointAuthSigningAlgValuesSupported(List.of("ES256", "PS256"))
        .codeChallengeMethodsSupported(List.of("S256"))
        // OpenID Provider parameters
        .userinfoEndpoint("https://op.example.com/userinfo")
        .clientRegistrationTypesSupported(List.of("automatic", "explicit"))
        .federationRegistrationEndpoint("https://op.example.com/federation-registration")
        .acrValuesSupported(List.of("http://id.elegnamnden.se/loa/1.0/loa3"))
        .subjectTypesSupported(List.of("pairwise"))
        .idTokenSigningAlgValuesSupported(List.of("ES256", "RS256"))
        .requestObjectSigningAlgValuesSupported(List.of("ES256"))
        .claimsSupported(List.of("sub", "https://id.oidc.se/claim/personalIdentityNumber"))
        .requestParameterSupported(true)
        .requestUriParameterSupported(false)
        // Sweden Connect extensions
        .oidcSeDiscoUserMessageSupported(true)
        .oidcSeDiscoUserMessageSupportedMimeTypes(List.of("text/markdown"))
        .oidcSeDiscoAuthnProviderSupported(true)
        // Common informational parameters
        .contacts(List.of("nisse@example.com"))
        .organizationUri("https://example.com/homepage")
        .policyUri("https://example.com/policy")
        .jwkSet(new JWKSet(List.of(JWK.parse(TestCredentials.getP521Credential().getCertificate()))))
        .logoUri(LanguageObject.builder(String.class)
            .defaultValue("https://example.com/logo")
            .langValue("en", "https://example.com/en-logo")
            .build())
        .organizationName(LanguageObject.builder(String.class)
            .defaultValue("Organization")
            .langValue("en", "English Organization name")
            .build())
        .build();

    assertNotNull(opMetadata);
    this.logMetadataValues(opMetadata);

    final Map<String, Object> jsonObject = opMetadata.toJsonObject();
    // The parameter was renamed from homepage_uri in OpenID Federation 1.0, Section 5.2.2
    assertEquals("https://example.com/homepage", jsonObject.get("organization_uri"));
    assertNull(jsonObject.get("homepage_uri"));
    // Language tagged parameters are written both untagged and with a language tag suffix
    assertEquals("Organization", jsonObject.get("organization_name"));
    assertEquals("English Organization name", jsonObject.get("organization_name#en"));

    log.info("Parsing metadata JSON object");
    final OpMetadata parsedMetadata = OpMetadata.getJsonSerializer().parse(jsonObject);

    assertEquals("https://op.example.com", parsedMetadata.getIssuer());
    assertEquals("https://op.example.com/authorize", parsedMetadata.getAuthorizationEndpoint());
    assertEquals("https://op.example.com/userinfo", parsedMetadata.getUserinfoEndpoint());
    assertEquals(List.of("automatic", "explicit"), parsedMetadata.getClientRegistrationTypesSupported());
    assertEquals("https://op.example.com/federation-registration",
        parsedMetadata.getFederationRegistrationEndpoint());
    assertEquals(List.of("ES256", "RS256"), parsedMetadata.getIdTokenSigningAlgValuesSupported());
    assertEquals(List.of("S256"), parsedMetadata.getCodeChallengeMethodsSupported());
    assertTrue(parsedMetadata.getRequestParameterSupported());
    assertFalse(parsedMetadata.getRequestUriParameterSupported());
    assertTrue(parsedMetadata.getOidcSeDiscoUserMessageSupported());
    assertEquals(List.of("text/markdown"), parsedMetadata.getOidcSeDiscoUserMessageSupportedMimeTypes());
    assertEquals("https://example.com/homepage", parsedMetadata.getOrganizationUri());
    assertEquals("Organization", parsedMetadata.getOrganizationName().getLanguageValue(null));
    assertEquals("English Organization name", parsedMetadata.getOrganizationName().getLanguageValue("en"));

    assertNotNull(parsedMetadata.getJwkSet());
    this.logMetadataValues(parsedMetadata);
  }

  @Test
  void testAsMetadta() throws Exception {

    final AuthorizationServerMetadata authorizationServerMetadata = AuthorizationServerMetadata.builder()
        // Parameters specific to an OAuth 2.0 Authorization Server
        .revocationEndpoint("https://as.example.com/revoke")
        .revocationEndpointAuthMethodsSupported(List.of("private_key_jwt"))
        .revocationEndpointAuthSigningAlgValuesSupported(List.of("ES256"))
        // Parameters inherited from BasicASMetadata
        .issuer("https://as.example.com")
        .authorizationEndpoint("https://as.example.com/authorize")
        .tokenEndpoint("https://as.example.com/token")
        .registrationEndpoint("https://as.example.com/register")
        .introspectionEndpoint("https://as.example.com/introspect")
        .introspectionEndpointAuthMethodsSupported(List.of("private_key_jwt"))
        .scopesSupported(List.of("https://id.swedenconnect.se/scope/boris"))
        .responseTypesSupported(List.of("code"))
        .responseModesSupported(List.of("query"))
        .grantTypesSupported(List.of("authorization_code"))
        .tokenEndpointAuthMethodsSupported(List.of("private_key_jwt"))
        .codeChallengeMethodsSupported(List.of("S256"))
        .serviceDocumentation("https://as.example.com/docs")
        .uiLocalesSupported(List.of("sv", "en"))
        .opPolicyUri("https://as.example.com/op-policy")
        .opTosUri("https://as.example.com/op-tos")
        // Common informational parameters
        .contacts(List.of("nisse@example.com"))
        .organizationUri("https://example.com/homepage")
        .jwkSet(new JWKSet(List.of(JWK.parse(TestCredentials.getP521Credential().getCertificate()))))
        .organizationName(LanguageObject.builder(String.class)
            .defaultValue("Organization")
            .langValue("sv", "Organisationen")
            .build())
        .build();

    assertNotNull(authorizationServerMetadata);
    this.logMetadataValues(authorizationServerMetadata);

    final Map<String, Object> jsonObject = authorizationServerMetadata.toJsonObject();
    assertEquals("https://as.example.com/revoke", jsonObject.get("revocation_endpoint"));
    assertEquals("Organisationen", jsonObject.get("organization_name#sv"));

    log.info("Parsing metadata JSON object");
    final AuthorizationServerMetadata parsedMetadata = AuthorizationServerMetadata.getJsonSerializer()
        .parse(jsonObject);

    assertEquals("https://as.example.com", parsedMetadata.getIssuer());
    assertEquals("https://as.example.com/revoke", parsedMetadata.getRevocationEndpoint());
    assertEquals(List.of("private_key_jwt"), parsedMetadata.getRevocationEndpointAuthMethodsSupported());
    assertEquals(List.of("ES256"), parsedMetadata.getRevocationEndpointAuthSigningAlgValuesSupported());
    assertEquals("https://as.example.com/introspect", parsedMetadata.getIntrospectionEndpoint());
    assertEquals(List.of("https://id.swedenconnect.se/scope/boris"), parsedMetadata.getScopesSupported());
    assertEquals(List.of("sv", "en"), parsedMetadata.getUiLocalesSupported());
    assertEquals("Organisationen", parsedMetadata.getOrganizationName().getLanguageValue("sv"));

    assertNotNull(parsedMetadata.getJwkSet());
    this.logMetadataValues(parsedMetadata);
  }

  @Test
  void testClientMetadata() throws Exception {

    final ClientMetadata clientMetadata = ClientMetadata.oauthClientMetadataBuilder()
        // Parameters specific to an OAuth 2.0 client
        .scope("https://id.swedenconnect.se/scope/boris")
        .softwareId("https://eid.svelegtest.se/eidasps")
        .softwareVersion("4.0.0")
        // Parameters inherited from BasicClientMetadata
        .redirectUris(List.of("https://client.example.com/callback"))
        .tokenEndpointAuthMethod("private_key_jwt")
        .grantTypes(List.of("authorization_code"))
        .responseTypes(List.of("code"))
        .clientUri("https://client.example.com")
        .clientName(LanguageObject.builder(String.class)
            .defaultValue("Client name")
            .langValue("sv", "Klientnamn")
            .build())
        .tosUri(LanguageObject.builder(String.class)
            .defaultValue("https://client.example.com/tos")
            .langValue("sv", "https://client.example.com/sv/tos")
            .build())
        // Common informational parameters
        .contacts(List.of("nisse@example.com"))
        .organizationUri("https://example.com/homepage")
        .jwkSet(new JWKSet(List.of(JWK.parse(TestCredentials.getP521Credential().getCertificate()))))
        .organizationName(LanguageObject.builder(String.class)
            .defaultValue("Organization")
            .langValue("en", "English Organization name")
            .build())
        .build();

    assertNotNull(clientMetadata);
    this.logMetadataValues(clientMetadata);

    final Map<String, Object> jsonObject = clientMetadata.toJsonObject();
    assertEquals("https://id.swedenconnect.se/scope/boris", jsonObject.get("scope"));
    assertEquals("Client name", jsonObject.get("client_name"));
    assertEquals("Klientnamn", jsonObject.get("client_name#sv"));

    log.info("Parsing metadata JSON object");
    final ClientMetadata parsedMetadata = ClientMetadata.getJsonSerializer().parse(jsonObject);

    assertEquals("https://id.swedenconnect.se/scope/boris", parsedMetadata.getScope());
    assertEquals("https://eid.svelegtest.se/eidasps", parsedMetadata.getSoftwareId());
    assertEquals("4.0.0", parsedMetadata.getSoftwareVersion());
    assertEquals(List.of("https://client.example.com/callback"), parsedMetadata.getRedirectUris());
    assertEquals("private_key_jwt", parsedMetadata.getTokenEndpointAuthMethod());
    assertEquals("Klientnamn", parsedMetadata.getClientName().getLanguageValue("sv"));
    assertEquals("https://client.example.com/sv/tos", parsedMetadata.getTosUri().getLanguageValue("sv"));
    assertEquals("https://example.com/homepage", parsedMetadata.getOrganizationUri());

    assertNotNull(parsedMetadata.getJwkSet());
    this.logMetadataValues(parsedMetadata);
  }

  @Test
  void testFederationEndpointMetadata() throws Exception {

    // Note that the JWK Set parameters of Section 5.2.1 of OpenID Federation 1.0 MUST NOT be used in
    // federation_entity metadata, so no keys are set here. The Federation Entity Keys live in the jwks claim of
    // the entity statement itself.
    final FederationEntityMetadata federationEntityMetadata = FederationEntityMetadata.builder()
        .federationFetchEndpoint("https://fed.example.com/fetch")
        .federationListEndpoint("https://fed.example.com/subordinate_listing")
        .federationResolveEndpoint("https://fed.example.com/resolve")
        .federationTrustMarkStatusEndpoint("https://fed.example.com/trust_mark_status")
        .federationTrustMarkListEndpoint("https://fed.example.com/trust_mark_listing")
        .federationTrustMarkEndpoint("https://fed.example.com/trust_mark")
        .federationHistoricalKeysEndpoint("https://fed.example.com/historical-keys")
        .federationDiscoveryEndpoint("https://fed.example.com/discovery")
        .endpointAuthSigningAlgValuesSupported(List.of("ES256", "RS256"))
        // Common informational parameters
        .contacts(List.of("nisse@example.com"))
        .organizationUri("https://example.com/homepage")
        .policyUri("https://example.com/policy")
        .logoUri(LanguageObject.builder(String.class)
            .defaultValue("https://example.com/logo")
            .build())
        .organizationName(LanguageObject.builder(String.class)
            .defaultValue("Organization")
            .langValue("sv", "Organisationen")
            .langValue("en", "English Organization name")
            .build())
        .build();

    assertNotNull(federationEntityMetadata);
    this.logMetadataValues(federationEntityMetadata);

    final Map<String, Object> jsonObject = federationEntityMetadata.toJsonObject();
    assertEquals("https://fed.example.com/resolve", jsonObject.get("federation_resolve_endpoint"));
    assertEquals("https://example.com/homepage", jsonObject.get("organization_uri"));
    assertNull(jsonObject.get("jwks"));
    assertNull(jsonObject.get("jwks_uri"));
    assertNull(jsonObject.get("signed_jwks_uri"));

    log.info("Parsing metadata JSON object");
    final FederationEntityMetadata parsedMetadata = FederationEntityMetadata.getJsonSerializer().parse(jsonObject);

    assertEquals("https://fed.example.com/fetch", parsedMetadata.getFederationFetchEndpoint());
    assertEquals("https://fed.example.com/subordinate_listing", parsedMetadata.getFederationListEndpoint());
    assertEquals("https://fed.example.com/resolve", parsedMetadata.getFederationResolveEndpoint());
    assertEquals("https://fed.example.com/trust_mark_status",
        parsedMetadata.getFederationTrustMarkStatusEndpoint());
    assertEquals("https://fed.example.com/trust_mark_listing", parsedMetadata.getFederationTrustMarkListEndpoint());
    assertEquals("https://fed.example.com/trust_mark", parsedMetadata.getFederationTrustMarkEndpoint());
    assertEquals("https://fed.example.com/historical-keys",
        parsedMetadata.getFederationHistoricalKeysEndpoint());
    assertEquals("https://fed.example.com/discovery", parsedMetadata.getFederationDiscoveryEndpoint());
    assertEquals(List.of("ES256", "RS256"), parsedMetadata.getEndpointAuthSigningAlgValuesSupported());
    assertEquals("Organisationen", parsedMetadata.getOrganizationName().getLanguageValue("sv"));

    this.logMetadataValues(parsedMetadata);
  }

}
