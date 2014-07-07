package com.example.eclipsepahotest;

import com.example.eclipsepahotest.ActionListener;
import com.example.eclipsepahotest.ActivityConstants;
import com.example.eclipsepahotest.Connections;
import com.example.eclipsepahotest.R;
import com.example.eclipsepahotest.ActionListener.Action;

import org.eclipse.paho.android.service.MqttAndroidClient;
import com.example.eclipsepahotest.Connection;
import com.example.eclipsepahotest.MqttCallbackHandler;
import com.example.eclipsepahotest.Notify;
import com.example.eclipsepahotest.Connection.ConnectionStatus;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttSecurityException;

import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;
import android.os.Build;

public class MainActivity extends ActionBarActivity {
 Button btnTest;
 Connection con;
 Button btnSubscribe;
 private Bundle result = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState == null) {
//            getSupportFragmentManager().beginTransaction()
//                    .add(R.id.container, new PlaceholderFragment())
//                    .commit();
        }
        btnSubscribe=(Button)findViewById(R.id.btnSubscribe);
     btnTest=   (Button)findViewById(R.id.btnTest);
     btnTest.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View view) {
            
        	 
        	 Bundle dataBundle=new Bundle();
        	 
        	 String server = "shajumohamed.com";
                 String port = "1883";//((EditText) findViewById(R.id.port))
                     //.getText().toString();
                String clientId ="shajuTest"; //((EditText) findViewById(R.id.clientId))
//                     .getText().toString();

                 if (server.equals(ActivityConstants.empty) || port.equals(ActivityConstants.empty) || clientId.equals(ActivityConstants.empty))
                 {
//                   String notificationText = newConnection.getString(R.string.missingOptions);
//                   Notify.toast(newConnection, notificationText, Toast.LENGTH_LONG);
//                   return false;
                 }

                 boolean cleanSession =false; //((CheckBox) findViewById(R.id.cleanSessionCheckBox)).isChecked();
                 //persist server
                 //persistServerURI(server);

                 //put data into a bundle to be passed back to ClientConnections
                 dataBundle.putString(ActivityConstants.server, server);
                 dataBundle.putString(ActivityConstants.port, port);
                 dataBundle.putString(ActivityConstants.clientId, clientId);
                 dataBundle.putInt(ActivityConstants.action, ActivityConstants.connect);
                 dataBundle.putBoolean(ActivityConstants.cleanSession, cleanSession);

                 if (result == null) {
                   // create a new bundle and put default advanced options into a bundle
                   result = new Bundle();

                   result.putString(ActivityConstants.message,
                       ActivityConstants.empty);
                   result.putString(ActivityConstants.topic, ActivityConstants.empty);
                   result.putInt(ActivityConstants.qos, ActivityConstants.defaultQos);
                   result.putBoolean(ActivityConstants.retained,
                       ActivityConstants.defaultRetained);

                   result.putString(ActivityConstants.username,
                       ActivityConstants.empty);
                   result.putString(ActivityConstants.password,
                       ActivityConstants.empty);

                   result.putInt(ActivityConstants.timeout,
                       ActivityConstants.defaultTimeOut);
                   result.putInt(ActivityConstants.keepalive,
                       ActivityConstants.defaultKeepAlive);
                   result.putBoolean(ActivityConstants.ssl,
                       ActivityConstants.defaultSsl);
                   dataBundle.putAll(result);
                   connectAction(dataBundle);
                   
                 }
                 
        	 Log.d("Test","Test");
        	 Toast.makeText(getApplicationContext(), "done",2000);//            //con.
        	
         }
     });
     
     btnSubscribe.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View view) {
            
        	 
        	 
                 Connection con=Connections.getInstance(view.getContext())
                		 .getConnection("tcp://shajumohamed.com:1883shajuTest");
                 
//        	 bun.putString(ActivityConstants.message, "Hello");
//            con=Connection.createConnection("shajuTest", "shajumohamed.com", 1883,view.getContext(), false);
//            MqttConnectOptions conOp=new MqttConnectOptions();
//            conOp.setCleanSession(false);
//            conOp.set
//            Connections conns=Connections.getInstance(view.getContext());
//            conns.addConnection(con);
      subscribe(view,"tcp://shajumohamed.com:1883shajuTest" );
         Log.d("Test",con.getHostName());
        	 Log.d("Test1","Test1");
        	 Toast.makeText(getApplicationContext(), "donssssse",2000);//            //con.
        	
         }
     });
        
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * A placeholder fragment containing a simple view.
     */
