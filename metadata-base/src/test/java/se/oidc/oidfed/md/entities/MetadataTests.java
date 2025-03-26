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

import static org.junit.jupiter.api.Assertions.assertNotNull;

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
        .homepageUri("https://example.com/homepage")
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
        .homepageUri("https://example.com/homepage")
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
        .build();
    // TODO write test
  }

  @Test
  void testAsMetadta() throws Exception {
    final AuthorizationServerMetadata authorizationServerMetadata = AuthorizationServerMetadata.builder()
        .build();

    // TODO write AS metadata test
  }

  @Test
  void testClientMetadata() throws Exception {
    final ClientMetadata clientMetadata = ClientMetadata.oauthClientMetadataBuilder()
        .build();
    // TODO write Client metadata test
  }

  @Test
  void testFederationEndpointMetadata() throws Exception {
    final FederationEntityMetadata federationEntityMetadata = FederationEntityMetadata.builder()
        .build();

    // TODO write FE metadata test
  }

}
