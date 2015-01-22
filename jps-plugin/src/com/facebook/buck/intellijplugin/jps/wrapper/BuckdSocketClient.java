/*
 * Copyright 2015-present Facebook, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may
 * not use this file except in compliance with the License. You may obtain
 * a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations
 * under the License.
 */

package com.facebook.buck.intellijplugin.jps.wrapper;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Preconditions;
import com.google.common.base.Throwables;
import com.intellij.openapi.diagnostic.Logger;
import org.java_websocket.client.WebSocketClient;
import org.java_websocket.drafts.Draft;
import org.java_websocket.drafts.Draft_17;
import org.java_websocket.handshake.ServerHandshake;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

/**
 * Buck socket client.
 */
public class BuckdSocketClient {

  private static final Logger LOG = Logger.getInstance(BuckdSocketClient.class);

  private final BuckPluginEventListener listener;
  private final URI echoUri;
  private WebSocketClient client;

  BuckdSocketClient(int port, BuckPluginEventListener listener) {
    String address = "ws://localhost:" + port + "/comet/echo";
    try {
      echoUri = new URI(address);
    } catch (URISyntaxException e) {
      throw Throwables.propagate(e);
    }
    this.listener = Preconditions.checkNotNull(listener);
  }

  public void start() {
    client = new DefaultWebSocketClient(echoUri, new Draft_17());
    try {
      client.connectBlocking();
    } catch (InterruptedException e) {
      LOG.error(e);
    }
  }

  public void stop() {
    client.close();
  }

  private void dispatch(String message) throws IOException {
    ObjectMapper mapper = MapperFactory.getInstance();
    JsonNode node = mapper.readTree(message);
    BuckEvent event = BuckEventFactory.toEvent(node);
    if (event != null) {
      listener.onEvent(event);
    }
  }

  private class DefaultWebSocketClient extends WebSocketClient {

    public DefaultWebSocketClient(URI echoUri, Draft draft) {
      super(echoUri, draft);
    }

    @Override
    public void onMessage(String message) {
      try {
        dispatch(message);
      } catch (IOException e) {
        LOG.warn("IO Error dispatching message", e);
      }
    }

    @Override
    public void onError(Exception e) {
      LOG.error(e);
    }

    @Override
    public void onOpen(ServerHandshake handshake) {
      LOG.info(String.format("Websocket opened: %s", handshake.toString()));
    }

    @Override
    public void onClose(int code, String reason, boolean remote) {
      LOG.info(String.format("Websocket closed: %d %s", code, reason));
    }
  }
}
