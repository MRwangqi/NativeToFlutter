import 'package:flutter/material.dart';

import 'package:flutter/material.dart';
import 'package:flutter/services.dart';
import 'package:flutter_lib/HomePage.dart';
import 'package:hello/hello.dart';

void main() => runApp(MyApp(Colors.blue));

@pragma('vm:entry-point')
void home() => runApp(HomePage(Colors.green));

final Map<String, WidgetBuilder> routerMap = {
  "home": (context) => HomePage(Colors.green),
};


class MyApp extends StatelessWidget {
  MyApp(this.color);

  final Color color;

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      title: 'Flutter Demo',
      routes: routerMap,
      theme: ThemeData(
        primarySwatch: this.color,
      ),
      home: MyHomePage(title: 'Flutter Demo Home Page'),
    );
  }
}

class MyHomePage extends StatefulWidget {
  MyHomePage({Key key, this.title}) : super(key: key);
  final String title;

  @override
  _MyHomePageState createState() => _MyHomePageState();
}

class _MyHomePageState extends State<MyHomePage> {
  int _counter = 0;
  MethodChannel _channel;

  String _platformVersion;

  // Platform messages are asynchronous, so we initialize in an async method.
  Future<void> initPlatformState() async {
    String platformVersion;
    // Platform messages may fail, so we use a try/catch PlatformException.
    // We also handle the message potentially returning null.
    try {
      platformVersion =
          await Hello.platformVersion ?? 'Unknown platform version';
    } on PlatformException {
      platformVersion = 'Failed to get platform version.';
    }

    // If the widget was removed from the tree while the asynchronous platform
    // message was in flight, we want to discard the reply rather than calling
    // setState to update our non-existent appearance.
    if (!mounted) return;

    setState(() {
      _platformVersion = platformVersion;
    });
  }


  @override
  void initState() {
    super.initState();
    _channel = MethodChannel('multiple-flutters');
    _channel.setMethodCallHandler((MethodCall call) async {
      if (call.method == "setCount") {
        // A notification that the host platform's data model has been updated.
        setState(() {
          _counter = call.arguments as int;
        });
      } else {
        throw Exception('not implemented ${call.method}');
      }
    });

    initPlatformState();
  }

  void _incrementCounter() {
    // Mutations to the data model are forwarded to the host platform.
    _channel.invokeMethod("incrementCount", _counter);
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: Text(widget.title),
      ),
      body: Center(
        child: Column(
          mainAxisAlignment: MainAxisAlignment.center,
          children: <Widget>[
            Text(
              'You have pushed the button this many times:' + _platformVersion,
            ),
            Text(
              '$_counter',
              style: Theme.of(context).textTheme.headline4,
            ),
            FloatingActionButton(
              onPressed: _incrementCounter,
              child: Text('Add'),
            ),
            FloatingActionButton(
              onPressed: () {
                _channel.invokeMethod("next", _counter);
              },
              child: Text('Next'),
            ),
          ],
        ),
      ),
    );
  }
}
