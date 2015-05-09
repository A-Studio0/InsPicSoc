package com.astudio.inspicsoc.websocket;

import java.net.URISyntaxException;
import java.util.LinkedList;

import android.util.Log;


import de.tavendo.autobahn.WebSocketConnection;
import de.tavendo.autobahn.WebSocketConnectionHandler;
import de.tavendo.autobahn.WebSocketException;

public class WebSocketClient {
	  private static final String TAG = "WSChannelRTCClient";
	  private WebSocketConnection ws;
	  private WebSocketObserver wsObserver;
	  private WebSocketEvents events = null;
	  private LooperExecutor executor = null;
	  private String wsServerUrl;
	  private WebSocketConnectionState state;
	  private LinkedList<String> wsSendQueue;

	  /**
	   * WebSocketConnectionState is the names of possible WS connection states.
	   */
	  public enum WebSocketConnectionState {
	    NEW, CONNECTED, REGISTERED, CLOSED, ERROR
	  };

	  /**
	   * Callback interface for messages delivered on WebSocket.
	   * All events are invoked from UI thread.
	   */
	  public interface WebSocketEvents {
	    public void onWebSocketOpen();
	    public void onWebSocketMessage(final String message);
	    public void onWebSocketClose();
	    public void onWebSocketError(final String description);
	  }

	  public WebSocketClient(WebSocketEvents events) {
		  this.events = events;
		  this.executor = new LooperExecutor();
		  wsSendQueue = new LinkedList<String>();
		  state = WebSocketConnectionState.NEW;
		  executor.requestStart();
	  }

	  public WebSocketConnectionState getState() {
	    return state;
	  }

	  public void connect(final String wsUrl) throws URISyntaxException {
	    if (state != WebSocketConnectionState.NEW) {
	      Log.e(TAG, "WebSocket is already connected.");
	      return;
	    }
	    wsServerUrl = wsUrl;

	    ws = new WebSocketConnection();
	    wsObserver = new WebSocketObserver();
	    try {
	      ws.connect(wsServerUrl, wsObserver);
	    } catch (WebSocketException e) {
	      reportError("WebSocket connection error: " + e.getMessage());
	    }
	  }

	  public void send(String message) {
	    switch (state) {
	      case NEW:
	      case CONNECTED:
	    	  ws.sendTextMessage(message);
//	        synchronized (wsSendQueue) {
//	          wsSendQueue.add(message);
//	          return;
//	        }
	    	  break;
	      case ERROR:
	      case CLOSED:
	        return;
	    }
	    return;
	  }


	  public void disconnect(boolean waitForComplete) {

	    // Close WebSocket in CONNECTED or ERROR states only.
	    if (state == WebSocketConnectionState.CONNECTED
	        || state == WebSocketConnectionState.ERROR) {
	      ws.disconnect();
	      state = WebSocketConnectionState.CLOSED;
	    }
	    executor.requestStop();
	  }

	  private void reportError(final String errorMessage) {
	    executor.execute(new Runnable() {
	      @Override
	      public void run() {
	        if (state != WebSocketConnectionState.ERROR) {
	          state = WebSocketConnectionState.ERROR;
	          events.onWebSocketError(errorMessage);
	        }
	      }
	    });
	  }

	  private class WebSocketObserver extends WebSocketConnectionHandler {
	    @Override
	    public void onOpen() {
	      Log.d(TAG, "WebSocket connection opened to: " + wsServerUrl);
	      executor.execute(new Runnable() {
	        @Override
	        public void run() {
	          state = WebSocketConnectionState.CONNECTED;
	          events.onWebSocketOpen();
	        }
	      });
	    }

	    @Override
	    public void onClose(int code, String reason) {
    	executor.execute(new Runnable() {
	        @Override
	        public void run() {
	          if (state != WebSocketConnectionState.CLOSED) {
	            state = WebSocketConnectionState.CLOSED;
	            events.onWebSocketClose();
	          }
	        }
	      });
	    }

	    @Override
	    public void onTextMessage(String payload) {
	      final String message = payload;
	      executor.execute(new Runnable() {
	        @Override
	        public void run() {
	          if (state == WebSocketConnectionState.CONNECTED
	              || state == WebSocketConnectionState.REGISTERED) {
	            events.onWebSocketMessage(message);
	          }
	        }
	      });
	    }

	    @Override
	    public void onRawTextMessage(byte[] payload) {
	    }

	    @Override
	    public void onBinaryMessage(byte[] payload) {
	    }
	  }

	}