//    public static class PlaceholderFragment extends Fragment {
//
//        public PlaceholderFragment() {
//        }
//
//        @Override
//        public View onCreateView(LayoutInflater inflater, ViewGroup container,
//                Bundle savedInstanceState) {
//            View rootView = inflater.inflate(R.layout.fragment_main, container, false);
//            return rootView;
//        }
//    }
    
    private void subscribe(View v,String clientHandle)
    {
      String topic ="TestTopic"; //((EditText) connectionDetails.findViewById(R.id.topic)).getText().toString();
     // ((EditText) connectionDetails.findViewById(R.id.topic)).getText().clear();

//      RadioGroup radio = (RadioGroup) connectionDetails.findViewById(R.id.qosSubRadio);
//      int checked = radio.getCheckedRadioButtonId();
     int qos = ActivityConstants.defaultQos;
//
//      switch (checked) {
//        case R.id.qos0 :
//          qos = 0;
//          break;
//        case R.id.qos1 :
//          qos = 1;
//          break;
//        case R.id.qos2 :
//          qos = 2;
//          break;
//      }
      

      try {
        String[] topics = new String[1];
        topics[0] = topic;
       MqttAndroidClient cl= Connections.getInstance(v.getContext()).getConnection(clientHandle).getClient();
     
       
     //  cl.subscribe(topic, qos, getBaseContext(), callback)
       cl.subscribe(topic, qos, null, new ActionListener(getApplicationContext(), com.example.eclipsepahotest.ActionListener.Action.SUBSCRIBE, clientHandle, topics));
      }
      catch (MqttSecurityException e) {
        Log.e(this.getClass().getCanonicalName(), "Failed to subscribe to" + topic + " the client with the handle " + clientHandle, e);
      }
      catch (MqttException e) {
        Log.e(this.getClass().getCanonicalName(), "Failed to subscribe to" + topic + " the client with the handle " + clientHandle, e);
      }
    }



private void connectAction(Bundle data) {
    MqttConnectOptions conOpt = new MqttConnectOptions();
    /*
     * Mutal Auth connections could do something like this
     * 
     * 
     * SSLContext context = SSLContext.getDefault();
     * context.init({new CustomX509KeyManager()},null,null); //where CustomX509KeyManager proxies calls to keychain api
     * SSLSocketFactory factory = context.getSSLSocketFactory();
     * 
     * MqttConnectOptions options = new MqttConnectOptions();
     * options.setSocketFactory(factory);
     * 
     * client.connect(options);
     * 
     */

    // The basic client information
    String server = (String) data.get(ActivityConstants.server);
    String clientId = (String) data.get(ActivityConstants.clientId);
    int port = Integer.parseInt((String) data.get(ActivityConstants.port));
    boolean cleanSession = (Boolean) data.get(ActivityConstants.cleanSession);

    boolean ssl = (Boolean) data.get(ActivityConstants.ssl);
    String uri = null;
    if (ssl) {
      Log.e("SSLConnection", "Doing an SSL Connect");
      uri = "ssl://";

    }
    else {
      uri = "tcp://";
    }

    uri = uri + server + ":" + port;

    MqttAndroidClient client;
    client = Connections.getInstance(this).createClient(this, uri, clientId);
    // create a client handle
    String clientHandle = uri + clientId;

    // last will message
    String message = (String) data.get(ActivityConstants.message);
    String topic = (String) data.get(ActivityConstants.topic);
    Integer qos = (Integer) data.get(ActivityConstants.qos);
    Boolean retained = (Boolean) data.get(ActivityConstants.retained);

    // connection options

    String username = (String) data.get(ActivityConstants.username);

    String password = (String) data.get(ActivityConstants.password);

    int timeout = (Integer) data.get(ActivityConstants.timeout);
    int keepalive = (Integer) data.get(ActivityConstants.keepalive);

    Connection connection = new Connection(clientHandle, clientId, server, port,
        this, client, ssl);
 //   arrayAdapter.add(connection);

   // connection.registerChangeListener(changeListener);
    // connect client

    String[] actionArgs = new String[1];
    actionArgs[0] = clientId;
    connection.changeConnectionStatus(com.example.eclipsepahotest.Connection.ConnectionStatus.CONNECTING);

    conOpt.setCleanSession(cleanSession);
    conOpt.setConnectionTimeout(timeout);
    conOpt.setKeepAliveInterval(keepalive);
    if (!username.equals(ActivityConstants.empty)) {
      conOpt.setUserName(username);
    }
    if (!password.equals(ActivityConstants.empty)) {
      conOpt.setPassword(password.toCharArray());
    }

    final ActionListener callback = new ActionListener(this,
        ActionListener.Action.CONNECT, clientHandle, actionArgs);

    boolean doConnect = true;

    if ((!message.equals(ActivityConstants.empty))
        || (!topic.equals(ActivityConstants.empty))) {
      // need to make a message since last will is set
      try {
        conOpt.setWill(topic, message.getBytes(), qos.intValue(),
            retained.booleanValue());
      }
      catch (Exception e) {
    	Log.e(this.getClass().getCanonicalName(), "Exception Occured", e);
        doConnect = false;
        callback.onFailure(null, e);
      }
    }
    client.setCallback(new MqttCallbackHandler(this, clientHandle));
    connection.addConnectionOptions(conOpt);
    Connections.getInstance(this).addConnection(connection);
    if (doConnect) {
      try {
        client.connect(conOpt, null, callback);
      }
      catch (MqttException e) {
        Log.e(this.getClass().getCanonicalName(),
            "MqttException Occured", e);
      }
    }

  }
}

