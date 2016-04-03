var kafka = require('kafka-node'),
    HighLevelProducer = kafka.HighLevelProducer,
    client = new kafka.Client('127.0.0.1:2181'),
    producer = new HighLevelProducer(client),
    payloads = [
        { topic: 'test', messages: 'hi' }
    ];

producer.on('ready', function () {
    producer.send(payloads, function (err, data) {
        console.log(data);
    });
});




var kafka = require('kafka-node'),
    HighLevelProducer = kafka.HighLevelProducer,
    KeyedMessage = kafka.KeyedMessage,
    client = new kafka.Client('172.17.0.2:2181'),
    producer = new HighLevelProducer(client),
    km = new KeyedMessage('key', 'message'),
    payloads = [
        { topic: 'test', messages: 'hi', partition: 1 }
    ];


producer.on('ready', function () {
    producer.send(payloads, function (err, data) {
        console.log(data);
                console.log(err);
    });
});

producer.on('error', function (err) {})
