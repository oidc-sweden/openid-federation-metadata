/*
 * // SPDX-FileCopyrightText: 2025 diggsweden/eudiw-wallet-token-lib
 * //
 * // SPDX-License-Identifier: Apache-2.0
 */
package se.oidc.oidfed.md.testdata.serialize;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

/**
 * Description
 *
 * @author Martin Lindström (martin@idsec.se)
 * @author Stefan Santesson (stefan@idsec.se)
 */
public class ExtendingClass extends AbstractBaseClass {

  public ExtendingClass() {
    this.oidcSeDiscoUserMessageSupported = true;
  }

  @JsonIgnore
  @Override
  public List<String> getLanguageTaggedParameters() {
    final List<String> allTags = new ArrayList<>(super.getLanguageTaggedParameters());
    allTags.addAll(List.of());
    return allTags;
  }

  /**
   * A discovery parameter specifying whether the OpenID Provider supports the https://id.oidc.se/param/userMessage
   * authentication request parameter
   */
  @JsonProperty("https://id.oidc.se/disco/userMessageSupported")
  @Getter
  private Boolean oidcSeDiscoUserMessageSupported;

}
