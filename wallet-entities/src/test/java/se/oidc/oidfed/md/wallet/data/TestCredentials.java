/*
 * // SPDX-FileCopyrightText: 2025 diggsweden/eudiw-wallet-token-lib
 * //
 * // SPDX-License-Identifier: Apache-2.0
 */
package se.oidc.oidfed.md.wallet.data;

import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.jwk.JWK;
import com.nimbusds.jose.jwk.JWKSet;
import lombok.Getter;
import se.swedenconnect.security.credential.KeyStoreCredential;
import se.swedenconnect.security.credential.PkiCredential;

import java.security.KeyStore;
import java.security.Security;
import java.security.cert.X509Certificate;
import java.util.Arrays;
import java.util.stream.Collectors;

/**
 * Credentials for unit tests
 */
public class TestCredentials {

  @Getter
  public static PkiCredential p256Credential;
  @Getter
  public static PkiCredential p521Credential;
  @Getter
  public static PkiCredential rsa3072Credential;


  public static final char[] pwd = "Test1234".toCharArray();

  static {
    try {
      if (Security.getProvider("BC") == null) {
        Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
      }

      final KeyStore keyStore = KeyStore.getInstance(KeyStore.getDefaultType());
      keyStore.load(TestCredentials.class.getResourceAsStream("/test-keys.jks"), pwd);

      p256Credential = new KeyStoreCredential(keyStore, "p256", pwd);
      p521Credential = new KeyStoreCredential(keyStore, "p521", pwd);
      rsa3072Credential = new KeyStoreCredential(keyStore, "rsa3072", pwd);

    }
    catch (final Exception e) {
      throw new RuntimeException(e);
    }
  }

  public static JWKSet getJwkSet(final X509Certificate... certificate) {

    return new JWKSet(
        Arrays.stream(certificate)
            .map(cert -> {
              try {
                return JWK.parse(cert);
              }
              catch (final JOSEException e) {
                throw new RuntimeException(e);
              }
            })
            .collect(Collectors.toList())
    );

  }

}
